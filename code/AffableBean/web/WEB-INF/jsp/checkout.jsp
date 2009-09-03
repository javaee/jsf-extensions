<%-- 
    Document   : checkout
    Created on : Jul 14, 2009, 11:46:38 PM
    Author     : nbuser
--%>


<%@page contentType="text/html; charset=UTF-8"%> <%-- required for '€' sign --%>

<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB">
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Dispatcher so that
     users view the same page when switching between EN and CS. --%>
<c:set var="view" value="/checkout" scope="session" />

<c:set var="cart" value="${sessionScope.cart}"/>


<script src="js/jquery.validate.js" type="text/javascript"></script>

<style type="text/css">
    label { line-height: 30px; }
    error { line-height: inherit !important }
    label.error { font-size: smaller; line-height: 20px; font-style: italic; }
</style>

<script type="text/javascript">

    $(document).ready(function(){
        $("#checkoutForm").validate({
            rules: {
                name: "required",
                email: {
                    required: true,
                    email: true
                },
                phone: {
                    required: true,
                    number: true,
                    minlength: 9
                },
                address: {
                    required: true
                },
                creditcard: {
                    required: true,
                    creditcard: true
                }
            }
        });
    });
</script>


<%-- HTML markup starts below --%>

<div id="cartColumn">

    <h2><fmt:message key="checkout" /></h2>

    <p><fmt:message key="checkoutText" /></p>

    <form id="checkoutForm" action="purchase" method="post">
        <table id="checkoutTable" class="rounded">
            <tr>
                <td><label for="name"><fmt:message key="customerName" />:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="name"
                           id="name"
                           class="required">
                </td>
            </tr>
            <tr>
                <td><label for="email"><fmt:message key="email" />:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="email"
                           id="email"
                           class="email required">
                </td>
            </tr>
            <tr>
                <td><label for="phone"><fmt:message key="phone" />:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="phone"
                           id="phone">
                </td>
            </tr>
            <tr>
                <td><label for="address"><fmt:message key="address" />:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="address"
                           id="address">

                    <br>
                    <fmt:message key="prague" /> <select name="cityRegion">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="creditcard"><fmt:message key="creditCard" />:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="19"
                           name="creditcard"
                           id="creditcard"
                           class="creditcard">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="<fmt:message key="submitOrder" />">
                </td>
            </tr>
        </table>
    </form>

    <div id="infoBox">

        <ul>
            <li><fmt:message key="nextDayGuarantee" /></li>
            <li><fmt:message key="deliveryFee" /></li>
        </ul>

        <table id="priceBox" class="rounded">
            <tr>
                <td><fmt:message key="subtotal" />:</td>
                <td class="priceColumn">€ <c:out value="${cart.total}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="surcharge" />:</td>
                <td class="priceColumn">€ 3.00</td>
            </tr>
            <tr></tr>
            <tr>
                <td class="total"><fmt:message key="total" />:</td>
                <td class="total priceColumn">€ <c:out value="${cart.total + 3.00}"/></td>
            </tr>
        </table>
    </div>
</div>