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

/**
 * Base generator for configuration files. Generators for configuration files
 * must provide a relative path to the file to be generated, reported separately
 * as the directory name and file name.
 *
 * @author gjmurphy
 */
abstract public class FileGenerator extends Generator {
    
    /**
     * Read-only property by which this file generator reports the name of the file
     * to which it should write.
     */
    abstract public String getFileName();
    
    /**
     * Read-only property by which this file generator reports the name of the directory
     * to which its file should be written.
     */
    abstract public String getDirectoryName();
    
}
