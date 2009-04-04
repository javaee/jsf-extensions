<%-- 
    Document   : category
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<sql:setDataSource dataSource="jdbc/affableBean" />
<sql:query var="categories" sql="SELECT * FROM category"></sql:query>


<script>
    $(document).ready( function(){
        $('.rounded').corners();
    });
</script>


            <div id="categoryLeftColumn">

                <c:forEach var="row" items="${categories.rows}">
                    
                    <div class="categoryButton rounded">
                        <a href="category?<c:out value="${row.name}"/>">
                            <c:out value="${row.name}"/>
                        </a>
                    </div>

                </c:forEach>

                <p><a href="index.jsp">welcome page</a></p>
            </div>

            <div id="categoryRightColumn">

                <p id="categoryTitle">
                    <c:out value="${category}" />
                </p>
                
                <table id="productTable">
                    <tr><td class="odd"></td></tr>
                    <tr><td class="even"></td></tr>
                    <tr><td class="odd"></td></tr>
                    <tr><td class="even"></td></tr>
                </table>
            </div>