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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
import org.nabucco.framework.content.impl.service.resolve.ContentPathResolver;
import org.nabucco.framework.content.impl.service.resolve.common.ResolveUtility;

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
            InternalData targetItem = (InternalData) this.resolveContentByPath(path.getValue());

            if (rq.getRemoveSource() != null && rq.getRemoveSource().getValue() == true) {
                this.clearReferences(sourceEntry);
            }

            if (targetItem == null) {
                String fullPath = path.getValue();
                String folderPath = fullPath.substring(0, fullPath.lastIndexOf(PATH_SEPARATOR));
                String filename = fullPath.substring(fullPath.lastIndexOf(PATH_SEPARATOR) + 1, fullPath.length());

                targetItem = sourceEntry;

                // Copy data
                targetItem.setModificationTime(NabuccoSystem.getCurrentTimestamp());
                targetItem.setCreationTime(NabuccoSystem.getCurrentTimestamp());
                targetItem.setName(filename);

                if (targetItem.getMaster() == null) {
                    ContentMaster master = new ContentMaster();
                    master.setOwner(super.getContext().getOwner());
                    master.setTenant(super.getContext().getTenant());
                    master.setDatatypeState(DatatypeState.INITIALIZED);
                    targetItem.setMaster(master);
                }

                targetItem.getMaster().setName(filename);

                // Maintain target
                targetItem = this.maintainContentEntry(targetItem);
                targetItem.getContentRelations().size();

                // Place target in the correct folder
                ContentFolder folder = this.resolveFolderStructure(folderPath);
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

                targetItem = this.copyContentData(sourceEntry, targetItem);
                targetItem = this.maintainContentEntry(targetItem);
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
     * Searches and clears references to the given content entry
     * 
     * @param sourceEntry
     *            the
     * @throws PersistenceException
     */
    private void clearReferences(InternalData sourceEntry) throws PersistenceException {
        if (sourceEntry.getDatatypeState() == DatatypeState.INITIALIZED) {
            return;
        }
        if (sourceEntry.getDatatypeState() == DatatypeState.CONSTRUCTED) {
            return;
        }

        ResolveUtility resUtility = new ResolveUtility(this.getPersistenceManager(), this.getContext());
        List<ContentFolder> relations = resUtility.findRelations(sourceEntry);

        for (ContentFolder folder : relations) {
            for (ContentRelation relation : folder.getContentRelations()) {
                if (relation.getTarget().getId().equals(sourceEntry.getId())) {
                    relation.setDatatypeState(DatatypeState.DELETED);
                }
            }

            this.maintainContentEntry(folder);
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
        ResolveUtility resolveUtil = new ResolveUtility(this.getPersistenceManager(), super.getContext());
        try {
            sourceEntry = resolveUtil.resolveInternalDataContent(sourceEntry);

            // Copy content of the source item to target item
            if (sourceEntry.getData() != null) {
                Data data = sourceEntry.getData().getData();

                if (targetEntry.getData() == null) {
                    ContentData targetData = new ContentData();
                    targetData.setDatatypeState(DatatypeState.INITIALIZED);
                    targetData.setData(data);
                    targetEntry.setData(targetData);
                } else {
                    targetEntry = resolveUtil.resolveInternalDataContent(targetEntry);
                    ContentData targetData = targetEntry.getData();
                    targetData.setDatatypeState(DatatypeState.MODIFIED);
                    targetData.setData(data);
                    targetEntry.setData(targetData);
                }
            }

            return targetEntry;

        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot maintain item", e);
        }
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

        ContentFolder parentfolder = null;
        String currentPath = path;

        // Search for the existing folder
        while (parentfolder == null) {
            parentfolder = (ContentFolder) this.resolveContentByPath(currentPath);

            if (parentfolder != null) {
                break;
            } else {
                if (currentPath.isEmpty()) {
                    break;
                } else {
                    if (currentPath.contains(PATH_SEPARATOR)) {
                        currentPath = currentPath.substring(0, currentPath.lastIndexOf(PATH_SEPARATOR));
                    } else {
                        break;
                    }
                }
            }
        }

        String producePath = null;
        if (parentfolder != null) {
            producePath = path.substring(currentPath.length(), path.length());
        } else {
            producePath = path;
        }

        Queue<String> folderNameList = new LinkedList<String>();
        String[] tokens = producePath.split(PATH_SEPARATOR);
        for (String token : tokens) {
            if (!token.isEmpty()) {
                folderNameList.add(token);
            }
        }

        Queue<ContentFolder> folderLinkList = new LinkedList<ContentFolder>();
        if (parentfolder != null) {
            folderLinkList.add(parentfolder);
        }

        // Produce folders
        while (!folderNameList.isEmpty()) {
            String folderName = null;

            folderName = folderNameList.poll();

            ContentFolder folder = (ContentFolder) this.produceContentElement(ContentEntryType.FOLDER);
            folder.setName(folderName);
            folder.getMaster().setName(folderName);
            folder = this.maintainContentEntry(folder);
            folderLinkList.add(folder);
        }

        ContentFolder retVal = null;

        // Create relations
        while (!folderLinkList.isEmpty()) {

            ContentFolder source = folderLinkList.poll();
            ContentFolder target = folderLinkList.peek();

            if (target == null) {
                retVal = source;
                break;
            }
            ContentRelation relation = new ContentRelation();
            relation.setDatatypeState(DatatypeState.INITIALIZED);
            relation.setTarget(target);
            source.getContentRelations().add(relation);
            source.setDatatypeState(DatatypeState.MODIFIED);
            this.maintainContentEntry(source);
        }

        return retVal;
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

    // /**
    // * Maintain the content entry to the database.
    // *
    // * @param contentEntry
    // * the content entry to persist
    // *
    // * @return the persisted entry
    // *
    // * @throws PersistenceException
    // * when the entry cannot be persisted
    // */
    // private ContentEntryElement maintainContentEntry(ContentEntryElement contentEntry) throws
    // PersistenceException {
    //
    // MaintainUtility utility = new MaintainUtility(super.getPersistenceManager(),
    // super.getContext());
    //
    // contentEntry = utility.maintainContentEntry(contentEntry);
    //
    // return contentEntry;
    // }
    //

    /**
     * Maintain the given content entry to the database.
     * 
     * @param entry
     *            the entry to maintain
     * 
     * @return the maintained entry
     * 
     * @throws Exception
     *             when the maintain fails
     */
    @SuppressWarnings("unchecked")
    private <T extends ContentEntryElement> T maintainContentEntry(T entry) throws PersistenceException {

        try {
            MaintainContent maintainContent;
            maintainContent = ContentComponentLocator.getInstance().getComponent().getMaintainContent();
            ServiceRequest<ContentEntryMsg> rq = new ServiceRequest<ContentEntryMsg>(this.getContext());

            ContentEntryMsg msg = new ContentEntryMsg();
            msg.setContentEntry(entry);
            rq.setRequestMessage(msg);

            ServiceResponse<ContentEntryMsg> rs = maintainContent.maintainContentEntry(rq);

            return (T) rs.getResponseMessage().getContentEntry();
        } catch (ServiceException e) {
            throw new PersistenceException("Cannot maintain", e);
        } catch (ConnectionException e) {
            throw new PersistenceException("Cannot maintain", e);
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
