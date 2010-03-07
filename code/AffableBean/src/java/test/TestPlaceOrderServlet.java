package test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cart.ShoppingCart;
import entity.Product;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.PlaceOrderLocal;
import session.ProductFacade;

/**
 *
 * @author troy
 */

@WebServlet(name="TestPlaceOrderServlet",
            urlPatterns={"/TestPlaceOrderServlet"})

public class TestPlaceOrderServlet extends HttpServlet {

    @EJB
    private PlaceOrderLocal placeOrder;
    @EJB
    private ProductFacade productFacade;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        System.out.println("TEST: PlaceOrderLocal...");

        System.out.println("Creating shopping cart...");
        ShoppingCart cart = new ShoppingCart();

        System.out.println("Adding items to cart...");

        String productId = "6";
        System.out.println("Adding product whose product ID = " + productId + " to cart");
        Product product = productFacade.find(Integer.parseInt(productId));
        cart.addItem(productId, product);

        productId = "10";
        System.out.println("Adding product whose product ID = " + productId + " to cart");
        product = productFacade.find(Integer.parseInt(productId));

        System.out.println("Updating quantity to product id=10");
        String quantity = "5";

        cart.addItem(productId, product);
        System.out.println("Quantity: " + quantity);
        cart.update(productId, product, quantity);

        System.out.println("Cart status: " + cart.getItems().toString());

        System.out.println("Placing order...");

        placeOrder.placeOrder("Harvey Keitel",
                "h.keitel@reservoirdogs.com",
                "606252924",
                "Dlouhá 34",
                "1",
                "1111222233334444",
                cart);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
