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
package org.nabucco.framework.content.facade.exception;

import org.nabucco.framework.content.facade.exception.ContentStorageException;

/**
 * ContentNotFoundException<p/>Thrown if content could not be found<p/>
 *
 * @version 1.0
 * @author Juergen Weismueller, PRODYNA AG, 2011-04-15
 */
public class ContentNotFoundException extends ContentStorageException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ContentNotFoundException instance. */
    public ContentNotFoundException() {
        super();
    }

    /**
     * Constructs a new ContentNotFoundException instance.
     *
     * @param message the String.
     */
    public ContentNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ContentNotFoundException instance.
     *
     * @param cause the Throwable.
     */
    public ContentNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ContentNotFoundException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public ContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
