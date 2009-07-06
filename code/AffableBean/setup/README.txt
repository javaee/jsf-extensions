
6 July 2009

The AffableBean application requires access to a MySQL database.

Before running the application,

1.  Download MySQL from: http://dev.mysql.com/downloads/
2.  Set up the database using root/root as username/password
3.  Run the affablebean.sql script found in this directory


Notes:

    The affablebean.sql script creates tables and sample data
    necessary for the application.

    The sun-resources.xml file creates the 'jdbc/affableBean'
    datasource, and 'affableBeanPool' connection pool on the
    server when the application is deployed.

    The application uses EclipseLink as the JPA provider, and
    is being developed using NetBeans 6.7 with the default
    installation of GlassFish v3 Prelude.