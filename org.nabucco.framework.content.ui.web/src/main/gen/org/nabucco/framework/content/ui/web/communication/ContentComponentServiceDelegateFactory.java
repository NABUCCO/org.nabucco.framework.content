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
package org.nabucco.framework.content.ui.web.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateFactorySupport;
import org.nabucco.framework.content.facade.component.ContentComponent;
import org.nabucco.framework.content.facade.component.ContentComponentLocator;
import org.nabucco.framework.content.ui.web.communication.maintain.MaintainContentDelegate;
import org.nabucco.framework.content.ui.web.communication.produce.ProduceContentDelegate;
import org.nabucco.framework.content.ui.web.communication.resolve.ResolveContentDelegate;
import org.nabucco.framework.content.ui.web.communication.search.SearchContentDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Content Component allows maintenance, resolution and delivery of content.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-14
 */
public class ContentComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<ContentComponent> {

    private static ContentComponentServiceDelegateFactory instance = new ContentComponentServiceDelegateFactory();

    private MaintainContentDelegate maintainContentDelegate;

    private ProduceContentDelegate produceContentDelegate;

    private ResolveContentDelegate resolveContentDelegate;

    private SearchContentDelegate searchContentDelegate;

    /** Constructs a new ContentComponentServiceDelegateFactory instance. */
    private ContentComponentServiceDelegateFactory() {
        super(ContentComponentLocator.getInstance());
    }

    /**
     * Getter for the MaintainContent.
     *
     * @return the MaintainContentDelegate.
     * @throws ClientException
     */
    public MaintainContentDelegate getMaintainContent() throws ClientException {
        try {
            if ((this.maintainContentDelegate == null)) {
                this.maintainContentDelegate = new MaintainContentDelegate(this.getComponent().getMaintainContent());
            }
            return this.maintainContentDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainContent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceContent.
     *
     * @return the ProduceContentDelegate.
     * @throws ClientException
     */
    public ProduceContentDelegate getProduceContent() throws ClientException {
        try {
            if ((this.produceContentDelegate == null)) {
                this.produceContentDelegate = new ProduceContentDelegate(this.getComponent().getProduceContent());
            }
            return this.produceContentDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceContent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ResolveContent.
     *
     * @return the ResolveContentDelegate.
     * @throws ClientException
     */
    public ResolveContentDelegate getResolveContent() throws ClientException {
        try {
            if ((this.resolveContentDelegate == null)) {
                this.resolveContentDelegate = new ResolveContentDelegate(this.getComponent().getResolveContent());
            }
            return this.resolveContentDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ResolveContent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the SearchContent.
     *
     * @return the SearchContentDelegate.
     * @throws ClientException
     */
    public SearchContentDelegate getSearchContent() throws ClientException {
        try {
            if ((this.searchContentDelegate == null)) {
                this.searchContentDelegate = new SearchContentDelegate(this.getComponent().getSearchContent());
            }
            return this.searchContentDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: SearchContent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the ContentComponentServiceDelegateFactory.
     */
    public static ContentComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
