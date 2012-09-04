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
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.content.facade.datatype.ContentMaster;
import org.nabucco.framework.content.facade.datatype.ContentRelation;

/**
 * ContentEntryElement<p/>Data Storage Definition Datatype<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-11
 */
public abstract class ContentEntryElement extends ContentEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final Version CONTENTENTRYVERSION_DEFAULT = new Version(0L);

    private static final StatusType STATUSTYPE_DEFAULT = StatusType.ACTIVE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l3,32;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l3,32;u0,n;m1,1;", "l0,255;u0,n;m0,1;", "m0,1;", "m1,1;", "l0,n;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "l0,n;u0,n;m1,1;", "m0,1;", "m0,1;", "m0,1;", "m0,n;" };

    public static final String OWNER = "owner";

    public static final String CREATIONTIME = "creationTime";

    public static final String CREATOR = "creator";

    public static final String MODIFICATIONTIME = "modificationTime";

    public static final String MODIFIER = "modifier";

    public static final String DESCRIPTION = "description";

    public static final String MASTER = "master";

    public static final String STATUSTYPE = "statusType";

    public static final String VALIDITYDATE = "validityDate";

    public static final String CONTENTENTRYID = "contentEntryId";

    public static final String CONTENTENTRYVERSION = "contentEntryVersion";

    public static final String CONTENTENTRYTYPE = "contentEntryType";

    public static final String CONTENTCATEGORYTYPE = "contentCategoryType";

    public static final String USAGETYPE = "usageType";

    public static final String CONTENTRELATIONS = "contentRelations";

    /** Owner of the content entry. */
    private Owner owner;

    /** Timestamp of the content creation. */
    private Timestamp creationTime;

    /** User ID of the content creation. */
    private UserId creator;

    /** Timestamp of the last content modification. */
    private Timestamp modificationTime;

    /** User ID of the last content modification. */
    private UserId modifier;

    /** Description of the content entry. */
    private Description description;

    /** Content Master */
    private ContentMaster master;

    /** Status of the Content Entry. */
    private StatusType statusType;

    /** Content entry validity date. */
    private Date validityDate;

    /** Functional Content Entry ID. */
    private FunctionalIdentifier contentEntryId;

    /** Version of the Content Entry. */
    private Version contentEntryVersion;

    /** Type of the Content Entry. */
    private Code contentEntryType;

    private Long contentEntryTypeRefId;

    protected static final String CONTENTENTRYTYPE_CODEPATH = "nabucco.framework.content.entrytype";

    /** Category Type of the Content Entry. */
    private Code contentCategoryType;

    private Long contentCategoryTypeRefId;

    protected static final String CONTENTCATEGORYTYPE_CODEPATH = "nabucco.framework.content.categorytype";

    /** The type of usage of content */
    private Code usageType;

    private Long usageTypeRefId;

    protected static final String USAGETYPE_CODEPATH = "nabucco.framework.content.usagetype";

    /** List of Content Relations. */
    private NabuccoList<ContentRelation> contentRelations;

    /** Constructs a new ContentEntryElement instance. */
    public ContentEntryElement() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        statusType = STATUSTYPE_DEFAULT;
        contentEntryVersion = CONTENTENTRYVERSION_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentEntryElement.
     */
    protected void cloneObject(ContentEntryElement clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getCreationTime() != null)) {
            clone.setCreationTime(this.getCreationTime().cloneObject());
        }
        if ((this.getCreator() != null)) {
            clone.setCreator(this.getCreator().cloneObject());
        }
        if ((this.getModificationTime() != null)) {
            clone.setModificationTime(this.getModificationTime().cloneObject());
        }
        if ((this.getModifier() != null)) {
            clone.setModifier(this.getModifier().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getMaster() != null)) {
            clone.setMaster(this.getMaster().cloneObject());
        }
        clone.setStatusType(this.getStatusType());
        if ((this.getValidityDate() != null)) {
            clone.setValidityDate(this.getValidityDate().cloneObject());
        }
        if ((this.getContentEntryId() != null)) {
            clone.setContentEntryId(this.getContentEntryId().cloneObject());
        }
        if ((this.getContentEntryVersion() != null)) {
            clone.setContentEntryVersion(this.getContentEntryVersion().cloneObject());
        }
        if ((this.getContentEntryType() != null)) {
            clone.setContentEntryType(this.getContentEntryType().cloneObject());
        }
        if ((this.getContentEntryTypeRefId() != null)) {
            clone.setContentEntryTypeRefId(this.getContentEntryTypeRefId());
        }
        if ((this.getContentCategoryType() != null)) {
            clone.setContentCategoryType(this.getContentCategoryType().cloneObject());
        }
        if ((this.getContentCategoryTypeRefId() != null)) {
            clone.setContentCategoryTypeRefId(this.getContentCategoryTypeRefId());
        }
        if ((this.getUsageType() != null)) {
            clone.setUsageType(this.getUsageType().cloneObject());
        }
        if ((this.getUsageTypeRefId() != null)) {
            clone.setUsageTypeRefId(this.getUsageTypeRefId());
        }
        if ((this.contentRelations != null)) {
            clone.contentRelations = this.contentRelations.cloneCollection();
        }
    }

    /**
     * Getter for the ContentRelationsJPA.
     *
     * @return the List<ContentRelation>.
     */
    List<ContentRelation> getContentRelationsJPA() {
        if ((this.contentRelations == null)) {
            this.contentRelations = new NabuccoListImpl<ContentRelation>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ContentRelation>) this.contentRelations).getDelegate();
    }

    /**
     * Setter for the ContentRelationsJPA.
     *
     * @param contentRelations the List<ContentRelation>.
     */
    void setContentRelationsJPA(List<ContentRelation> contentRelations) {
        if ((this.contentRelations == null)) {
            this.contentRelations = new NabuccoListImpl<ContentRelation>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ContentRelation>) this.contentRelations).setDelegate(contentRelations);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ContentEntry.class).getPropertyMap());
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 5, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CREATIONTIME, PropertyDescriptorSupport.createBasetype(CREATIONTIME, Timestamp.class, 6,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(CREATOR,
                PropertyDescriptorSupport.createBasetype(CREATOR, UserId.class, 7, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(MODIFICATIONTIME, PropertyDescriptorSupport.createBasetype(MODIFICATIONTIME, Timestamp.class,
                8, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(MODIFIER,
                PropertyDescriptorSupport.createBasetype(MODIFIER, UserId.class, 9, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 10,
                PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(MASTER, PropertyDescriptorSupport.createDatatype(MASTER, ContentMaster.class, 11,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STATUSTYPE, PropertyDescriptorSupport.createEnumeration(STATUSTYPE, StatusType.class, 12,
                PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(VALIDITYDATE,
                PropertyDescriptorSupport.createBasetype(VALIDITYDATE, Date.class, 13, PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(CONTENTENTRYID, PropertyDescriptorSupport.createBasetype(CONTENTENTRYID,
                FunctionalIdentifier.class, 14, PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(CONTENTENTRYVERSION, PropertyDescriptorSupport.createBasetype(CONTENTENTRYVERSION,
                Version.class, 15, PROPERTY_CONSTRAINTS[10], false));
        propertyMap.put(CONTENTENTRYTYPE, PropertyDescriptorSupport.createDatatype(CONTENTENTRYTYPE, Code.class, 16,
                PROPERTY_CONSTRAINTS[11], false, PropertyAssociationType.COMPONENT, CONTENTENTRYTYPE_CODEPATH));
        propertyMap.put(CONTENTCATEGORYTYPE, PropertyDescriptorSupport.createDatatype(CONTENTCATEGORYTYPE, Code.class,
                17, PROPERTY_CONSTRAINTS[12], false, PropertyAssociationType.COMPONENT, CONTENTCATEGORYTYPE_CODEPATH));
        propertyMap.put(USAGETYPE, PropertyDescriptorSupport.createDatatype(USAGETYPE, Code.class, 18,
                PROPERTY_CONSTRAINTS[13], false, PropertyAssociationType.COMPONENT, USAGETYPE_CODEPATH));
        propertyMap.put(CONTENTRELATIONS, PropertyDescriptorSupport.createCollection(CONTENTRELATIONS,
                ContentRelation.class, 19, PROPERTY_CONSTRAINTS[14], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CREATIONTIME), this.creationTime,
                null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CREATOR), this.creator, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(MODIFICATIONTIME),
                this.modificationTime, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(MODIFIER), this.modifier, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(MASTER), this.getMaster(), null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(STATUSTYPE),
                this.getStatusType(), null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(VALIDITYDATE), this.validityDate,
                null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CONTENTENTRYID),
                this.contentEntryId, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CONTENTENTRYVERSION),
                this.contentEntryVersion, null));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CONTENTENTRYTYPE),
                this.getContentEntryType(), this.contentEntryTypeRefId));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CONTENTCATEGORYTYPE),
                this.getContentCategoryType(), this.contentCategoryTypeRefId));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(USAGETYPE), this.getUsageType(),
                this.usageTypeRefId));
        properties.add(super.createProperty(ContentEntryElement.getPropertyDescriptor(CONTENTRELATIONS),
                this.contentRelations, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATIONTIME) && (property.getType() == Timestamp.class))) {
            this.setCreationTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATOR) && (property.getType() == UserId.class))) {
            this.setCreator(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATIONTIME) && (property.getType() == Timestamp.class))) {
            this.setModificationTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFIER) && (property.getType() == UserId.class))) {
            this.setModifier(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MASTER) && (property.getType() == ContentMaster.class))) {
            this.setMaster(((ContentMaster) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUSTYPE) && (property.getType() == StatusType.class))) {
            this.setStatusType(((StatusType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALIDITYDATE) && (property.getType() == Date.class))) {
            this.setValidityDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTENTRYID) && (property.getType() == FunctionalIdentifier.class))) {
            this.setContentEntryId(((FunctionalIdentifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTENTRYVERSION) && (property.getType() == Version.class))) {
            this.setContentEntryVersion(((Version) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTENTRYTYPE) && (property.getType() == Code.class))) {
            this.setContentEntryType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTCATEGORYTYPE) && (property.getType() == Code.class))) {
            this.setContentCategoryType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USAGETYPE) && (property.getType() == Code.class))) {
            this.setUsageType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTRELATIONS) && (property.getType() == ContentRelation.class))) {
            this.contentRelations = ((NabuccoList<ContentRelation>) property.getInstance());
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
        final ContentEntryElement other = ((ContentEntryElement) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.creationTime == null)) {
            if ((other.creationTime != null))
                return false;
        } else if ((!this.creationTime.equals(other.creationTime)))
            return false;
        if ((this.creator == null)) {
            if ((other.creator != null))
                return false;
        } else if ((!this.creator.equals(other.creator)))
            return false;
        if ((this.modificationTime == null)) {
            if ((other.modificationTime != null))
                return false;
        } else if ((!this.modificationTime.equals(other.modificationTime)))
            return false;
        if ((this.modifier == null)) {
            if ((other.modifier != null))
                return false;
        } else if ((!this.modifier.equals(other.modifier)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.master == null)) {
            if ((other.master != null))
                return false;
        } else if ((!this.master.equals(other.master)))
            return false;
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
            return false;
        if ((this.validityDate == null)) {
            if ((other.validityDate != null))
                return false;
        } else if ((!this.validityDate.equals(other.validityDate)))
            return false;
        if ((this.contentEntryId == null)) {
            if ((other.contentEntryId != null))
                return false;
        } else if ((!this.contentEntryId.equals(other.contentEntryId)))
            return false;
        if ((this.contentEntryVersion == null)) {
            if ((other.contentEntryVersion != null))
                return false;
        } else if ((!this.contentEntryVersion.equals(other.contentEntryVersion)))
            return false;
        if ((this.contentEntryType == null)) {
            if ((other.contentEntryType != null))
                return false;
        } else if ((!this.contentEntryType.equals(other.contentEntryType)))
            return false;
        if ((this.contentEntryTypeRefId == null)) {
            if ((other.contentEntryTypeRefId != null))
                return false;
        } else if ((!this.contentEntryTypeRefId.equals(other.contentEntryTypeRefId)))
            return false;
        if ((this.contentCategoryType == null)) {
            if ((other.contentCategoryType != null))
                return false;
        } else if ((!this.contentCategoryType.equals(other.contentCategoryType)))
            return false;
        if ((this.contentCategoryTypeRefId == null)) {
            if ((other.contentCategoryTypeRefId != null))
                return false;
        } else if ((!this.contentCategoryTypeRefId.equals(other.contentCategoryTypeRefId)))
            return false;
        if ((this.usageType == null)) {
            if ((other.usageType != null))
                return false;
        } else if ((!this.usageType.equals(other.usageType)))
            return false;
        if ((this.usageTypeRefId == null)) {
            if ((other.usageTypeRefId != null))
                return false;
        } else if ((!this.usageTypeRefId.equals(other.usageTypeRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.creationTime == null) ? 0 : this.creationTime.hashCode()));
        result = ((PRIME * result) + ((this.creator == null) ? 0 : this.creator.hashCode()));
        result = ((PRIME * result) + ((this.modificationTime == null) ? 0 : this.modificationTime.hashCode()));
        result = ((PRIME * result) + ((this.modifier == null) ? 0 : this.modifier.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.master == null) ? 0 : this.master.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.validityDate == null) ? 0 : this.validityDate.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryId == null) ? 0 : this.contentEntryId.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryVersion == null) ? 0 : this.contentEntryVersion.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryType == null) ? 0 : this.contentEntryType.hashCode()));
        result = ((PRIME * result) + ((this.contentEntryTypeRefId == null) ? 0 : this.contentEntryTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.contentCategoryType == null) ? 0 : this.contentCategoryType.hashCode()));
        result = ((PRIME * result) + ((this.contentCategoryTypeRefId == null) ? 0 : this.contentCategoryTypeRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.usageType == null) ? 0 : this.usageType.hashCode()));
        result = ((PRIME * result) + ((this.usageTypeRefId == null) ? 0 : this.usageTypeRefId.hashCode()));
        return result;
    }

    @Override
    public abstract ContentEntryElement cloneObject();

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
     * Owner of the content entry.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * Timestamp of the content creation.
     *
     * @return the Timestamp.
     */
    public Timestamp getCreationTime() {
        return this.creationTime;
    }

    /**
     * Timestamp of the content creation.
     *
     * @param creationTime the Timestamp.
     */
    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Timestamp of the content creation.
     *
     * @param creationTime the Long.
     */
    public void setCreationTime(Long creationTime) {
        if ((this.creationTime == null)) {
            if ((creationTime == null)) {
                return;
            }
            this.creationTime = new Timestamp();
        }
        this.creationTime.setValue(creationTime);
    }

    /**
     * User ID of the content creation.
     *
     * @return the UserId.
     */
    public UserId getCreator() {
        return this.creator;
    }

    /**
     * User ID of the content creation.
     *
     * @param creator the UserId.
     */
    public void setCreator(UserId creator) {
        this.creator = creator;
    }

    /**
     * User ID of the content creation.
     *
     * @param creator the String.
     */
    public void setCreator(String creator) {
        if ((this.creator == null)) {
            if ((creator == null)) {
                return;
            }
            this.creator = new UserId();
        }
        this.creator.setValue(creator);
    }

    /**
     * Timestamp of the last content modification.
     *
     * @return the Timestamp.
     */
    public Timestamp getModificationTime() {
        return this.modificationTime;
    }

    /**
     * Timestamp of the last content modification.
     *
     * @param modificationTime the Timestamp.
     */
    public void setModificationTime(Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * Timestamp of the last content modification.
     *
     * @param modificationTime the Long.
     */
    public void setModificationTime(Long modificationTime) {
        if ((this.modificationTime == null)) {
            if ((modificationTime == null)) {
                return;
            }
            this.modificationTime = new Timestamp();
        }
        this.modificationTime.setValue(modificationTime);
    }

    /**
     * User ID of the last content modification.
     *
     * @return the UserId.
     */
    public UserId getModifier() {
        return this.modifier;
    }

    /**
     * User ID of the last content modification.
     *
     * @param modifier the UserId.
     */
    public void setModifier(UserId modifier) {
        this.modifier = modifier;
    }

    /**
     * User ID of the last content modification.
     *
     * @param modifier the String.
     */
    public void setModifier(String modifier) {
        if ((this.modifier == null)) {
            if ((modifier == null)) {
                return;
            }
            this.modifier = new UserId();
        }
        this.modifier.setValue(modifier);
    }

    /**
     * Description of the content entry.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the content entry.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the content entry.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Content Master
     *
     * @param master the ContentMaster.
     */
    public void setMaster(ContentMaster master) {
        this.master = master;
    }

    /**
     * Content Master
     *
     * @return the ContentMaster.
     */
    public ContentMaster getMaster() {
        return this.master;
    }

    /**
     * Status of the Content Entry.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * Status of the Content Entry.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * Status of the Content Entry.
     *
     * @param statusType the String.
     */
    public void setStatusType(String statusType) {
        if ((statusType == null)) {
            this.statusType = null;
        } else {
            this.statusType = StatusType.valueOf(statusType);
        }
    }

    /**
     * Content entry validity date.
     *
     * @return the Date.
     */
    public Date getValidityDate() {
        return this.validityDate;
    }

    /**
     * Content entry validity date.
     *
     * @param validityDate the Date.
     */
    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    /**
     * Content entry validity date.
     *
     * @param validityDate the java.util.Date.
     */
    public void setValidityDate(java.util.Date validityDate) {
        if ((this.validityDate == null)) {
            if ((validityDate == null)) {
                return;
            }
            this.validityDate = new Date();
        }
        this.validityDate.setValue(validityDate);
    }

    /**
     * Functional Content Entry ID.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getContentEntryId() {
        return this.contentEntryId;
    }

    /**
     * Functional Content Entry ID.
     *
     * @param contentEntryId the FunctionalIdentifier.
     */
    public void setContentEntryId(FunctionalIdentifier contentEntryId) {
        this.contentEntryId = contentEntryId;
    }

    /**
     * Functional Content Entry ID.
     *
     * @param contentEntryId the String.
     */
    public void setContentEntryId(String contentEntryId) {
        if ((this.contentEntryId == null)) {
            if ((contentEntryId == null)) {
                return;
            }
            this.contentEntryId = new FunctionalIdentifier();
        }
        this.contentEntryId.setValue(contentEntryId);
    }

    /**
     * Version of the Content Entry.
     *
     * @return the Version.
     */
    public Version getContentEntryVersion() {
        return this.contentEntryVersion;
    }

    /**
     * Version of the Content Entry.
     *
     * @param contentEntryVersion the Version.
     */
    public void setContentEntryVersion(Version contentEntryVersion) {
        this.contentEntryVersion = contentEntryVersion;
    }

    /**
     * Version of the Content Entry.
     *
     * @param contentEntryVersion the Long.
     */
    public void setContentEntryVersion(Long contentEntryVersion) {
        if ((this.contentEntryVersion == null)) {
            if ((contentEntryVersion == null)) {
                return;
            }
            this.contentEntryVersion = new Version();
        }
        this.contentEntryVersion.setValue(contentEntryVersion);
    }

    /**
     * Type of the Content Entry.
     *
     * @param contentEntryType the Code.
     */
    public void setContentEntryType(Code contentEntryType) {
        this.contentEntryType = contentEntryType;
        if ((contentEntryType != null)) {
            this.setContentEntryTypeRefId(contentEntryType.getId());
        } else {
            this.setContentEntryTypeRefId(null);
        }
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
     * Getter for the ContentEntryTypeRefId.
     *
     * @return the Long.
     */
    public Long getContentEntryTypeRefId() {
        return this.contentEntryTypeRefId;
    }

    /**
     * Setter for the ContentEntryTypeRefId.
     *
     * @param contentEntryTypeRefId the Long.
     */
    public void setContentEntryTypeRefId(Long contentEntryTypeRefId) {
        this.contentEntryTypeRefId = contentEntryTypeRefId;
    }

    /**
     * Category Type of the Content Entry.
     *
     * @param contentCategoryType the Code.
     */
    public void setContentCategoryType(Code contentCategoryType) {
        this.contentCategoryType = contentCategoryType;
        if ((contentCategoryType != null)) {
            this.setContentCategoryTypeRefId(contentCategoryType.getId());
        } else {
            this.setContentCategoryTypeRefId(null);
        }
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
     * Getter for the ContentCategoryTypeRefId.
     *
     * @return the Long.
     */
    public Long getContentCategoryTypeRefId() {
        return this.contentCategoryTypeRefId;
    }

    /**
     * Setter for the ContentCategoryTypeRefId.
     *
     * @param contentCategoryTypeRefId the Long.
     */
    public void setContentCategoryTypeRefId(Long contentCategoryTypeRefId) {
        this.contentCategoryTypeRefId = contentCategoryTypeRefId;
    }

    /**
     * The type of usage of content
     *
     * @param usageType the Code.
     */
    public void setUsageType(Code usageType) {
        this.usageType = usageType;
        if ((usageType != null)) {
            this.setUsageTypeRefId(usageType.getId());
        } else {
            this.setUsageTypeRefId(null);
        }
    }

    /**
     * The type of usage of content
     *
     * @return the Code.
     */
    public Code getUsageType() {
        return this.usageType;
    }

    /**
     * Getter for the UsageTypeRefId.
     *
     * @return the Long.
     */
    public Long getUsageTypeRefId() {
        return this.usageTypeRefId;
    }

    /**
     * Setter for the UsageTypeRefId.
     *
     * @param usageTypeRefId the Long.
     */
    public void setUsageTypeRefId(Long usageTypeRefId) {
        this.usageTypeRefId = usageTypeRefId;
    }

    /**
     * List of Content Relations.
     *
     * @return the NabuccoList<ContentRelation>.
     */
    public NabuccoList<ContentRelation> getContentRelations() {
        if ((this.contentRelations == null)) {
            this.contentRelations = new NabuccoListImpl<ContentRelation>(NabuccoCollectionState.INITIALIZED);
        }
        return this.contentRelations;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryElement.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryElement.class).getAllProperties();
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

    /**
     * Getter for the UsageTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getUsageTypeCodePath() {
        return new CodePath(USAGETYPE_CODEPATH);
    }
}
