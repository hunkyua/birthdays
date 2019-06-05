<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="login">
<div class="form">
    <h1>Welcome to Birthdays App ;)</h1>
    <form action="birthdays" method="post">
        <input type="text" name="login" placeholder="login"><br>
        <input type="text" name="password" placeholder="password"> <br>
        <button type="button" onclick="location.href = '/registration'">Register</button>
        <input type="submit" value="Enter">
    </form>
</div>

</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
