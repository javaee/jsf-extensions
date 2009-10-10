<%--
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
--%>


<%@page contentType="text/html; charset=UTF-8"%> <%-- required for '€' sign --%>

<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between EN and CS. --%>
<c:set var="view" value="/checkout" scope="session"/>

<c:set var="cart" value="${sessionScope.cart}"/>


<script src="js/jquery.validate.js" type="text/javascript"></script>

<%-- Add Czech field validation messages if 'cs' is the chosen locale --%>
<c:choose>
  <%-- When 'language' session attribute hasn't been set, check browser's preferred locale --%>
  <c:when test="${empty sessionScope.language}">
    <c:if test="${pageContext.request.locale.language eq 'cs'}">
      <script src="js/localization/messages_cs.js" type="text/javascript"></script>
    </c:if>
  </c:when>
  <%-- Otherwise, check 'language' session attribute --%>
  <c:otherwise>
    <c:if test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] eq 'cs'}">
      <script src="js/localization/messages_cs.js" type="text/javascript"></script>
    </c:if>
  </c:otherwise>
</c:choose>

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

    <h2><fmt:message key="checkout"/></h2>

    <p><fmt:message key="checkoutText"/></p>

    <form id="checkoutForm" action="purchase" method="post">
        <table id="checkoutTable" class="rounded">
            <c:if test="${!empty requestScope.errorMessage}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <span class="errorMessage smallText"><fmt:message key="errorMessage"/><br>

                            <c:if test="${!empty requestScope.nameError}">
                                <span style="margin-left: 6em"><fmt:message key="nameError"/></span><br>
                            </c:if>
                            <c:if test="${!empty requestScope.emailError}">
                                <span style="margin-left: 6em"><fmt:message key="emailError"/></span><br>
                            </c:if>
                            <c:if test="${!empty requestScope.phoneError}">
                                <span style="margin-left: 6em"><fmt:message key="phoneError"/></span><br>
                            </c:if>
                            <c:if test="${!empty requestScope.addressError}">
                                <span style="margin-left: 6em"><fmt:message key="addressError"/></span><br>
                            </c:if>
                            <c:if test="${!empty requestScope.cityRegionError}">
                                <span style="margin-left: 6em"><fmt:message key="cityRegionError"/></span><br>
                            </c:if>
                            <c:if test="${!empty requestScope.ccNumberError}">
                                <span style="margin-left: 6em"><fmt:message key="ccNumberError"/></span>
                            </c:if>

                        </span></td>
                </tr>
            </c:if>
            <tr>
                <td><label for="name"><fmt:message key="customerName"/>:</label></td>
                <td>
                    <input type="text"
                           size="30"
                           maxlength="30"
                           name="name"
                           id="name"
                           class="required">
                </td>
            </tr>
            <tr>
                <td><label for="email"><fmt:message key="email"/>:</label></td>
                <td>
                    <input type="text"
                           size="30"
                           maxlength="30"
                           name="email"
                           id="email"
                           class="email required">
                </td>
            </tr>
            <tr>
                <td><label for="phone"><fmt:message key="phone"/>:</label></td>
                <td>
                    <input type="text"
                           size="30"
                           maxlength="30"
                           name="phone"
                           id="phone">
                </td>
            </tr>
            <tr>
                <td><label for="address"><fmt:message key="address"/>:</label></td>
                <td><input type="text"
                           size="30"
                           maxlength="30"
                           name="address"
                           id="address">

                    <br>
                    <fmt:message key="prague"/> <select name="cityRegion">
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
                <td><label for="creditcard"><fmt:message key="creditCard"/>:</label></td>
                <td><input type="text"
                           size="30"
                           maxlength="19"
                           name="ccNumber"
                           id="creditcard"
                           class="creditcard">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="<fmt:message key="submitOrder"/>">
                </td>
            </tr>
        </table>
    </form>

    <div id="infoBox">

        <ul>
            <li><fmt:message key="nextDayGuarantee"/></li>
            <li><fmt:message key="deliveryFee1"/>
                <c:out value="${initParam.deliverySurcharge}"/>
                <fmt:message key="deliveryFee2"/></li>
        </ul>

        <table id="priceBox" class="rounded">
            <tr>
                <td><fmt:message key="subtotal"/>:</td>
                <td class="priceColumn">€ <c:out value="${cart.subtotal}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="surcharge"/>:</td>
                <td class="priceColumn">€ <c:out value="${initParam.deliverySurcharge}"/></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="total"><fmt:message key="total"/>:</td>
                <td class="total priceColumn">€ <c:out value="${cart.total}"/></td>
            </tr>
        </table>
    </div>
</div>