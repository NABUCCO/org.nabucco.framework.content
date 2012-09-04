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
 * ContentFolderComponentRelation<p/>Folder for grouping of content entries.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-11
 */
public class ContentFolderComponentRelation extends ComponentRelation<ContentFolder> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ContentFolderComponentRelation instance. */
    protected ContentFolderComponentRelation() {
        super();
    }

    /**
     * Constructs a new ContentFolderComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public ContentFolderComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the ContentFolder.
     */
    public ContentFolder getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the ContentFolder.
     */
    public void setTarget(ContentFolder target) {
        super.setTarget(target);
    }

    @Override
    public ContentFolderComponentRelation cloneObject() {
        ContentFolderComponentRelation clone = new ContentFolderComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
