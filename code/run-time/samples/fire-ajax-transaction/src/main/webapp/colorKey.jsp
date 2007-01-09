<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>

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
