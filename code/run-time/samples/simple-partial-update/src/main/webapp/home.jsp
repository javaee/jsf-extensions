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
    <h1>DynaFaces simple partial update</h1>
            <h:form prependId="false">

<table border="1">

<tr>

<td>Value of <code>&#35;{testBean.text}</code> on last render.
</td>

<td><h:outputText id="text" value="#{testBean.text}"/>
</td>

</tr>

<tr>

<td>Button with actionListener that changes <code>&#35;{testBean.text}</code></td>

<td><h:commandButton id="button" actionListener="#{testBean.changeText}"
    value="submit"/>
</td>

</tr>

</table>
            
            </h:form>
  <script type='text/javascript'>
    DynaFaces.installDeferredAjaxTransaction($('button'), 'click'); 
  </script>


  </body>
</html>
</f:view>
