<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

 
<f:view>
<html>
<head>
  <title>Fruit Varieties: Ajax Zones Example</title>
  <jsfExt:scripts />
</head>
<body  bgcolor="white">
 
<h:form prependId="false" id="form"> 
 
  <h1>Fruit Varieties: Ajax Zones Example</h1>

   <h:panelGrid columns="2" cellpadding="4">
     <h:outputText value="Select a fruit:"/>
     <h:outputText value="Select a variety:"/>
     <jsfExt:ajaxZone id="zone1" execute="zone1" render="zone2,zone3">        
         <h:selectOneRadio id="fruit" value="#{fruitInfoBean.fruit}"
                           valueChangeListener="#{fruitInfoBean.changeFruit}">
         <f:selectItems value="#{fruitInfoBean.fruits}"/>
      </h:selectOneRadio>
  </jsfExt:ajaxZone>
  <jsfExt:ajaxZone id="zone2" execute="zone2" render="zone3">
        <h:selectOneMenu id="variety" value="#{fruitInfoBean.variety}"
            valueChangeListener="#{fruitInfoBean.updateVariety}">
        <f:selectItems value="#{fruitInfoBean.varieties}"/>
    </h:selectOneMenu>
  </jsfExt:ajaxZone>
</h:panelGrid>

<jsfExt:ajaxZone id="zone3">
    <h:outputText id="varietyInfo" value="#{fruitInfoBean.varietyInfo}" />
</jsfExt:ajaxZone>
 
</h:form>

</body>
</html>
</f:view>
