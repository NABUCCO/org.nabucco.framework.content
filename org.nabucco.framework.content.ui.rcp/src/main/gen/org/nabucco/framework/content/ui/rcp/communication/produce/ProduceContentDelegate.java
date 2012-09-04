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
package org.nabucco.framework.content.ui.rcp.communication.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryAssignmentPrototypeRq;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;

/**
 * ProduceContentDelegate<p/>Service to produce content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class ProduceContentDelegate extends ServiceDelegateSupport {

    private ProduceContent service;

    /**
     * Constructs a new ProduceContentDelegate instance.
     *
     * @param service the ProduceContent.
     */
    public ProduceContentDelegate(ProduceContent service) {
        super();
        this.service = service;
    }

    /**
     * ProduceContentEntry.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryPrototypeRq.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg produceContentEntry(ContentEntryPrototypeRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ContentEntryPrototypeRq> request = new ServiceRequest<ContentEntryPrototypeRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceContentEntry(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceContent.class, "produceContentEntry", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceContent.produceContentEntry");
    }

    /**
     * ProduceContentEntryAssignment.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryAssignmentPrototypeRq.
     * @return the ContentEntryAssignmentMsg.
     * @throws ClientException
     */
    public ContentEntryAssignmentMsg produceContentEntryAssignment(ContentEntryAssignmentPrototypeRq message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ContentEntryAssignmentPrototypeRq> request = new ServiceRequest<ContentEntryAssignmentPrototypeRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryAssignmentMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceContentEntryAssignment(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceContent.class, "produceContentEntryAssignment", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceContent.produceContentEntryAssignment");
    }
}
