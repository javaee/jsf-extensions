<%-- 
    Document   : checkout
    Created on : Jul 14, 2009, 11:46:38 PM
    Author     : nbuser
--%>


<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB" scope="page" >
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<c:set var="cart" value="${sessionScope.cart}"/>


<script src="js/jquery.validate.js" type="text/javascript"></script>

<style type="text/css">
    error { line-height: inherit; !important }
    label { line-height: 30px; }
    label.error { font-size: smaller; line-height: 20px; font-style: italic; }
    input.error { border: 1px dotted; line-height: 20px; }
</style>

<script>

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
                    minlength: 5
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

    <h2>checkout</h2>

    <p>In order to purchase the items in your shopping cart, please provide us with
        the following information:</p>

    <form id="checkoutForm" action="purchase" method="post">
        <table id="checkoutTable" class="rounded">
            <tr>
                <td><label for="name">name:</label></td>
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
                <td><label for="email">email:</label></td>
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
                <td><label for="phone">phone:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="phone"
                           id="phone">
                </td>
            </tr>
            <tr>
                <td><label for="address">address:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="30"
                           name="address"
                           id="address">

                    <br>
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
                <td><label for="creditcard">credit card:</label></td>
                <td>
                    <input type="text"
                           size="28"
                           maxlength="19"
                           name="ccNumber"
                           id="creditcard"
                           class="creditcard">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="submit purchase">
                </td>
            </tr>
        </table>
    </form>

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