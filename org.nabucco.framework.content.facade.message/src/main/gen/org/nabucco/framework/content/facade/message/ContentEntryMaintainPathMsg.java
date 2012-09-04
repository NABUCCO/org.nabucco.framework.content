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
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;

/**
 * ContentEntryMaintainPathMsg<p/>Message holding the path to a content entry and new content entry.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-12
 */
public class ContentEntryMaintainPathMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l1,n;u0,n;m1,1;", "m1,1;", "l0,n;u0,n;m0,1;" };

    public static final String PATH = "path";

    public static final String ENTRY = "entry";

    public static final String REMOVESOURCE = "removeSource";

    /** Path to a content entry. */
    private ContentEntryPath path;

    /** The entry to be maintained to the given path */
    private ContentEntryElement entry;

    /** Indicates if the source item (or reference to it) should be removed (moved) to the aim directory */
    private Flag removeSource;

    /** Constructs a new ContentEntryMaintainPathMsg instance. */
    public ContentEntryMaintainPathMsg() {
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
        propertyMap.put(PATH, PropertyDescriptorSupport.createBasetype(PATH, ContentEntryPath.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ENTRY, PropertyDescriptorSupport.createDatatype(ENTRY, ContentEntryElement.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(REMOVESOURCE,
                PropertyDescriptorSupport.createBasetype(REMOVESOURCE, Flag.class, 2, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryMaintainPathMsg.getPropertyDescriptor(PATH), this.path));
        properties.add(super.createProperty(ContentEntryMaintainPathMsg.getPropertyDescriptor(ENTRY), this.getEntry()));
        properties.add(super.createProperty(ContentEntryMaintainPathMsg.getPropertyDescriptor(REMOVESOURCE),
                this.removeSource));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PATH) && (property.getType() == ContentEntryPath.class))) {
            this.setPath(((ContentEntryPath) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRY) && (property.getType() == ContentEntryElement.class))) {
            this.setEntry(((ContentEntryElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REMOVESOURCE) && (property.getType() == Flag.class))) {
            this.setRemoveSource(((Flag) property.getInstance()));
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
        final ContentEntryMaintainPathMsg other = ((ContentEntryMaintainPathMsg) obj);
        if ((this.path == null)) {
            if ((other.path != null))
                return false;
        } else if ((!this.path.equals(other.path)))
            return false;
        if ((this.entry == null)) {
            if ((other.entry != null))
                return false;
        } else if ((!this.entry.equals(other.entry)))
            return false;
        if ((this.removeSource == null)) {
            if ((other.removeSource != null))
                return false;
        } else if ((!this.removeSource.equals(other.removeSource)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.path == null) ? 0 : this.path.hashCode()));
        result = ((PRIME * result) + ((this.entry == null) ? 0 : this.entry.hashCode()));
        result = ((PRIME * result) + ((this.removeSource == null) ? 0 : this.removeSource.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Path to a content entry.
     *
     * @return the ContentEntryPath.
     */
    public ContentEntryPath getPath() {
        return this.path;
    }

    /**
     * Path to a content entry.
     *
     * @param path the ContentEntryPath.
     */
    public void setPath(ContentEntryPath path) {
        this.path = path;
    }

    /**
     * The entry to be maintained to the given path
     *
     * @return the ContentEntryElement.
     */
    public ContentEntryElement getEntry() {
        return this.entry;
    }

    /**
     * The entry to be maintained to the given path
     *
     * @param entry the ContentEntryElement.
     */
    public void setEntry(ContentEntryElement entry) {
        this.entry = entry;
    }

    /**
     * Indicates if the source item (or reference to it) should be removed (moved) to the aim directory
     *
     * @return the Flag.
     */
    public Flag getRemoveSource() {
        return this.removeSource;
    }

    /**
     * Indicates if the source item (or reference to it) should be removed (moved) to the aim directory
     *
     * @param removeSource the Flag.
     */
    public void setRemoveSource(Flag removeSource) {
        this.removeSource = removeSource;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryMaintainPathMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryMaintainPathMsg.class).getAllProperties();
    }
}
