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
package org.nabucco.framework.content.impl.service.produce;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignment;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintFactory;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.component.ContentComponentLocator;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryAssignmentPrototypeRq;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;

/**
 * ProduceContentEntryServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ProduceContentEntryAssignmentServiceHandlerImpl extends ProduceContentEntryAssignmentServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryAssignmentMsg produceContentEntryAssignment(ContentEntryAssignmentPrototypeRq msg)
            throws ProduceException {
        try {
            ContentEntryAssignmentMsg rs = new ContentEntryAssignmentMsg();
            ContentEntryAssignmentElement assignment = new ContentEntryAssignmentElement();

            ContentEntryElement contentEntry = (ContentEntryElement) msg.getContentEntry();
            if (contentEntry != null) {
                assignment.setEntry(contentEntry);
            } else {
                ContentEntryPrototypeRq requestMessage = new ContentEntryPrototypeRq();
                ContentEntryType contentEntryType = msg.getContentEntryType();

                if (contentEntryType == null) {
                    throw new ProduceException("Cannot create content assignment with type 'null'.");
                }
                requestMessage.setType(contentEntryType);

                ServiceRequest<ContentEntryPrototypeRq> rq = new ServiceRequest<ContentEntryPrototypeRq>(
                        super.getContext());
                rq.setRequestMessage(requestMessage);
                ServiceResponse<ContentEntryMsg> produceContentEntry = ContentComponentLocator.getInstance()
                        .getComponent().getProduceContent().produceContentEntry(rq);
                ContentEntryElement entry = produceContentEntry.getResponseMessage().getContentEntry();

                assignment.setEntry(entry);
            }

            ContentEntryAssignmentType type = msg.getType();
            assignment.setType(type);
            assignment.setDatatypeState(DatatypeState.INITIALIZED);

            if (type.equals(ContentEntryAssignmentType.TEXT)) {
                Constraint constraint = ConstraintFactory.getInstance().createMultiplicityConstraint(1, 1);
                NabuccoPropertyDescriptor descriptor = ContentEntryAssignment
                        .getPropertyDescriptor(ContentEntryAssignment.TEXTCONTENT);
                assignment.addConstraint(descriptor, constraint);
            }

            rs.setContentEntryAssignment(assignment);

            return rs;
        } catch (ServiceException e) {
            throw new ProduceException("Cannot create content assignment", e);
        } catch (ConnectionException e) {
            throw new ProduceException("Cannot create content assignment. Connection problem.", e);
        }
    }
}
