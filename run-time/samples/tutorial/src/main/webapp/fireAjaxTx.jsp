<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib prefix="jsfExt"
uri="http://java.sun.com/jsf/extensions/dynafaces" %>

<f:view>
<html>
<head>
  <title>Fruit Varieties</title>
  <jsfExt:scripts />
</head>
<body  bgcolor="white">

<h:form prependId="false" id="form">

  <h1>Fruit Varieties: Fire Ajax Transaction Example</h1>

  <jsfExt:scripts />

   <h:panelGrid columns="2" cellpadding="4">
     <h:outputText value="Select a fruit:"/>
     <h:outputText value="Select a variety:"/>
     <h:selectOneMenu id="fruit" value="#{fruitInfoBean.fruit}" onclick="DynaFaces.fireAjaxTransaction(this, { execute: 'fruit'});"
        valueChangeListener="#{fruitInfoBean.changeFruit}">
        <f:selectItems value="#{fruitInfoBean.fruits}"/>
    </h:selectOneMenu>
    <h:selectOneMenu id="variety" value="#{fruitInfoBean.variety}"
        onchange="DynaFaces.fireAjaxTransaction(this, { execute: 'variety' });"
        valueChangeListener="#{fruitInfoBean.updateVariety}">
        <f:selectItems value="#{fruitInfoBean.varieties}"/>
    </h:selectOneMenu>
 </h:panelGrid>
    <h:outputText id="varietyInfo" value="#{fruitInfoBean.varietyInfo}" />

</h:form>

</body>
</html>
</f:view>
