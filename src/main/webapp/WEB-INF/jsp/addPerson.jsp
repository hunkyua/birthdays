<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script type = "text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js" ></script>
  </head>
  <body>
  <p class="addPersonText">Fill the form for add a new Person</p>
  <form id="addPerson" action="createperson" method="post">
    <label>
      <input class="name" type="text" name="name" placeholder="Name"/>
    </label><br>
    <label>
      <input class="surname" type="text" name="surname" placeholder="Surname" />
    </label><br>
    <label>
      <input class="email" type="text" name="email" placeholder="Email"/>
    </label><br>
    <label>
      <input class="date" type="date" name="dateOfBirth" />
    </label><br><br>
    <input type="submit" value="Create Person">
  </form>
  <p class="error"><c:out value="${Error}"/></p>
  <p class="alert"><c:out value="${Alert}"/></p>
  <br><br>
  <div class="back">
    <a href="listofpersons">
      <button>Go Back</button>
    </a>
  </div>
  </body>
  <footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
