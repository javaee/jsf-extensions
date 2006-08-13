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
    <title>DynaFaces simple partial update</title>
    <jsfExt:scripts/>
  </head>

  <body>
    <h1>DynaFaces immediate attribute test</h1>
            <h:form prependId="false">

            <h:outputText id="text" value="#{testBean.text}"/>

            <p>Required value. </p> 

            <h:inputText id="value" required="true"  />
         
            <p>    <h:commandButton id="button"
                actionListener="#{testBean.changeText}"
                value="submit via ajax, update whole page"/></p>


            </h:form>
  <script type='text/javascript'>
    DynaFaces.installDeferredAjaxTransaction($('button'), 'click'); 
  </script>


  </body>
</html>
</f:view>
