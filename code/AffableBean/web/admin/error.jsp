<%-- 
    Document   : error
    Created on : Jul 13, 2009, 12:24:52 AM
    Author     : nbuser
--%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/affablebean.css">
        <link rel="shortcut icon" href="../img/favicon.ico">

        <script src="../resources/jquery_1.2.6/jquery-1.2.6.js" type="text/javascript"></script>
        <script src="../resources/jquery_1.2.6/jquery.corners.js" type="text/javascript"></script>

        <script type="text/javascript">
            $(document).ready( function(){
                $('.rounded').corners();
            });
        </script>

        <title>The Affable Bean :: Admin Console</title>
    </head>

    <body>
        <div id="main">
            <div id="header">
                <div id="headerBar"></div>

                <a href="<c:url value="index.jsp"/>"><img src="../img/logoText.png" id="logoText" alt="the affable bean"></a>
            </div>

            <h2>admin console</h2>

            <div id="loginBox" class="rounded">

                <p class="error">Invalid username or password.</p>

                <p>Return to <strong><a href="<c:url value="login.jsp"/>">admin login</a></strong>.</p>

            </div>
        </div>
    </body>
</html>