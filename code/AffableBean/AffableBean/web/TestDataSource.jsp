<%-- 
    Document   : TestDataSource
    Created on : Mar 2, 2009, 11:33:28 PM
    Author     : troy
--%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sql:setDataSource dataSource="jdbc/affableBean" />
<sql:query var="customers" sql="SELECT * FROM customer"></sql:query>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>data from affablebean.customers:</h1>

        <table border="1" align="center" valign="center">
            <c:forEach var="row" items="${customers.rows}">
                <tr>
                    <td><c:out value="${row.firstname}"/></td>
                    <td><c:out value="${row.familyname}"/></td>
                    <td><c:out value="${row.telephone}"/></td>
                    <td><c:out value="${row.email}"/></td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
