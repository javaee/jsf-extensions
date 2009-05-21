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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * An implementation of TagLibFileGenerator that creates the taglib file by
 * merging component info with a template file.
 * 
 * @author gjmurphy
 */
class TldFileGeneratorImpl extends TldFileGenerator {
    
    static final String TEMPLATE = "com/sun/faces/mirror/generator/Tld.template";
    
    VelocityEngine velocityEngine;
    
    
    TldFileGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            Set<DeclaredComponentInfo> componentInfoSet = new TreeSet(new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    String tag1 = ((DeclaredComponentInfo) obj1).getTagName();
                    String tag2 = ((DeclaredComponentInfo) obj2).getTagName();
                    if (tag1 == null) {
                        if (tag2 == null)
                            return 0;
                        return -1;
                    } else {
                        return tag1.compareTo(tag2);
                    }
                }
            });
            componentInfoSet.addAll(this.getDeclaredComponentInfoSet());
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            PrintWriter printWriter = this.getPrintWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("escaper", this.getEscaper());
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("componentInfoSet", componentInfoSet);
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = this.velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
    public String getFileName() {
        return "taglib.tld";
    }
    
    @Override
    public String getDirectoryName() {
        return "META-INF";
    }
    
}
