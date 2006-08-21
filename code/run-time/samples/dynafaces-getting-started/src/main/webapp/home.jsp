<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces" %>

<f:view>
<html>
<head>
  <title>Check If userid is in use, via Ajax</title>
  <jsfExt:scripts />
</head>
<body  bgcolor="white">

<h:form id="form">  

  <h1>Check If userid is in use, via Ajax</h1>

<p>Choose your userid and password</p>

<h:panelGrid columns="2">

  Userid:

  <h:inputText id="userid" value="#{user.userid}" />

  Password:

  <h:inputSecret value="#{user.password}" />

  <h:commandButton value="Check Id Availability" 
                   actionListener="#{user.checkIdAvailability}"
                   onclick="DynaFaces.fireAjaxTransaction(this, { inputs: 'userid', render: 'userid', 'status' });" />

  <h:commandButton value="register" action="register" />

  &nbsp;

  <h:outputText value="#{user.idAvailabilityStatus}" />

</h:panelGrid>


</h:form>

</body>
</html>
</f:view>
