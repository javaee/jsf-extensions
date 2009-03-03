/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package listeners;

import database.entity.controller.CustomerJpaController;
import javax.servlet.*;
import javax.persistence.*;


public final class ContextListener implements ServletContextListener {

//    @PersistenceUnit(unitName = "AffableBeanPU")
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();

        try {
            CustomerJpaController customerTable = new CustomerJpaController();
            context.setAttribute("customerTable", customerTable);
        } catch (Exception ex) {
            System.out.println(
                    "Couldn't create database bean: "
                    + ex.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();

        context.removeAttribute("customerTable");
    }
}
