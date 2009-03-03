/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.entity.Customer;
import database.entity.controller.CustomerJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author troy
 */
public class TestServlet3 extends HttpServlet {

    private CustomerJpaController customerTable;

    @Override
    public void init() throws ServletException {
        customerTable = (CustomerJpaController)getServletContext().getAttribute("customerTable");

        if (customerTable == null) {
            throw new UnavailableException("Couldn't get database.");
        }
    }

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
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet2 at " + request.getContextPath() + "</h1>");
            out.println("<h1>Search Customer Information</h1>");
            String customerNr = request.getParameter("customer_nr");
            if ((customerNr != null) && !(customerNr.equals(""))) {

                Customer customer = customerTable.findCustomer(customerNr);
                if (customer != null) {
                    out.println("Customer's info for nr. " + customerNr + ": " + customer.getFirstname());
                } else {
                    out.println("Customer not found.");
                }
            }
            out.println("<form>");
            out.println("Customer number: <input type='text' name='customer_nr' />");
            out.println("<input type=submit value=Select />");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        return "Short description";
    }// </editor-fold>

}
