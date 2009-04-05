package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author troy
 */
public class CategoryServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher;

//// add language resource bundle to user's session
//
//// get user's locale
//Locale locale = request.getLocale();
//
//// get resource bundle
//ResourceBundle messages =
//        ResourceBundle.getBundle("resources.messages", locale);
//
//// put resource bundle in user's session
//HttpSession session = request.getSession();
//session.setAttribute("messages", messages);


        String categoryId = request.getQueryString();

        // if category not specified in request, redirect request to the welcome page
        if (categoryId == null) {

            response.sendRedirect("index.jsp");

        } else {

            request.setAttribute("categoryId", categoryId);

            String url = "/WEB-INF/jsp/category.jsp";
            requestDispatcher = getServletContext().getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        }
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
        return java.util.ResourceBundle.getBundle("resources/messages").getString("The_CategoryServlet_places_the_requested_category_in_request_scope_") +
                "and forwards the request to the \"category.jsp\" view.";
    }// </editor-fold>
}