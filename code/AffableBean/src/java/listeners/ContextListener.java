/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package listeners;

import database.AffableBeanDBAO;
import javax.servlet.*;
import javax.persistence.*;


public final class ContextListener implements ServletContextListener {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();

        try {
            AffableBeanDBAO affableBeanDBAO = new AffableBeanDBAO(emf);
            context.setAttribute("affableBeanDBAO", affableBeanDBAO);
        } catch (Exception ex) {
            System.err.println(
                    "Couldn't create AffableBean database bean: "
                    + ex.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();

        AffableBeanDBAO affableBeanDBAO = (AffableBeanDBAO) context.getAttribute("affableBeanDBAO");

        if (affableBeanDBAO != null) {
            affableBeanDBAO.remove();
        }

        context.removeAttribute("affableBeanDBAO");
    }
}
