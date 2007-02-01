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
    <title>Avatar Test Page</title>
    <%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
  </head>

  <body>
    <h1>Avatar Test Page</h1>

<f:view>

  <h:form id="form1">
  
    <p>Subtree 1</p>
      
    <h:panelGrid columns="2" border="1" id="subtree1">
        AJAX AVATAR INPUT 1
        
       <h:inputText id="input1" />
       
       &nbsp;
       
       <h:commandButton value="submit" />

    </h:panelGrid>
    
    <input type="hidden" name="com.sun.faces.PCtxt" id="com.sun.faces.PCtxt"
              value=":form1:subtree1" />    
    
  </h:form>
    
  <h:form id="form2">
      
    <p>Subtree 2</p>
      
    <h:panelGrid columns="2" border="1" id="subtree2">
        AJAX AVATAR INPUT 2
        
       <h:inputText id="input2" />
       
       &nbsp;
       
       <h:commandButton value="submit" />
       

    </h:panelGrid>

   <p>Renderer-Type for ViewRoot is 

    <h:outputText id="viewRootRendererType" 
                  value="#{facesContext.viewRoot.rendererType}" /> </p>
                  

  </h:form>

</f:view>

    <hr />

    <address><a href="mailto:ed.burns@sun.com">Edward Burns</a></address>
<!-- Created: Sun Dec  4 14:11:55 EST 2005 -->
<!-- hhmts start -->
Last modified: Sun Dec  4 14:13:34 EST 2005
<!-- hhmts end -->
  </body>
</html>
