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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.content.facade.service.maintain.MaintainContent;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.content.facade.service.resolve.ResolveContent;
import org.nabucco.framework.content.facade.service.search.SearchContent;

/**
 * ContentComponent<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public interface ContentComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.content";

    final String COMPONENT_PREFIX = "cont";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.content.facade.component.ContentComponent");

    /**
     * Getter for the MaintainContent.
     *
     * @return the MaintainContent.
     * @throws ServiceException
     */
    MaintainContent getMaintainContent() throws ServiceException;

    /**
     * Getter for the ProduceContent.
     *
     * @return the ProduceContent.
     * @throws ServiceException
     */
    ProduceContent getProduceContent() throws ServiceException;

    /**
     * Getter for the ResolveContent.
     *
     * @return the ResolveContent.
     * @throws ServiceException
     */
    ResolveContent getResolveContent() throws ServiceException;

    /**
     * Getter for the SearchContent.
     *
     * @return the SearchContent.
     * @throws ServiceException
     */
    SearchContent getSearchContent() throws ServiceException;
}
