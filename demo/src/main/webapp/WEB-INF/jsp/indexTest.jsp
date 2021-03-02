<!DOCTYPE html>
<html>
<head>
<title>Page Title</title>
</head>
<body>

<%-- welcome to jsp :) --%>

<h1>This is a Heading with jsp!</h1>
<p>This is a paragraph.</p>
    <%! int x = 0; %>
<%
    int x = 3;
    //out.println("your number with one more is: " + ++x);
%>
<p>your number is now after adding: <%= x %> </p>

</body>
</html>
