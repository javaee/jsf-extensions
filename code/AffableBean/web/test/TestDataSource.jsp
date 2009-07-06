<%-- 
    Document   : TestDataSource
    Created on : Mar 2, 2009, 11:33:28 PM
    Author     : troy
--%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sql:setDataSource dataSource="jdbc/affableBean" />
<sql:query var="categories" sql="SELECT * FROM category"></sql:query>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Data Source</title>
    </head>
    <body>
        <h1>data from affablebean.category:</h1>

        <table align="center">
            <c:forEach var="row" items="${categories.rows}">
                <tr>
                    <td><c:out value="${row.category_id}"/></td>
                    <td><c:out value="${row.name}"/></td>
                    <td><c:out value="${row.image_path}"/></td>
                    <td><c:out value="${row.last_update}"/></td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
