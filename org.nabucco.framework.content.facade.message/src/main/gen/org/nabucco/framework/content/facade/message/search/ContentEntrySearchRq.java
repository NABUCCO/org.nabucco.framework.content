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
package org.nabucco.framework.content.facade.message.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
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
 * ContentEntrySearchRq<p/>List message for ContentEntry instances<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-06-15
 */
public class ContentEntrySearchRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final Flag LATESTVERSION_DEFAULT = new Flag(true);

    private static final StatusType STATUSTYPE_DEFAULT = StatusType.ACTIVE;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l3,12;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "m0,1;",
            "m0,1;", "m0,1;", "l0,n;u0,n;m1,1;" };

    public static final String STATUSTYPE = "statusType";

    public static final String OWNER = "owner";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String CONTENTENTRYTYPE = "contentEntryType";

    public static final String CONTENTCATEGORYTYPE = "contentCategoryType";

    public static final String LATESTVERSION = "latestVersion";

    /** Status of the content entry. */
    private StatusType statusType;

    /** Owner of the content entry. */
    private Owner owner;

    /** Name of the content entry. */
    private Name name;

    /** Type of the content entry. */
    private ContentEntryType type;

    /** Type of the Content Entry. */
    private Code contentEntryType;

    protected static final String CONTENTENTRYTYPE_CODEPATH = "nabucco.framework.content.entrytype";

    /** Category Type of the Content Entry. */
    private Code contentCategoryType;

    protected static final String CONTENTCATEGORYTYPE_CODEPATH = "nabucco.framework.content.categorytype";

    /** Search only for the latest version of a content entry. */
    protected Flag latestVersion;

    /** Constructs a new ContentEntrySearchRq instance. */
    public ContentEntrySearchRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        statusType = STATUSTYPE_DEFAULT;
        latestVersion = LATESTVERSION_DEFAULT;
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(STATUSTYPE, PropertyDescriptorSupport.createEnumeration(STATUSTYPE, StatusType.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, ContentEntryType.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(CONTENTENTRYTYPE, PropertyDescriptorSupport.createDatatype(CONTENTENTRYTYPE, Code.class, 4,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPONENT, CONTENTENTRYTYPE_CODEPATH));
        propertyMap.put(CONTENTCATEGORYTYPE, PropertyDescriptorSupport.createDatatype(CONTENTCATEGORYTYPE, Code.class,
                5, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPONENT, CONTENTCATEGORYTYPE_CODEPATH));
        propertyMap.put(LATESTVERSION,
                PropertyDescriptorSupport.createBasetype(LATESTVERSION, Flag.class, 6, PROPERTY_CONSTRAINTS[6], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(STATUSTYPE),
                this.getStatusType()));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(OWNER), this.owner));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(NAME), this.name));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(TYPE), this.getType()));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(CONTENTENTRYTYPE),
                this.getContentEntryType()));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(CONTENTCATEGORYTYPE),
                this.getContentCategoryType()));
        properties.add(super.createProperty(ContentEntrySearchRq.getPropertyDescriptor(LATESTVERSION),
                this.latestVersion));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(STATUSTYPE) && (property.getType() == StatusType.class))) {
            this.setStatusType(((StatusType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == ContentEntryType.class))) {
            this.setType(((ContentEntryType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTENTRYTYPE) && (property.getType() == Code.class))) {
            this.setContentEntryType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTCATEGORYTYPE) && (property.getType() == Code.class))) {
            this.setContentCategoryType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LATESTVERSION) && (property.getType() == Flag.class))) {
            this.setLatestVersion(((Flag) property.getInstance()));
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
        final ContentEntrySearchRq other = ((ContentEntrySearchRq) obj);
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.contentEntryType == null)) {
            if ((other.contentEntryType != null))
                return false;
        } else if ((!this.contentEntryType.equals(other.contentEntryType)))
            return false;
        if ((this.contentCategoryType == null)) {
            if ((other.contentCategoryType != null))
                return false;
        } else if ((!this.contentCategoryType.equals(other.contentCategoryType)))
            return false;
        if ((this.latestVersion == null)) {
            if ((other.latestVersion != null))
                return false;
        } else if ((!this.latestVersion.equals(other.latestVersion)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryType == null) ? 0 : this.contentEntryType.hashCode()));
        result = ((PRIME * result) + ((this.contentCategoryType == null) ? 0 : this.contentCategoryType.hashCode()));
        result = ((PRIME * result) + ((this.latestVersion == null) ? 0 : this.latestVersion.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Status of the content entry.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * Status of the content entry.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * Owner of the content entry.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the content entry.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Name of the content entry.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the content entry.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Type of the content entry.
     *
     * @return the ContentEntryType.
     */
    public ContentEntryType getType() {
        return this.type;
    }

    /**
     * Type of the content entry.
     *
     * @param type the ContentEntryType.
     */
    public void setType(ContentEntryType type) {
        this.type = type;
    }

    /**
     * Type of the Content Entry.
     *
     * @return the Code.
     */
    public Code getContentEntryType() {
        return this.contentEntryType;
    }

    /**
     * Type of the Content Entry.
     *
     * @param contentEntryType the Code.
     */
    public void setContentEntryType(Code contentEntryType) {
        this.contentEntryType = contentEntryType;
    }

    /**
     * Category Type of the Content Entry.
     *
     * @return the Code.
     */
    public Code getContentCategoryType() {
        return this.contentCategoryType;
    }

    /**
     * Category Type of the Content Entry.
     *
     * @param contentCategoryType the Code.
     */
    public void setContentCategoryType(Code contentCategoryType) {
        this.contentCategoryType = contentCategoryType;
    }

    /**
     * Search only for the latest version of a content entry.
     *
     * @return the Flag.
     */
    public Flag getLatestVersion() {
        return this.latestVersion;
    }

    /**
     * Search only for the latest version of a content entry.
     *
     * @param latestVersion the Flag.
     */
    public void setLatestVersion(Flag latestVersion) {
        this.latestVersion = latestVersion;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntrySearchRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntrySearchRq.class).getAllProperties();
    }

    /**
     * Getter for the ContentEntryTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getContentEntryTypeCodePath() {
        return new CodePath(CONTENTENTRYTYPE_CODEPATH);
    }

    /**
     * Getter for the ContentCategoryTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getContentCategoryTypeCodePath() {
        return new CodePath(CONTENTCATEGORYTYPE_CODEPATH);
    }
}
