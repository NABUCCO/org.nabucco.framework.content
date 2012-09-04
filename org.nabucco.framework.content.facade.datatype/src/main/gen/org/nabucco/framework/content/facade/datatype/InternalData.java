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
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.file.FileExtension;
import org.nabucco.framework.base.facade.datatype.file.MimeType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * InternalData<p/>Storage in internal Database.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-11
 */
public class InternalData extends ContentEntryElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final MimeType MIMETYPE_DEFAULT = new MimeType("TEXT");

    private static final ContentEntryType TYPE_DEFAULT = ContentEntryType.INTERNAL_DATA;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,128;u0,n;m0,1;", "m0,1;" };

    public static final String FILEEXTENSION = "fileExtension";

    public static final String MIMETYPE = "mimeType";

    public static final String DATA = "data";

    /** File Extension of the Content Entry. */
    private FileExtension fileExtension;

    /** MimeType of the Contentn Item. */
    private MimeType mimeType;

    /** Data of the content entry. */
    private ContentData data;

    /** Constructs a new InternalData instance. */
    public InternalData() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        type = TYPE_DEFAULT;
        mimeType = MIMETYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the InternalData.
     */
    protected void cloneObject(InternalData clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getFileExtension() != null)) {
            clone.setFileExtension(this.getFileExtension().cloneObject());
        }
        if ((this.getMimeType() != null)) {
            clone.setMimeType(this.getMimeType().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ContentEntryElement.class).getPropertyMap());
        propertyMap.put(FILEEXTENSION, PropertyDescriptorSupport.createBasetype(FILEEXTENSION, FileExtension.class, 20,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(MIMETYPE,
                PropertyDescriptorSupport.createBasetype(MIMETYPE, MimeType.class, 21, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DATA, PropertyDescriptorSupport.createDatatype(DATA, ContentData.class, 22,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(InternalData.getPropertyDescriptor(FILEEXTENSION), this.fileExtension, null));
        properties.add(super.createProperty(InternalData.getPropertyDescriptor(MIMETYPE), this.mimeType, null));
        properties.add(super.createProperty(InternalData.getPropertyDescriptor(DATA), this.getData(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FILEEXTENSION) && (property.getType() == FileExtension.class))) {
            this.setFileExtension(((FileExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MIMETYPE) && (property.getType() == MimeType.class))) {
            this.setMimeType(((MimeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATA) && (property.getType() == ContentData.class))) {
            this.setData(((ContentData) property.getInstance()));
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
        final InternalData other = ((InternalData) obj);
        if ((this.fileExtension == null)) {
            if ((other.fileExtension != null))
                return false;
        } else if ((!this.fileExtension.equals(other.fileExtension)))
            return false;
        if ((this.mimeType == null)) {
            if ((other.mimeType != null))
                return false;
        } else if ((!this.mimeType.equals(other.mimeType)))
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
        result = ((PRIME * result) + ((this.fileExtension == null) ? 0 : this.fileExtension.hashCode()));
        result = ((PRIME * result) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        return result;
    }

    @Override
    public InternalData cloneObject() {
        InternalData clone = new InternalData();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * File Extension of the Content Entry.
     *
     * @return the FileExtension.
     */
    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    /**
     * File Extension of the Content Entry.
     *
     * @param fileExtension the FileExtension.
     */
    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * File Extension of the Content Entry.
     *
     * @param fileExtension the String.
     */
    public void setFileExtension(String fileExtension) {
        if ((this.fileExtension == null)) {
            if ((fileExtension == null)) {
                return;
            }
            this.fileExtension = new FileExtension();
        }
        this.fileExtension.setValue(fileExtension);
    }

    /**
     * MimeType of the Contentn Item.
     *
     * @return the MimeType.
     */
    public MimeType getMimeType() {
        return this.mimeType;
    }

    /**
     * MimeType of the Contentn Item.
     *
     * @param mimeType the MimeType.
     */
    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * MimeType of the Contentn Item.
     *
     * @param mimeType the String.
     */
    public void setMimeType(String mimeType) {
        if ((this.mimeType == null)) {
            if ((mimeType == null)) {
                return;
            }
            this.mimeType = new MimeType();
        }
        this.mimeType.setValue(mimeType);
    }

    /**
     * Data of the content entry.
     *
     * @param data the ContentData.
     */
    public void setData(ContentData data) {
        this.data = data;
    }

    /**
     * Data of the content entry.
     *
     * @return the ContentData.
     */
    public ContentData getData() {
        return this.data;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InternalData.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InternalData.class).getAllProperties();
    }
}
