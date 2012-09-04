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
package org.nabucco.framework.content.ui.web.communication.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.content.facade.message.ContentEntryListMsg;
import org.nabucco.framework.content.facade.message.search.ContentEntrySearchRq;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * SearchContentDelegate<p/>Search service of the content component<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-06-15
 */
public class SearchContentDelegate extends ServiceDelegateSupport {

    private SearchContent service;

    /**
     * Constructs a new SearchContentDelegate instance.
     *
     * @param service the SearchContent.
     */
    public SearchContentDelegate(SearchContent service) {
        super();
        this.service = service;
    }

    /**
     * SearchContentEntries.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the ContentEntrySearchRq.
     * @return the ContentEntryListMsg.
     * @throws SearchException
     */
    public ContentEntryListMsg searchContentEntries(ContentEntrySearchRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<ContentEntrySearchRq> request = new ServiceRequest<ContentEntrySearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ContentEntryListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchContentEntries(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchContent.class, "searchContentEntries", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: SearchContent.searchContentEntries");
    }
}
