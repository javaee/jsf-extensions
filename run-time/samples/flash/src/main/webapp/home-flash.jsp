<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<!--
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License). You may not use this file except in
 compliance with the License.
 
 You can obtain a copy of the License at
 https://javaserverfaces.dev.java.net/CDDL.html or
 legal/CDDLv1.0.txt. 
 See the License for the specific language governing
 permission and limitations under the License.
 
 When distributing Covered Code, include this CDDL
 Header Notice in each file and include the License file
 at legal/CDDLv1.0.txt.    
 If applicable, add the following below the CDDL Header,
 with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"
 
 [Name of File] [ver.__] [Date]
 
 Copyright 2005 Sun Microsystems Inc. All Rights Reserved
-->

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
