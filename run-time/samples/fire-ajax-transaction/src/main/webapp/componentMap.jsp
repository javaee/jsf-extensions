<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/fireAjaxTrans"  prefix="g" %>

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
