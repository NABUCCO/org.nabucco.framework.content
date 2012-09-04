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
package org.nabucco.framework.content.impl.service.maintain.common;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentMaster;
import org.nabucco.framework.content.facade.datatype.ContentRelation;
import org.nabucco.framework.content.facade.datatype.InternalData;

/**
 * MaintainUtility
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MaintainUtility {

    PersistenceManager manager;

    ServiceMessageContext context;

    /**
     * 
     * Creates a new {@link MaintainUtility} instance.
     * 
     */
    public MaintainUtility(PersistenceManager manager, ServiceMessageContext context) {
        if (manager == null) {
            throw new IllegalArgumentException("Cannot init Maintain util. manager is 'null'");
        }
        if (context == null) {
            throw new IllegalArgumentException("Cannot init Maintain util. context is 'null'");
        }
        this.manager = manager;
        this.context = context;
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
    public ContentEntryElement maintainContentEntry(ContentEntryElement contentEntry) throws PersistenceException {
        if (contentEntry.getMaster() != null) {
            ContentMaster master = manager.persist(contentEntry.getMaster());
            contentEntry.setMaster(master);
        }

        if (contentEntry.getDatatypeState() == DatatypeState.DELETED) {
            return this.deleteEntry(contentEntry);
        }
        ContentEntryElement retVal = this.persistEntry(contentEntry);

        retVal.getContentRelations().size();

        return retVal;
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
            contentEntry = manager.persist(contentEntry);
            contentEntry = manager.find(contentEntry);
        }
        contentEntry.getContentRelations().size();

        for (ContentRelation relation : contentEntry.getContentRelations()) {
            if (relation.getDatatypeState() != DatatypeState.PERSISTENT) {
                ContentEntryElement target = relation.getTarget();
                relation = manager.persist(relation);
                if (target.getDatatypeState() != DatatypeState.PERSISTENT) {
                    this.maintainContentEntry(target);
                }
            }
        }

        if (contentEntry.getDatatypeState() == DatatypeState.INITIALIZED) {
            contentEntry.setCreator(context.getUserId());
            contentEntry.setCreationTime(NabuccoSystem.getCurrentTimestamp());
        }

        contentEntry.setModifier(context.getUserId());
        contentEntry.setModificationTime(NabuccoSystem.getCurrentTimestamp());

        contentEntry = manager.persist(contentEntry);

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
            manager.persist(data);
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
            contentEntry = manager.find(contentEntry);
            contentEntry.setDatatypeState(DatatypeState.DELETED);
        }

        for (ContentRelation relation : contentEntry.getContentRelations()) {
            ContentEntryElement child = relation.getTarget();

            relation.setDatatypeState(DatatypeState.DELETED);
            relation = manager.persist(relation);

            child.setDatatypeState(DatatypeState.DELETED);
            if (child.getMaster() != null) {
                child.getMaster().setDatatypeState(DatatypeState.DELETED);
            }
            this.maintainContentEntry(child);

        }

        ContentEntryType type = contentEntry.getType();
        switch (type) {

        case INTERNAL_DATA: {
            InternalData data = (InternalData) contentEntry;
            if (data.getData() != null) {
                data.getData().setDatatypeState(DatatypeState.DELETED);
                manager.persist(data.getData());
            }
            break;
        }

        case EXTERNAL_DATA: {
            // TODO: External Adapters!
        }

        }

        manager.persist(contentEntry);

        return contentEntry;
    }
}
