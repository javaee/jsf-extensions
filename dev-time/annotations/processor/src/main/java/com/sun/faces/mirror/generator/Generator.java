/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.faces.mirror.generator;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Abstract base class for all source file and config file generators.
 *
 * @author gjmurphy
 */
abstract public class Generator {
    
    /**
     * The generate method is invoked by the processor, after the {@code printWriter}
     * property has been set. The generator should write the entire contents of its 
     * output file to the print writer when this method is called. A generator's properties
     * will always be initialized before it is asked to generate its source. A generator
     * may be invoked more than once. The {@code printWriter} will be reset before each
     * call to generate.
     */
    abstract public void generate() throws GeneratorException;

    private PrintWriter printWriter;

    /**
     * Protected getter for property printWriter, used by subclasses to obtain the
     * print writer to use during generation.
     */
    protected PrintWriter getPrintWriter() {
        return this.printWriter;
    }

    /**
     * Setter for property printWriter. The annotation processor will set the print
     * writer before each call to {@link #generate}.
     */
    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    
    /**
     * Returns an escaper appropriate for the format of the output file. The default
     * return value is null, indicating that no escaping is necessary.
     */
    protected Escaper getEscaper() {
      return null;
    }
    
}
