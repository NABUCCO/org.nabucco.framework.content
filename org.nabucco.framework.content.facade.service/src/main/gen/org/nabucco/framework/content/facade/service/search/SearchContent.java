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
package org.nabucco.framework.content.facade.service.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.content.facade.message.ContentEntryListMsg;
import org.nabucco.framework.content.facade.message.search.ContentEntrySearchRq;

/**
 * SearchContent<p/>Search service of the content component<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-06-15
 */
public interface SearchContent extends Service {

    /**
     * Search Content Entries.
     *
     * @param rq the ServiceRequest<ContentEntrySearchRq>.
     * @return the ServiceResponse<ContentEntryListMsg>.
     * @throws SearchException
     */
    ServiceResponse<ContentEntryListMsg> searchContentEntries(ServiceRequest<ContentEntrySearchRq> rq)
            throws SearchException;
}
