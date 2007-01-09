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


package fireAjaxTrans;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

public class Bean {
    
    private static final Logger LOGGER = Logger.getLogger("fireAjaxTrans");
    
    //
    // Relationship Instance Variables
    //
    
    private Map<String, String> options = null;
    
    //
    // Constructors
    //
    
    public Bean() {
        loadOptions();
    }
    
    private String fireOptions = null;
    
    private String instructions = null;
    
    public void setFireOptions(String fireOptions) {
        this.fireOptions = fireOptions;
    }
    
    public String getFireOptions() {
        return fireOptions;
    }
    
    public void getOptions(ActionEvent ae) {
        UICommand command = (UICommand)ae.getComponent();
        fireOptions = (String)options.get(command.getId());
        displayOptions();
    }
    
    public void reset(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIForm form = (UIForm)context.getViewRoot().findComponent("form");
        if (form != null) {
            UIComponent component = form.findComponent("_0");
            setInitialRender(component);
        }
        fireOptions = null;
    }
    
    public String getInstructions() {
        instructions = "The <i>View</i> area is a view of components (panelGrids and commandButtons).  ";
        instructions += "Initially, each of the panelGrid and commandButton components have a green border, ";
        instructions += "meaning that all these components have gone through initial render phase of the lifecycle.  ";
        instructions += "Each of the 'fireAjaxTransaction' buttons causes a different 'DynaFaces.fireAjaxTransaction' event to occur, ";
        instructions += "affecting one or more components in the view.  The color of a component may change color depending on what ";
        instructions += "lifecycle phase the component was processed.";
        return instructions;
    }
    
    private void loadOptions() {
        options = new HashMap<String, String>();
        options.put("ajax1", "{execute:'_1,ajax1'}");
        options.put("ajax2", "{execute:'_6,_10,ajax2'}");
        options.put("ajax3", "{execute:'_5,ajax3'}");
        options.put("ajax4", "{execute:'_4,_9,ajax3'}");
        options.put("ajax5", "{execute:'ajax5',render:'_1'}");
        options.put("ajax6", "{execute:'ajax6',render:'_6'}");
        options.put("ajax7", "{execute:'ajax7,_10',render:'_5'}");
        options.put("ajax8", "{execute:'ajax8,_1,_2,_3,_4',render:'_1,_2,_3,_4'}"); 
    }
    
    private void displayOptions() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIForm form = (UIForm)context.getViewRoot().findComponent("form");
        UIComponent component = form.findComponent("optionsTitle");
        component.setRendered(true);
        component = form.findComponent("fireOptions");
        component.setRendered(true);
    }
    
    private void setInitialRender(UIComponent component) {
        Iterator<UIComponent> kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = kids.next();
            setInitialRender(kid);
        }
        if (component.getAttributes().get("styleClass") != null) {
            component.getAttributes().remove("styleClass");
        }
    }
}
