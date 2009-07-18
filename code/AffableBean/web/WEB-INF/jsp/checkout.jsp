<%-- 
    Document   : checkout
    Created on : Jul 14, 2009, 11:46:38 PM
    Author     : nbuser
--%>


<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB" scope="page" >
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<c:set var="cart" value="${sessionScope.cart}"/>


<%-- HTML markup starts below --%>

<div id="cartColumn">

    <h2>checkout</h2>

    <p>In order to purchase the items in your shopping cart, please provide us with
        the following information:</p>

    <table id="checkoutTable" class="rounded">
        <form action="purchase" method="post">
            <tr>
                <td>name:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="name">
                </td>
            </tr>
            <tr>
                <td>email:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="email">
                </td>
            </tr>
            <tr>
                <td>phone:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="phone">
                </td>
            </tr>
            <tr>
                <td>address:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="address">

                    Prague <select name="cityRegion">
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
                <td>credit card:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="19"
                           name="ccNumber">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="submit purchase">
                </td>
            </tr>
        </form>
    </table>

    <div id="infoBox">

        <ul>
            <li>Next-day delivery is guaranteed.</li>
            <li>A $3.00 delivery surcharge is applied to all purchase orders.</li>
        </ul>

        <table id="priceBox" class="rounded">
            <tr>
                <td>subtotal:</td>
                <td class="priceColumn">$ <c:out value="${cart.total}"/></td>
            </tr>
            <tr>
                <td>delivery surcharge:</td>
                <td class="priceColumn">$ 3.00</td>
            </tr>
            <tr></tr>
            <tr>
                <td class="total">total:</td>
                <td class="total priceColumn">$ <c:out value="${cart.total + 3.00}"/></td>
            </tr>
        </table>
    </div>
</div>