/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.content.facade.datatype;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;

/**
 * ContentEntryAssignmentElementComponentRelation<p/>ContentEntryAssignment<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-22
 */
public class ContentEntryAssignmentElementComponentRelation extends ComponentRelation<ContentEntryAssignmentElement> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ContentEntryAssignmentElementComponentRelation instance. */
    protected ContentEntryAssignmentElementComponentRelation() {
        super();
    }

    /**
     * Constructs a new ContentEntryAssignmentElementComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public ContentEntryAssignmentElementComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the ContentEntryAssignmentElement.
     */
    public ContentEntryAssignmentElement getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the ContentEntryAssignmentElement.
     */
    public void setTarget(ContentEntryAssignmentElement target) {
        super.setTarget(target);
    }

    @Override
    public ContentEntryAssignmentElementComponentRelation cloneObject() {
        ContentEntryAssignmentElementComponentRelation clone = new ContentEntryAssignmentElementComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
