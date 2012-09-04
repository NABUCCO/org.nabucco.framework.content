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

import java.io.UnsupportedEncodingException;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;
import org.nabucco.framework.base.facade.datatype.text.TextContent;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.component.ContentComponentLocator;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMaintainRq;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.impl.service.maintain.common.MaintainUtility;

/**
 * MaintainContentEntryAssignmentServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MaintainContentEntryAssignmentServiceHandlerImpl extends MaintainContentEntryAssignmentServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String URL_DELIMITER = "/";

    @Override
    protected ContentEntryAssignmentMsg maintainContentEntryAssignment(ContentEntryAssignmentMaintainRq msg)
            throws MaintainException {
        ContentEntryAssignmentElement assignment = msg.getContentEntryAssignment();
        ContentEntryAssignmentType type = msg.getType();

        try {
            if (assignment.getEntry() != null) {

                if (type.equals(ContentEntryAssignmentType.TEXT)) {
                    this.contevertTextToData(assignment);
                }

                // If content entry is new and must be stored to the path, then do it, else only
                // maintain
                if (assignment.getEntry().getDatatypeState() == DatatypeState.INITIALIZED) {

                    // Produce upload path
                    String uploadFilePath = this.getUploadPath(assignment);

                    // Put element to the correct path if nessecary
                    ContentEntryMaintainPathMsg pathMessage = new ContentEntryMaintainPathMsg();
                    pathMessage.setEntry(assignment.getEntry());
                    pathMessage.setPath(new ContentEntryPath(uploadFilePath));
                    pathMessage.setRemoveSource(new Flag(true));

                    ServiceRequest<ContentEntryMaintainPathMsg> pathRq = new ServiceRequest<ContentEntryMaintainPathMsg>(
                            super.getContext());
                    pathRq.setRequestMessage(pathMessage);

                    ServiceResponse<ContentEntryMsg> rs = ContentComponentLocator.getInstance().getComponent()
                            .getMaintainContent().maintainContentEntryByPath(pathRq);
                    ContentEntryElement contentEntry = rs.getResponseMessage().getContentEntry();

                    assignment.setEntry(contentEntry);
                } else {
                    // Save Content entry
                    MaintainUtility utility = new MaintainUtility(super.getPersistenceManager(), super.getContext());

                    ContentEntryElement contentEntry = utility.maintainContentEntry(assignment.getEntry());
                    assignment.setEntry(contentEntry);

                }

            }

            ContentEntryAssignmentElement persistedassignment = super.getPersistenceManager().persist(assignment);

            // Set transient values of maintained content
            persistedassignment.setUploadFilePath(assignment.getUploadFilePath());
            persistedassignment.setTextContent(assignment.getTextContent());

            persistedassignment.setDatatypeState(DatatypeState.PERSISTENT);

            ContentEntryAssignmentMsg rs = new ContentEntryAssignmentMsg();
            rs.setContentEntryAssignment(persistedassignment);

            return rs;
        } catch (Exception e) {
            throw new MaintainException("Error maintaining content entry assignment with id '"
                    + assignment.getId() + "'.", e);
        }
    }

    /**
     * Converts the text to the data objects
     * 
     * @param assignment
     *            the assignment to convert
     * @throws UnsupportedEncodingException
     * @throws ClientException
     */
    private void contevertTextToData(ContentEntryAssignmentElement assignment) throws UnsupportedEncodingException,
            ClientException {
        ContentEntry entry = assignment.getEntry();
        ContentEntryType entryType = entry.getType();
        byte[] value;
        TextContent textContent = assignment.getTextContent();
        if (textContent == null) {
            textContent = new TextContent("");
        }
        value = textContent.getValue().getBytes(SerializationConstants.CHARSET);

        switch (entryType) {
        case EXTERNAL_DATA: {
            ExternalData data = (ExternalData) assignment.getEntry();
            if (data.getData() == null) {
                data.setData(value);
            } else {
                data.getData().setValue(value);
            }
        }
        case INTERNAL_DATA: {
            InternalData data = (InternalData) assignment.getEntry();
            if (data.getData() == null) {
                ContentData contentData = new ContentData();
                contentData.setData(value);
                contentData.setDatatypeState(DatatypeState.INITIALIZED);

                data.setData(contentData);
            } else {
                data.getData().setData(value);
                data.getData().setDatatypeState(DatatypeState.MODIFIED);
            }
            break;
        }
        case FOLDER: {
            throw new ClientException("Cannot resolve assignment that references a folder.");
        }
        }

        if (assignment.getEntry().getDatatypeState() == DatatypeState.PERSISTENT) {
            assignment.getEntry().setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Returns the upload path for the content entry
     * 
     * @param datatype
     *            the entry assignment
     * @return upload path
     */
    private String getUploadPath(ContentEntryAssignmentElement datatype) {
        StringBuilder builder = new StringBuilder();
        builder.append(datatype.getUploadFilePath().getValue());
        builder.append(datatype.getOwner().getValueAsString());
        builder.append(URL_DELIMITER);
        builder.append(datatype.getElementIdentifier().getValue());
        builder.append(URL_DELIMITER);
        builder.append(datatype.getEntry().getName().getValue());

        return builder.toString();
    }
}
