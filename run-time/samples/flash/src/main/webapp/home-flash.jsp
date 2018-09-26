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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%> 
<%@taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/flash"%> 

<f:view>

<html>
  <head>
    <title><%@ include file="title.jsp" %></title>

    <link rel="stylesheet" href="css/default_developer.css" />
    <link rel="stylesheet" href="css/default.css" />
    <link rel="stylesheet" href="css/homepage.css" />    
  </head>

    <%@ page contentType="text/html" %>

<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" rightmargin="0" bgcolor="#ffffff" class="vaa2v0">

<a name="top"></a> 

<%@ include file="masthead.jsp" %>

<!-- BEGIN PAGETITLE -->
<div class="pagetitle"><%@ include file="title.jsp" %></div>
<!-- END PAGETITLE -->

<%@ include file="topPanel.jsp" %>


<!-- BEGIN WRAPPER TABLE, 2 COLUMN, MAIN/RIGHT -->
<table border="0" cellpadding="0" cellspacing="10" width="100%">
<tr valign="top"><td>

<!-- BEGIN MAIN COLUMN -->

<%@ include file="mainColumn.jsp" %>

</td>

<!-- BEGIN RIGHT COLUMN -->
<td>

<div class="e15"><div class="e15v0">
<div class="cornerTL"><div class="cornerTR"></div></div>
<div class="pad">

<%@ include file="rightColumn.jsp" %>

</div>
<div class="cornerBL"><div class="cornerBR"></div></div>

</div></div>

</td>

</tr>


<tr>
<td class="headerbar3">

<div class="headerpadding2"><span style="color:#fff;">Running the Demo</span></div>

</td>

</tr>
<tr>

<td>

<%@ include file="bottomPanel.jsp" %>

</td>
</tr>

</table>

  </body>
</html>
</f:view>
