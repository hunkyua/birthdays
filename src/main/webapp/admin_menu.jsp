<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="admin_menu">
<h1>Hello <c:out value="${login}"/></h1>
<div class="menu">
    <a href="addPerson.jsp">
        <button>Add Person</button>
    </a><br><br><br>
    <a href="logout">
        <button>Logout</button>
    </a>
</div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
