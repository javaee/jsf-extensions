<%-- 
    Document   : category
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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


<%-- HTML markup starts below --%>

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
        <span style="background-color: #f5eabe; padding: 7px; position: relative;"
            class="rounded"><c:out value="${categoryName}" /></span>
    </p>

    <table id="productTable" style="z-index: 1; position: relative;">

    <c:forEach var="row" items="${products.rows}" varStatus="iter">

        <tr>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                <c:out value="[ image ]"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                <c:out value="${row.name}"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                $ <c:out value="${row.price}"/>
            </td>
            <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">

                <form action="cart">
                    <input type="hidden" name="productId" value="<c:out value="${row.product_id}"/>">
                    <input type="text" maxlength="2" size="2" value="1" name="quantity">
                    <input type="submit" name="submit" value="<fmt:message key="addToCart" />">
                </form>
                
            </td>
        </tr>
                
    </c:forEach>

    </table>
</div>