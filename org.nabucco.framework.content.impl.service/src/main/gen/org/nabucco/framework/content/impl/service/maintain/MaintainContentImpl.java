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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;

/**
 * MaintainContentImpl<p/>Service to provide maintain operations for Content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class MaintainContentImpl extends ServiceSupport implements MaintainContent {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainContent";

    private static Map<String, String[]> ASPECTS;

    private MaintainContentEntryServiceHandler maintainContentEntryServiceHandler;

    private MaintainContentEntryByPathServiceHandler maintainContentEntryByPathServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainContentImpl instance. */
    public MaintainContentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainContentEntryServiceHandler = injector.inject(MaintainContentEntryServiceHandler.getId());
        if ((this.maintainContentEntryServiceHandler != null)) {
            this.maintainContentEntryServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainContentEntryServiceHandler.setLogger(super.getLogger());
        }
        this.maintainContentEntryByPathServiceHandler = injector.inject(MaintainContentEntryByPathServiceHandler
                .getId());
        if ((this.maintainContentEntryByPathServiceHandler != null)) {
            this.maintainContentEntryByPathServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainContentEntryByPathServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainContentEntry", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.relating", "org.nabucco.aspect.resolving" });
            ASPECTS.put("maintainContentEntryByPath", new String[] { "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.validating", "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ContentEntryMsg> maintainContentEntry(ServiceRequest<ContentEntryMsg> rq)
            throws MaintainException {
        if ((this.maintainContentEntryServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainContentEntry().");
            throw new InjectionException("No service implementation configured for maintainContentEntry().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.maintainContentEntryServiceHandler.init();
        rs = this.maintainContentEntryServiceHandler.invoke(rq);
        this.maintainContentEntryServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ContentEntryMsg> maintainContentEntryByPath(ServiceRequest<ContentEntryMaintainPathMsg> rq)
            throws MaintainException {
        if ((this.maintainContentEntryByPathServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainContentEntryByPath().");
            throw new InjectionException("No service implementation configured for maintainContentEntryByPath().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.maintainContentEntryByPathServiceHandler.init();
        rs = this.maintainContentEntryByPathServiceHandler.invoke(rq);
        this.maintainContentEntryByPathServiceHandler.finish();
        return rs;
    }
}
