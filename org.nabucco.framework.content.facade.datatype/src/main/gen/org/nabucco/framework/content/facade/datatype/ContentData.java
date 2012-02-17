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
package org.nabucco.framework.content.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ContentData<p/>Data of t a content entry.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-11
 */
public class ContentData extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m0,1;" };

    public static final String BYTESIZE = "byteSize";

    public static final String DATA = "data";

    /** Size of the Content Item Data. */
    private Number byteSize;

    /** The concrete Content Data. */
    private Data data;

    /** Constructs a new ContentData instance. */
    public ContentData() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentData.
     */
    protected void cloneObject(ContentData clone) {
        super.cloneObject(clone);
        if ((this.getByteSize() != null)) {
            clone.setByteSize(this.getByteSize().cloneObject());
        }
        if ((this.getData() != null)) {
            clone.setData(this.getData().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(BYTESIZE,
                PropertyDescriptorSupport.createBasetype(BYTESIZE, Number.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DATA,
                PropertyDescriptorSupport.createBasetype(DATA, Data.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentData.getPropertyDescriptor(BYTESIZE), this.byteSize, null));
        properties.add(super.createProperty(ContentData.getPropertyDescriptor(DATA), this.data, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BYTESIZE) && (property.getType() == Number.class))) {
            this.setByteSize(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATA) && (property.getType() == Data.class))) {
            this.setData(((Data) property.getInstance()));
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
        final ContentData other = ((ContentData) obj);
        if ((this.byteSize == null)) {
            if ((other.byteSize != null))
                return false;
        } else if ((!this.byteSize.equals(other.byteSize)))
            return false;
        if ((this.data == null)) {
            if ((other.data != null))
                return false;
        } else if ((!this.data.equals(other.data)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.byteSize == null) ? 0 : this.byteSize.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        return result;
    }

    @Override
    public ContentData cloneObject() {
        ContentData clone = new ContentData();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Size of the Content Item Data.
     *
     * @return the Number.
     */
    public Number getByteSize() {
        return this.byteSize;
    }

    /**
     * Size of the Content Item Data.
     *
     * @param byteSize the Number.
     */
    public void setByteSize(Number byteSize) {
        this.byteSize = byteSize;
    }

    /**
     * Size of the Content Item Data.
     *
     * @param byteSize the Integer.
     */
    public void setByteSize(Integer byteSize) {
        if ((this.byteSize == null)) {
            if ((byteSize == null)) {
                return;
            }
            this.byteSize = new Number();
        }
        this.byteSize.setValue(byteSize);
    }

    /**
     * The concrete Content Data.
     *
     * @return the Data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * The concrete Content Data.
     *
     * @param data the Data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * The concrete Content Data.
     *
     * @param data the byte[].
     */
    public void setData(byte[] data) {
        if ((this.data == null)) {
            if ((data == null)) {
                return;
            }
            this.data = new Data();
        }
        this.data.setValue(data);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentData.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentData.class).getAllProperties();
    }
}
