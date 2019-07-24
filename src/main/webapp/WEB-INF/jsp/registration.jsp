<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</head>
<body class="registration">
<div class="form">
    <h1>Welcome to registration on Birthdays App :)</h1>
    <form action="doRegistration" method="post">
        <input type="text" name="login" placeholder="login" required><br>
        <input type="text" name="password" placeholder="password" required> <br>
        <input type="submit" value="Enter">
    </form>
</div>
<p class="error"><c:out value="${Error}"/></p>
<br><br>
<div class="back">
    <a href="../../index">
        <button>Go Back</button>
    </a>
</div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
