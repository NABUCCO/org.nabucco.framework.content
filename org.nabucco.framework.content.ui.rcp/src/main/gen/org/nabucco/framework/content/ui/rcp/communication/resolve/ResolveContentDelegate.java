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
package org.nabucco.framework.content.ui.rcp.communication.resolve;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;

/**
 * ResolveContentDelegate<p/>Resolves content entries and allows for content loading.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class ResolveContentDelegate extends ServiceDelegateSupport {

    private ResolveContent service;

    /**
     * Constructs a new ResolveContentDelegate instance.
     *
     * @param service the ResolveContent.
     */
    public ResolveContentDelegate(ResolveContent service) {
        super();
        this.service = service;
    }

    /**
     * ResolveContentEntry.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryMsg.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg resolveContentEntry(ContentEntryMsg message, ServiceSubContext... subContexts)
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
                response = service.resolveContentEntry(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveContent.class, "resolveContentEntry", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ResolveContent.resolveContentEntry");
    }

    /**
     * ResolveContentEntryByPath.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryPathMsg.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg resolveContentEntryByPath(ContentEntryPathMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ContentEntryPathMsg> request = new ServiceRequest<ContentEntryPathMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.resolveContentEntryByPath(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveContent.class, "resolveContentEntryByPath", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ResolveContent.resolveContentEntryByPath");
    }

    /**
     * ResolveContentEntryData.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ContentEntryMsg.
     * @return the ContentEntryMsg.
     * @throws ClientException
     */
    public ContentEntryMsg resolveContentEntryData(ContentEntryMsg message, ServiceSubContext... subContexts)
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
                response = service.resolveContentEntryData(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveContent.class, "resolveContentEntryData", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ResolveContent.resolveContentEntryData");
    }
}
