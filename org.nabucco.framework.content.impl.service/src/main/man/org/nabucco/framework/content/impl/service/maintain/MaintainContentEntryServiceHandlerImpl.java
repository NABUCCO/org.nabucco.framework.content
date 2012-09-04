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

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.impl.service.maintain.common.MaintainUtility;

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

            MaintainUtility utility = new MaintainUtility(super.getPersistenceManager(), super.getContext());

            contentEntry = utility.maintainContentEntry(contentEntry);
            contentEntry.getContentRelations().size();

        } catch (Exception e) {
            throw new MaintainException("Error maintaining content entry with id '" + contentEntry.getId() + "'.", e);
        }

        ContentEntryMsg rs = new ContentEntryMsg();
        rs.setContentEntry(contentEntry);

        return rs;
    }

}
