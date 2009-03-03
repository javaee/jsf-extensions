/*
 * DBConnection.java
 *
 * Created on 13 August 2007, 22:07
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author troy a. giunipero
 * @version
 */
public class DBConnection {

    public Connection getCon()
            throws NamingException, SQLException {


        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/affableBean");
        Connection con = ds.getConnection();

        return con;
    }
}