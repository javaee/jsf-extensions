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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import java.io.File;
import java.io.PrintWriter;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
public class GeneratorFactory {
    
    VelocityEngine velocityEngine;
    
    public GeneratorFactory() {
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.addProperty("resource.loader", "classpath");
        this.velocityEngine.addProperty("classpath.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try {
            this.velocityEngine.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public FacesConfigFileGenerator getFacesConfigFileGenerator() {
        return new FacesConfigFileGeneratorImpl(this.velocityEngine);
    }     
    
    public FaceletsConfigFileGenerator getFaceletsConfigFileGenerator() {
        return new FaceletsConfigFileGeneratorImpl(this.velocityEngine);
    }
    
    public BeanInfoSourceGenerator getBeanInfoSourceGenerator() {
        return new BeanInfoSourceGeneratorImpl(this.velocityEngine);
    }
    
    public TagLibFileGenerator getTagLibFileGenerator() {
        return new TagLibFileGeneratorImpl(this.velocityEngine);
    }
    
    public TagSourceGenerator getTagSourceGenerator() {
        return new TagSourceGeneratorImpl(this.velocityEngine);
    }
    
    public DebugGenerator getDebugGenerator() {
        return new DebugGenerator(this.velocityEngine);
    }
    
}
