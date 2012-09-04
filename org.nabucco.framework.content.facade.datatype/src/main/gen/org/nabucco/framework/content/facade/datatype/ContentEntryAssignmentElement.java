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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;

/**
 * ContentEntryAssignmentElement<p/>ContentEntryAssignment<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-22
 */
public class ContentEntryAssignmentElement extends ContentEntryAssignment implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String ENTRY = "entry";

    public static final String OWNER = "owner";

    public static final String ELEMENTIDENTIFIER = "elementIdentifier";

    /** The assigned entry */
    private ContentEntryElement entry;

    /** The owner type of the assignment */
    private FunctionalIdentifier owner;

    /** The Unique identifier of the attachment */
    private FunctionalIdentifier elementIdentifier;

    /** Constructs a new ContentEntryAssignmentElement instance. */
    public ContentEntryAssignmentElement() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentEntryAssignmentElement.
     */
    protected void cloneObject(ContentEntryAssignmentElement clone) {
        super.cloneObject(clone);
        if ((this.getEntry() != null)) {
            clone.setEntry(this.getEntry().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getElementIdentifier() != null)) {
            clone.setElementIdentifier(this.getElementIdentifier().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ContentEntryAssignment.class).getPropertyMap());
        propertyMap.put(ENTRY, PropertyDescriptorSupport.createDatatype(ENTRY, ContentEntryElement.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OWNER, PropertyDescriptorSupport.createBasetype(OWNER, FunctionalIdentifier.class, 7,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(ELEMENTIDENTIFIER, PropertyDescriptorSupport.createBasetype(ELEMENTIDENTIFIER,
                FunctionalIdentifier.class, 8, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryAssignmentElement.getPropertyDescriptor(ENTRY),
                this.getEntry(), null));
        properties.add(super.createProperty(ContentEntryAssignmentElement.getPropertyDescriptor(OWNER), this.owner,
                null));
        properties.add(super.createProperty(ContentEntryAssignmentElement.getPropertyDescriptor(ELEMENTIDENTIFIER),
                this.elementIdentifier, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ENTRY) && (property.getType() == ContentEntryElement.class))) {
            this.setEntry(((ContentEntryElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == FunctionalIdentifier.class))) {
            this.setOwner(((FunctionalIdentifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ELEMENTIDENTIFIER) && (property.getType() == FunctionalIdentifier.class))) {
            this.setElementIdentifier(((FunctionalIdentifier) property.getInstance()));
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
        final ContentEntryAssignmentElement other = ((ContentEntryAssignmentElement) obj);
        if ((this.entry == null)) {
            if ((other.entry != null))
                return false;
        } else if ((!this.entry.equals(other.entry)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.elementIdentifier == null)) {
            if ((other.elementIdentifier != null))
                return false;
        } else if ((!this.elementIdentifier.equals(other.elementIdentifier)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.entry == null) ? 0 : this.entry.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.elementIdentifier == null) ? 0 : this.elementIdentifier.hashCode()));
        return result;
    }

    @Override
    public ContentEntryAssignmentElement cloneObject() {
        ContentEntryAssignmentElement clone = new ContentEntryAssignmentElement();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The assigned entry
     *
     * @param entry the ContentEntryElement.
     */
    public void setEntry(ContentEntryElement entry) {
        this.entry = entry;
    }

    /**
     * The assigned entry
     *
     * @return the ContentEntryElement.
     */
    public ContentEntryElement getEntry() {
        return this.entry;
    }

    /**
     * The owner type of the assignment
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getOwner() {
        return this.owner;
    }

    /**
     * The owner type of the assignment
     *
     * @param owner the FunctionalIdentifier.
     */
    public void setOwner(FunctionalIdentifier owner) {
        this.owner = owner;
    }

    /**
     * The owner type of the assignment
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new FunctionalIdentifier();
        }
        this.owner.setValue(owner);
    }

    /**
     * The Unique identifier of the attachment
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getElementIdentifier() {
        return this.elementIdentifier;
    }

    /**
     * The Unique identifier of the attachment
     *
     * @param elementIdentifier the FunctionalIdentifier.
     */
    public void setElementIdentifier(FunctionalIdentifier elementIdentifier) {
        this.elementIdentifier = elementIdentifier;
    }

    /**
     * The Unique identifier of the attachment
     *
     * @param elementIdentifier the String.
     */
    public void setElementIdentifier(String elementIdentifier) {
        if ((this.elementIdentifier == null)) {
            if ((elementIdentifier == null)) {
                return;
            }
            this.elementIdentifier = new FunctionalIdentifier();
        }
        this.elementIdentifier.setValue(elementIdentifier);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentElement.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignmentElement.class).getAllProperties();
    }
}
