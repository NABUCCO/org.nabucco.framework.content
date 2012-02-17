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
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.content.Content;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;

/**
 * ContentMaster<p/>Persistent Content Master.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-11
 */
public class ContentMaster extends Content implements Datatype {

    private static final long serialVersionUID = 1L;

    private Long contentTypeRefId;

    /** Constructs a new ContentMaster instance. */
    public ContentMaster() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentMaster.
     */
    protected void cloneObject(ContentMaster clone) {
        super.cloneObject(clone);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Content.class).getPropertyMap());
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentMaster.getPropertyDescriptor(CONTENTTYPE), this.getContentType(),
                this.contentTypeRefId));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
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
        final ContentMaster other = ((ContentMaster) obj);
        if ((this.contentTypeRefId == null)) {
            if ((other.contentTypeRefId != null))
                return false;
        } else if ((!this.contentTypeRefId.equals(other.contentTypeRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.contentTypeRefId == null) ? 0 : this.contentTypeRefId.hashCode()));
        return result;
    }

    @Override
    public ContentMaster cloneObject() {
        ContentMaster clone = new ContentMaster();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Getter for the ContentTypeRefId.
     *
     * @return the Long.
     */
    public Long getContentTypeRefId() {
        return this.contentTypeRefId;
    }

    /**
     * Setter for the ContentTypeRefId.
     *
     * @param contentTypeRefId the Long.
     */
    public void setContentTypeRefId(Long contentTypeRefId) {
        this.contentTypeRefId = contentTypeRefId;
    }

    /**
     * Setter for the ContentType.
     *
     * @param contentType the Code.
     */
    public void setContentType(Code contentType) {
        super.setContentType(contentType);
        if ((contentType != null)) {
            this.setContentTypeRefId(contentType.getId());
        } else {
            this.setContentTypeRefId(null);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentMaster.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentMaster.class).getAllProperties();
    }
}
