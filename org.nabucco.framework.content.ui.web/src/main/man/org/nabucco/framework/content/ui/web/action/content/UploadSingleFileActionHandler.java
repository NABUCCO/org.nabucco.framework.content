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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.ui.web.action.handler.work.editor.ControlDataUploadHandler;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;
import org.nabucco.framework.content.ui.web.communication.maintain.MaintainContentDelegate;
import org.nabucco.framework.content.ui.web.communication.resolve.ResolveContentDelegate;

/**
 * UploadPersonProfileActionHandler
 * 
 * Moves the uploaded file from temp folder to the correct place
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class UploadSingleFileActionHandler extends ControlDataUploadHandler<InternalData> {

    public final static String ID = "Content.UploadSingleFile";

    @Override
    protected List<NabuccoDatatype> uploadData(List<String> picList, String uploadPath, NabuccoWebSession session)
            throws ActionException {
        try {
            // Check that the data is single
            if (picList.size() > 1) {
                throw new ActionException(
                        "There is more that one data element that need to be moved to the sigle data element.");
            }

            if (picList.size() == 0) {
                throw new ActionException("No elements for upload.");
            }

            String tempDataPath = picList.get(0);
            if (tempDataPath == null || tempDataPath.length() == 0) {
                throw new ActionException("The temp path is empty. Cannot move data.");
            }

            if (uploadPath == null || uploadPath.length() == 0) {
                throw new ActionException("Cannot upload to the empty upload path.");
            }

            // Search for the path
            InternalData sourceEntry = (InternalData) this.searchContentByPath(session, tempDataPath);

            if (sourceEntry == null) {
                throw new ActionException("Cannot find temp entry on path: '" + tempDataPath + "'.");
            }

            MaintainContentDelegate maintainContent = ContentComponentServiceDelegateFactory.getInstance()
                    .getMaintainContent();
            ContentEntryMaintainPathMsg message = new ContentEntryMaintainPathMsg();
            message.setPath(new ContentEntryPath(uploadPath));
            message.setEntry(sourceEntry);
            ContentEntryMsg maintainContentEntryByPath = maintainContent.maintainContentEntryByPath(message, session);
            ContentEntryElement targetEntry = maintainContentEntryByPath.getContentEntry();


            ArrayList<NabuccoDatatype> retVal = new ArrayList<NabuccoDatatype>();
            retVal.add(targetEntry);
            return retVal;
        } catch (MaintainException e) {
            throw new ActionException("Cannot maintain file", e);
        } catch (ClientException e) {
            throw new ActionException("Cannot upload data", e);
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


}
