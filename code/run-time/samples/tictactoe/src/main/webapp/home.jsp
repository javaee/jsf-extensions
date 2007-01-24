<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
<%@ taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces" %>

<f:view>
    <html>
        <head>
            <link rel="stylesheet" type="text/css"
                  href="${pageContext.request.contextPath}/stylesheet.css">
            <title>DynaFaces 3D Tic Tac Toe</title>
            <jsfExt:scripts />
        </head>
        <body  bgcolor="white">
            
            <h:form id="form" prependId="false">  
                
                <h1>3D Tic Tac Toe</h1>
                
                <jsfExt:ajaxZone id="zone0" render="zone0,zone1">
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
                </jsfExt:ajaxZone>
                
                <br>
                
                <jsfExt:ajaxZone id="zone1" render="zone0,zone1" execute="zone1">
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
            
        </body>
    </html>
</f:view>
