<%--
  Created by IntelliJ IDEA.
  User: S529822
  Date: 2/6/2021
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        table {
            width:100%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        th {
            background-color: black;
            color: white;
        }


    </style>
    <script type="text/javascript" src = "js/addRow.js"></script>
</head>
<body>
    <h2>Student Details</h2>
    <label for="name">Name:</label><br>
    <input type="text" name="name" id="name" /><br>
    <label for="age">Age:</label><br>
    <input type="number" name="age" id="age" /><br>
    <label for="classes">Classes:</label><br>
    <input type="text" name="classes" id="classes" /><br>
    <button onclick="addRow();">Add Student</button>
    <button onclick="addRows();">Get Students</button>
    <input type="hidden" name="status" id="status" /><br>


<div align="center">
    <h2>List of Students</h2>
    <table id="myTable">
        <tr>
            <th>Name</th>
            <th>Age</th>
            <th>Class</th>
        </tr>

    </table>
</div>



</body>
</html>