/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package tictactoe;

import javax.faces.component.UICommand;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>This bean encapsulates the mechanics of a three dimensional 
 *  tic tac toe game.  It keeps track of individual scores, 
 *  determines moves and records moves.
 * <p/>
 *
 */

public class Game {
    
    private static final Logger LOGGER = Logger.getLogger("demo1");
    
    //
    // Relationship Instance Variables
    //
    
    // Keeps track of all moves made by both players
    private int[] moves = new int[27];
    
    // Keeps track of available positions for a move
    private ArrayList availableIds = null;
    
    // Individual score counters
    private int score1 = 0;
    private int score2 = 0;
    
    public static final String FORM_ZONE_ID = "form:zone1:";
    
    //
    // Constructors
    //
    
    public Game() {
        initializeGame();
    }
    
    /**
     * <p>Action event method to start a new game.</p>
     */
    public void start(ActionEvent e) {
        initializeGame();
    }
    
    /**
     * <p>Action event method for a selection (move).</p>
     */
    public void select(ActionEvent e) {
        UICommand command = (UICommand)e.getComponent();
        if (!isMoveOK(command)) {
            return;
        }
        command.setValue("X");
        command.getAttributes().put("styleClass","x-button-border");
        nextMove();
        determineScores();
    }
    
    // Accessor methods for individual score counters
    
    public void setScore1(int score1) {
        this.score1 = score1;
    }
    
    public int getScore1() {
        return score1;
    }
    
    public void setScore2(int score2) {
        this.score2 = score2;
    }
    
    public int getScore2() {
        return score2;
    }
    
    /**
     * <p>Clear everything out in preparation for a new game.</p>
     **/
    private void initializeGame() {
        availableIds = new ArrayList();
        for (int i=0; i<moves.length; i++) {
            if (moves[i] != 0) {
                String id = FORM_ZONE_ID + "_" + Integer.toString(i);
                FacesContext context = FacesContext.getCurrentInstance();
                UIViewRoot viewRoot = context.getViewRoot();
                UICommand command = (UICommand)viewRoot.findComponent(id);
                command.setValue(null);
                command.getAttributes().put("styleClass", null);
                moves[i] = 0;
            }
            score1 = 0;
            score2 = 0;
        }
    }
    
    /**
     *<p>Make sure the selected move corresponds with an
     * available slot.</p>
     */
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
    
    /**
     *<p>Determine the next move (computer).  The move is determined
     * by the following priority:</p>
     *<ul>
     *<li>a calculated defensive move</li>
     *<li>a calculated offensive move</li>
     *<li>a random move</li>
     *</ul>
     */
    private void nextMove() {
        if (!calculateMove()) {
            randomMove();
        }
    }
    
    /**
     *<p>Generate a random number between 0 and the number
     * of available slots (inclusive).  This number is used
     * as an index into the <code>availableIds</code> list
     * to id of the move.</p>
     */
    private void randomMove() {
        determineAvailableMoves();
        if (availableIds.size() == 0) {
            return;
        }
        Random generator = new Random();
        int randomIndex = generator.nextInt(availableIds.size());
        Integer value = (Integer)availableIds.get(randomIndex);
        int intValue = value.intValue();
        moves[intValue] = -1;
        String id = FORM_ZONE_ID + "_" + Integer.toString(intValue);
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UICommand command = (UICommand)viewRoot.findComponent(id);
        command.setValue("O");
        command.getAttributes().put("styleClass","o-button-border");
    }
    
    /**
     *<p>Calculate the next defensive move.  If a defensive move
     * was not made, calculate an offensive move.</p>
     */
    private boolean calculateMove() {
        if (!defensiveMove()) {
            if (!offensiveMove()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     *<p>Calculate the next defensive move.</p>
     */
    private boolean defensiveMove() {
        int sum = 0;
        for (int i=0; i<41; i++) {
            int availableId = 0;
            for (int j=0; j<3; j++) {
                sum = sum + moves[patterns[i][j]];
                if (moves[patterns[i][j]] == 0) {
                    availableId = patterns[i][j];
                }
            }
            if (sum == 2) {
                moves[availableId] = -1;
                String id = FORM_ZONE_ID + "_" + Integer.toString(availableId);
                recordMove(id);
                return true;
            }
            sum = 0;
        }
        return false;
    }
    
    /**
     *<p>Calculate the next offensive move.</p>
     */
    private boolean offensiveMove() {
        int sum = 0;
        for (int i=0; i<41; i++) {
            int availableId = 0;
            for (int j=0; j<3; j++) {
                sum = sum + moves[patterns[i][j]];
                if (moves[patterns[i][j]] == 0) {
                    availableId = patterns[i][j];
                }
            }
            if (sum == -2) {
                moves[availableId] = -1;
                String id = FORM_ZONE_ID + "_" + Integer.toString(availableId);
                recordMove(id);
                return true;
            }
            sum = 0;
        }
        return false;
    }
    
    /**
     *<p>Find the component that corresponds with the 
     * <code>id</code> argument and set its value and
     * style to indicate the move.</p>
     */
    private void recordMove(String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UICommand command = (UICommand)viewRoot.findComponent(id);
        command.setValue("O");
        command.getAttributes().put("styleClass","o-button-border");
    }
    
    /**
     *<p>Determine and record all available moves.</p>
     */
    private void determineAvailableMoves() {
        availableIds.clear();
        for (int i=0; i<moves.length; i++) {
            if (moves[i] == 0) {
                availableIds.add(Integer.valueOf(i));
            }
        }
    }
    
    /**
     *<p>Calculate individual scores based on winning
     * pattern matches.</p>
     */
    private void determineScores() {
        score1 = 0;
        score2 = 0;
        int sum = 0;
        for (int i=0; i<41; i++) {
            for (int j=0; j<3; j++) {
                sum = sum + moves[patterns[i][j]];
            }
            if (sum == 3) {
                score1++;
            } else if (sum == -3) {
                score2++;
            }
            sum = 0;
        }
    }
    
    // Winning pattern combinations
    
    private int[][] patterns = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},
    {0,4,8},{2,4,6},{9,10,11},{12,13,14},{15,16,17},{9,12,15},{10,13,16},{11,14,17},
    {9,13,17},{11,13,15},{18,19,20},{21,22,23},{24,25,26},{18,21,24},{19,22,25},{20,23,26},
    {18,22,26},{20,22,24},{0,9,18},{1,10,19},{2,11,20},{3,12,21},{4,13,22},{5,14,23},
    {6,15,24},{7,16,25},{8,17,26},{0,10,20},{3,13,23},{6,16,26},{0,12,24},{1,13,25},
    {2,14,26},{0,13,26},{2,13,24}};
    
}
