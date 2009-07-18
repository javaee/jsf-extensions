
6 July 2009

The AffableBean application requires access to a MySQL database.

Before running the application,

1.  Download MySQL from: http://dev.mysql.com/downloads/

2.  Set up the database using 'root'/'root' as username/password.

3.  From the IDE's Services window, right-click the MySQL Server
    node and choose Create Database.

4.  In the Create Database dialog, type in 'affablebean' and select
    the 'Grant Full Access To *@localhost' option.

5.  Click OK to exit the dialog.

6.  Run the affablebean.sql script found in this directory.
    To do so, double-click the affablebean.sql node to open it
    in the IDE's SQL editor.

7.  In the toolbar above the editor, make sure the connection
    to the 'affablebean' database is selected:

    jdbc:mysql://localhost:3306/affablebean

8.  Click the Run SQL button to run the script.


Notes:

    The affablebean.sql script creates tables and sample data
    necessary for the application.

    The sun-resources.xml file creates the 'jdbc/affableBean'
    datasource, and 'affableBeanPool' connection pool on the
    server when the application is deployed.

    The application uses EclipseLink as the JPA provider, and
    is being developed using NetBeans 6.7 with the default
    installation of GlassFish v3 Prelude.