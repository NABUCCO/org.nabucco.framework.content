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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentMsg;
import org.nabucco.framework.content.facade.message.ContentEntryAssignmentResolveRq;
import org.nabucco.framework.content.impl.service.resolve.common.ResolveUtility;

/**
 * ResolveContentEntryAssignmentServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResolveContentEntryAssignmentServiceHandlerImpl extends ResolveContentEntryAssignmentServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ContentEntryAssignmentMsg resolveContentEntryAssignment(ContentEntryAssignmentResolveRq msg)
            throws ResolveException {

        try {
            ContentEntryAssignmentElement assignment = super.getPersistenceManager().find(
                    msg.getContentEntryAssignment());

            ContentEntryAssignmentType contentEntryAssignmentType = assignment.getType();

            ContentEntryElement entry = assignment.getEntry();

            if (entry != null) {

                if (contentEntryAssignmentType == ContentEntryAssignmentType.TEXT) {

                    String stringData = "";
                    ContentEntryType type = entry.getType();
                    switch (type) {
                    case EXTERNAL_DATA: {
                        ExternalData data = (ExternalData) entry;

                        byte[] value = data.getData().getValue();
                        stringData = new String(value, SerializationConstants.CHARSET);
                        break;
                    }
                    case INTERNAL_DATA: {

                        InternalData data = (InternalData) entry;
                        ResolveUtility util = new ResolveUtility(this.getPersistenceManager(), super.getContext());
                        data = util.resolveInternalDataContent(data);

                        if (data.getData() != null) {
                            byte[] value = data.getData().getData().getValue();
                            stringData = new String(value, SerializationConstants.CHARSET);
                        }

                        break;
                    }
                    case FOLDER: {
                        throw new ResolveException("Cannot resolve assignment that references a folder.");
                    }
                    }

                    assignment.setTextContent(stringData);

                }

                assignment.setDatatypeState(DatatypeState.PERSISTENT);

            }
            ContentEntryAssignmentMsg rs = new ContentEntryAssignmentMsg();
            rs.setContentEntryAssignment(assignment);
            return rs;
        } catch (Exception e) {
            throw new ResolveException("Error resolving content entry assignment with id '"
                    + msg.getContentEntryAssignment().getId() + "'.", e);
        }
    }

}
