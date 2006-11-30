/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */


package tictactoe;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>This bean ... 
 * </p>
 */

public class Game {

    private static final Logger LOGGER = Logger.getLogger("tictactoe");

    //
    // Relationship Instance Variables
    //

    private int[] moves = new int[27];
    
    // 
    // Constructors
    //

    public Game() {
        initializeGame();
    }

    public void start(ActionEvent e) {
        initializeGame();
    }
    public void select(ActionEvent e) {
        UICommand command = (UICommand)e.getComponent();
        if (!isMoveOK(command)) {
            return;
        }
        command.setValue("X");
        nextMove();

    }

    private void initializeGame() {
        for (int i=0; i<moves.length; i++) {
            moves[i] = 0;
        }
    }
    private boolean isMoveOK(UICommand command) {
        String id = command.getId().substring(1);
        int idx = Integer.parseInt(id);
        if (moves[idx] == 0) {
            moves[idx] = 1;
            return true;
        } else {
            return false;
        }
    }
    private void nextMove() {
        String id = null;
        for (int i=0; i<moves.length; i++) {
            if (moves[i] == 0) {
                moves[i] = -1;
                id = "form:" + "_" + Integer.toString(i);
                break;
            }
        }
        AsyncResponse async = AsyncResponse.getInstance();
        List<String> renderSubtrees = async.getRenderSubtrees();
        renderSubtrees.add(id);
    }
}
