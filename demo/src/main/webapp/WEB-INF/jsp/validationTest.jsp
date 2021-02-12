<%--
  Created by IntelliJ IDEA.
  User: S529822
  Date: 2/12/2021
  Time: 12:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>LicensePlateInput</h2>
<form action="/validationForm/validate" method="post" id="myForm">
    <label for="licensePlate">License Plate:</label><br>
    <input type="text" id="licensePlate" name="licensePlate"><br>
    <label for="ownerName">Owner Name:</label><br>
    <select name="ownerName" id="ownerName">
        <option value="Nick">Nick</option>
        <option value="Matthew">Loser</option>
    </select> <br>
    <label for="yearsOwned">Years Owned:</label><br>
    <input type="number" id="yearsOwned" name="yearsOwned"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
