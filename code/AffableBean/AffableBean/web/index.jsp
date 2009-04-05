            <%--
                Document   : index
                Created on : Feb 18, 2009, 2:36:42 PM
                Author     : nbuser
            --%>


<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sql:setDataSource dataSource="jdbc/affableBean" />
<sql:query var="categories" sql="SELECT * FROM category" />


            <div id="indexLeftColumn">
                <div id="welcomeText">
                    <p><span style="font-size: larger">Welcome to the online home of the
                        Affable Bean Green Grocer.</span></p>

                    <p>Enjoy browsing and learning more about our unique home delivery
                        service bringing you fresh organic produce, dairy, meats, breads
                        and other delicious and healthy items to your doorstep.</p>

                    <p><a href="category.jsp">category page</a></p>
                </div>
            </div>

            <div id="indexRightColumn">

                <c:forEach var="row" items="${categories.rows}">
                    
                    <div class="categoryBox">

                        <a href="category?<c:out value="${row.category_id}"/>">

                            <div class="categoryLabel">
                                <span class="opaqueText">  <!-- is there a better way? -->
                                    <c:out value="${row.name}"/>
                                </span>
                            </div>

                            <img src="<c:out value="${row.image_path}"/>"
                                alt="<c:out value="${row.name}"/>"
                                class="categoryImage">
                        </a>
                    </div>

                </c:forEach>

            </div>