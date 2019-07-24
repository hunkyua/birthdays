<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script type = "text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js" ></script>
</head>
<body>
<a style="align-content: center" href="addperson"><button>Add Person</button></a><br>
<table class="persons">
    <tr>
        <th>№</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</th>
        <th>DateOfBirth</th>
    </tr>
    <c:forEach items="${persons}" var="person" varStatus="loop">
    <tr onclick="onHover(${loop.count})">
        <td id="loop">${loop.count}</td>
        <td>${person.name}</td>
        <td>${person.surname}</td>
        <td>${person.email}</td>
        <td>${person.dateOfBirth}</td>
    </tr>
    </c:forEach>
</table>
<br><br><br>
<div class="menu">
    <a href="menu"><button>Go Back</button></a>
</div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
