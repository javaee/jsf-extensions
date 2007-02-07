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

<f:loadBundle basename="fireAjaxTrans.Resources" var="bundle"/>
<h:panelGrid cellspacing="30" columns="2">
    <h:panelGrid border="1">
        <f:facet name="header">
            <h:outputText value="Color Key" styleClass="keytitle"/>
        </f:facet>
        <h:panelGrid columns="3">
            <h:panelGrid styleClass="initial-render">
                <f:facet name="header">
                    <h:outputText value="Initial Render" styleClass="keytitle"/>
                </f:facet>
            </h:panelGrid>
            <h:panelGrid styleClass="execute">
                <f:facet name="header">
                    <h:outputText value="Execute Phase" styleClass="keytitle"/>
                </f:facet>
            </h:panelGrid>
            <h:panelGrid styleClass="render">
                <f:facet name="header">
                    <h:outputText value="Render Phase" styleClass="keytitle"/>
                </f:facet>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
    <h:panelGrid border="1">
        <f:facet name="header">
            <h:outputText value="Instructions" styleClass="keytitle"/>
        </f:facet>
        <h:outputText value="#{bundle.instructions}" escape="false"/>
    </h:panelGrid>
</h:panelGrid>
