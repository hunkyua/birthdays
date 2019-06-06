<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="login">
    <h1>Sign In Form</h1>
    <div id="wrapper">
        <form id="signin" method="post" action="birthdays" autocomplete="off">
            <input type="text" id="login" name="login" placeholder="login" />
            <input type="password" id="password" name="password" placeholder="password" />
            <button type="submit">&#xf0da;</button>
            <p>Don't have an account?<a href="../registration">click here</a></p>
        </form>
    </div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
