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
package org.nabucco.framework.content.facade.service.resolve;

import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;

/**
 * ResolveContent<p/>Resolves content entries and allows for content loading.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public interface ResolveContent extends Service {

    /**
     * Resolves a single content entry without content data.
     *
     * @param rq the ServiceRequest<ContentEntryMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws ResolveException
     */
    ServiceResponse<ContentEntryMsg> resolveContentEntry(ServiceRequest<ContentEntryMsg> rq) throws ResolveException;

    /**
     * Resolves a single content entry without content data.
     *
     * @param rq the ServiceRequest<ContentEntryPathMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws ResolveException
     */
    ServiceResponse<ContentEntryMsg> resolveContentEntryByPath(ServiceRequest<ContentEntryPathMsg> rq)
            throws ResolveException;

    /**
     * Resolves a single content entry with content data.
     *
     * @param rq the ServiceRequest<ContentEntryMsg>.
     * @return the ServiceResponse<ContentEntryMsg>.
     * @throws ResolveException
     */
    ServiceResponse<ContentEntryMsg> resolveContentEntryData(ServiceRequest<ContentEntryMsg> rq)
            throws ResolveException;
}
