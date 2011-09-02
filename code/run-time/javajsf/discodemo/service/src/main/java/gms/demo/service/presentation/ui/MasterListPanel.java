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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import gms.demo.model.MemberInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds a table containing information about the groups' masters.
 */
public class MasterListPanel extends Panel implements Serializable {

    private MainUI app;
    private Table table = new Table("Master member for each group:");

    public MasterListPanel(MainUI app) {
        this.app = app;

        // general table information
        addComponent(table);
//        table.setSizeUndefined();
//        table.setWidth("100%");
//        table.setColumnHeaders(new String[]{
//            "Group Name",
//            "Group Name Space",
//            "Member Name",
//            "IP Address",
//            "Port",
//            "Protocol"
//        });
        table.setSelectable(true);
        table.setMultiSelect(true);

        initTableSource();
        addComponent(table);
    }

    void initTableSource() {
        BeanItemContainer<FlatModelInfo> source =
            new BeanItemContainer<FlatModelInfo>(FlatModelInfo.class);
        for (MemberInfo info : app.getService().getMasters()) {
            source.addBean(new FlatModelInfo(info));
        }
        table.setContainerDataSource(source);
    }

    // a little ugly...
    public Set<MemberInfo> getSelectedMasters() {
        Object value = table.getValue();
        if (value instanceof Collection) {
            Collection flatMembers = (Collection) value;
            Set<MemberInfo> masters =
                new HashSet<MemberInfo>(flatMembers.size());
            for (Object o : flatMembers) {
                FlatModelInfo fmi = (FlatModelInfo) o;
                masters.add(fmi.returnMemberInfo());
            }
            return masters;
        }
        return null;
    }
}
