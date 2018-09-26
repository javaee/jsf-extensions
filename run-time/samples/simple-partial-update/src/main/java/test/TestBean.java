/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 *
 * Created on 14 luglio 2006, 17.51
 */

package test;

import javax.faces.component.ActionSource;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author agori
 */
public class TestBean {
    
    
    private String text = "initial value";
    
    
    
    public TestBean() {
      
    }
    
    public String update() {
        return null;
    }

    public void changeText(ActionEvent e) {
	setText("Received ActionEvent at: currentTimeMillis " + System.currentTimeMillis() +
                " phase for event: " + e.getPhaseId().toString() + 
                " source of event: " + e.getComponent().getId());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for property immediateButtonImmediate.
     * @return Value of property immediateButtonImmediate.
     */
    public boolean isImmediateButtonIsImmediate() {
        FacesContext context = FacesContext.getCurrentInstance();
        final boolean [] result = new boolean[1];
        result[0] = false;
        
        context.getViewRoot().invokeOnComponent(context, "immediateButton", new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
                result[0] = ((ActionSource)comp).isImmediate();
            }
        });

        return result[0];
    }

    /**
     * Holds value of property requiredText.
     */
    private String requiredText;

    /**
     * Getter for property requiredText.
     * @return Value of property requiredText.
     */
    public String getRequiredText() {
        return this.requiredText;
    }

    /**
     * Setter for property requiredText.
     * @param requiredText New value of property requiredText.
     */
    public void setRequiredText(String requiredText) {
        this.requiredText = requiredText;
    }

    /**
     * Holds value of property requiredImmediateText.
     */
    private String requiredImmediateText;

    /**
     * Getter for property requiredImmediateText.
     * @return Value of property requiredImmediateText.
     */
    public String getRequiredImmediateText() {
        return this.requiredImmediateText;
    }

    /**
     * Setter for property requiredImmediateText.
     * @param requiredImmediateText New value of property requiredImmediateText.
     */
    public void setRequiredImmediateText(String requiredImmediateText) {
        this.requiredImmediateText = requiredImmediateText;
    }
    
   
    
}
