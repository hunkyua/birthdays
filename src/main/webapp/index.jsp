<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="index.title"/></title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body class="login">

<a href="?language=en" methods="get">English</a>
<a href="?language=ru" methods="get">Русский</a>

    <div id="wrapper">
        <h1><spring:message code="index.appNaming"/></h1>
        <form id="signin" method="post" action="menu" autocomplete="off">
            <input type="text" id="login" name="login" placeholder="<spring:message code="index.login"/>" required/>
            <input type="password" id="password" name="password" placeholder="<spring:message code="index.password"/>" required/>
            <button type="submit">&#xf0da;</button>
            <p><spring:message code="index.dontHaveAnAccount"/><a href="registration"> <spring:message code="index.clickHere"/></a></p>
            <p class="error"><c:out value="${Error}"/></p>
            <p class="alert"><c:out value="${Alert}"/></p>
        </form>
    </div>
</body>
<footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
