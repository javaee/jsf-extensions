<!--
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 2 only ("GPL") or the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License. You can obtain
 a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 Sun designates this particular file as subject to the "Classpath" exception
 as provided by Sun in the GPL Version 2 section of the License file that
 accompanied this code.  If applicable, add the following below the License
 Header, with the fields enclosed by brackets [] replaced by your own
 identifying information: "Portions Copyrighted [year]
 [name of copyright owner]"

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
-->

<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="g" uri="http://java.sun.com/jsf/fireAjaxTrans" %>

<h:panelGrid border="1" headerClass="keytitle"> 
    <f:facet name="header">
        <h:outputText value="View"/>
    </f:facet>
    
    <g:panelGrid id="_0" border="1" columns="5">      
        <f:facet name="header">
            <h:outputText value="_0"/>
        </f:facet>
        <g:commandButton id="_1" value="_1" style="height:40px;width:40px;"/>
        <g:commandButton id="_2" value="_2" style="height:40px;width:40px;"/> 
        <g:commandButton id="_3" value="_3" style="height:40px;width:40px;"/>                                
        <g:commandButton id="_4" value="_4" style="height:40px;width:40px;"/>
        <g:panelGrid id="_5" border="1" columns="2">
            <f:facet name="header">
                <h:outputText value="_5"/>
            </f:facet>
            <g:panelGrid id="_6" border="1">
                <f:facet name="header">
                    <h:outputText value="_6"/>
                </f:facet>
                <g:commandButton id="_7" value="_7" style="height:40px;width:40px;"/>
                <g:commandButton id="_8" value="_8" style="height:40px;width:40px;"/>
            </g:panelGrid>
            <g:commandButton id="_9" value="_9" style="height:40px;width:40px;"/>
            <g:commandButton id="_10" value="_10" style="height:40px;width:40px;"/>
        </g:panelGrid>
    </g:panelGrid>
</h:panelGrid>
