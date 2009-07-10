<%-- 
    Document   : category
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB" scope="page" >
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>


<%-- HTML markup starts below --%>

<div id="categoryLeftColumn">

    <c:forEach var="category" items="${affableBeanDB.categories}">

        <div class="categoryButton rounded"

             <c:if test="${category.name == selectedCategory.name}">
                id="selectedCategory"
             </c:if>>

            <c:set var="url" value="category?${category.categoryId}"/>
            <a href="${url}" class="categoryText">
                <c:out value="${category.name}"/>
            </a>
        </div>

    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle">
        <span style="background-color: #f5eabe; padding: 7px; position: relative;"
              class="rounded"><c:out value="${selectedCategory.name}" /></span>
    </p>

    <table id="productTable">

        <c:forEach var="product" items="${categoryProducts}" varStatus="iter">

            <tr>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    <c:out value="[ image ]"/>
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    <c:out value="${product.name}"/>
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                    $ <c:out value="${product.price}"/> / unit
                </td>
                <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">

                    <form action="addToCart" method="post">
                        <input type="hidden"
                                           name="productId"
                                           value="<c:out value="${product.productId}"/>">
                        <input type="text"
                                           maxlength="2"
                                           size="2"
                                           value="1"
                                           name="quantity"
                                           style="margin:5px">
                        <input type="submit"
                                           name="submit"
                                           value="<fmt:message key="addToCart"/>">
                    </form>

                </td>
            </tr>

        </c:forEach>

    </table>
</div>