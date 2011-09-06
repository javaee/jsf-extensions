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

package gms.demo.service.presentation.ui;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import gms.demo.model.GroupId;
import gms.demo.model.Location;
import gms.demo.model.MemberInfo;

import java.io.Serializable;

import static gms.demo.service.presentation.ui.FlatModelInfo.*;

/**
 * Holds a form for creating a new MemberInfo. If MemberInfo just had
 * Java primitives in it, we could create the Form object from it directly.
 */
public class CreateMemberInfoPanel extends Panel
    implements Serializable, Button.ClickListener {

    private PropertysetItem propertysetItem = new MemberInfoPropertySet();
    private Button createButton;
    private Button cancelButton;
    private MainUI app;
    private static final String INSTRUCTIONS_TEXT =
        "<p>The combination of Group Name and Group Name Space must be " +
            "unique.</p>" +
            "<p>To simplify adding multiple test masters, " +
            "this form remembers the last data that was input.</p>" +
            "<p>The Reset button will revert back to the last set of data " +
            "that was committed.</p>" +
            "<p>Click Cancel to dismiss this form.</p>";

    CreateMemberInfoPanel(MainUI app) {
        this.app = app;
        
        // createButton form and data source
        Form form = new Form();
        form.setCaption("Create New MemberInfo");
        form.setWriteThrough(false);
        form.setItemDataSource(propertysetItem);

        // validation
        // todo: more of these
        form.getField(PORT).addValidator(new IntegerValidator(
            PORT + " field must be an integer."));

        // buttons
        createButton = new Button("Create", form, "commit");
        Button resetButton = new Button("Reset", form, "discard");
        cancelButton = new Button("Cancel");
        form.getFooter().addComponent(createButton);
        form.getFooter().addComponent(resetButton);
        form.getFooter().addComponent(cancelButton);
        form.setWidth(24f, Sizeable.UNITS_EM);

        // explanatory text
        Label instructions = new Label(INSTRUCTIONS_TEXT, Label.CONTENT_XHTML);
        instructions.setWidth(24f, Sizeable.UNITS_EM);

        // now add to panel
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSpacing(true);
        hLayout.addComponent(form);
        hLayout.addComponent(instructions);
        hLayout.setComponentAlignment(instructions, Alignment.MIDDLE_LEFT);
        addComponent(hLayout);
    }

    // we add listeners here to avoid doing it in the constructor
    @Override
    public void attach() {
        createButton.addListener(this);
        cancelButton.addListener(this);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == createButton) {
            MemberInfo newMember = new MemberInfo(
                new GroupId(formVal(G_NAME), formVal(G_NAME_SPACE)),
                new Location(formVal(IP_ADDR), formNum(PORT), formVal(PROT)),
                formVal(M_NAME),
                false);
            MemberInfo original = app.getService().setMaster(newMember);
            if (original == null) {
                app.reloadMasterListPanel();
                setVisible(false);
            } else {
                app.warn("Master is already set for group:\n" +
                    original.toString());
            }
        } else if (clickEvent.getButton() == cancelButton) {
            setVisible(false);
        }
    }

    // utility method to get the values from the form's source
    private String formVal(String id) {
        return propertysetItem.getItemProperty(id).toString();
    }

    // utility method to get the values from the form's source
    private int formNum(String id) {
        return Integer.valueOf(propertysetItem.getItemProperty(id).toString());
    }

    private static class MemberInfoPropertySet extends PropertysetItem {
        MemberInfoPropertySet() {
            addItemProperty(M_NAME, new ObjectProperty<String>(""));
            addItemProperty(G_NAME, new ObjectProperty<String>(""));
            addItemProperty(G_NAME_SPACE, new ObjectProperty<String>(""));
            addItemProperty(IP_ADDR, new ObjectProperty<String>(""));
            addItemProperty(PORT, new ObjectProperty<String>(""));
            addItemProperty(PROT, new ObjectProperty<String>(""));
        }
    }
}
