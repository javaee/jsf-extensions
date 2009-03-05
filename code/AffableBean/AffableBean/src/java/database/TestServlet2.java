/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet tests whether data can be retrieved from the affablebean
 * database using a persistence context with entity classes
 */
@PersistenceContext(name = "persistence/affableBean", unitName = "AffableBeanPU")
public class TestServlet2 extends HttpServlet {

    @Resource
    private javax.transaction.UserTransaction utx;

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
            out.println("<title>Servlet TestServlet2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet2 at " + request.getContextPath() + "</h1>");
            out.println("<h1>Search Customer Information</h1>");
            String customerNr = request.getParameter("customer_nr");
            if ((customerNr != null) && !(customerNr.equals(""))) {

                Customer customer = findByID(new String(customerNr));
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

    public Customer findByID(String customerNr) {
        Customer customer = null;
        try {
            Context ctx = (Context) new javax.naming.InitialContext().lookup("java:comp/env");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("persistence/affableBean");
            customer = em.find(Customer.class, customerNr);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
        return customer;
    }
}
