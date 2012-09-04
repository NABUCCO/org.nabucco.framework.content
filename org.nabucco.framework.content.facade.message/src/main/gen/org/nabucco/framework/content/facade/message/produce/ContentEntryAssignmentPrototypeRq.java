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
package org.nabucco.framework.content.facade.message.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ContentEntryAssignmentPrototypeRq<p/>Prototype for content entry assignments<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-22
 */
public class ContentEntryAssignmentPrototypeRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m1,1;", "m1,1;" };

    public static final String CONTENTENTRY = "contentEntry";

    public static final String CONTENTENTRYTYPE = "contentEntryType";

    public static final String TYPE = "type";

    /** The content entry to transport. */
    private ContentEntry contentEntry;

    /** Type of the content entry to create */
    private ContentEntryType contentEntryType;

    /** Type of the content entry assignement (TEXT, File etc) */
    private ContentEntryAssignmentType type;

    /** Constructs a new ContentEntryAssignmentPrototypeRq instance. */
    public ContentEntryAssignmentPrototypeRq() {
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
        propertyMap.put(CONTENTENTRY, PropertyDescriptorSupport.createDatatype(CONTENTENTRY, ContentEntry.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(CONTENTENTRYTYPE, PropertyDescriptorSupport.createEnumeration(CONTENTENTRYTYPE,
                ContentEntryType.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, ContentEntryAssignmentType.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryAssignmentPrototypeRq.getPropertyDescriptor(CONTENTENTRY),
                this.getContentEntry()));
        properties.add(super.createProperty(ContentEntryAssignmentPrototypeRq.getPropertyDescriptor(CONTENTENTRYTYPE),
                this.getContentEntryType()));
        properties.add(super.createProperty(ContentEntryAssignmentPrototypeRq.getPropertyDescriptor(TYPE),
                this.getType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONTENTENTRY) && (property.getType() == ContentEntry.class))) {
            this.setContentEntry(((ContentEntry) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTENTRYTYPE) && (property.getType() == ContentEntryType.class))) {
            this.setContentEntryType(((ContentEntryType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == ContentEntryAssignmentType.class))) {
            this.setType(((ContentEntryAssignmentType) property.getInstance()));
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
        final ContentEntryAssignmentPrototypeRq other = ((ContentEntryAssignmentPrototypeRq) obj);
        if ((this.contentEntry == null)) {
            if ((other.contentEntry != null))
                return false;
        } else if ((!this.contentEntry.equals(other.contentEntry)))
            return false;
        if ((this.contentEntryType == null)) {
            if ((other.contentEntryType != null))
                return false;
        } else if ((!this.contentEntryType.equals(other.contentEntryType)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.contentEntry == null) ? 0 : this.contentEntry.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryType == null) ? 0 : this.contentEntryType.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The content entry to transport.
     *
     * @return the ContentEntry.
     */
    public ContentEntry getContentEntry() {
        return this.contentEntry;
    }

    /**
     * The content entry to transport.
     *
     * @param contentEntry the ContentEntry.
     */
    public void setContentEntry(ContentEntry contentEntry) {
        this.contentEntry = contentEntry;
    }

    /**
     * Type of the content entry to create
     *
     * @return the ContentEntryType.
     */
    public ContentEntryType getContentEntryType() {
        return this.contentEntryType;
    }

    /**
     * Type of the content entry to create
     *
     * @param contentEntryType the ContentEntryType.
     */
    public void setContentEntryType(ContentEntryType contentEntryType) {
        this.contentEntryType = contentEntryType;
    }

    /**
     * Type of the content entry assignement (TEXT, File etc)
     *
     * @return the ContentEntryAssignmentType.
     */
    public ContentEntryAssignmentType getType() {
        return this.type;
    }

    /**
     * Type of the content entry assignement (TEXT, File etc)
     *
     * @param type the ContentEntryAssignmentType.
     */
    public void setType(ContentEntryAssignmentType type) {
        this.type = type;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentPrototypeRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentPrototypeRq.class).getAllProperties();
    }
}
