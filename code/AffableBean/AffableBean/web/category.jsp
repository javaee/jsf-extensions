<%-- 
    Document   : category
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<sql:setDataSource dataSource="jdbc/affableBean" />

<%-- get data for navigation bar --%>
<sql:query var="categories" sql="SELECT * FROM category" />

<%-- get name of the selected category --%>
<sql:query var="selectedCategory" sql="SELECT name FROM category WHERE category_id = ?">
    <sql:param value="${categoryId}" />
</sql:query>

<%-- put selected category name in request scope --%>
<c:forEach var="row" items="${selectedCategory.rows}">
    <c:set var="categoryName" value="${row.name}" scope="request" />
</c:forEach>

<%-- get product data --%>
<sql:query var="products" sql="SELECT * FROM product WHERE category_id = ?">
    <sql:param value="${categoryId}" />
</sql:query>


<script>
    $(document).ready( function(){
        $('.rounded').corners();
    });
</script>


<div id="categoryLeftColumn">

    <c:forEach var="row" items="${categories.rows}">

        <div class="categoryButton rounded">
            <a href="category?<c:out value="${row.category_id}"/>">
                <c:out value="${row.name}"/>
            </a>
        </div>

    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle">
        <c:out value="${categoryName}" />
    </p>

    <table id="productTable">

    <c:forEach var="row" items="${products.rows}" varStatus="iter">

        <tr>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                <c:out value="[ image ]"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                <c:out value="${row.name}"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                &euro; <c:out value="${row.price}"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                <input type="text" maxlength="2" size="2" value="1">
                <c:out value="add to cart"/>
            </td>
        </tr>
                
    </c:forEach>

    </table>
</div>