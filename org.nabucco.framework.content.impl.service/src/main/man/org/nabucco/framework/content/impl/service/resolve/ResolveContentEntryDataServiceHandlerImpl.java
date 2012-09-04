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

import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.impl.service.resolve.common.ResolveUtility;

/**
 * ResolveContentEntryDataServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResolveContentEntryDataServiceHandlerImpl extends ResolveContentEntryDataServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryMsg resolveContentEntryData(ContentEntryMsg msg) throws ResolveException {

        ContentEntryMsg rs = new ContentEntryMsg();

        ContentEntryElement contentEntry = msg.getContentEntry();
        try {
            ContentEntryElement entry = this.resolveContentEntry(contentEntry);
            if (entry != null) {
                entry.getContentRelations().size();
            }
            rs.setContentEntry(entry);

        } catch (Exception e) {
            throw new ResolveException("Error resolving content entry with id '" + contentEntry.getId() + "'.", e);
        }

        return rs;
    }

    /**
     * Resolve the content entry including its data.
     * 
     * @param entry
     *            the content entry to resolve
     * 
     * @return the resolved content entry including its data
     * 
     * @throws PersistenceException
     *             when the entry cannot be found
     */
    private ContentEntryElement resolveContentEntry(ContentEntryElement entry) throws PersistenceException {

        ContentEntryType type = entry.getType();
        switch (type) {

        case INTERNAL_DATA: {
            ResolveUtility util = new ResolveUtility(this.getPersistenceManager(), super.getContext());
            ContentData contentData = util.resolveInternalContentData((InternalData) entry);
            if (contentData != null) {
                InternalData internalDataEntry = (InternalData) entry;
                internalDataEntry.setData(contentData);
            }
            break;
        }

        case EXTERNAL_DATA: {

            entry = super.getPersistenceManager().find(entry);
            // TODO: Adapter!
            break;
        }

        default:
            entry = super.getPersistenceManager().find(entry);
            break;

        }

        return entry;
    }
}
