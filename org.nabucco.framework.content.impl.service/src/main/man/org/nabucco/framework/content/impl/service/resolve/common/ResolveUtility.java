/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.content.impl.service.resolve.common;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentFolder;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.impl.service.maintain.common.MaintainUtility;

/**
 * ResolveUtility
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResolveUtility {

    PersistenceManager manager;

    ServiceMessageContext context;

    /**
     * 
     * Creates a new {@link MaintainUtility} instance.
     * 
     */
    public ResolveUtility(PersistenceManager manager, ServiceMessageContext context) {
        if (manager == null) {
            throw new IllegalArgumentException("Cannot init Resolve util. manager is 'null'");
        }
        if (context == null) {
            throw new IllegalArgumentException("Cannot init Resolve util. context is 'null'");
        }
        this.manager = manager;
        this.context = context;
    }

    /**
     * Sets the content data of internal data to the resolved value. Resolving doents change the
     * state of the resolved internal data
     * 
     * @param data
     *            internal data to be filled
     * @return filled internal data
     * @throws PersistenceException
     */
    public InternalData resolveInternalDataContent(InternalData data) throws PersistenceException {
        ContentData resolveInternalContentData = this.resolveInternalContentData(data);
        if (resolveInternalContentData != null) {
            DatatypeState datatypeState = data.getDatatypeState();
            data.setData(resolveInternalContentData);
            data.setDatatypeState(datatypeState);
        }

        return data;
    }

    /**
     * Resolves content data from the given internal data
     * 
     * @param data
     *            data to resolve content data from
     * @return content data or null if not found
     * @throws PersistenceException
     *             if non unique result
     */
    public ContentData resolveInternalContentData(InternalData data) throws PersistenceException {

        if (data.getData() != null) {
            if (data.getData().getDatatypeState() == DatatypeState.INITIALIZED) {
                return data.getData();
            }

            manager.refresh(data.getData());

            return data.getData();
        }

        ContentData retVal = null;

        NabuccoQuery<ContentData> query = manager
        // .createQuery("select c from ContentData c, InternalData i where i.data.id = c.id and i.id = :id");
                .createQuery("select i.data from InternalData i where i.id = :id");

        query.setParameter(ContentEntryElement.ID, data.getId());
        List<ContentData> resultList = query.getResultList();
        if (resultList.size() == 1) {
            retVal = resultList.get(0);
        } else if (resultList.size() > 1) {
            throw new PersistenceException("Non Unique result by resolving of entry data.");
        } else {
            return null;
        }

        return retVal;
    }

    /**
     * Looks around what content folders relate to the given element
     * 
     * @param element
     *            the element to search relations for
     * @return list of folders or empty list
     * @throws PersistenceException
     *             by persistance problems
     */
    public List<ContentFolder> findRelations(ContentEntryElement element) throws PersistenceException {
        List<ContentFolder> retVal = new ArrayList<ContentFolder>();

        StringBuilder queryString = new StringBuilder();
        queryString.append("select c from ContentEntryElement c");
        queryString.append(" inner join c.contentRelationsJPA r");
        queryString.append(" inner join r.target e");
        queryString.append(" where ");
        queryString.append("e.id = :identifier");

        NabuccoQuery<ContentEntryElement> query = manager.createQuery(queryString.toString());
        query.setParameter("identifier", element.getId());

        List<ContentEntryElement> resultList = query.getResultList();

        for (ContentEntryElement foundItem : resultList) {
            if (foundItem instanceof ContentFolder) {
                retVal.add((ContentFolder) foundItem);
            }
        }

        return retVal;
    }

}
