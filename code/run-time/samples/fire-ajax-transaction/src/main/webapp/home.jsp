<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces" %>

<f:view>
    <html>
        <head>
            <link rel="stylesheet" type="text/css"
                  href="${pageContext.request.contextPath}/stylesheet.css">
            <title>DynaFaces.fireAjaxTransaction</title>
            <jsfExt:scripts />
        </head>
        <body  bgcolor="white">
            
            <h:form id="form" prependId="false">  
                
                <h1>DynaFaces.fireAjaxTransaction</h1>
                
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
