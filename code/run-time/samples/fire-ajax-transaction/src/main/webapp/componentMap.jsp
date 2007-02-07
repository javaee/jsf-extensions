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
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="g" uri="http://java.sun.com/jsf/fireAjaxTrans" %>

<h:panelGrid border="1" headerClass="keytitle"> 
    <f:facet name="header">
        <h:outputText value="View"/>
    </f:facet>
    
    <g:panelGrid id="_0" border="1" columns="5">      
        <f:facet name="header">
            <h:outputText value="_0"/>
        </f:facet>
        <g:commandButton id="_1" value="_1" style="height:40px;width:40px;"/>
        <g:commandButton id="_2" value="_2" style="height:40px;width:40px;"/> 
        <g:commandButton id="_3" value="_3" style="height:40px;width:40px;"/>                                
        <g:commandButton id="_4" value="_4" style="height:40px;width:40px;"/>
        <g:panelGrid id="_5" border="1" columns="2">
            <f:facet name="header">
                <h:outputText value="_5"/>
            </f:facet>
            <g:panelGrid id="_6" border="1">
                <f:facet name="header">
                    <h:outputText value="_6"/>
                </f:facet>
                <g:commandButton id="_7" value="_7" style="height:40px;width:40px;"/>
                <g:commandButton id="_8" value="_8" style="height:40px;width:40px;"/>
            </g:panelGrid>
            <g:commandButton id="_9" value="_9" style="height:40px;width:40px;"/>
            <g:commandButton id="_10" value="_10" style="height:40px;width:40px;"/>
        </g:panelGrid>
    </g:panelGrid>
</h:panelGrid>
