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

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ContentProduceException<p/>Thrown in case of problems with producing a content entry<p/>
 *
 * @version 1.0
 * @author Frank Hardy, PRODYNA AG, 2010-10-19
 */
public class ContentProduceException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ContentProduceException instance. */
    public ContentProduceException() {
        super();
    }

    /**
     * Constructs a new ContentProduceException instance.
     *
     * @param message the String.
     */
    public ContentProduceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ContentProduceException instance.
     *
     * @param cause the Throwable.
     */
    public ContentProduceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ContentProduceException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public ContentProduceException(String message, Throwable cause) {
        super(message, cause);
    }
}
