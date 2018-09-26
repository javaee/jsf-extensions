<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 2 only ("GPL") or the Common Development
    and Distribution License("CDDL") (collectively, the "License").  You
    may not use this file except in compliance with the License.  You can
    obtain a copy of the License at
    https://oss.oracle.com/licenses/CDDL+GPL-1.1
    or LICENSE.txt.  See the License for the specific
    language governing permissions and limitations under the License.

    When distributing the software, include this License Header Notice in each
    file and include the License file at LICENSE.txt.

    GPL Classpath Exception:
    Oracle designates this particular file as subject to the "Classpath"
    exception as provided by Oracle in the GPL Version 2 section of the License
    file that accompanied this code.

    Modifications:
    If applicable, add the following below the License Header, with the fields
    enclosed by brackets [] replaced by your own identifying information:
    "Portions Copyright [year] [name of copyright owner]"

    Contributor(s):
    If you wish your version of this file to be governed by only the CDDL or
    only the GPL Version 2, indicate your decision by adding "[Contributor]
    elects to include this software in this distribution under the [CDDL or GPL
    Version 2] license."  If you don't indicate a single choice of license, a
    recipient has the option to distribute your version of this file under
    either the CDDL, the GPL Version 2 or to extend the choice of license to
    its licensees as provided above.  However, if you add GPL Version 2 code
    and therefore, elected the GPL Version 2 license, then the option applies
    only if the new code is made subject to such option by the copyright
    holder.

--%>

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
