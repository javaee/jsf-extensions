/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author troy
 *
 * THIS TEST REQUIRES transaction-type="JTA" in PU
 *
 * Uncomment the line below  -  actually, still works with it commented out
 */
//@PersistenceContext(name = "persistence/LogicalName", unitName = "AffableBeanPU")
public class NewServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;



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

            // boilerplate code from Designing Enterprise Web Apps 8.5
            // see:  http://java.sun.com/blueprints/guidelines/designing_enterprise_applications_2e/transactions/transactions6.html
            Context ic = new InitialContext();
            UserTransaction ut =
                    (UserTransaction) ic.lookup("java:comp/UserTransaction");
            ut.begin();

// access resources transactionally here

            Customer customer = new Customer();
            customer.setName("Harry");
            customer.setEmail("blah@blah.cm");
            customer.setPhone("123456789");
            customer.setAddress("Husa ulice");
            customer.setCityRegion("2");
            customer.setCcNumber("1234567812345678");

            EntityManager em = emf.createEntityManager();

            em.persist(customer);
            em.close();
            ut.commit();

        } catch (Exception ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
