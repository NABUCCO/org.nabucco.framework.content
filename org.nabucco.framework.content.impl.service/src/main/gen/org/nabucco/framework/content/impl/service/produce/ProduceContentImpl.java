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
package org.nabucco.framework.content.impl.service.produce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;

/**
 * ProduceContentImpl<p/>Service to produce content datatypes.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class ProduceContentImpl extends ServiceSupport implements ProduceContent {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceContent";

    private static Map<String, String[]> ASPECTS;

    private ProduceContentEntryServiceHandler produceContentEntryServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceContentImpl instance. */
    public ProduceContentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.produceContentEntryServiceHandler = injector.inject(ProduceContentEntryServiceHandler.getId());
        if ((this.produceContentEntryServiceHandler != null)) {
            this.produceContentEntryServiceHandler.setPersistenceManager(persistenceManager);
            this.produceContentEntryServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("produceContentEntry", new String[] { "org.nabucco.aspect.initializing" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ContentEntryMsg> produceContentEntry(ServiceRequest<ContentEntryPrototypeRq> rq)
            throws ProduceException {
        if ((this.produceContentEntryServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceContentEntry().");
            throw new InjectionException("No service implementation configured for produceContentEntry().");
        }
        ServiceResponse<ContentEntryMsg> rs;
        this.produceContentEntryServiceHandler.init();
        rs = this.produceContentEntryServiceHandler.invoke(rq);
        this.produceContentEntryServiceHandler.finish();
        return rs;
    }
}
