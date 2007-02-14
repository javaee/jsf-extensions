<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:jsfExt="http://java.sun.com/jsf/extensions/dynafaces">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    
    <!--
The contents of this file are subject to the terms
of the Common Development and Distribution License
(the License). You may not use this file except in
compliance with the License.

You can obtain a copy of the License at
https://javaserverfaces.dev.java.net/CDDL.html or
legal/CDDLv1.0.txt.
See the License for the specific language governing
permission and limitations under the License.

When distributing Covered Code, include this CDDL
Header Notice in each file and include the License file
at legal/CDDLv1.0.txt.
If applicable, add the following below the CDDL Header,
with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

[Name of File] [ver.__] [Date]

Copyright 2005 Sun Microsystems Inc. All Rights Reserved
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
                    
                    <h1>Dynamic Faces fireAjaxTransaction</h1>
                    
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
