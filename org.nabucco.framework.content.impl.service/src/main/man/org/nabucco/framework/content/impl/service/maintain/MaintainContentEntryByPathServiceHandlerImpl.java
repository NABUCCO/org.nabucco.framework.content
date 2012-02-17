/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.content.impl.service.maintain;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.component.ContentComponentLocator;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentFolder;
import org.nabucco.framework.content.facade.datatype.ContentMaster;
import org.nabucco.framework.content.facade.datatype.ContentRelation;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.content.impl.service.resolve.ContentPathResolver;

/**
 * Maintains the given content entry to the given path.
 * 
 * Creates folders if necessary and persist all of them
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MaintainContentEntryByPathServiceHandlerImpl extends MaintainContentEntryByPathServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String PATH_SEPARATOR = "/";

    @Override
    protected ContentEntryMsg maintainContentEntryByPath(ContentEntryMaintainPathMsg rq) throws MaintainException {
        ContentEntryPath path = rq.getPath();
        if (rq.getEntry() == null) {
            throw new IllegalArgumentException("Cannot maintain content. the Content entry is 'null'");
        }

        if (path == null) {
            throw new IllegalArgumentException("Cannot maintain content. the Path is 'null'");
        }

        try {
            // Try to find content if possible
            InternalData sourceEntry = (InternalData) rq.getEntry();
            InternalData targetItem = (InternalData) resolveContentByPath(path.getValue());

            if (targetItem == null) {
                String fullPath = path.getValue();
                String folderPath = fullPath.substring(0, fullPath.lastIndexOf(PATH_SEPARATOR));
                String filename = fullPath.substring(fullPath.lastIndexOf(PATH_SEPARATOR) + 1, fullPath.length());

                // If the item is new, than clone it to a new
                targetItem = (InternalData) produceContentElement(ContentEntryType.INTERNAL_DATA);

                // Copy data
                targetItem.setName(sourceEntry.getName());
                targetItem.setFileExtension(sourceEntry.getFileExtension());
                targetItem.setOwner(sourceEntry.getOwner());
                targetItem.setModificationTime(NabuccoSystem.getCurrentTimestamp());
                targetItem.setCreationTime(NabuccoSystem.getCurrentTimestamp());
                targetItem.setName(filename);
                targetItem.getMaster().setName(filename);

                targetItem = copyContentData(sourceEntry, targetItem);
                copyMasterData(sourceEntry, targetItem);
                // Maintain target
                targetItem = (InternalData) this.maintainContentEntry(targetItem);
                targetItem.getContentRelations().size();

                // Place target in the correct folder
                ContentFolder folder = resolveFolderStructure(folderPath);
                ContentRelation contentRelation = new ContentRelation();
                contentRelation.setTarget(targetItem);
                contentRelation.setDatatypeState(DatatypeState.INITIALIZED);
                folder.getContentRelations().add(contentRelation);
                folder.setDatatypeState(DatatypeState.MODIFIED);

                // Maintain folder
                this.maintainContentEntry(folder);

            } else {
                // If the item already exist, then change data and persist,
                // no folder resolving is needed
                targetItem.setModificationTime(NabuccoSystem.getCurrentTimestamp());
                targetItem.setDatatypeState(DatatypeState.MODIFIED);

                targetItem = copyContentData(sourceEntry, targetItem);
                targetItem = (InternalData) this.maintainContentEntry(targetItem);
                targetItem.getContentRelations().size();
            }

            // Build answer
            ContentEntryMsg rs = new ContentEntryMsg();
            rs.setContentEntry(targetItem);

            return rs;
        } catch (Exception e) {
            throw new MaintainException("Error maintaining content entry with id '" + rq.getEntry().getId() + "'.", e);
        }
    }

    /**
     * Copy data from source to target
     * 
     * @param sourceEntry
     *            source
     * @param targetEntry
     *            target
     * @return copied target item
     * @throws PersistenceException
     *             if cannot load data
     */
    private InternalData copyContentData(InternalData sourceEntry, InternalData targetEntry)
            throws PersistenceException {
        try {
            sourceEntry = resolveEntryData(sourceEntry);

            // Copy content of the source item to target item
            if (sourceEntry.getData() != null) {
                Data data = sourceEntry.getData().getData();

                if (targetEntry.getData() == null) {
                    ContentData targetData = new ContentData();
                    targetData.setDatatypeState(DatatypeState.INITIALIZED);
                    targetData.setData(data);
                    targetEntry.setData(targetData);
                } else {
                    targetEntry = resolveEntryData(targetEntry);
                    ContentData targetData = targetEntry.getData();
                    targetData.setDatatypeState(DatatypeState.MODIFIED);
                    targetData.setData(data);
                    targetEntry.setData(targetData);
                }
            }

            return targetEntry;

        } catch (ServiceException e) {
            throw new PersistenceException("Cannot maintain item", e);
        } catch (ConnectionException e) {
            throw new PersistenceException("Cannot create connection", e);
        }
    }

   

    /**
     * Resolve entry data
     * 
     * @param sourceEntry
     *            entry to be resolved
     * @return resolved entry
     * @throws ServiceException
     * @throws ConnectionException
     * @throws ResolveException
     */
    private InternalData resolveEntryData(InternalData sourceEntry) throws ServiceException, ConnectionException,
            ResolveException {
        // Load data if any
        ResolveContent resolveContent = ContentComponentLocator.getInstance().getComponent().getResolveContent();

        ServiceRequest<ContentEntryMsg> rq = new ServiceRequest<ContentEntryMsg>(super.getContext());
        ContentEntryMsg requestMessage = new ContentEntryMsg();
        requestMessage.setContentEntry(sourceEntry);
        rq.setRequestMessage(requestMessage);
        ServiceResponse<ContentEntryMsg> resolveContentEntryData = resolveContent.resolveContentEntryData(rq);
        ContentEntryElement contentEntry = resolveContentEntryData.getResponseMessage().getContentEntry();
        sourceEntry = (InternalData) contentEntry;

        sourceEntry.getContentRelations().size();

        return sourceEntry;
    }

    /**
     * Copy data from source to target
     * 
     * @param sourceEntry
     *            source
     * @param targetItem
     *            target
     */
    private void copyMasterData(InternalData sourceEntry, InternalData targetItem) {
        ContentMaster sourceMaster = sourceEntry.getMaster();
        ContentMaster targetMaster = targetItem.getMaster();
        if (sourceMaster == null)
            return;

        targetMaster.setDatatypeState(DatatypeState.INITIALIZED);
        targetMaster.setDescription(sourceMaster.getDescription());
        targetMaster.setOwner(sourceMaster.getOwner());

    }

    /**
     * Create the element tree recursively
     * 
     * @param session
     *            session
     * @param path
     *            the path like org/nabucco/person/image, org/nabucco/person, org/nabucco, org
     * @return content data of test png
     * @throws ResolveException
     *             If cannot resolve path
     * @throws PersistenceException
     *             if cannot maintain folder
     * @throws ProduceException
     *             if cannot create folder
     */
    private ContentFolder resolveFolderStructure(String path) throws ResolveException, PersistenceException,
            ProduceException {
        ContentFolder folder = (ContentFolder) this.resolveContentByPath(path);

        if (folder != null) {
            return folder;
        }

        ContentFolder parentFolder = null;
        if (path.contains(PATH_SEPARATOR)) {
            // Separate path for recursively call
            // org/nabucco/profile

            // FolderPath = org/nabucco/
            String folderPath = path.substring(0, path.lastIndexOf(PATH_SEPARATOR));
            // Path = profile
            path = path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1, path.length());

            parentFolder = this.resolveFolderStructure(folderPath);
            if (parentFolder.getDatatypeState().equals(DatatypeState.PERSISTENT)) {
                parentFolder.setDatatypeState(DatatypeState.MODIFIED);
            }
        }

        folder = (ContentFolder) produceContentElement(ContentEntryType.FOLDER);
        folder.setName(path);
        folder.getMaster().setName(path);

        folder = (ContentFolder) maintainContentEntry(folder);

        // Create connection from parent if any
        if (parentFolder != null) {
            ContentRelation relation = new ContentRelation();
            relation.setDatatypeState(DatatypeState.INITIALIZED);
            relation.setTarget(folder);
            parentFolder.getContentRelations().add(relation);
            parentFolder.setDatatypeState(DatatypeState.MODIFIED);
            parentFolder = (ContentFolder) maintainContentEntry(parentFolder);
        }

        return folder;

    }

    /**
     * Resolves content by the path
     * 
     * @param path
     *            the path to resolve
     * @return resolved content
     * @throws ResolveException
     *             if cannot resolve
     */
    private ContentEntryElement resolveContentByPath(String path) throws ResolveException {
        ContentPathResolver resolver = new ContentPathResolver(super.getPersistenceManager());

        ContentEntryElement contentEntry = resolver.resolve(path);
        if (contentEntry != null) {
            contentEntry.getContentRelations().size();
        }

        return contentEntry;
        
        
        
    }

    /**
     * Maintain the content entry to the database.
     * 
     * @param contentEntry
     *            the content entry to persist
     * 
     * @return the persisted entry
     * 
     * @throws PersistenceException
     *             when the entry cannot be persisted
     */
    private ContentEntryElement maintainContentEntry(ContentEntryElement contentEntry) throws PersistenceException {

        try {
            MaintainContent maintainContent;
            maintainContent = ContentComponentLocator.getInstance().getComponent().getMaintainContent();

            ServiceRequest<ContentEntryMsg> rq = new ServiceRequest<ContentEntryMsg>(super.getContext());
            ContentEntryMsg requestMessage = new ContentEntryMsg();
            requestMessage.setContentEntry(contentEntry);
            rq.setRequestMessage(requestMessage);
            ServiceResponse<ContentEntryMsg> maintainContentEntry = maintainContent.maintainContentEntry(rq);

            return maintainContentEntry.getResponseMessage().getContentEntry();
        } catch (ServiceException e) {
            throw new PersistenceException("Cannot maintain item", e);
        } catch (ConnectionException e) {
            throw new PersistenceException("Cannot create connection", e);
        }
    }

    /**
     * Produces a new content element
     * 
     * @param type
     *            the type of element to produce
     * @param session
     *            the session
     * @return produced element
     * @throws ClientException
     *             if cannot produce
     */
    private ContentEntryElement produceContentElement(ContentEntryType type) throws ProduceException {
        try {
            ProduceContent produceContent;
            produceContent = ContentComponentLocator.getInstance().getComponent().getProduceContent();

            ServiceRequest<ContentEntryPrototypeRq> message = new ServiceRequest<ContentEntryPrototypeRq>(
                    super.getContext());

            ContentEntryPrototypeRq requestMessage = new ContentEntryPrototypeRq();
            requestMessage.setType(type);

            message.setRequestMessage(requestMessage);

            ServiceResponse<ContentEntryMsg> produceContentEntry = produceContent.produceContentEntry(message);

            ContentEntryElement contentEntry = produceContentEntry.getResponseMessage().getContentEntry();

            return contentEntry;
        } catch (ServiceException e) {
            throw new ProduceException("Cannot produce item", e);
        } catch (ConnectionException e) {
            throw new ProduceException("Cannot locate conntection", e);
        }
    }
}
