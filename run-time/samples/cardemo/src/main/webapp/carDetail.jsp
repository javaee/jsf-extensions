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

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;CHARSET=UTF-8" />
    <title>CarStore</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/stylesheet.css" />
</head>

<f:loadBundle basename="carstore.bundles.Resources" var="bundle"/>

<body bgcolor="white">

<f:view>

    <h:form prependId="false">


        <!-- non-option details -->

        <h:panelGrid columns="1"
                     summary="#{bundle.carDetails}"
                     title="#{bundle.carDetails}">

            <h:graphicImage url="/images/cardemo.jpg"/>

            <h:graphicImage
                  binding="#{carstore.currentModel.components.image}"/>

            <h:outputText styleClass="subtitlebig"
                          binding="#{carstore.currentModel.components.title}"/>

            <h:outputText
                  binding="#{carstore.currentModel.components.description}"/>

<jsfExt:ajaxZone id="zone1">

            <h:panelGrid columns="2">

                <h:outputText styleClass="subtitle"
                              value="#{bundle.basePriceLabel}"/>

                <h:outputText
                      binding="#{carstore.currentModel.components.basePrice}"/>

                <h:outputText styleClass="subtitle"
                              value="#{bundle.yourPriceLabel}"/>

                <h:outputText value="#{carstore.currentModel.currentPrice}"/>

            </h:panelGrid>

</jsfExt:ajaxZone>

            <h:commandButton action="#{carstore.buyCurrentCar}"
                             value="#{bundle.buy}"/>

        </h:panelGrid>

        <jsp:include page="optionsPanel.jsp"/>

        <h:commandButton action="#{carstore.buyCurrentCar}"
                         value="#{bundle.buy}"/>

    </h:form>

    <jsp:include page="bottomMatter.jsp"/>

</f:view>
</body>

</html>
</jsp:root>
