/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package dispatcher;

import cart.ShoppingCart;
import database.AffableBeanDBAO;
import entity.Category;
import entity.Product;
import exceptions.BadInputException;
import exceptions.CategoryNotFoundException;
import exceptions.ProductNotFoundException;
import exceptions.ProductsNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Dispatcher extends HttpServlet {

//    @Resource
//    private UserTransaction utx;
    private AffableBeanDBAO affableBeanDBAO;
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

        // gets DBAO instance from servlet context
        affableBeanDBAO = (AffableBeanDBAO) getServletContext().getAttribute("affableBeanDBAO");
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

                // synchronize HttpSession object to protect session attributes
                synchronized (session) {
                    // get selected category
                    try {
                        selectedCategory = affableBeanDBAO.getCategory(categoryId);
                    } catch (CategoryNotFoundException cnfe) {
                        System.err.println("Unable to get category. " + cnfe.getMessage());
                    }

                    // place selected category in session scope
                    session.setAttribute("selectedCategory", selectedCategory);

                    // get all products for selected category
                    try {
                        categoryProducts = affableBeanDBAO.getCategoryProducts(categoryId);
                    } catch (ProductsNotFoundException pnfe) {
                        System.err.println("Unable to get products. " + pnfe.getMessage());
                    }

                    // place category products in session scope
                    session.setAttribute("categoryProducts", categoryProducts);
                }
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

            if (userView != null) {
                // return user from whence s/he came
                userPath = userView;
            } else {
                // if previous view cannot be determined, send user to welcome page
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

                try {
                    product = affableBeanDBAO.getProduct(productId);

                    cart.add(productId, product);

                } catch (ProductNotFoundException pnfe) {

                    System.err.println("Unable to add product to cart. " + pnfe.getMessage());
                }

            }

            // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {

            userPath = "/cart";

            if (!productId.equals("") && !quantity.equals("")) {

                try {
                    product = affableBeanDBAO.getProduct(productId);

                    cart.update(productId, product, quantity);

                } catch (BadInputException bne) {

                    System.err.println("Unable to complete updateCart action. " + bne.getMessage());

                } catch (ProductNotFoundException pnfe) {

                    System.err.println("Unable to update cart. " + pnfe.getMessage());
                }

            }

            // if purchase action is called
        } else if (userPath.equals("/purchase")) {

            // extract user data from request
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String cityRegion = request.getParameter("cityRegion");
            String ccNumber = request.getParameter("ccNumber");


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
