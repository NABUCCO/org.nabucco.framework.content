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
package org.nabucco.framework.content.facade.component;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for ContentComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class ContentComponentLocator extends ComponentLocatorSupport<ContentComponent> implements
        ComponentLocator<ContentComponent> {

    private static ContentComponentLocator instance;

    /**
     * Constructs a new ContentComponentLocator instance.
     *
     * @param component the Class<ContentComponent>.
     * @param jndiName the String.
     */
    private ContentComponentLocator(String jndiName, Class<ContentComponent> component) {
        super(jndiName, component);
    }

    @Override
    public ContentComponent getComponent() throws ConnectionException {
        ContentComponent component = super.getComponent();
        if ((component instanceof ContentComponentLocal)) {
            return new ContentComponentLocalProxy(((ContentComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the ContentComponentLocator.
     */
    public static ContentComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new ContentComponentLocator(ContentComponent.JNDI_NAME, ContentComponent.class);
        }
        return instance;
    }
}
