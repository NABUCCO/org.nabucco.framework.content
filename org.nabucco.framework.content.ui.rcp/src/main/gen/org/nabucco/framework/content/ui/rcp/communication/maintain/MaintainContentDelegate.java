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
package org.nabucco.framework.content.ui.rcp.communication.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMaintainRq;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;

/**
 * MaintainContentDelegate<p/>Service to provide maintain operations for Content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class MaintainContentDelegate extends ServiceDelegateSupport {

    private MaintainContent service;

    /**
     * Constructs a new MaintainContentDelegate instance.
     *
     * @param service the MaintainContent.
     */
    public MaintainContentDelegate(MaintainContent service) {
        super();
        this.service = service;
    }

    /**
     * MaintainContentEntryAssignment.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryAssignmentMaintainRq.
     * @return the ContentEntryAssignmentMsg.
     * @throws ClientException
     */
    public ContentEntryAssignmentMsg maintainContentEntryAssignment(ContentEntryAssignmentMaintainRq message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ContentEntryAssignmentMaintainRq> request = new ServiceRequest<ContentEntryAssignmentMaintainRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryAssignmentMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainContentEntryAssignment(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainContent.class, "maintainContentEntryAssignment", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainContent.maintainContentEntryAssignment");
    }

    /**
     * MaintainContentEntry.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryMsg.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg maintainContentEntry(ContentEntryMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ContentEntryMsg> request = new ServiceRequest<ContentEntryMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainContentEntry(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainContent.class, "maintainContentEntry", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainContent.maintainContentEntry");
    }

    /**
     * MaintainContentEntryByPath.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryMaintainPathMsg.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg maintainContentEntryByPath(ContentEntryMaintainPathMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ContentEntryMaintainPathMsg> request = new ServiceRequest<ContentEntryMaintainPathMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainContentEntryByPath(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainContent.class, "maintainContentEntryByPath", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainContent.maintainContentEntryByPath");
    }
}
