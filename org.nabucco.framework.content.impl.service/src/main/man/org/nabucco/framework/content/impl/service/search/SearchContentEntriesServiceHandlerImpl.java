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
package org.nabucco.framework.content.impl.service.search;

import java.util.List;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.message.ContentEntryListMsg;
import org.nabucco.framework.content.facade.message.search.ContentEntrySearchRq;

/**
 * SearchContentEntriesServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SearchContentEntriesServiceHandlerImpl extends SearchContentEntriesServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryListMsg searchContentEntries(ContentEntrySearchRq msg) throws SearchException {

        ContentEntryListMsg rs = new ContentEntryListMsg();

        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("select c from ContentEntry c where");
            queryString.append(" (c.statusType = :statusType)");
            queryString.append(" and (c.name = :name or :name is null)");
            queryString.append(" and (c.owner = :owner or :owner is null)");
            queryString.append(" and (c.type = :type or :type is null)");
            queryString.append(" and (c.contentEntryTypeRefId = :contentEntryType or :contentEntryType is null)");
            queryString.append(" and (c.contentCategoryTypeRefId = :contentCategoryType or :contentCategoryType is null)");

            Long entryTypeId = msg.getContentEntryType() != null ? msg.getContentEntryType().getId() : null;
            Long categoryId = msg.getContentCategoryType() != null ? msg.getContentCategoryType().getId() : null;

            NabuccoQuery<ContentEntryElement> query = super.getPersistenceManager().createQuery(queryString.toString());
            query.setParameter(ContentEntrySearchRq.STATUSTYPE, msg.getStatusType());
            query.setParameter(ContentEntrySearchRq.NAME, msg.getName());
            query.setParameter(ContentEntrySearchRq.OWNER, msg.getOwner());
            query.setParameter(ContentEntrySearchRq.TYPE, msg.getType());
            query.setParameter(ContentEntrySearchRq.CONTENTENTRYTYPE, entryTypeId);
            query.setParameter(ContentEntrySearchRq.CONTENTCATEGORYTYPE, categoryId);

            List<ContentEntryElement> contentEntries = query.getResultList();
            rs.getContentEntries().addAll(contentEntries);

        } catch (PersistenceException pe) {
            throw new SearchException("Cannot execute search query.", pe);
        }

        return rs;
    }
}
