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
package org.nabucco.framework.content.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.content.facade.component.ContentComponentLocal;
import org.nabucco.framework.content.facade.component.ContentComponentRemote;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * ContentComponentImpl<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public class ContentComponentImpl extends ComponentSupport implements ContentComponentLocal, ContentComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ContentComponent";

    /** Constructs a new ContentComponentImpl instance. */
    public ContentComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super
                .lookup(ContentComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE, ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public MaintainContent getMaintainContentLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.MAINTAIN_CONTENT_LOCAL, MaintainContent.class);
    }

    @Override
    public MaintainContent getMaintainContent() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.MAINTAIN_CONTENT_REMOTE, MaintainContent.class);
    }

    @Override
    public ProduceContent getProduceContentLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.PRODUCE_CONTENT_LOCAL, ProduceContent.class);
    }

    @Override
    public ProduceContent getProduceContent() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.PRODUCE_CONTENT_REMOTE, ProduceContent.class);
    }

    @Override
    public ResolveContent getResolveContentLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.RESOLVE_CONTENT_LOCAL, ResolveContent.class);
    }

    @Override
    public ResolveContent getResolveContent() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.RESOLVE_CONTENT_REMOTE, ResolveContent.class);
    }

    @Override
    public SearchContent getSearchContentLocal() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.SEARCH_CONTENT_LOCAL, SearchContent.class);
    }

    @Override
    public SearchContent getSearchContent() throws ServiceException {
        return super.lookup(ContentComponentJndiNames.SEARCH_CONTENT_REMOTE, SearchContent.class);
    }
}
