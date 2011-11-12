/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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

package javajsf.demo.ui;

import com.sun.faces.jsf_extensions_javajsf.Application;
import com.sun.faces.jsf_extensions_javajsf.JavaJsfApplication;

import com.sun.faces.jsf_extensions_javajsf.ui.Form;
import javax.faces.component.UIComponent;
import javax.faces.component.ActionSource2;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UISelectBoolean;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

@JavaJsfApplication(urlPatterns = {"/ui/*"})
public class MainUI extends Application
    implements ActionListener, ValueChangeListener {
    public static final String JAVAJSF_URI = "http://jsf.java.net/javajsf";
    UICommand button;
    Form loginForm;
    Form defaultForm;

    public MainUI() {
    }

    @Override
    public void init() {
        
        UIComponent window = createFaceletsComponent(JAVAJSF_URI, "window");
        this.setMainWindow(window);

        initLoginContent();
        initDefaultContent();

        window.getFacets().put("content", loginForm);

    }
    
    private void initLoginContent() {
        UIComponent input = createComponent("javax.faces.HtmlInputText");
        UIComponent field = createFaceletsComponent(JAVAJSF_URI, "field");
        field.setId("input");
        field.getAttributes().put("label", "Enter some text: ");
        field.getFacets().put("input", input);
        
        button = (UICommand) createComponent("javax.faces.HtmlCommandButton");
        button.setId("button");
        button.setValue("submit");
        button.addActionListener(this);
        
        UIComponent layout = createComponent("javax.faces.HtmlPanelGrid");
        layout.getChildren().add(field);
        layout.getChildren().add(button);
        
        loginForm = (Form) createFaceletsComponent(JAVAJSF_URI, "form");
        loginForm.getAttributes().put("caption", "This is the Login Form");
        loginForm.getAttributes().put("description", "This is the Login Form Caption");
        loginForm.getFacets().put("body", layout);
        
        
    }
    
    private void initDefaultContent() {

        UISelectBoolean checkbox = (UISelectBoolean) createComponent("javax.faces.HtmlSelectBooleanCheckbox");
        checkbox.addValueChangeListener(this);

        UIComponent field = createFaceletsComponent(JAVAJSF_URI, "field");
        field.setId("checkbox");
        field.getAttributes().put("label", "checkbox: ");
        field.getFacets().put("input", checkbox);
        
        
        UIComponent layout = createComponent("javax.faces.HtmlPanelGrid");
        
        layout.getChildren().add(field);
        
        defaultForm = (Form) createFaceletsComponent(JAVAJSF_URI, "form");
        defaultForm.getAttributes().put("caption", "Default Content");
        defaultForm.getAttributes().put("description", "description of default content");
        defaultForm.getFacets().put("body", layout);

        
    }
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void processAction(ActionEvent clickEvent) {
        ActionSource2 source = (ActionSource2) clickEvent.getSource();
        EditableValueHolder input;
        if (source == button) {
            input = loginForm.getField("input");
            System.out.println("value: " + input.getValue());
            if ("a".equals(input.getValue())) {
                getMainWindow().getFacets().put("content", defaultForm);
            }
        }
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        getMainWindow().getFacets().put("content", loginForm);
    }
    
    

}
