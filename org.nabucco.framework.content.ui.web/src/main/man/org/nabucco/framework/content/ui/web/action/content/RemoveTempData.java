/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.content.ui.web.action.content;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentRelation;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;

/**
 * RemoveTempData
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class RemoveTempData extends WebActionHandlerSupport {

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(RemoveTempData.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        ContentEntryElement tempFolder = this.getTempFolder(parameter);

        for (ContentRelation relation : tempFolder.getContentRelations()) {
            ContentEntryElement target = relation.getTarget();
            target.setDatatypeState(DatatypeState.DELETED);
            if (target.getMaster() != null) {
                target.getMaster().setDatatypeState(DatatypeState.DELETED);
            }
            relation.setDatatypeState(DatatypeState.DELETED);

        }
        this.maintainContentEntry(tempFolder, parameter);

        return new WebActionResult();
    }

    /**
     * Maintain content entry element
     * 
     * @param element
     *            element
     * @param parameter
     */
    private void maintainContentEntry(ContentEntryElement element, WebActionParameter parameter) throws ClientException {

        try {
            ContentEntryMsg message = new ContentEntryMsg();
            message.setContentEntry(element);

            ContentComponentServiceDelegateFactory.getInstance().getMaintainContent()
                    .maintainContentEntry(message, parameter.getSession());
        } catch (MaintainException e) {
            throw new ClientException(e);
        }
    }

    /**
     * Search for the folders which are in temp folder
     * 
     * @param parameter
     * @return
     * @throws ClientException
     */
    private ContentEntryElement getTempFolder(WebActionParameter parameter) throws ClientException {
        try {
            ContentEntryPathMsg message = new ContentEntryPathMsg();
            message.setPath(new ContentEntryPath("temp"));

            ContentEntryMsg rs = ContentComponentServiceDelegateFactory.getInstance().getResolveContent()
                    .resolveContentEntryByPath(message, parameter.getSession());

            ContentEntryElement tempFolder = rs.getContentEntry();

            return tempFolder;
        } catch (ResolveException e) {
            throw new ClientException("Cannot resolve data", e);
        }
    }
}
