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
import com.sun.faces.jsf_extensions_javajsf.Application;
import com.sun.faces.jsf_extensions_javajsf.JavaJsfApplication;
import javajsf.demo.boundary.DiscoveryService;

import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.component.ActionSource2;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;

@JavaJsfApplication(urlPatterns = {"/ui/*"})
public class MainUI extends Application
    implements Serializable, ActionListener, PhaseListener {

    private DiscoveryService service;

    private UIComponent window;

    private Button refreshButton = new Button("Reload Members");
    private Button removeButton = new Button("Remove Selected Members");
    private Button clearAllButton = new Button("Remove All Members");
    private Button createNewButton = new Button("Create New Member");
    /*****
    private CreateMemberInfoPanel cmiPanel;
    private MasterListPanel mlPanel;
     *******/

    private HttpServletRequest httpReq;
    private UIComponent loginForm ;
    private UICommand loginButton;
    private Button logoutButton;

    UIComponent loginLayout;
    UIComponent defaultLayout;

    private static final String GMS_USER = "GMS_USER";
    
    public MainUI() {
    }

    public MainUI(DiscoveryService service) {
        this();
        this.service = service;
    }

    @Override
    public void init() {
        window = createComponent("window");
        loginForm = createComponent("form");
        loginButton = (UICommand) createComponent("org.primefaces.component.CommandButton",
                "org.primefaces.component.CommandButtonRenderer");
        loginButton.setValue("OK");
        /**
        Button logoutButton = new Button("Logout");
         */

        
        /*
         * Create main window. A window represents a browser tab. We don't
         * care about multiple browser tabs being open,
         * so we'll go with just the one window.
         */
        setMainWindow(window);

        initLoginContent();
        /****
        initDefaultContent();

        if (httpReq.isUserInRole(GMS_USER)) {
            window.setContent(defaultLayout);
        } else {
         */
            window.getFacets().put("content", loginLayout);
        /*******
        }
         *****/
    }
    
    @Override
    public void destroy() {
        
    }

    private void initLoginContent() {
        loginForm.getAttributes().put("caption", "Login:");
        loginForm.getAttributes().put("description", 
                "<br/>This application does not use your SSO or LDAP " +
                "passwords. So don't go giving me all your " +
                "login info.");

        UIComponent formLayout = createComponent("org.primefaces.component.Layout",
                "org.primefaces.component.LayoutRenderer");
        formLayout.getAttributes().put("style", "width:400px;height:200px;");
        
        UIComponent layoutUnit = 
                createComponent("org.primefaces.component.LayoutUnit",
                "org.primefaces.component.LayoutUnitRenderer");
        UIComponent field = createComponent("field");
        field.setId("user");
        field.getAttributes().put("label", "User:");
        UIComponent text = (UIComponent) createComponent("org.primefaces.component.InputText", 
                "org.primefaces.component.InputTextRenderer");
        field.getFacets().put("input", text);
        layoutUnit.getChildren().add(field);
        formLayout.getChildren().add(layoutUnit);
        
        layoutUnit = 
                createComponent("org.primefaces.component.LayoutUnit",
                "org.primefaces.component.LayoutUnitRenderer");
        field = createComponent("field");
        field.setId("pass");
        field.getAttributes().put("label", "Password:");
        text = (UIComponent) createComponent("org.primefaces.component.Password", 
                "org.primefaces.component.PasswordRenderer");
        field.getFacets().put("input", text);
        layoutUnit.getChildren().add(field);
        formLayout.getChildren().add(layoutUnit);
        
        layoutUnit = 
                createComponent("org.primefaces.component.LayoutUnit",
                "org.primefaces.component.LayoutUnitRenderer");
        layoutUnit.getChildren().add(loginButton);
        formLayout.getChildren().add(layoutUnit);
        
        loginForm.getFacets().put("body", formLayout);
        
        loginButton.addActionListener(this);

        // a panel will make this look a little nicer
        UIComponent loginPanel = createComponent("org.primefaces.component.Panel",
                "org.primefaces.component.PanelRenderer");
        loginPanel.getChildren().add(loginForm);
        /****
        loginPanel.setWidth(24f, Sizeable.UNITS_EM);
        loginPanel.setHeight(20f, Sizeable.UNITS_EM);
         */ 

        // add the components to a root layout and center it
        loginLayout = createComponent("org.primefaces.component.Layout",
                "org.primefaces.component.LayoutRenderer");
        loginLayout.getAttributes().put("fullPage", Boolean.TRUE);
        
        layoutUnit = 
                createComponent("org.primefaces.component.LayoutUnit",
                "org.primefaces.component.LayoutUnitRenderer");
        layoutUnit.getChildren().add(loginPanel);
        loginLayout.getChildren().add(layoutUnit);
        
        /****

        // this will keep the form near the top of the page
        loginLayout.setMargin(true);
        loginLayout.setComponentAlignment(loginPanel, Alignment.TOP_CENTER);

        // this would center it on the page
//        loginLayout.setSizeFull();
//        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
         * 
         * 
         */ 
    }

        /****
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
