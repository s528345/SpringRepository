<%--
  Created by IntelliJ IDEA.
  User: S529822
  Date: 2/6/2021
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Person Details</h2>
<form action="/postBlank" method="post">
    <label for="fName">Name:</label><br>
    <input type="text" id="fName" name="fName"><br>
    <label for="age">Age:</label><br>
    <input type="text" id="age" name="age"><br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>