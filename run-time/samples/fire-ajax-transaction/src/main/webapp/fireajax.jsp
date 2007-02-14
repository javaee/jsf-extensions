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

<h:panelGrid border="1" columns="2" cellspacing="10" headerClass="keytitle"> 
    <f:facet name="header">
        <h:outputText value="Command"/>
    </f:facet>
    <h:commandButton id="ajax1"
                     value="fireAjaxTransaction [ajax1]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax1,_1'}); return false;"
                     actionListener="#{bean.getOptions}"/>  
    <h:commandButton id="ajax2"
                     value="fireAjaxTransaction [ajax2]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax2,_6,_10'}); return false;"
                     actionListener="#{bean.getOptions}"/> 
    <h:commandButton id="ajax3"
                     value="fireAjaxTransaction [ajax3]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax3,_5'}); return false;"
                     actionListener="#{bean.getOptions}"/> 
    <h:commandButton id="ajax4"
                     value="fireAjaxTransaction [ajax4]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax4,_4,_9'}); return false;"
                     actionListener="#{bean.getOptions}"/>
    <h:commandButton id="ajax5"
                     value="fireAjaxTransaction [ajax5]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax5',render:'_1,optionsTitle,fireOptions'}); return false;"
                     actionListener="#{bean.getOptions}"/>
    <h:commandButton id="ajax6"
                     value="fireAjaxTransaction [ajax6]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax6',render:'_6,optionsTitle,fireOptions'}); return false;"
                     actionListener="#{bean.getOptions}"/>
    <h:commandButton id="ajax7"
                     value="fireAjaxTransaction [ajax7]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax7,_10',render:'_5,optionsTitle,fireOptions'}); return false;"
                     actionListener="#{bean.getOptions}"/> 
    <h:commandButton id="ajax8"
                     value="fireAjaxTransaction [ajax8]"
                     onclick="DynaFaces.fireAjaxTransaction(this,
                     {execute:'ajax8,_1,_2,_3,_4',render:'_1,_2,_3,_4,optionsTitle,fireOptions'}); return false;"
                     actionListener="#{bean.getOptions}"/> 
</h:panelGrid>

