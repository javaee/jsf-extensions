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
/**
 * Generator classes that write configuration files and source files based on
 * the component and renderer info found during annotation processing.
 *
 * <p>A generator is invoked in three phases. First, the generator's {@code init()}
 * method will be called, and component or renderer info will be passed to the
 * generator. The exact parameters vary from generator to generator. Next, the
 * annotation processor will ask the generator for information about the 
 * configuration or source file to be created, such as its name. Finally, the
 * annotation process will create an appropriate writer to which the file may
 * be written, and call {@code Generator.generate(PrintWriter)}.
 */
package com.sun.faces.mirror.generator;