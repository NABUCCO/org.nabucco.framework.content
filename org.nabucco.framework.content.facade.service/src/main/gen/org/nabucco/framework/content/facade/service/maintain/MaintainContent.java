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
package org.nabucco.framework.content.facade.service.maintain;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMaintainRq;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;

/**
 * MaintainContent<p/>Service to provide maintain operations for Content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public interface MaintainContent extends Service {

    /**
     * Maintain the given content entry.
     *
     * @param rq the ServiceRequest<ContentEntryAssignmentMaintainRq>.
     * @return the ServiceResponse<ContentEntryAssignmentMsg>.
     * @throws MaintainException
     */
    ServiceResponse<ContentEntryAssignmentMsg> maintainContentEntryAssignment(
            ServiceRequest<ContentEntryAssignmentMaintainRq> rq) throws MaintainException;

    /**
     * Maintain the given content entry.
     *
     * @param rq the ServiceRequest<ContentEntryMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws MaintainException
     */
    ServiceResponse<ContentEntryMsg> maintainContentEntry(ServiceRequest<ContentEntryMsg> rq) throws MaintainException;

    /**
     * Maintain a single content entry to the given path
     *
     * @param rq the ServiceRequest<ContentEntryMaintainPathMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws MaintainException
     */
    ServiceResponse<ContentEntryMsg> maintainContentEntryByPath(ServiceRequest<ContentEntryMaintainPathMsg> rq)
            throws MaintainException;
}
