<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.topnav {
  overflow: hidden;
  background-color: #333;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}


</style>
</head>
<body>

<div class="topnav">
  <a href="index.jsp">Home</a>
  <a href="Carrello.jsp">Carrello</a>
  <a href="Login.jsp">Login</a>
  <a href="./UserControl?action=Logout">Logout</a>
</div>

</body>
</html>