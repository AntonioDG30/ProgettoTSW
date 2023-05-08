<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	String Result="";
	String FormMod="";
	int PuntiFedelta=0;
%>
<%	

	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    PuntiFedelta = (int)session.getAttribute("PuntiFedelta");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
		<title>Account page</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Email != null) 
			{
		%>
		<h2><%=Email%>, benvenuto nella tua pagina personale</h2>
		<% 
			}
			else
			{
				response.sendRedirect("./Login.jsp");
			}
		%>
		<h2>Salto punti fedeltà: <%=PuntiFedelta%></h2>
		<% 
			if(Result != null) 
			{
		%>
		<%=Result%>
		<% 
			}
		%>	
	</body>
</html>