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
package org.nabucco.framework.content.impl.service.produce;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryAssignmentPrototypeRq;

/**
 * ProduceContentEntryAssignmentServiceHandler<p/>Service to produce content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public abstract class ProduceContentEntryAssignmentServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.content.impl.service.produce.ProduceContentEntryAssignmentServiceHandler";

    /** Constructs a new ProduceContentEntryAssignmentServiceHandler instance. */
    public ProduceContentEntryAssignmentServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ContentEntryAssignmentPrototypeRq>.
     * @return the ServiceResponse<ContentEntryAssignmentMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<ContentEntryAssignmentMsg> invoke(ServiceRequest<ContentEntryAssignmentPrototypeRq> rq)
            throws ProduceException {
        ServiceResponse<ContentEntryAssignmentMsg> rs;
        ContentEntryAssignmentMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceContentEntryAssignment(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ContentEntryAssignmentMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ProduceException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ProduceException wrappedException = new ProduceException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ProduceException("Error during service invocation.", e);
        }
    }

    /**
     * Produce a new Content Entry Assignment.
     *
     * @param msg the ContentEntryAssignmentPrototypeRq.
     * @return the ContentEntryAssignmentMsg.
     * @throws ProduceException
     */
    protected abstract ContentEntryAssignmentMsg produceContentEntryAssignment(ContentEntryAssignmentPrototypeRq msg)
            throws ProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
