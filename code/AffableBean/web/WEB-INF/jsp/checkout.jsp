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
                <td>first name:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="firstName">
                </td>
            </tr>
            <tr>
                <td>last name:</td>
                <td>
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="lastName">
                </td>
            </tr>
            <tr>
                <td>telephone:</td>
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
                           name="address1">
                    <input type="text"
                           size="20"
                           maxlength="30"
                           name="address2">
                    Prague <select>
                               <option>1</option>
                               <option>2</option>
                               <option>3</option>
                               <option>4</option>
                               <option>5</option>
                               <option>6</option>
                               <option>7</option>
                               <option>8</option>
                               <option>9</option>
                               <option>10</option>
                               <option>11</option>
                               <option>12</option>
                               <option>13</option>
                               <option>14</option>
                               <option>15</option>
                               <option>16</option>
                           </select>
                </td>
            </tr>
            <tr>
                <td>credit card:</td>
                <td>
                    <input type="text"
                           size="19"
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