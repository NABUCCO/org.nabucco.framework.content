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
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogLinkModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;

/**
 * DownloadAttachmentActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DownloadAttachmentActionHandler extends WebActionHandlerSupport {

    private static final String RESOURCE_PREFIX = "./resource/id/";

    private static final String DIALOG_CONTROL_LINK = "link";

    private static final String DIALOG_ID = "DownloadDialog";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        RelationTab attachmentTab = (RelationTab) parameter.getElement();

        String instance = parameter.get(NabuccoServletPathType.INSTANCE);
        if (instance == null || instance.length() == 0) {
            return new WebActionResult();
        }
        long downloadIdentifier = Long.parseLong(instance);
        Datatype relationDatatype = attachmentTab.getTableModel().getDatatypeByObjectId(downloadIdentifier);
        ComponentRelation<?> relation = (ComponentRelation<?>) relationDatatype;
        NabuccoDatatype downloadingDatatype = relation.getTarget();
        if (downloadingDatatype instanceof ContentEntryAssignmentElement == false) {
            throw new IllegalStateException(
                    "Cannot download the attachment that is not of type content entry assignment element.");
        }

        ContentEntryAssignmentElement attachmentData = (ContentEntryAssignmentElement) downloadingDatatype;
        String key = NabuccoServletUtil.getResourceController().addDownloadReferenceResource(
                attachmentData.getEntry().getId(), attachmentData.getEntry().getType());

        String url = RESOURCE_PREFIX + key + "/" + attachmentData.getEntry().getName().getValue();
        Dialog downloadDialog = this.prepareDownloadDialog(url);

        WebActionResult result = new WebActionResult();
        result.addItem(new OpenItem(WebElementType.DIALOG, downloadDialog.getInstanceId()));
        return result;
    }

    /**
     * Prepares download dialog
     * 
     * @param url
     *            the url to be downloaded
     * @return dialog instance
     * @throws ClientException
     *             if cannot prepare dialog
     */
    private Dialog prepareDownloadDialog(String url) throws ClientException {
        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(DIALOG_ID);

        if (dialog == null) {
            throw new ClientException("Cannot create dialog with id '" + DIALOG_ID + "'.");
        }

        DialogGridModelElement control = ((GridDialogModel) dialog.getModel()).getControl(DIALOG_CONTROL_LINK);
        if (control instanceof DialogLinkModel == false) {
            throw new ClientException("The dialog window has no element Link or it has a wrong type");
        }
        DialogLinkModel downloadLinkControl = (DialogLinkModel) control;
        downloadLinkControl.setUrl(url);
        return dialog;
    }
}
