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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter Registrati</title>
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
	
	<h2>Registra il tuo nuovo account</h2>
	<form method="post" action="./UserControl?action=Registrati">
		<pre>
			Inserisci i seguenti dati:
			Email: <input type="email" name="email" placeholder="Inserisci la tua email" required>
			Password: <input type="password" name="password" placeholder="********" required>
			<input type="submit"> <input type="reset">
		</pre>
	</form>
</body>
</html>