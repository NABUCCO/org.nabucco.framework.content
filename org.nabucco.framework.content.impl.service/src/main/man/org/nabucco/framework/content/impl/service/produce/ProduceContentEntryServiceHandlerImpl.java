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
package org.nabucco.framework.content.impl.service.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ContentFolder;
import org.nabucco.framework.content.facade.datatype.ContentMaster;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;

/**
 * ProduceContentEntryServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProduceContentEntryServiceHandlerImpl extends ProduceContentEntryServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryMsg produceContentEntry(ContentEntryPrototypeRq msg) throws ProduceException {

        ContentEntryElement entry = this.createEntry(msg.getType());
        entry.setOwner(super.getContext().getOwner());
        entry.setDatatypeState(DatatypeState.INITIALIZED);

        ContentMaster master = this.createMaster(msg.getMaster());
        master.setTenant(new Tenant());
        entry.setMaster(master);

        ContentEntryMsg rs = new ContentEntryMsg();
        rs.setContentEntry(entry);
        return rs;
    }

    /**
     * Create the content master instance when no original master is delivered.
     * 
     * @param master
     *            the original content master
     * 
     * @return the original content master, or a new one if the original is null
     */
    private ContentMaster createMaster(ContentMaster master) {
        if (master == null) {
            master = new ContentMaster();
            master.setOwner(super.getContext().getOwner());
            master.setDatatypeState(DatatypeState.INITIALIZED);
        }
        return master;
    }

    /**
     * Create a new {@link ContentEntryElement} instance.
     * 
     * @param type
     *            the content type
     * 
     * @return the new content instance
     */
    private ContentEntryElement createEntry(ContentEntryType type) {
        switch (type) {

        case FOLDER:
            return new ContentFolder();
        case INTERNAL_DATA:
            return new InternalData();
        case EXTERNAL_DATA:
            return new ExternalData();

        default:
            throw new IllegalStateException("Content Entry '" + type + "' is not supported yet.");
        }
    }

}
