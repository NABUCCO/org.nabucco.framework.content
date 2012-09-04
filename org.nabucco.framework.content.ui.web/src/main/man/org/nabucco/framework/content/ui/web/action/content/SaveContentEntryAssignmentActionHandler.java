/*
R * Copyright 2012 PRODYNA AG
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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.ui.web.action.handler.SaveActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMaintainRq;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.ui.web.communication.ContentComponentServiceDelegateFactory;

/**
 * CreateNewTextAttachmentActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SaveContentEntryAssignmentActionHandler extends SaveActionHandler<ContentEntryAssignmentElement> {

    @Override
    protected ContentEntryAssignmentElement saveDatatype(ContentEntryAssignmentElement assignment, EditorItem editor,
            WebActionParameter parameter) throws ClientException {
        try {
            if (assignment.getEntry() == null) {
                throw new ClientException("Cannot save content assignment without uploaded entry");
            }

            // Set master name be identical to the filename
            ContentEntryElement entry = assignment.getEntry();
            entry.getMaster().setName(entry.getName());

            // Save Assignment (incl. changes as name etc)
            ContentEntryAssignmentMaintainRq message = new ContentEntryAssignmentMaintainRq();
            message.setContentEntryAssignment(assignment);
            message.setType(assignment.getType());

            ContentEntryAssignmentMsg rs;
            rs = ContentComponentServiceDelegateFactory.getInstance().getMaintainContent()
                    .maintainContentEntryAssignment(message, parameter.getSession());

            ContentEntryAssignmentElement contentEntryAssignment = rs.getContentEntryAssignment();

            return contentEntryAssignment;

        } catch (MaintainException e) {
            throw new ClientException("Cannot maintain content entry assignment with id '" + assignment.getId() + "'",
                    e);
        }
    }

    @Override
    protected void afterExecution(ContentEntryAssignmentElement datatype, EditorItem editor,
            WebActionParameter parameter) throws ClientException {
        if (editor.getSource() != null) {
            this.addToSource(datatype, editor);
        }
    }

    @Override
    protected WebActionResult getCustomResultActions(ContentEntryAssignmentElement datatype, EditorItem editor,
            WebActionParameter parameter) {

        WebActionResult result = new WebActionResult();
        //
        // if (datatype.getType() == ContentEntryAssignmentType.FILE) {
        // result.addItem(new RefreshItem(WebElementType.EDITOR));
        // }

        return result;
    }

    /**
     * Adds to source
     * 
     * @param assignment
     * @param sourceElement
     * @return
     * @throws ClientException
     */
    private WebActionResult addToSource(ContentEntryAssignmentElement assignment, EditorItem editor)
            throws ClientException {

        WebActionResult result = new WebActionResult();
        WebElement sourceElement = editor.getSourceWebElement();
        WorkItem sourceWorkingItem = editor.getSource();

        if (assignment == null) {
            return result;
        }

        switch (sourceElement.getType()) {

        case EDITOR_RELATION_TAB: {
            RelationTab relationTab = (RelationTab) sourceElement;
            String property = relationTab.getProperty();

            if (sourceWorkingItem.getType() != WebElementType.EDITOR) {
                throw new ClientException(
                        "Cannot add new element to the given source item because the type of the source item is not supported.");
            }

            Datatype sourceDatatype = ((EditorItem) sourceWorkingItem).getModel().getDatatype();
            super.addProperty(assignment, sourceDatatype, property);

            result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA, sourceWorkingItem.getInstanceId()));

            break;
        }

        default: {
            throw new ClientException("Not expected type of source for the picker control. '");
        }

        }

        return result;
    }

}
