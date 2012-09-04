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
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;

/**
 * CreateNewTextAttachmentActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CreateNewTextAttachmentAssignmentActionHandler extends CreateNewAttachmentSupport {

    public final static String ID = "Content.CreateNewTextAttachmentAssignment";

    private final String EDITOR_ID = "TextAttachmentAssignmentEditor";

    @Override
    protected String getEditorId(WebActionParameter parameter, ContentEntryAssignmentElement assignment)
            throws ClientException {
        return EDITOR_ID;
    }

    @Override
    public ContentEntryAssignmentType getAssignmentType() {
        return ContentEntryAssignmentType.TEXT;
    }

}
