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

import com.sun.faces.jsf_extensions_javajsf.ui.Button;
import com.sun.faces.jsf_extensions_javajsf.ui.VerticalLayout;
import com.sun.faces.jsf_extensions_javajsf.ui.Window;
import com.sun.faces.jsf_extensions_javajsf.Application;
import com.sun.faces.jsf_extensions_javajsf.JavaJSFApplication;
import com.sun.jersey.api.representation.Form;
import javajsf.demo.boundary.DiscoveryService;

import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import javax.faces.component.ActionSource2;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;

@JavaJSFApplication
public class MainUI extends Application
    implements Serializable, ActionListener, PhaseListener {

    private DiscoveryService service;

    private Window window = new Window("Discovery Service Prototype");

    private Button refreshButton = new Button("Reload Members");
    private Button removeButton = new Button("Remove Selected Members");
    private Button clearAllButton = new Button("Remove All Members");
    private Button createNewButton = new Button("Create New Member");
    /*****
    private CreateMemberInfoPanel cmiPanel;
    private MasterListPanel mlPanel;
     *******/

    private HttpServletRequest httpReq;
    private Form loginForm = new Form();
    private Button loginButton = new Button("OK", loginForm, "commit");
    private Button logoutButton = new Button("Logout");

    VerticalLayout loginLayout;
    VerticalLayout defaultLayout;

    private static final String GMS_USER = "GMS_USER";

    public MainUI(DiscoveryService service) {
        this.service = service;
    }

    @Override
    public void init() {
        /*
         * Create main window. A window represents a browser tab. We don't
         * care about multiple browser tabs being open,
         * so we'll go with just the one window.
         */
        setMainWindow(window);

        /****
        initLoginContent();
        initDefaultContent();

        if (httpReq.isUserInRole(GMS_USER)) {
            window.setContent(defaultLayout);
        } else {
            window.setContent(loginLayout);
        }
         *****/
    }

    /*********
    private void initLoginContent() {
        loginForm.setCaption("Login:");
        loginForm.setDescription(
            "<br/>This application does not use your SSO or LDAP " +
                "passwords. So don't go giving me all your " +
                "login info.");

        loginForm.addField("user", new TextField("User:"));
        loginForm.addField("pass", new PasswordField("Password:"));
        loginForm.getFooter().addComponent(loginButton);
        loginButton.addListener(this);

        // a panel will make this look a little nicer
        Panel loginPanel = new Panel();
        loginPanel.addComponent(loginForm);
        loginPanel.setWidth(24f, Sizeable.UNITS_EM);
        loginPanel.setHeight(20f, Sizeable.UNITS_EM);

        // add the components to a root layout and center it
        loginLayout = new VerticalLayout();
        loginLayout.addComponent(loginPanel);

        // this will keep the form near the top of the page
        loginLayout.setMargin(true);
        loginLayout.setComponentAlignment(loginPanel, Alignment.TOP_CENTER);

        // this would center it on the page
//        loginLayout.setSizeFull();
//        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    private void initDefaultContent() {

        // add buttons to the main layout
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        refreshButton.addListener(this);
        removeButton.addListener(this);
        clearAllButton.addListener(this);
        createNewButton.addListener(this);
        buttonLayout.addComponent(refreshButton);
        buttonLayout.addComponent(removeButton);
        buttonLayout.addComponent(clearAllButton);
        buttonLayout.addComponent(createNewButton);
        buttonLayout.setMargin(true);

        // add form for creating new entries, hidden at first
        cmiPanel = new CreateMemberInfoPanel(this);
        cmiPanel.setVisible(false);

        // add table that shows all the group masters
        mlPanel = new MasterListPanel(this);

        // set up logout button
        logoutButton.addListener(this);

        // add everything to main layout
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSizeUndefined();
        mainLayout.addComponent(buttonLayout);
        mainLayout.addComponent(cmiPanel);
        mainLayout.addComponent(mlPanel);
        mainLayout.setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);
        mainLayout.addComponent(logoutButton);
        mainLayout.setComponentAlignment(logoutButton, Alignment.BOTTOM_RIGHT);

        // add the main layout to a root layout and center it
        defaultLayout = new VerticalLayout();
        defaultLayout.addComponent(mainLayout);
        defaultLayout.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
    }
     ******/ 
     

    @Override
    public void processAction(ActionEvent clickEvent) {
        ActionSource2 source = (ActionSource2) clickEvent.getSource();
        /******
        if (refreshButton == source) {
            reloadMasterListPanel();
        } else if (removeButton == source) {
            Set<MemberInfo> masters = mlPanel.getSelectedMasters();
            if (masters != null && masters.size() >0) {
                for (MemberInfo target : masters) {
                    service.deleteMaster(target);
                }
                reloadMasterListPanel();
            } else {
                warn("No masters selected in table");
            }
        } else if (clearAllButton == source) {
            service.removeAllMasters();
            reloadMasterListPanel();
        } else if (createNewButton == source) {
            cmiPanel.setVisible(true);
        } else if (loginButton == source) {
                String name = (String) loginForm.getField("user").getValue();
                String pass = (String) loginForm.getField("pass").getValue();

                // attempt to login
                try {
                    //httpReq.login(name, pass);

                    // if that succeeded, reload page
                    System.out.println("LOGIN OK: " + name);
                    window.setContent(defaultLayout);
                } catch (Exception e) {
                    System.out.println("LOGIN FAIL: " + name);
                    warn(String.format("Login failed for '%s'", name));
                }
        } else if (logoutButton == source) {
            try {
                httpReq.logout();
                loginForm.getField("pass").setValue("");
                loginForm.discard();
                window.setContent(loginLayout);
            } catch (ServletException e) {
                warn(e.toString());
            }
        }
         * ********/
    }
    
    /***********
    
    void reloadMasterListPanel() {
        mlPanel.initTableSource();
    }

    public DiscoveryService getService() {
        return service;
    }

    // utility method
    void warn(String msg) {
        getMainWindow().showNotification(msg,
            Window.Notification.TYPE_WARNING_MESSAGE);
    }
     *****/ 

    @Override
    public void beforePhase(PhaseEvent e) {
    }

    @Override
    public void afterPhase(PhaseEvent e) {

    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
    
    
    /*********

    // This is the Servlet class to load the Vaadin Application
    @DeclareRoles(GMS_USER)
    @WebServlet(urlPatterns = {"/ui/*", "/VAADIN/*"})
    public static class MyServlet extends AbstractApplicationServlet {

        @EJB
        private DiscoveryService service;

        @Override
        protected Class<? extends Application> getApplicationClass() {
            return MainUI.class;
        }

        @Override
        protected Application getNewApplication(HttpServletRequest request)
            throws ServletException {

            return new MainUI(service);
        }
    }
    
     ********/

}
