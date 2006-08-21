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
    <link rel="stylesheet" href="css/default_developer.css" />
    <link rel="stylesheet" href="css/default.css" />
    <link rel="stylesheet" href="css/homepage.css" />    
    <jsfExt:scripts/>
  </head>

  <body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" rightmargin="0" bgcolor="#ffffff" class="vaa2v0">

<a name="top"></a> 

<jsp:include page="masthead.jspf" />

<!-- BEGIN PAGETITLE -->
<div class="pagetitle">DynaFaces Simple Partial Update Example</div>
<!-- END PAGETITLE -->

<p>This example shows updating the entire page via Ajax.  Doing so is
certainly not the most optimal thing to do for network activity, but it
is simple and gets the point across.</p>

            <h:form prependId="false">

<!-- BEGIN WRAPPER TABLE, 2 COLUMN, MAIN/RIGHT -->
<table border="0" cellpadding="0" cellspacing="10" width="100%">
<tr valign="top"><td>

<!-- BEGIN MAIN COLUMN -->
<table bgcolor="#eeeeee" border="0" cellpadding="2" cellspacing="0" width="100%" class="vtop">
<tr>
<td colspan="2" class="headerbar3">
<div class="headerpadding2"><span style="color:#fff;">Let's do some Ajax with JSF</span></div>

</td>
</tr>

<tr>

<td>Value of <code>&#35;{testBean.text}</code> on last render.
</td>

<td><code><h:outputText id="text" value="#{testBean.text}"/></code>
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

<td>Text field with <code>required</code> = <code>true</code> and <code>immediate</code> = <code>true</code>.
</td>

<td><h:inputText id="requiredImmediateInputText" 
                 value="#{testBean.requiredImmediateText}"
                 required="true" immediate="true" />
</td>

</tr>

<tr>

<td>Non immedite button with actionListener that changes
<code>&#35;{testBean.text}</code></td>

<td><h:commandButton id="nonImmediateButton" 
    actionListener="#{testBean.changeText}"
    value="submit"/>
</td>

</tr>

<tr>

<td>Button same as above but with <code>immediate</code> set to
<code>true</code> <i>for this request only</i>.</td>

<td><h:commandButton id="immediateButton" 
    actionListener="#{testBean.changeText}"
    value="submit immediate"/>
</td>

</tr>

<tr>

<td>Value of <code>immediate</code> property of "submit immediate"
button.  This should be false and demonstrates that, no matter what
immediate value we set via Ajax, the value set by the page author is the
one that persists.
</td>

<td><code><h:outputText value="#{testBean.immediateButtonIsImmediate}" /></code>
</td>

</tr>

<tr>

<td>Messages rendered via <code>&lt;h:messages&gt;</code> component.
</td>

<td><b><h:messages /></b>
</td>
</tr>

</table>

</td>

<!-- BEGIN RIGHT COLUMN -->
<td>

<div class="e15"><div class="e15v0">
<div class="cornerTL"><div class="cornerTR"></div></div>
<div class="pad">

<div class="navtitle">Example Files</div>

<div class="bluearrows"><a href="ShowSource.jsp?filename=/home.jsp">home.jsp</a></div>
<div class="bluearrows"><a href="ShowSource.jsp?filename=/WEB-INF/web.xml">WEB-INF/web.xml</a></div>
<div class="bluearrows"><a href="ShowSource.jsp?filename=/WEB-INF/faces-config.xml">WEB-INF/faces-config.xml</a></div>
<div class="bluearrows"><a href="ShowSource.jsp?filename=/test/TestBean.java">test/TestBean.java</a></div>

</div>
<div class="cornerBL"><div class="cornerBR"></div></div>

</div></div>

</td>

</tr>


<tr>
<td class="headerbar3">
<div class="headerpadding2"><span style="color:#fff;">Running the Demo</span></div>
</tr>
<tr>
<td>

<p><code>TestBean</code> is a request scoped managed bean.  The buttons
below have an <code>actionListener</code> that changes the value of this
property to show a time stamp, the lifecycle phase in which the event
was delivered, and the id of the element that is the source of the
event.  Recall from core JSF behavior that an input field with
<code>immediate == true</code> will have its validations performed
during the <code>APPLY_REQUEST VALUES</code> phase.  An input field
without <code>immediate</code> set will have its validations performed
during the <code>PROCESS_VALIDATIONS</code> phase.  A button with
<code>immediate == true</code> will bypass validations entirely, whereas
a button without <code>immediate</code> set will have the validations
performed as normal.
</p>

	<ol>

	  <li><p>Click the <b>submit</b> button.  The page should
	  refresh via Ajax and re-render with a validation message in
	  the <code>Messages</code> area.</p></li>

	  <li><p>Click the <b>submit immediate</b> button.  The page
	  should refresh via Ajax and re-render with an informational
	  value in the first row of the table.  Note that the message
	  indicates the event was delivered during the <code>APPLY
	  REQUEST VALUES</code> lifecycle phase.  The validation message
	  should disappear, because this button is marked as immediate
	  via Ajax.</p></li>

	  <li><p>Enter values in both text fields and again click
	  <b>submit</b>.  Note the event is delivered during the
	  <code>INVOKE APPLICATION</code> lifecycle phase and no
	  validation message appears.</p></li>

	  <li><p>Again click the <b>submit immediate</b> value.  Note
	  again that the event is delivered during <code>APPLY REQUEST
	  VALUES</code> and that the values are cleared. </p></li>

	</ol>

</td>
</tr>

</table>
            
            </h:form>
  <script type='text/javascript'>
    DynaFaces.installDeferredAjaxTransaction($('nonImmediateButton'), 'click'); 
    DynaFaces.installDeferredAjaxTransaction($('immediateButton'), 'click', { 
        execute: "immediateButton",
        immediate: true 
    }); 
  </script>




  </body>
</html>
</f:view>
