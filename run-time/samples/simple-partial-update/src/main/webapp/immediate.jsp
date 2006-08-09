<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%> 
<%@taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<f:view>

<html>
  <head>
    <title>DynaFaces immediate attribute test</title>
    <jsfExt:scripts/>
  </head>

  <body>
    <h1>DynaFaces immediate attribute test</h1>
            <h:form prependId="false">

            <h:outputText id="text" value="#{testBean.text}"/>

            <p>Required value.  Leave blank.</p> 

            <h:inputText id="value" required="true"  />
         
            <p>    <h:commandButton id="immediate"
                actionListener="#{testBean.changeText}" 
                onclick="DynaFaces.fireAjaxTransaction(this, { immediate: true } ); return false;"
                value="submit via ajax with immediate true"/></p>

            <p>    <h:commandButton id="nonImmediate"
                actionListener="#{testBean.changeText}" 
                onclick="DynaFaces.fireAjaxTransaction(this); return false;"
                value="submit via ajax with immediate not set"/></p>


            </h:form>


  </body>
</html>
</f:view>
