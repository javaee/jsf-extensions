<h:form prependId="false">

<table bgcolor="#eeeeee" border="0" cellpadding="2" cellspacing="0" width="100%" class="vtop">
<colgroup>
  <col width="45%" />
  <col width="55%" />
</colgroup>

<tr>
<td colspan="2" class="headerbar3">
<div class="headerpadding2"><span style="color:#fff;">Let's do some Ajax with JSF</span></div>

</td>
</tr>

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
    onclick="DynaFaces.fireAjaxTransaction($('nonImmediateButton'), 'click'); return false;"
    value="submit"/>
</td>

</tr>

<tr>

<td>Button same as above but with <code>immediate</code> set to
<code>true</code> <i>for this request only</i>.</td>

<td><h:commandButton id="immediateButton" 
    actionListener="#{testBean.changeText}"
    onclick="DynaFaces.fireAjaxTransaction(this, { execute: 'immediateButton', immediate: true }); return false;"
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

 </h:form>
