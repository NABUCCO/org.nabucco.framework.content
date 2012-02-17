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
package org.nabucco.framework.content.impl.service.maintain;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;

/**
 * MaintainContentEntryByPathServiceHandler<p/>Service to provide maintain operations for Content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public abstract class MaintainContentEntryByPathServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.content.impl.service.maintain.MaintainContentEntryByPathServiceHandler";

    /** Constructs a new MaintainContentEntryByPathServiceHandler instance. */
    public MaintainContentEntryByPathServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ContentEntryMaintainPathMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws MaintainException
     */
    protected ServiceResponse<ContentEntryMsg> invoke(ServiceRequest<ContentEntryMaintainPathMsg> rq)
            throws MaintainException {
        ServiceResponse<ContentEntryMsg> rs;
        ContentEntryMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.maintainContentEntryByPath(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ContentEntryMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (MaintainException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            MaintainException wrappedException = new MaintainException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new MaintainException("Error during service invocation.", e);
        }
    }

    /**
     * Maintain a single content entry to the given path
     *
     * @param msg the ContentEntryMaintainPathMsg.
     * @return the ContentEntryMsg.
     * @throws MaintainException
     */
    protected abstract ContentEntryMsg maintainContentEntryByPath(ContentEntryMaintainPathMsg msg)
            throws MaintainException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
