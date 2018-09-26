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

