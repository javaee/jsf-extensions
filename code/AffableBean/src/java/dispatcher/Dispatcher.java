package dispatcher;

import cart.ShoppingCart;
import database.AffableBeanDBAO;
import entity.Category;
import entity.Product;
import exceptions.CategoryNotFoundException;
import exceptions.ProductNotFoundException;
import exceptions.ProductsNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nbuser
 */
public class Dispatcher extends HttpServlet {

//    @Resource
//    private UserTransaction utx;
    private AffableBeanDBAO affableBeanDBAO;
    private Category selectedCategory;
    private List categoryProducts;
    private ShoppingCart cart;
    private HttpSession session;
    private String requestedPath;

    @Override
    public void init() {

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

        session = request.getSession();
        requestedPath = request.getServletPath();
        cart = (ShoppingCart) session.getAttribute("cart");

        String clear;

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // if category page is requested
        if (requestedPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                try {
                    selectedCategory = affableBeanDBAO.getCategory(categoryId);
                } catch (CategoryNotFoundException ex) {
                    System.out.println("Unable to get category: " + ex.getMessage());
                }

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all products for selected category
                try {
                    categoryProducts = affableBeanDBAO.getCategoryProducts(categoryId);
                } catch (ProductsNotFoundException ex) {
                    System.out.println("Unable to get products: " + ex.getMessage());
                }

                // place category products in request scope
                session.setAttribute("categoryProducts", categoryProducts);
            }

            // if shopping cart page is requested
        } else if (requestedPath.equals("/viewCart")) {

            requestedPath = "/cart";

            clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {
                cart.clear();
            }

            // if checkout page is requested
        } else if (requestedPath.equals("/checkout")) {
            // TODO
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/jsp" + requestedPath + ".jsp";

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

        session = request.getSession();
        requestedPath = request.getServletPath();
        cart = (ShoppingCart) session.getAttribute("cart");

        Product product;

        // if addToCart action is called
        if (requestedPath.equals("/addToCart")) {

            requestedPath = "/category";

            // get categoryId from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            if (!productId.equals("") && !quantity.equals("")) {

                try {
                    product = affableBeanDBAO.getProduct(productId);

                    cart.add(productId, product, quantity);

                } catch (ProductNotFoundException ex) {

                    System.out.println("Unable to add product to cart: " + ex.getMessage());
                }
            }

            // if updateCart action is called
        } else if (requestedPath.equals("/updateCart")) {

            requestedPath = "/cart";

            // get categoryId from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            if (!productId.equals("") && !quantity.equals("")) {

                try {
                    product = affableBeanDBAO.getProduct(productId);

                    cart.update(productId, product, quantity);

                } catch (ProductNotFoundException ex) {

                    System.out.println("Unable to update cart: " + ex.getMessage());
                }
            }
        }
        
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/jsp" + requestedPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
