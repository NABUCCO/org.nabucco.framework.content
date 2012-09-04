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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.content.facade.message.ContentEntryListMsg;
import org.nabucco.framework.content.facade.message.search.ContentEntrySearchRq;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * SearchContentImpl<p/>Search service of the content component<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-06-15
 */
public class SearchContentImpl extends ServiceSupport implements SearchContent {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchContent";

    private static Map<String, String[]> ASPECTS;

    private SearchContentEntriesServiceHandler searchContentEntriesServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchContentImpl instance. */
    public SearchContentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchContentEntriesServiceHandler = injector.inject(SearchContentEntriesServiceHandler.getId());
        if ((this.searchContentEntriesServiceHandler != null)) {
            this.searchContentEntriesServiceHandler.setPersistenceManager(persistenceManager);
            this.searchContentEntriesServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("searchContentEntries", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ContentEntryListMsg> searchContentEntries(ServiceRequest<ContentEntrySearchRq> rq)
            throws SearchException {
        if ((this.searchContentEntriesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchContentEntries().");
            throw new InjectionException("No service implementation configured for searchContentEntries().");
        }
        ServiceResponse<ContentEntryListMsg> rs;
        this.searchContentEntriesServiceHandler.init();
        rs = this.searchContentEntriesServiceHandler.invoke(rq);
        this.searchContentEntriesServiceHandler.finish();
        return rs;
    }
}
