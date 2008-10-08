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

import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.DeclaredRendererInfo;
import com.sun.mirror.apt.Messager;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Set;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * An implementation of FacesConfigFileGenerator that creates the config file by
 * merging component and renderer info with a template file.
 * 
 * 
 * @author gjmurphy
 */
class FacesConfigFileGeneratorImpl extends FacesConfigFileGenerator {
    
    static final String TEMPLATE = "com/sun/faces/mirror/generator/FacesConfig.template";
    
    VelocityEngine velocityEngine;
    
    FacesConfigFileGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        Set<DeclaredComponentInfo> componentInfoSet = this.getDeclaredComponentInfoSet();        
        Set<DeclaredRendererInfo> rendererInfoSet = this.getDeclaredRendererInfoSet();
        Set<String> propertyResolverNameSet = this.getDeclaredPropertyResolverNameSet();
        Set<String> variableResolverNameSet = this.getDeclaredVariableResolverNameSet();
        Set<String> javaeeResolverNameSet = this.getDeclaredJavaEEResolverNameSet();
        PrintWriter printWriter = this.getPrintWriter();
        if (componentInfoSet.size() == 0 && rendererInfoSet.size() == 0)
            return;
        try {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("componentInfoSet", componentInfoSet);
            velocityContext.put("rendererInfoSet", rendererInfoSet);
            velocityContext.put("propertyResolverNameSet", propertyResolverNameSet);
            velocityContext.put("variableResolverNameSet", variableResolverNameSet);
            velocityContext.put("javaeeResolverNameSet", javaeeResolverNameSet);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
    public String getFileName() {
        return "faces-config.xml";
    }
    
    @Override
    public String getDirectoryName() {
        return "META-INF";
    }
    
}
