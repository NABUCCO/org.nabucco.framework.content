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
package org.nabucco.framework.content.ui.web.action.content;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.control.FileControl;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;
import org.nabucco.framework.content.ui.web.communication.resolve.ResolveContentDelegate;

/**
 * UploadContentEntryElementActionHandler
 * 
 * Moves the uploaded file from temp folder to the correct place
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class UploadContentEntryElementActionHandler extends WebActionHandler {

    private static final String ENTRY_PROPERTY = "entry";

    public final static String ID = "Content.UploadContentEntryElement";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult retVal = new WebActionResult();
        if (parameter.getElement() instanceof FileControl == false) {
            return retVal;
        }

        String url = parameter.getJsonRequest().getValue();
        if (url == null || url.isEmpty()) {
            return new WebActionResult();
        }

        ContentEntryElement source = this.searchContentByPath(parameter.getSession(), url);
        source = this.resolveContentData(parameter.getSession(), source);

        if (source == null) {
            throw new ClientException("Cannot find content entry with url:" + url + ". Cannot Upload attachment");
        }

        FileControl element = (FileControl) parameter.getElement();
        NabuccoProperty property = element.getModel().getProperty();
        ContentEntryElement target = (ContentEntryElement) property.getInstance();
        switch (target.getType()) {
        case INTERNAL_DATA: {
            InternalData targetInstance = (InternalData) target;
            InternalData sourceInstance = (InternalData) source;

            targetInstance.setName(sourceInstance.getName());
            ContentData sourceData = sourceInstance.getData();
            if (sourceData != null && sourceData.getData() != null) {
                ContentData newData = new ContentData();
                newData.setDatatypeState(DatatypeState.INITIALIZED);
                newData.setData(sourceData.getData().getValue());
                targetInstance.setData(newData);
            } else {
                throw new IllegalArgumentException("Cannot upload data. The uploaded file has data 'null'");
            }

            break;
        }
        case EXTERNAL_DATA: {
            ExternalData targetInstance = (ExternalData) target;
            InternalData sourceInstance = (InternalData) source;

            targetInstance.setName(sourceInstance.getName());
            targetInstance.setData(sourceInstance.getData().getData());

            break;
        }
        case FOLDER: {
            throw new IllegalArgumentException("Cannot upload file. The type is 'folder'");
        }
        }

        if (target.getDatatypeState() == DatatypeState.PERSISTENT) {
            target.setDatatypeState(DatatypeState.MODIFIED);
        }
        NabuccoProperty uploadedEntryProperty = property.getParent().getProperty(ENTRY_PROPERTY).createProperty(target);
        property.getParent().setProperty(uploadedEntryProperty);

        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem editor = NabuccoServletUtil.getEditor(editorId);
        editor.getModel().refresh();

        retVal.addItem(new RefreshItem(WebElementType.EDITOR));
        return retVal;
    }

    /**
     * Search for the content with given path
     * 
     * @param session
     *            the session
     * @param path
     *            th path to search for
     * @return content entry or null if not found
     * 
     * @throws ClientException
     * @throws ResolveException
     */
    private ContentEntryElement searchContentByPath(NabuccoWebSession session, String path) throws ClientException {
        try {
            ResolveContentDelegate resolveContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getResolveContent();
            ContentEntryPathMsg message = new ContentEntryPathMsg();
            message.setPath(new ContentEntryPath(path));
            ContentEntryMsg rs;
            rs = resolveContent.resolveContentEntryByPath(message, session);
            ContentEntryElement foundEntry = rs.getContentEntry();

            return foundEntry;
        } catch (ResolveException e) {
            throw new ClientException("Cannot resolve path", e);
        }
    }

    /**
     * Search for the content with given path
     * 
     * @param session
     *            the session
     * @param path
     *            th path to search for
     * @return content entry or null if not found
     * 
     * @throws ClientException
     * @throws ResolveException
     */
    private ContentEntryElement resolveContentData(NabuccoWebSession session, ContentEntry entry)
            throws ClientException {
        try {
            ResolveContentDelegate resolveContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getResolveContent();
            ContentEntryMsg message = new ContentEntryMsg();
            message.setContentEntry((ContentEntryElement) entry);
            ContentEntryMsg rs;
            rs = resolveContent.resolveContentEntryData(message, session);
            ContentEntryElement foundEntry = rs.getContentEntry();

            return foundEntry;
        } catch (ResolveException e) {
            throw new ClientException("Cannot resolve path", e);
        }
    }

}
