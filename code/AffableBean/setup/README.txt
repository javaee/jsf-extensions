
6 July 2009

The AffableBean application requires access to a MySQL database.

Before running the application,

 1. Download MySQL from: http://dev.mysql.com/downloads/

    The database configuration uses 'root' / '' as the default username / password.
    This is used in the AffableBean project.

 2. From the IDE's Services window, right-click the MySQL Server
    node and choose Create Database.

 3. In the Create Database dialog, type in 'affablebean' and select
    the 'Grant Full Access To *@localhost' option.

 4. Click OK to exit the dialog.

 5. Run the affablebean_schema.sql script found in this directory.
    This script creates tables necessary for the application.
    To do so, double-click the affablebean_schema.sql node to open
    it in the IDE's SQL editor.

 6. In the toolbar above the editor, make sure the connection
    to the 'affablebean' database is selected:

    jdbc:mysql://localhost:3306/affablebean

 7. Click the Run SQL button to run the script.

 8. Also run the affablebean_sample_data.sql script found in this directory.
    This script generates sample data necessary for the application.

 9. It is necessary to enable automatic driver deployment on the server.
    This is because the GF server doesn't contain the MySQL driver by default.
    Choose Tools > Servers, and select your server in the left pane. Then
    in the right pane, select the 'Enable JDBC Driver Deployment' option.

    After making this change, you'll need to restart the server (if it's
    already been started).


Notes:

    The sun-resources.xml file creates the 'jdbc/affablebean'
    data source, and 'affablebeanPool' connection pool on the
    server when the application is deployed.

    *The server may need to be restarted for the data source and
    connection pool settings to take effect.*

    The application uses EclipseLink as the persistence provider, and
    is being developed using NetBeans 6.8 and 6.9 dev with GlassFish v3.

    It is possible to run the application on GlassFish v2.1. To
    do so, make the following changes:

        1.  Open the persistence unit (Configuration Files >
            persistence.xml). In the IDE's Design View, select
            TopLink from the Persistence Provider drop-down.

        2.  If you are using a blank password to connect to the
            MySQL database, open the Server Resources >
            sun-resources.xml file. In your connection pool
            settings, set the value for the password to "()".

            <property name="Password" value="()"/>

        3.  Set the project to run on GlassFish 2.1. In the Projects
            window, right-click the AffableBean node and choose
            Properties. Select the Run category, then select
            GlassFish 2.1 from the Server drop-down.