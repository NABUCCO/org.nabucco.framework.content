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
package org.nabucco.framework.content.impl.service.resolve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;

/**
 * ResolveContentImpl<p/>Resolves content entries and allows for content loading.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class ResolveContentImpl extends ServiceSupport implements ResolveContent {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveContent";

    private static Map<String, String[]> ASPECTS;

    private ResolveContentEntryServiceHandler resolveContentEntryServiceHandler;

    private ResolveContentEntryByPathServiceHandler resolveContentEntryByPathServiceHandler;

    private ResolveContentEntryDataServiceHandler resolveContentEntryDataServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveContentImpl instance. */
    public ResolveContentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveContentEntryServiceHandler = injector.inject(ResolveContentEntryServiceHandler.getId());
        if ((this.resolveContentEntryServiceHandler != null)) {
            this.resolveContentEntryServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveContentEntryServiceHandler.setLogger(super.getLogger());
        }
        this.resolveContentEntryByPathServiceHandler = injector.inject(ResolveContentEntryByPathServiceHandler.getId());
        if ((this.resolveContentEntryByPathServiceHandler != null)) {
            this.resolveContentEntryByPathServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveContentEntryByPathServiceHandler.setLogger(super.getLogger());
        }
        this.resolveContentEntryDataServiceHandler = injector.inject(ResolveContentEntryDataServiceHandler.getId());
        if ((this.resolveContentEntryDataServiceHandler != null)) {
            this.resolveContentEntryDataServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveContentEntryDataServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("resolveContentEntry", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.resolving" });
            ASPECTS.put("resolveContentEntryByPath", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.resolving" });
            ASPECTS.put("resolveContentEntryData", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ContentEntryMsg> resolveContentEntry(ServiceRequest<ContentEntryMsg> rq)
            throws ResolveException {
        if ((this.resolveContentEntryServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveContentEntry().");
            throw new InjectionException("No service implementation configured for resolveContentEntry().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.resolveContentEntryServiceHandler.init();
        rs = this.resolveContentEntryServiceHandler.invoke(rq);
        this.resolveContentEntryServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ContentEntryMsg> resolveContentEntryByPath(ServiceRequest<ContentEntryPathMsg> rq)
            throws ResolveException {
        if ((this.resolveContentEntryByPathServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveContentEntryByPath().");
            throw new InjectionException("No service implementation configured for resolveContentEntryByPath().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.resolveContentEntryByPathServiceHandler.init();
        rs = this.resolveContentEntryByPathServiceHandler.invoke(rq);
        this.resolveContentEntryByPathServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ContentEntryMsg> resolveContentEntryData(ServiceRequest<ContentEntryMsg> rq)
            throws ResolveException {
        if ((this.resolveContentEntryDataServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveContentEntryData().");
            throw new InjectionException("No service implementation configured for resolveContentEntryData().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.resolveContentEntryDataServiceHandler.init();
        rs = this.resolveContentEntryDataServiceHandler.invoke(rq);
        this.resolveContentEntryDataServiceHandler.finish();
        return rs;
    }
}
