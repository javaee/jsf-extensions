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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>RoR Flash Test Page 3</title>
    <%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
    <%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/flash" %>
  </head>

  <body>
    <h1>RoR Flash Test Page 3</h1>

<f:view>

  <p>If you have something in flash.now that you later, during the same
  request, decide you want to promote to stick around for the next
  request, use flash.keep.</p>

  <h:form id="form1">

  <h:panelGrid columns="2" border="1">

    Value of the previous request's foo

    <h:outputText value="#{flash.foo}" />

    Value of the this request's bar.  Should be null.

    <h:outputText value="#{flash.bar}" />

    Put <code>banzai</code> in the flash.now under key
    <code>buckaroo</code>.

    <jsfExt:set var="#{flash.now.buckaroo}" value="banzai" />

    <f:verbatim>
      &lt;jsfExt:set var="\#{flash.now.buckaroo}" value="banzai" /&gt;
    </f:verbatim>

    Value of <code>\#{flash.now.buckaroo}</code>, should be
    <code>banzai</code>.

    <h:outputText value="#{flash.now.buckaroo}" />

    Promote buckaroo to stick around for the next request.

    <jsfExt:set var="#{flash.keep.buckaroo}" value="#{flash.now.buckaroo}" />

    <f:verbatim>
      &lt;jsfExt:set var="\#{flash.now.buckaroo}" 
                     value="\#{flash.keep.buckaroo}" /&gt;
    </f:verbatim>

    <h:commandButton value="reload" />

    <h:commandButton value="back" action="back" />

    &nbsp;

    <h:commandButton value="next" action="next" />

   </h:panelGrid>

  </h:form>

</f:view>

    <hr />

    <address><a href="mailto:ed.burns@sun.com">Edward Burns</a></address>
<!-- Created: Sun Dec  4 14:11:55 EST 2005 -->
<!-- hhmts start -->
Last modified: Thu Jun  1 13:47:37 PDT 2006
<!-- hhmts end -->
  </body>
</html>
