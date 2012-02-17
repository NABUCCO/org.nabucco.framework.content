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
package org.nabucco.framework.content.ui.web;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.ui.web.servlet.resource.ResourceServlet;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;
import org.nabucco.framework.content.ui.web.communication.maintain.MaintainContentDelegate;
import org.nabucco.framework.content.ui.web.communication.produce.ProduceContentDelegate;
import org.nabucco.framework.content.ui.web.communication.resolve.ResolveContentDelegate;

/**
 * ContentResourceServlet
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ContentResourceServlet extends ResourceServlet {

    private static final String EXTENSION_SEPARATOR = ".";

    private static final String PATH_SEPARATOR = "/";

    private static final long serialVersionUID = 1L;

    private static final String TEMP_FOLDER = "temp";

    @Override
    protected byte[] loadData(Long id, ContentEntryType type, NabuccoWebSession session) throws ClientException {
        try {
            ResolveContentDelegate resolveContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getResolveContent();

            ContentEntryElement searchingElement = null;

            // Try to resolve the entry element
            switch (type) {
            case EXTERNAL_DATA: {
                searchingElement = new ExternalData();
                break;
            }
            case INTERNAL_DATA: {
                searchingElement = new InternalData();
                break;
            }
            default: {
                throw new ClientException("Cannot resolve data entry. The type is not supported yet.");
            }
            }

            searchingElement.setId(id);

            ContentEntryMsg rq = new ContentEntryMsg();
            rq.setContentEntry(searchingElement);
            ContentEntryMsg rs = resolveContent.resolveContentEntry(rq, session);
            ContentEntryElement resolvedEntry = rs.getContentEntry();

            if (resolvedEntry == null) {
                return null;
            }

            // If found, resolve data
            rq = new ContentEntryMsg();
            rq.setContentEntry(resolvedEntry);
            rs = resolveContent.resolveContentEntryData(rq, session);

            ContentEntry entry = rs.getContentEntry();
            switch (entry.getType()) {

            case INTERNAL_DATA: {
                InternalData internalData = (InternalData) entry;
                return internalData.getData().getData().getValue();
            }

            case EXTERNAL_DATA: {
                ExternalData externalData = (ExternalData) entry;
                return externalData.getData().getValue();
            }
            default: {
                throw new ClientException("Cannot resolve data entry. The type is not supported yet.");
            }
            }

        } catch (Exception e) {
            throw new ClientException("Error resolving content for element with id '" + id + "'.", e);
        }
    }

    @Override
    protected byte[] loadDataFromUrl(String url, NabuccoWebSession session) throws ClientException {

        try {

            ResolveContentDelegate resolveContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getResolveContent();

            ContentEntryElement entry = null;

            {
                ContentEntryPathMsg rq = new ContentEntryPathMsg();
                rq.setPath(new ContentEntryPath(url));
                ContentEntryMsg rs = resolveContent.resolveContentEntryByPath(rq, session);
                entry = rs.getContentEntry();
            }

            ContentEntryMsg rq = new ContentEntryMsg();
            rq.setContentEntry(entry);
            ContentEntryMsg rs = resolveContent.resolveContentEntryData(rq, session);

            entry = rs.getContentEntry();

            switch (entry.getType()) {

            case INTERNAL_DATA: {
                InternalData internalData = (InternalData) entry;
                return internalData.getData().getData().getValue();
            }

            case EXTERNAL_DATA: {
                ExternalData externalData = (ExternalData) entry;
                return externalData.getData().getValue();
            }

            }

            return null;
        } catch (Exception e) {
            throw new ClientException("Error resolving content for url '" + url + "'.", e);
        }
    }

    @Override
    protected String writeData(String originalFilename, String instanceId, byte[] data, NabuccoWebSession session)
            throws ClientException {

        // Creates a temp file
        if (instanceId == null || instanceId.length() == 0) {
            instanceId = NabuccoSystem.createUUID();
        }
        String filename = instanceId;
        InternalData newContentElement = (InternalData) produceContentElement(ContentEntryType.INTERNAL_DATA, session);

        ContentData contentData = new ContentData();
        contentData.setData(data);
        contentData.setDatatypeState(DatatypeState.INITIALIZED);

        /**
         * If any extension it should be appended to the filename
         */
        if (originalFilename.contains(EXTENSION_SEPARATOR)) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(EXTENSION_SEPARATOR) + 1,
                    originalFilename.length());
            newContentElement.setFileExtension(fileExtension);
            filename = filename.concat(EXTENSION_SEPARATOR);
            filename = filename.concat(fileExtension);
        }

        String path = TEMP_FOLDER + PATH_SEPARATOR + filename;

        newContentElement.getMaster().setName(filename);
        newContentElement.setName(filename);
        newContentElement.setData(contentData);
        newContentElement.setCreationTime(NabuccoSystem.getCurrentTimestamp());
        newContentElement = (InternalData) maintainContentElement(newContentElement, path, session);

        return TEMP_FOLDER + PATH_SEPARATOR + filename;
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
    private ContentEntryElement produceContentElement(ContentEntryType type, NabuccoWebSession session)
            throws ClientException {
        try {
            ProduceContentDelegate produceContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getProduceContent();
            ContentEntryPrototypeRq message = new ContentEntryPrototypeRq();

            message.setType(type);

            ContentEntryMsg produceContentEntry;
            produceContentEntry = produceContent.produceContentEntry(message, session);

            ContentEntryElement contentEntry = produceContentEntry.getContentEntry();

            return contentEntry;
        } catch (ProduceException e) {
            throw new ClientException("Cannot produce content element", e);
        }
    }

    /**
     * Maintains content element
     * 
     * @param entry
     *            the content entry to be maintained
     * @param path
     *            the path to maintain the content
     * @param session
     *            session to use
     * @return maintained element
     * @throws ClientException
     *             if cannot maintain
     */
    private ContentEntryElement maintainContentElement(ContentEntryElement entry, String path, NabuccoWebSession session)
            throws ClientException {
        try {

            MaintainContentDelegate maintainContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getMaintainContent();
            ContentEntryMaintainPathMsg message = new ContentEntryMaintainPathMsg();
            message.setEntry(entry);
            message.setPath(new ContentEntryPath(path));
            ContentEntryMsg maintainContentEntry = maintainContent.maintainContentEntryByPath(message, session);
            ContentEntryElement contentEntry = maintainContentEntry.getContentEntry();
            return contentEntry;
        } catch (MaintainException e) {
            throw new ClientException("Cannot maintaint content element", e);
        }

    }

}
