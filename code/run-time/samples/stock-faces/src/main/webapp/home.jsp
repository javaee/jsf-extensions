<?xml version="1.0" encoding="UTF-8" ?>
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
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <link rel="stylesheet" href="stylesheet.css" type="text/css" />
 <title>fireAjaxTransaction / queueEvent Example</title>
<jsfExt:scripts/>
<script type="text/javascript">
var js;
function include_js(file) {
    var html_doc = document.getElementsByTagName('head')[0];
    js = document.createElement('script');
    js.setAttribute('type', 'text/javascript');
    js.setAttribute('src', file);
    html_doc.appendChild(js);
    js.onreadystatechange = function () {
        if (js.readyState == 'complete') {
        }
    }
    js.onload = function () {
    }
    return false;
}
include_js('javascripts/stock-faces.js');
</script>
</head>
<body>
  <h:form id="form" prependId="false">
    <h:panelGrid border="1" columns="1" styleClass="panel-input-border">
        <h:panelGrid border="1" columns="7">
            <h:outputText value="Symbol:"/>
            <h:inputText id="symbol"/>
            <h:commandButton id="search" value="Search" 
                onclick="DynaFaces.fireAjaxTransaction(this, {});return false;"
                actionListener="#{bean.getStockInfo}" />
            <h:outputText value="Proxy Host:"/>
            <h:inputText id="proxyHost"/>
            <h:outputText value="Proxy Port:"/>
            <h:inputText id="proxyPort"/>
            <h:outputText value="Streaming:"/>
            <h:selectOneMenu id="streaming" value="Off" 
                onchange="toggleStreaming()">
                <f:selectItem itemValue="Off" itemLabel="Off"/>
                <f:selectItem itemValue="On" itemLabel="On"/>
            </h:selectOneMenu>
            <h:selectOneMenu id="connection" value="Local"> 
                <f:selectItem itemValue="Local" itemLabel="Local"/>
                <f:selectItem itemValue="Remote" itemLabel="Remote"/>
            </h:selectOneMenu>
        </h:panelGrid>
    </h:panelGrid>
          
    <h:panelGrid id="stockdata" border="1" columns="8" 
        styleClass="panel-data-border" rendered="false">
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Symbol"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Name"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Open"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Last"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value=""/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Change"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Change %"/>
            </f:facet>
        </h:panelGrid>
        <h:panelGrid>
            <f:facet name="header">
                <h:outputText value="Volume"/>
            </f:facet>
        </h:panelGrid>
    </h:panelGrid>
           
  </h:form>
</body>
</html>
</f:view>
</jsp:root>
