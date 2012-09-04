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
package org.nabucco.framework.content.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * ContentComponentLocal<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public interface ContentComponentLocal extends ContentComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the MaintainContentLocal.
     *
     * @return the MaintainContent.
     * @throws ServiceException
     */
    MaintainContent getMaintainContentLocal() throws ServiceException;

    /**
     * Getter for the ProduceContentLocal.
     *
     * @return the ProduceContent.
     * @throws ServiceException
     */
    ProduceContent getProduceContentLocal() throws ServiceException;

    /**
     * Getter for the ResolveContentLocal.
     *
     * @return the ResolveContent.
     * @throws ServiceException
     */
    ResolveContent getResolveContentLocal() throws ServiceException;

    /**
     * Getter for the SearchContentLocal.
     *
     * @return the SearchContent.
     * @throws ServiceException
     */
    SearchContent getSearchContentLocal() throws ServiceException;
}
