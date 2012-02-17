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
package org.nabucco.framework.content.impl.service.maintain;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentMaster;
import org.nabucco.framework.content.facade.datatype.ContentRelation;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;

/**
 * MaintainContentEntryServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MaintainContentEntryServiceHandlerImpl extends MaintainContentEntryServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryMsg maintainContentEntry(ContentEntryMsg rq) throws MaintainException {

        ContentEntryElement contentEntry = rq.getContentEntry();

        try {
            if (contentEntry.getMaster() != null) {
                ContentMaster master = super.getPersistenceManager().persist(contentEntry.getMaster());
                contentEntry.setMaster(master);
            }

            contentEntry = this.maintainContentEntry(contentEntry);
            contentEntry.getContentRelations().size();

        } catch (Exception e) {
            throw new MaintainException("Error maintaining content entry with id '" + contentEntry.getId() + "'.", e);
        }

        ContentEntryMsg rs = new ContentEntryMsg();
        rs.setContentEntry(contentEntry);

        return rs;
    }

    /**
     * Maintain the content entry to the database.
     * 
     * @param contentEntry
     *            the content entry to persist
     * 
     * @return the persisted entry
     * 
     * @throws PersistenceException
     *             when the entry cannot be persisted
     */
    private ContentEntryElement maintainContentEntry(ContentEntryElement contentEntry) throws PersistenceException {
        if (contentEntry.getDatatypeState() == DatatypeState.DELETED) {
            return this.deleteEntry(contentEntry);
        }
        return this.persistEntry(contentEntry);
    }

    /**
     * Modify the content entry.
     * 
     * @param contentEntry
     *            the content entry to create or modify
     * 
     * @return the persisted entry
     * 
     * @throws PersistenceException
     *             when the entry cannot be persisted
     */
    private ContentEntryElement persistEntry(ContentEntryElement contentEntry) throws PersistenceException {

        ContentEntryType type = contentEntry.getType();
        switch (type) {

        case INTERNAL_DATA: {
            InternalData data = (InternalData) contentEntry;
            this.persistData(data.getData());
            break;
        }

        case EXTERNAL_DATA: {
            // TODO: External Adapters!
        }

        }

        if (!contentEntry.getContentRelations().isTraversable()) {
            contentEntry = super.getPersistenceManager().find(contentEntry);
        }
        contentEntry.getContentRelations().size();
        
        for (ContentRelation relation : contentEntry.getContentRelations()) {
            if (relation.getDatatypeState() != DatatypeState.PERSISTENT) {
                this.maintainContentEntry(relation.getTarget());
                relation = super.getPersistenceManager().persist(relation);
            }
        }

        if (contentEntry.getDatatypeState() == DatatypeState.INITIALIZED) {
            contentEntry.setCreator(super.getContext().getUserId());
            contentEntry.setCreationTime(NabuccoSystem.getCurrentTimestamp());
        }

        contentEntry.setModifier(super.getContext().getUserId());
        contentEntry.setModificationTime(NabuccoSystem.getCurrentTimestamp());

        contentEntry = super.getPersistenceManager().persist(contentEntry);

        return contentEntry;
    }

    /**
     * Persist the content data.
     * 
     * @param data
     *            the data to persist
     * 
     * @throws PersistenceException
     *             when the data cannot be persisted
     */
    private void persistData(ContentData data) throws PersistenceException {
        if (data != null) {
            data.setByteSize(data.getData().getValue().length);
            super.getPersistenceManager().persist(data);
        }
    }

    /**
     * Delete the content entry recursively.
     * 
     * @param contentEntry
     *            the content entry to delete
     * 
     * @return the deleted entry
     * 
     * @throws PersistenceException
     *             when the entry cannot be deleted
     */
    private ContentEntryElement deleteEntry(ContentEntryElement contentEntry) throws PersistenceException {
        
        if (!contentEntry.getContentRelations().isTraversable()) {
            contentEntry = super.getPersistenceManager().find(contentEntry);
        }
        
        for (ContentRelation relation : contentEntry.getContentRelations()) {
            ContentEntryElement child = relation.getTarget();

            relation.setDatatypeState(DatatypeState.DELETED);
            relation = super.getPersistenceManager().persist(relation);

            child.setDatatypeState(DatatypeState.DELETED);
            this.maintainContentEntry(child);
        }

        ContentEntryType type = contentEntry.getType();
        switch (type) {

        case INTERNAL_DATA: {
            InternalData data = (InternalData) contentEntry;
            if (data.getData() != null) {
                data.getData().setDatatypeState(DatatypeState.DELETED);
                super.getPersistenceManager().persist(data.getData());
            }
            break;
        }

        case EXTERNAL_DATA: {
            // TODO: External Adapters!
        }

        }

        super.getPersistenceManager().persist(contentEntry);

        return contentEntry;
    }
}
