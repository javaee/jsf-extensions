/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */
package controller;

import cart.ShoppingCart;
import session.CategoryFacade;
import session.ProductFacade;
import session.PlaceOrderLocal;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name="Controller",
            urlPatterns={"/category", "/addToCart", "/viewCart", "/updateCart", "/checkout", "/purchase", "/chooseLanguage"},
            loadOnStartup=1)

public class ControllerServlet extends HttpServlet {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private PlaceOrderLocal placeOrder;

    private Category selectedCategory;
    private List categoryProducts;
    private ShoppingCart cart;
    private String userPath;
    private String surcharge;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initializes the servlet with configuration information
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");

        getServletContext().setAttribute("categories", categoryFacade.findAll());
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        userPath = request.getServletPath();
        cart = (ShoppingCart) session.getAttribute("cart");

        String clear;

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // if category page is requested
        if (userPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                selectedCategory = categoryFacade.find(Short.parseShort(categoryId));

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all products for selected category
                categoryProducts = productFacade.findForCategory(selectedCategory);

                // place category products in session scope
                session.setAttribute("categoryProducts", categoryProducts);
            }

            // if shopping cart page is requested
        } else if (userPath.equals("/viewCart")) {

            userPath = "/cart";

            clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {
                cart.clear();
            }

            // if checkout page is requested
        } else if (userPath.equals("/checkout")) {

            // calculate total
            cart.calculateTotal(surcharge);

            // forward to /WEB-INF/view/checkout.jsp
            // switch to a secure channel

            // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {

            // get language choice
            String language = request.getParameter("language");

            // place in session scope
            session.setAttribute("language", language);

            String userView = (String) session.getAttribute("view");

            if ((userView != null) && (!userView.equals("/index"))) {
                // return user from whence s/he came
                userPath = userView;
            } else {
                // if previous view is index, or cannot be determined, send user to welcome page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        userPath = request.getServletPath();
        cart = (ShoppingCart) session.getAttribute("cart");

        // get user input from request
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");

        Product product;

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {

            userPath = "/category";

            if (!productId.equals("")) {

                product = productFacade.find(Integer.parseInt(productId));

                cart.addItem(productId, product);

            }

            // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {

            userPath = "/cart";

            if (!productId.equals("") && !quantity.equals("")) {

                product = productFacade.find(Integer.parseInt(productId));
                cart.update(productId, product, quantity);
            }

            // if purchase action is called
        } else if (userPath.equals("/purchase")) {

            // extract user data from request
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String cityRegion = request.getParameter("cityRegion");
            String ccNumber = request.getParameter("creditcard");

            // perform simple validation
            boolean errorMessage = false;
            boolean nameError;
            boolean emailError;
            boolean phoneError;
            boolean addressError;
            boolean cityRegionError;
            boolean ccNumberError;

            if (name == null
                    || name.equals("")
                    || name.length() > 45) {
                errorMessage = true;
                nameError = true;
                request.setAttribute("nameError", nameError);
            }
            if (email == null
                    || email.equals("")
                    || !email.contains("@")) {
                errorMessage = true;
                emailError = true;
                request.setAttribute("emailError", emailError);
            }
            if (phone == null
                    || phone.equals("")
                    || phone.length() < 9) {
                errorMessage = true;
                phoneError = true;
                request.setAttribute("phoneError", phoneError);
            }
            if (address == null
                    || address.equals("")
                    || address.length() > 45) {
                errorMessage = true;
                addressError = true;
                request.setAttribute("addressError", addressError);
            }
            if (cityRegion == null
                    || cityRegion.equals("")
                    || cityRegion.length() > 2) {
                errorMessage = true;
                cityRegionError = true;
                request.setAttribute("cityRegionError", cityRegionError);
            }
            if (ccNumber == null
                    || ccNumber.equals("")
                    || ccNumber.length() > 20) {
                errorMessage = true;
                ccNumberError = true;
                request.setAttribute("ccNumberError", ccNumberError);
            }

            // if validation error found, return user to checkout
            if (errorMessage == true) {
                request.setAttribute("errorMessage", errorMessage);
                userPath = "/checkout";

            // otherwise, save order to database
            } else {

                boolean success;
                success = placeOrder.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

                // if order processed successfully
                // send user to confirmation page
                if (success) {
                    userPath = "/confirmation";
                // otherwise, send back to checkout page
                } else {
                    userPath = "/checkout";
                }
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}