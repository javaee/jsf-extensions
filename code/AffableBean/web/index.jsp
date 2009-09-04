<%--
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
--%>


<jsp:useBean id="affableBeanDB" class="database.AffableBeanDB">
    <jsp:setProperty name="affableBeanDB" property="database" value="${affableBeanDBAO}" />
</jsp:useBean>

<%-- HTML markup starts below --%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p>
            <span style="font-size: larger">
                <fmt:message key="greeting" />
            </span>
        </p>

        <p><fmt:message key="introText" /></p>
    </div>
</div>

<div id="indexRightColumn">

    <c:forEach var="category" items="${affableBeanDB.categories}">

        <div class="categoryBox">

            <a href="<c:url value="category?${category.id}"/>">

                <span class="categoryLabel"></span>
                <span class="categoryLabelText"><fmt:message key="${category.name}"/></span>

                <img src="<c:out value="${category.imagePath}"/>"
                     alt="<fmt:message key="${category.name}"/>"
                     class="categoryImage">
            </a>
        </div>

    </c:forEach>

</div>