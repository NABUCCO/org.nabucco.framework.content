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
package org.nabucco.framework.content.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.content.facade.datatype.ContentEntryAssignmentElement;

/**
 * ContentEntryAssignmentMsg<p/>Service DTO for ContentEntryAssignment<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-22
 */
public class ContentEntryAssignmentMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String CONTENTENTRYASSIGNMENT = "contentEntryAssignment";

    /** The content entry assignment to transport. */
    private ContentEntryAssignmentElement contentEntryAssignment;

    /** Constructs a new ContentEntryAssignmentMsg instance. */
    public ContentEntryAssignmentMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(CONTENTENTRYASSIGNMENT, PropertyDescriptorSupport.createDatatype(CONTENTENTRYASSIGNMENT,
                ContentEntryAssignmentElement.class, 0, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryAssignmentMsg.getPropertyDescriptor(CONTENTENTRYASSIGNMENT),
                this.getContentEntryAssignment()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONTENTENTRYASSIGNMENT) && (property.getType() == ContentEntryAssignmentElement.class))) {
            this.setContentEntryAssignment(((ContentEntryAssignmentElement) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ContentEntryAssignmentMsg other = ((ContentEntryAssignmentMsg) obj);
        if ((this.contentEntryAssignment == null)) {
            if ((other.contentEntryAssignment != null))
                return false;
        } else if ((!this.contentEntryAssignment.equals(other.contentEntryAssignment)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.contentEntryAssignment == null) ? 0 : this.contentEntryAssignment
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The content entry assignment to transport.
     *
     * @return the ContentEntryAssignmentElement.
     */
    public ContentEntryAssignmentElement getContentEntryAssignment() {
        return this.contentEntryAssignment;
    }

    /**
     * The content entry assignment to transport.
     *
     * @param contentEntryAssignment the ContentEntryAssignmentElement.
     */
    public void setContentEntryAssignment(ContentEntryAssignmentElement contentEntryAssignment) {
        this.contentEntryAssignment = contentEntryAssignment;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentMsg.class).getAllProperties();
    }
}