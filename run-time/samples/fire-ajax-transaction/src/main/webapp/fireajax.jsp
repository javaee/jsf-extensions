<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/fireAjaxTrans"  prefix="g" %>

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

