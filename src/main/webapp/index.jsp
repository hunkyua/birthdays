<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body class="login">
    <div id="wrapper">
        <h1>BirthdaysApp ;)</h1>
        <form id="signin" method="post" action="menu" autocomplete="off" >
            <input type="text" id="login" name="login" placeholder="login" required/>
            <input type="password" id="password" name="password" placeholder="password" required/>
            <button type="submit">&#xf0da;</button>
            <p>Don't have an account?<a href="registration">click here</a></p>
            <p class="error"><c:out value="${Error}"/></p>
            <p class="alert"><c:out value="${Alert}"/></p>
        </form>
    </div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
