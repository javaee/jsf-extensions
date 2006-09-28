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
    <title>RoR Flash Test Page 1</title>
    <%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
    <%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/flash" %>
  </head>

  <body>
    <h1>RoR Flash Test Page 1</h1>

<f:view>

  <p>This series of pages illustrates the usage of the flash concept
  taken from <a target="_"
  href="http://api.rubyonrails.com/classes/ActionController/Flash.html">Ruby
  On Rails</a>.</p>

  <p>In JSF, the flash is exposed naturally via the new <a
  href="http://java.sun.com/products/jsp/reference/techart/unifiedEL.html">Unified
  Expression Language in Java EE 5</a>.  It is implemented via a custom
  <code>ELResolver</code> that introduces a new implicit object called
  "flash".  I considered calling it "dhhIsMyHero" but opted for the
  simpler "flash" instead.</p>

  <p>Using the flash is simple, and semantically identical to the way it
  works in Rails.  It's a Map.  Stuff you put in the Map will be
  accessible on the "next" view shown to user.  The Map will be cleared
  when the user has been shown the "next" view.</p>

  <h:form id="form1">

  <h:panelGrid columns="2" border="1" width="600">
      
    Put <code>fooValue</code> in the flash under key <code>foo</code>
    using <code>jsfExt:set</code>.  Note that things stored in the flash
    during <b>this</b> request are only retrievable on the <b>next</b>
    request.  If you want to store something on this request and see it
    on this one as well, use either <code>\#{flash.now.foo}</code> or
    <code>\#{requestScope.foo}</code>.  The former is simply an alias
    for the latter.

    <jsfExt:set var="#{flash.foo}" value="fooValue" />

    <f:verbatim>
      &lt;jsfExt:set var="\#{flash.foo}" value="fooValue" /&gt;
    </f:verbatim>

    Value of <code>\#{flash.foo}</code>, should be <code>null</code>.

    <h:outputText value="#{flash.foo}" />

    <h:commandButton value="reload" />

    <h:commandButton value="next" action="next" />

   </h:panelGrid>

  </h:form>

</f:view>

    <hr />

    <address><a href="mailto:ed.burns@sun.com">Edward Burns</a></address>
<!-- Created: Sun Dec  4 14:11:55 EST 2005 -->
<!-- hhmts start -->
Last modified: Thu Jun  1 13:47:08 PDT 2006
<!-- hhmts end -->
  </body>
</html>
