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
package org.nabucco.framework.content.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.content.facade.component.ContentComponent;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * ContentComponentLocalProxy<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public class ContentComponentLocalProxy implements ContentComponent {

    private static final long serialVersionUID = 1L;

    private final ContentComponentLocal delegate;

    /**
     * Constructs a new ContentComponentLocalProxy instance.
     *
     * @param delegate the ContentComponentLocal.
     */
    public ContentComponentLocalProxy(ContentComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public MaintainContent getMaintainContent() throws ServiceException {
        return this.delegate.getMaintainContentLocal();
    }

    @Override
    public ProduceContent getProduceContent() throws ServiceException {
        return this.delegate.getProduceContentLocal();
    }

    @Override
    public ResolveContent getResolveContent() throws ServiceException {
        return this.delegate.getResolveContentLocal();
    }

    @Override
    public SearchContent getSearchContent() throws ServiceException {
        return this.delegate.getSearchContentLocal();
    }
}
