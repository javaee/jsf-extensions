            <%--
                Document   : index
                Created on : Feb 18, 2009, 2:36:42 PM
                Author     : nbuser
            --%>

<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sql:setDataSource dataSource="jdbc/affableBean" />
<sql:query var="categories" sql="SELECT * FROM category"></sql:query>


            <div id="indexLeftColumn">
                <div id="welcomeText">
                    <p>Welcome to the online home of the Affable Bean Green Grocer.</p>

                    <p>Enjoy browsing and learning more about our unique home delivery
                        service bringing you fresh organic produce, dairy, meats, eggs
                        and other delicious and healthy items to your doorstep.</p>

                    <p><a href="category.jsp">category page</a></p>
                </div>
            </div>

            <div id="indexRightColumn">

                <c:forEach var="row" items="${categories.rows}">

                    <div class="categoryBox">
                            <c:out value="${row.name}"/>
                        <img src="<c:out value="${row.image_path}"/>"
                             alt="<c:out value="${row.name}"/>">
                    </div>

                </c:forEach>

            </div>