<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	String Result="";
%>
<%	
	Result = (String) request.getAttribute("Result");	
%> 

<%
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	}
%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter Login</title>
	</head>
<body>
	<% 
		if(Email != null)
		{
				response.sendRedirect("./index.jsp");
		}
	%>
	<%@include file="NavBar.jsp" %>
	<%
		if(Result != null) 
		{
	%>
	<%=Result%>
	<% 
		}
	%>
	
	<form method="post" action="./UserControl?action=Login">
		<pre>
			Inserisci i tuoi dati di accesso:
			Email: <input type="email" name="email" placeholder="Inserisci la tua email" required>
			Password: <input type="password" name="password" placeholder="********" required>
			<input type="submit"> <input type="reset">
			Non sei ancora registrato? <a href="Registrati.jsp">Registrati ora!</a>
		</pre>
	</form>
</body>
</html>