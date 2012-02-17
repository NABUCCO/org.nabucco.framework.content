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
package org.nabucco.framework.content.impl.service.resolve;

import java.util.List;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;

/**
 * ContentPathResolver
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ContentPathResolver {

    private static final Pattern PATTERN = Pattern.compile("\\/");

    private PersistenceManager manager;

    /**
     * Creates a new {@link ContentPathResolver} instance.
     * 
     * @param manager
     *            the persistence manager
     */
    public ContentPathResolver(PersistenceManager manager) {
        this.manager = manager;
    }

    /**
     * Resolves the codes for a given code path.
     * 
     * @param path
     *            the code path to resolve
     * 
     * @throws ResolveException
     *             when the resolve did not finish normally
     */
    public ContentEntryElement resolve(String path) throws ResolveException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Cannot resolve content path [null].");
        }

        String[] entries = PATTERN.split(path);
        if (entries.length == 0) {
            throw new IllegalArgumentException("Cannot resolve content path [].");
        }

        String queryString = this.createQuery(entries.length);

        try {

            NabuccoQuery<ContentEntryElement> query = this.manager.createQuery(queryString);

            for (int i = 0; i < entries.length; i++) {
                String entry = entries[i];

                if (entry == null || entry.isEmpty()) {
                    throw new IllegalArgumentException("Cannot resolve content entry [] in path '" + path + "'.");
                }

                query.setParameter("name" + i, entry);
            }
            List<ContentEntryElement> resultList = query.getResultList();
            
            if(resultList.isEmpty()){
                return null;
            } else if(resultList.size() > 1){
                throw new ResolveException("The search result is not unique");
            }
            
            return resultList.get(0);

        } catch (PersistenceException pe) {
            throw new ResolveException("Error resolving content entry for path '" + path + "'.", pe);
        }
    }

    /**
     * Create the dynamic query for content path resolving.
     * 
     * @param folderCount
     *            the amount of content folder tokens
     * 
     * @return the query string
     */
    private String createQuery(int folderCount) {

        StringBuilder queryString = new StringBuilder();
        queryString.append("select c" + (folderCount - 1) + " from ContentEntryElement c");

        for (int i = 0; i < folderCount; i++) {
            if (i == 0) {
                queryString.append(i);
            } else {
                queryString.append(" inner join c" + (i - 1) + ".contentRelationsJPA r" + (i - 1));
                queryString.append(" inner join r" + (i - 1) + ".target c" + i);
            }
        }

        queryString.append(" where ");

        for (int i = 0; i < folderCount; i++) {
            if (i != 0) {
                queryString.append(" and ");
            }
            queryString.append("c" + i + ".name.value = :name" + i);
        }

        return queryString.toString();
    }
}
