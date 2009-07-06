<%--
    Document   : cart
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<%@page contentType="text/html; charset=UTF-8"%> <%-- required for proceed to checkout arrow --%>

<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB" scope="page" >
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>


<%-- HTML markup starts below --%>

<div id="cartColumn">

    <c:choose>
        <c:when test="${sessionScope.cart.numberOfItems != 0}">
            <p>Your shopping cart contains ${sessionScope.cart.numberOfItems} items.</p>
        </c:when>
        <c:otherwise>
            <p>Your shopping cart is empty.</p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <c:if test="${sessionScope.cart.numberOfItems != 0}">
            <a href="viewCart?clear=true" class="rounded bubble hMargin">clear cart</a>
        </c:if>

            <a href="category<c:if test="${empty sessionScope.selectedCategory}">?1</c:if>"
                        class="rounded bubble hMargin">continue shopping</a>

        <c:if test="${sessionScope.cart.numberOfItems != 0}">
            <a href="checkout" class="rounded bubble hMargin">proceed to checkout ➟</a>
        </c:if>
    </div>

    <c:if test="${sessionScope.cart.numberOfItems != 0}">

        <h4 id="subtotal">subtotal: $ <c:out value="${sessionScope.cart.total}"/></h4>

        <table id="cartTable">

            <tr class="header">
                <th>product</th>
                <th>name</th>
                <th>price</th>
                <th>quantity</th>
            </tr>

            <c:forEach var="item" items="${sessionScope.cart.items}" varStatus="iter">

                <c:set var="product" value="${item.item}" />

                <tr>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        <c:out value="[ image ]"/>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        <c:out value="${product.name}"/>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        $ <c:out value="${product.price * item.quantity}"/>
                        <br>
                        <span class="smallText">( $ <c:out value="${product.price}"/> / unit )</span>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">

                        <form action="updateCart" method="post">
                            <input type="hidden" name="productId" value="<c:out value="${product.productId}"/>">
                            <input type="text" maxlength="2" size="2" value="${item.quantity}" name="quantity" style="margin:5px">
                            <input type="submit" name="submit" value="update">
                        </form>

                    </td>
                </tr>

            </c:forEach>

        </table>

    </c:if>
</div>