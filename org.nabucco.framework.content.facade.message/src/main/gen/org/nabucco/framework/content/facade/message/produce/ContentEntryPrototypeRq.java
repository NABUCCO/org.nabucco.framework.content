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
package org.nabucco.framework.content.facade.message.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.content.facade.datatype.ContentMaster;

/**
 * ContentEntryPrototypeRq<p/>Prototype for content entries.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-13
 */
public class ContentEntryPrototypeRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String TYPE = "type";

    public static final String MASTER = "master";

    /** Type of the content entry to produce. */
    private ContentEntryType type;

    /** Optional master of the content entry to produce. */
    private ContentMaster master;

    /** Constructs a new ContentEntryPrototypeRq instance. */
    public ContentEntryPrototypeRq() {
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
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, ContentEntryType.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(MASTER, PropertyDescriptorSupport.createDatatype(MASTER, ContentMaster.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryPrototypeRq.getPropertyDescriptor(TYPE), this.getType()));
        properties.add(super.createProperty(ContentEntryPrototypeRq.getPropertyDescriptor(MASTER), this.getMaster()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == ContentEntryType.class))) {
            this.setType(((ContentEntryType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MASTER) && (property.getType() == ContentMaster.class))) {
            this.setMaster(((ContentMaster) property.getInstance()));
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
        final ContentEntryPrototypeRq other = ((ContentEntryPrototypeRq) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.master == null)) {
            if ((other.master != null))
                return false;
        } else if ((!this.master.equals(other.master)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.master == null) ? 0 : this.master.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Type of the content entry to produce.
     *
     * @return the ContentEntryType.
     */
    public ContentEntryType getType() {
        return this.type;
    }

    /**
     * Type of the content entry to produce.
     *
     * @param type the ContentEntryType.
     */
    public void setType(ContentEntryType type) {
        this.type = type;
    }

    /**
     * Optional master of the content entry to produce.
     *
     * @return the ContentMaster.
     */
    public ContentMaster getMaster() {
        return this.master;
    }

    /**
     * Optional master of the content entry to produce.
     *
     * @param master the ContentMaster.
     */
    public void setMaster(ContentMaster master) {
        this.master = master;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryPrototypeRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryPrototypeRq.class).getAllProperties();
    }
}
