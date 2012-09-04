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

import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.ui.web.action.handler.OpenEditorActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentResolveRq;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;

/**
 * OpenAttachmentAssignmentEditorActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class OpenContentElememntAssignmentEditorActionHandler extends
        OpenEditorActionHandler<ContentEntryAssignmentElement> {

    public final static String ID = "Content.OpenContentElememntAssignmentEditor";

    private final String EDITOR_ID_TEXT = "TextAttachmentAssignmentEditor";

    private final String EDITOR_ID_FILE = "FileAttachmentAssignmentEditor";

    @Override
    protected String getEditorId(WebActionParameter parameter, ContentEntryAssignmentElement assignment)
            throws ClientException {
        ContentEntryAssignmentType type = assignment.getType();
        switch (type) {
        case FILE: {
            return EDITOR_ID_FILE;
        }
        case TEXT: {
            return EDITOR_ID_TEXT;
        }
        default:
            return EDITOR_ID_FILE;
        }
    }

    @Override
    protected ContentEntryAssignmentElement loadDatatype(Long id, WebActionParameter parameter) throws ClientException {
        try {
            ContentEntryAssignmentElement element = new ContentEntryAssignmentElement();
            element.setId(id);

            ContentEntryAssignmentResolveRq message = new ContentEntryAssignmentResolveRq();
            message.setContentEntryAssignment(element);

            ContentEntryAssignmentMsg rs = ContentComponentServiceDelegateFactory.getInstance().getResolveContent()
                    .resolveContentEntryAssignment(message, parameter.getSession());

            ContentEntryAssignmentElement retVal = rs.getContentEntryAssignment();

            return retVal;
        } catch (ResolveException e) {
            throw new ClientException("Cannot resolve content assignment with id " + id);
        }
    }

}
