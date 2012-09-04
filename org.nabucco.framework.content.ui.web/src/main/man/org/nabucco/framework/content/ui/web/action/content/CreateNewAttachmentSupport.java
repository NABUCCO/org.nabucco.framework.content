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

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.ui.web.action.handler.OpenEditorActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryAssignmentPrototypeRq;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;
import org.nabucco.framework.content.ui.web.communication.produce.ProduceContentDelegate;

/**
 * CreateNewTextAttachmentActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class CreateNewAttachmentSupport extends OpenEditorActionHandler<ContentEntryAssignmentElement> {

    @Override
    protected ContentEntryAssignmentElement loadDatatype(Long id, WebActionParameter parameter) throws ClientException {
        ContentEntryAssignmentElement newContentAssignment = this.produceContentEntryAssignment(parameter);

        EditorItem sourceEditor = this.getSourceEditor(parameter);

        newContentAssignment.setOwner(sourceEditor.getInstanceId());
        newContentAssignment.setElementIdentifier(NabuccoSystem.createUUID());

        return newContentAssignment;
    }

    /**
     * Produces a new content entry instance
     * 
     * @param parameter
     *            parameter instance to use
     * 
     * @return new content entry instance
     */
    private ContentEntryAssignmentElement produceContentEntryAssignment(WebActionParameter parameter)
            throws ClientException {
        try {
            ContentEntryAssignmentPrototypeRq message = new ContentEntryAssignmentPrototypeRq();
            message.setType(this.getAssignmentType());
            message.setContentEntryType(ContentEntryType.INTERNAL_DATA);

            ProduceContentDelegate produceService = ContentComponentServiceDelegateFactory.getInstance()
                    .getProduceContent();
            ContentEntryAssignmentMsg rs = produceService
                    .produceContentEntryAssignment(message, parameter.getSession());
            ContentEntryAssignmentElement contentEntry = rs.getContentEntryAssignment();

            return contentEntry;
        } catch (ProduceException ex) {
            throw new ActionException("Error producing new content entry instance.", ex);
        }
    }

    /**
     * Returns the type of the assignemt to be created
     * 
     * @return type of assignment
     */
    protected abstract ContentEntryAssignmentType getAssignmentType();
}
