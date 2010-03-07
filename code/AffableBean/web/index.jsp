<%--
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
--%>


<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between EN and CS. --%>
<c:set var='view' value='/index' scope='session' />


<%-- HTML markup starts below --%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p>
            <span style="font-size: larger">
                <fmt:message key='greeting' />
            </span>
        </p>

        <p><fmt:message key='introText' /></p>
    </div>
</div>

<div id="indexRightColumn">

    <c:forEach var='category' items='${categories}'>

        <div class="categoryBox">

            <a href="<c:url value='category?${category.id}'/>">

                <span class="categoryLabel"></span>
                <span class="categoryLabelText"><fmt:message key='${category.name}'/></span>

                <img src="<c:out value='${initParam.categoryImagePath}${category.name}.jpg'/>"
                     alt="<fmt:message key='${category.name}'/>"
                     class="categoryImage">
            </a>
        </div>

    </c:forEach>

</div>