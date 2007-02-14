<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:jsfExt="http://java.sun.com/jsf/extensions/dynafaces">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    
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
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html;CHARSET=UTF-8" />
            <title>3D Tic Tac Toe</title>
            <link rel="stylesheet" type="text/css"
                  href="${pageContext.request.contextPath}/stylesheet.css" />
        </head>
        
        <f:loadBundle basename="tictactoe.Resources" var="bundle"/>
        
        <body bgcolor="white">
            
            <f:view>
                
                <h:form id="form" prependId="false">  
                    
                    <h1>3D Tic Tac Toe</h1>
                    
                    <jsfExt:ajaxZone id="zone0" render="zone0,zone1">
                        <h:panelGrid columns="2" > 
                            
                            
                            <h:panelGrid id="controls" columns="3" styleClass="control-panel-border">
                                <h:commandButton value="New Game" 
                                                 actionListener="#{game.start}"/>
                                <h:panelGrid columns="2">
                                    <h:outputText value="X Wins:"/>
                                    <h:outputText id="score1" value="#{game.score1}"/>
                                    <h:outputText value="O Wins:"/>
                                    <h:outputText id="score2" value="#{game.score2}"/>
                                </h:panelGrid>
                            </h:panelGrid>
                            
                            
                            <h:panelGrid border="1">
                                <f:facet name="header">
                                    <h:outputText value="Instructions" styleClass="keytitle"/>
                                </f:facet>
                                <h:outputText value="#{bundle.instructions}" escape="false"/>
                                <h:selectOneMenu valueChangeListener="#{game.showPatterns}">
                                    <f:selectItem itemValue="-1" itemLabel="Select Pattern"/>
                                    <f:selectItem itemValue="24" itemLabel="Vertical-1"/>
                                    <f:selectItem itemValue="25" itemLabel="Vertical-2"/>
                                    <f:selectItem itemValue="26" itemLabel="Vertical-3"/>
                                    <f:selectItem itemValue="33" itemLabel="Horizontal-1"/>
                                    <f:selectItem itemValue="34" itemLabel="Horizontal-2"/>
                                    <f:selectItem itemValue="39" itemLabel="Diagonal-1"/>
                                    <f:selectItem itemValue="40" itemLabel="Diagonal-2"/>
                                </h:selectOneMenu>
                                
                            </h:panelGrid>
                            
                        </h:panelGrid>
                    </jsfExt:ajaxZone>
                    
                    <br/>
                    
                    <jsfExt:ajaxZone id="zone1" render="zone0,zone1" >
                        <h:panelGrid id="board" columns="3" styleClass="panel-border">
                            <h:panelGrid id="board1" columns="3">
                                <h:commandButton id="_0" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_1" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_2" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_3" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_4" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_5" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_6" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_7" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_8" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                            </h:panelGrid>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:panelGrid id="board2" columns="3">
                                <h:commandButton id="_9" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_10" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_11" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_12" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_13" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_14" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_15" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_16" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_17" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                            </h:panelGrid>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:panelGrid id="board3" columns="3">
                                <h:commandButton id="_18" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_19" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_20" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_21" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_22" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_23" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_24" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_25" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                                <h:commandButton id="_26" style="height:45px;width:45px;"
                                                 actionListener ="#{game.select}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </jsfExt:ajaxZone>                                
                </h:form>    
            </f:view>
            
        </body>
    </html>
</jsp:root>
