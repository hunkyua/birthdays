<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Birthdays</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type = "text/javascript" src="js/script.js" ></script>
  </head>
  <body>
  <p class="addPersonText">Fill the form for addPerson a new Person</p>
  <form id="addPerson" action="addperson" method="post">
    <input class="name" type="text" name="name" placeholder="Name" required><br>
    <input class="surname" type="text" name="surname" placeholder="Surname" required><br>
    <input class="email" type="text" name="email" placeholder="Email"><br>
    <input class="date" type="date" name="dateOfBirthday" data-date-inline-picker="false" data-date-open-on-focus="true" required/><br><br>
    <input type="submit" value="Create Person">
  </form>
  <p class="error"><c:out value="${Error}"/></p>
  <br><br>
  <div class="back">
    <a href="../listofpersons">
      <button>Go Back</button>
    </a>
  </div>
  </body>
  <footer> Hunky_UA <br> Copyright © 2019</footer>
</html>
