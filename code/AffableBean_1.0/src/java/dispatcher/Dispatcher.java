package dispatcher;

import cart.ShoppingCart;
import database.AffableBeanDBAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author troy
 */
public class Dispatcher extends HttpServlet {

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

        AffableBeanDBAO affableBeanDBAO = (AffableBeanDBAO) getServletContext().getAttribute("affableBeanDBAO");

        HttpSession session = request.getSession();
        String requestedPath = request.getServletPath();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // if category is requested
        if (requestedPath.equals("/category")) {

            String categoryId = request.getQueryString();

            if (categoryId != null) {

                request.setAttribute("categoryId", categoryId);

                String url = "/WEB-INF/jsp" + requestedPath + ".jsp";

                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (requestedPath.equals("/cart")) {

            String productId = request.getQueryString();

            if (productId != null) {
                
                String url = "/WEB-INF/jsp" + requestedPath + ".jsp";

                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
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
