<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:jsfExt="http://java.sun.com/jsf/extensions/dynafaces">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    
<!--
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 2 only ("GPL") or the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License. You can obtain
 a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 Sun designates this particular file as subject to the "Classpath" exception
 as provided by Sun in the GPL Version 2 section of the License file that
 accompanied this code.  If applicable, add the following below the License
 Header, with the fields enclosed by brackets [] replaced by your own
 identifying information: "Portions Copyrighted [year]
 [name of copyright owner]"

 Contributor(s):

 If you wish your version of this file to be governed by only the CDDL or
 only the GPL Version 2, indicate your decision by adding "[Contributor]
 elects to include this software in this distribution under the [CDDL or GPL
 Version 2] license."  If you don't indicate a single choice of license, a
 recipient has the option to distribute your version of this file under
 either the CDDL, the GPL Version 2 or to extend the choice of license to
 its licensees as provided above.  However, if you add GPL Version 2 code
 and therefore, elected the GPL Version 2 license, then the option applies
 only if the new code is made subject to such option by the copyright
 holder.
-->

    <f:view>   
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html;CHARSET=UTF-8" />
                <title>Dynamic Faces fireAjaxTransaction</title>
                <link rel="stylesheet" type="text/css"
                      href="${pageContext.request.contextPath}/stylesheet.css" />
                <jsfExt:scripts />
            </head>
            <body  bgcolor="white">
                <h:form id="form" prependId="false">  
                    <h:panelGrid styleClass="title-panel">
                        <h:outputText value="f i r e A j a x T r a n s a c t i o n" styleClass="title-panel-text"/>
                        <h:outputText value="Powered By Dynamic Faces" styleClass="title-panel-subtext"/>
                    </h:panelGrid>

                    <jsp:include page="colorKey.jsp"/> 
                    
                    <h:panelGrid columns="2" cellspacing="30">     
                        <jsp:include page="fireajax.jsp"/>             
                        <jsp:include page="componentMap.jsp"/>             
                    </h:panelGrid>
                    
                    <h:panelGrid border="1" columns="2" cellspacing="40">
                        <h:commandButton id="reset"
                                         value="reset"
                                         onclick="DynaFaces.fireAjaxTransaction(this,
                                         {execute:'reset'}); return false;"
                                         actionListener="#{bean.reset}"/> 
                        <h:panelGrid columns="2">
                            <h:panelGrid id="optionsTitle">
                                <h:outputText value="Options: " styleClass="options-prompt"/>
                            </h:panelGrid>
                            <h:panelGrid id="fireOptions">                         
                                <h:outputText value="#{bean.fireOptions}" styleClass="options"/>                        
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>              
                    
                </h:form>  
                
            </body>
        </html>
    </f:view>
    
</jsp:root>
