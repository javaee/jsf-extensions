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

<td>Text field with <code>required</code> = <code>true</code>.
</td>

<td><h:inputText id="required" required="true" 
                 value="#{testBean.requiredText}"/>
</td>

</tr>


<tr>

<td>Button with actionListener that changes <code>&#35;{testBean.text}</code></td>

<td><h:commandButton id="button" actionListener="#{testBean.changeText}"
    value="submit"/>
</td>

</tr>

<tr>

<td>Text field with <code>required</code> = <code>true</code> and <code>immediate</code> = <code>true</code>.
</td>

<td><h:inputText id="requiredImmediate" 
                 value="#{testBean.requiredImmediateText}"
                 required="true" immediate="true" />
</td>

</tr>



<tr>

<td>Button same as above but with <code>immediate</code> set to
<code>true</code> <i>for this request only</i>.</td>

<td><h:commandButton id="immediate" actionListener="#{testBean.changeText}"
    value="submit immediate"/>
</td>

</tr>

<tr>

<td>Value of <code>immediate</code> property of "submit immediate" button.
</td>

<td><code><h:outputText value="#{testBean.immediateButtonIsImmediate}" /></code>
</td>

</tr>

<tr>

<td>Messages
</td>

<td><h:messages />
</td>
</tr>


</table>
            
            </h:form>
  <script type='text/javascript'>
    DynaFaces.installDeferredAjaxTransaction($('button'), 'click'); 
    DynaFaces.installDeferredAjaxTransaction($('immediate'), 'click', { 
        execute: "immediate",
        immediate: true 
    }); 
  </script>


  </body>
</html>
</f:view>
