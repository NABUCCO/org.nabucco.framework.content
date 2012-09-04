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
package org.nabucco.framework.content.impl.service.search;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.content.facade.message.ContentEntryListMsg;
import org.nabucco.framework.content.facade.message.search.ContentEntrySearchRq;

/**
 * SearchContentEntriesServiceHandler<p/>Search service of the content component<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-06-15
 */
public abstract class SearchContentEntriesServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.content.impl.service.search.SearchContentEntriesServiceHandler";

    /** Constructs a new SearchContentEntriesServiceHandler instance. */
    public SearchContentEntriesServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ContentEntrySearchRq>.
     * @return the ServiceResponse<ContentEntryListMsg>.
     * @throws SearchException
     */
    protected ServiceResponse<ContentEntryListMsg> invoke(ServiceRequest<ContentEntrySearchRq> rq)
            throws SearchException {
        ServiceResponse<ContentEntryListMsg> rs;
        ContentEntryListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.searchContentEntries(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ContentEntryListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException("Error during service invocation.", e);
        }
    }

    /**
     * Search Content Entries.
     *
     * @param msg the ContentEntrySearchRq.
     * @return the ContentEntryListMsg.
     * @throws SearchException
     */
    protected abstract ContentEntryListMsg searchContentEntries(ContentEntrySearchRq msg) throws SearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
