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
package org.nabucco.framework.content.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;

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
     * @param session the NabuccoSession.
     * @param message the ContentEntryPrototypeRq.
     * @return the ContentEntryMsg.
     * @throws ProduceException
     */
    public ContentEntryMsg produceContentEntry(ContentEntryPrototypeRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws ProduceException {
        ServiceRequest<ContentEntryPrototypeRq> request = new ServiceRequest<ContentEntryPrototypeRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
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
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ProduceException("Cannot execute service operation: ProduceContent.produceContentEntry");
    }
}
