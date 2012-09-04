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
package org.nabucco.framework.content.impl.component;

/**
 * ContentComponentJndiNames<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public interface ContentComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.component.QueryFilterService/remote";

    final String MAINTAIN_CONTENT_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.maintain.MaintainContent/local";

    final String MAINTAIN_CONTENT_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.maintain.MaintainContent/remote";

    final String PRODUCE_CONTENT_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.produce.ProduceContent/local";

    final String PRODUCE_CONTENT_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.produce.ProduceContent/remote";

    final String RESOLVE_CONTENT_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.resolve.ResolveContent/local";

    final String RESOLVE_CONTENT_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.resolve.ResolveContent/remote";

    final String SEARCH_CONTENT_LOCAL = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.search.SearchContent/local";

    final String SEARCH_CONTENT_REMOTE = "nabucco/org.nabucco.framework.content/org.nabucco.framework.content.facade.service.search.SearchContent/remote";
}
