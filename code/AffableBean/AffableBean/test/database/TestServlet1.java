/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet tests whether data can be retrieved from the affablebean
 * database using a JDBC DriverManager to connect to a database driver
 */
public class TestServlet1 extends HttpServlet {

    // change these based on your environment
    private String userName = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost/affablebean";
    private String driver = "com.mysql.jdbc.Driver";
    private String query = "SELECT firstname, familyname FROM customer c WHERE c.id = ";
    
    Connection con;
    ResultSet rs;
    Statement s;

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
            out.println("<title>Servlet TestServlet1</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet1 at " + request.getContextPath() + "</h1>");

            String customerNr = request.getParameter("customer_nr");
            if ((customerNr != null) && !(customerNr.equals(""))) {

                query = query + customerNr;

                int count = 0;

                try {
                    rs = createQuery(query);

                    while (rs.next()) {

                        String firstName = rs.getString("firstname");
                        String familyName = rs.getString("familyname");
                        out.println("Customer's info for nr. " + customerNr + ": first name = " + firstName + ", family name = " + familyName);

                        System.out.println(
                                "first name = " + firstName + ", family name = " + familyName);
                        ++count;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(TestServlet1.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(count + " rows were retrieved");

                closeResources();

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

    protected Connection connect() {

        con = null;
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server");
        }
        return con;
    }

    protected ResultSet createQuery(String sqlQuery) throws SQLException {

        con = connect();
        s = con.createStatement();
        s.executeQuery(sqlQuery);
        rs = s.getResultSet();

        return rs;
    }

    protected void closeResources() {
        try {
            rs.close();
            s.close();
            con.close();
            System.out.println("ResultSet, Statement, and Database connection closed");
        } catch (Exception e) { /* ignore close errors */ }
    }
}
