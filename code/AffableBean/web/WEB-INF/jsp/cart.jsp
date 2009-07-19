<%--
    Document   : cart
    Created on : Feb 18, 2009, 7:07:03 PM
    Author     : nbuser
--%>


<%@page contentType="text/html; charset=UTF-8"%> <%-- required for 'proceed to checkout' arrow --%>

<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB" scope="page" >
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<c:set var="cart" value="${sessionScope.cart}"/>


<%-- HTML markup starts below --%>

<div id="cartColumn">

    <c:choose>
        <c:when test="${cart.numberOfItems > 1}">
            <p>Your shopping cart contains ${cart.numberOfItems} items.</p>
        </c:when>
        <c:when test="${cart.numberOfItems == 1}">
            <p>Your shopping cart contains ${cart.numberOfItems} item.</p>
        </c:when>
        <c:otherwise>
            <p>Your shopping cart is empty.</p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${cart.numberOfItems != 0}">

            <c:url var="url" value="viewCart">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${url}" class="rounded bubble hMargin">clear cart</a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                <c:when test="${!empty sessionScope.selectedCategory}">
                    category
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="url" value="${value}" />
        <a href="${url}" class="rounded bubble hMargin">continue shopping</a>

        <%-- checkout widget --%>
        <c:if test="${cart.numberOfItems != 0}">
            <a href="<c:url value="checkout" />" class="rounded bubble hMargin">proceed to checkout ➟</a>
        </c:if>
    </div>

    <c:if test="${cart.numberOfItems != 0}">

        <h4 id="subtotal">subtotal: $ <c:out value="${cart.total}"/></h4>

        <table id="cartTable">

            <tr class="header">
                <th>product</th>
                <th>name</th>
                <th>price</th>
                <th>quantity</th>
            </tr>

            <c:forEach var="item" items="${cart.items}" varStatus="iter">

                <c:set var="product" value="${item.item}" />

                <tr>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        <c:out value="[ image ]"/>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        <c:out value="${product.name}"/>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">
                        $ <c:out value="${item.total}"/>
                        <br>
                        <span class="smallText">( $ <c:out value="${product.price}"/> / unit )</span>
                    </td>
                    <td class="${((iter.index % 2) == 0) ? 'even' : 'odd'}">

                        <form action="updateCart" method="post">
                            <input type="hidden"
                                   name="productId"
                                   value="<c:out value="${product.id}"/>">
                            <input type="text"
                                   maxlength="2"
                                   size="2"
                                   value="${item.quantity}"
                                   name="quantity"
                                   style="margin:5px">
                            <input type="submit"
                                   name="submit"
                                   value="update">
                        </form>

                    </td>
                </tr>

            </c:forEach>

        </table>

    </c:if>
</div>