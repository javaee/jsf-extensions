<%-- 
    Document   : category
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>

<%@page contentType="text/html; charset=UTF-8"%> <%-- required for '€' sign --%>

<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB">
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Dispatcher so that
     users view the same page when switching between EN and CS. --%>
<c:set var="view" value="/category" scope="session" />


<%-- HTML markup starts below --%>

<div id="categoryLeftColumn">

    <c:forEach var="category" items="${affableBeanDB.categories}">

        <c:choose>
            <c:when test="${category.name == selectedCategory.name}">
                <div class="categoryButton rounded" id="selectedCategory">
                    <span class="categoryText">
                        <fmt:message key="${category.name}"/>
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="category?${category.id}"/>" class="rounded categoryButton">
                    <div class="categoryText">
                        <fmt:message key="${category.name}"/>
                    </div>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle">
        <span style="background-color: #f5eabe; padding: 7px; position: relative;"
              class="rounded"><fmt:message key="${selectedCategory.name}" /></span>
    </p>

    <table id="productTable">
        
        <c:forEach var="product" items="${categoryProducts}" varStatus="iter">

            <tr>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    <img src="<c:out value="${initParam.imagePath}${product.name}.png"/>"
                        alt="image of <fmt:message key="${product.name}"/>">
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    <fmt:message key="${product.name}"/>
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    € <c:out value="${product.price}"/> / unit
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">

                    <form action="<c:url value="addToCart"/>" method="post">
                        <input type="hidden"
                               name="productId"
                               value="<c:out value="${product.id}"/>">
                        <input type="submit"
                               name="submit"
                               value="<fmt:message key="addToCart"/>">
                    </form>

                </td>
            </tr>

        </c:forEach>

    </table>
</div>