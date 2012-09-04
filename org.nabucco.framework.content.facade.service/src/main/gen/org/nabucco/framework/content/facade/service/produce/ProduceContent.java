/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.content.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryAssignmentPrototypeRq;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;

/**
 * ProduceContent<p/>Service to produce content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public interface ProduceContent extends Service {

    /**
     * Produce a new Content Entry.
     *
     * @param rq the ServiceRequest<ContentEntryPrototypeRq>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws ProduceException
     */
    ServiceResponse<ContentEntryMsg> produceContentEntry(ServiceRequest<ContentEntryPrototypeRq> rq)
            throws ProduceException;

    /**
     * Produce a new Content Entry Assignment.
     *
     * @param rq the ServiceRequest<ContentEntryAssignmentPrototypeRq>.
     * @return the ServiceResponse<ContentEntryAssignmentMsg>.
     * @throws ProduceException
     */
    ServiceResponse<ContentEntryAssignmentMsg> produceContentEntryAssignment(
            ServiceRequest<ContentEntryAssignmentPrototypeRq> rq) throws ProduceException;
}
