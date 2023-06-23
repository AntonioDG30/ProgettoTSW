<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String EmailUtente="";
%> 

<%
	synchronized(session) 
	{
		session = request.getSession();
	    EmailUtente=(String)session.getAttribute("Email");
	}
%>
<!DOCTYPE html>
<html lang="it">
	<head>
	
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<style>
			body 
			{
			  margin: 0;
			  font-family: Arial, Helvetica, sans-serif;
			}
			
			.topnav 
			{
			  overflow: hidden;
			  background-color: #333;
			}
			
			.topnav a 
			{
			  float: left;
			  color: #f2f2f2;
			  text-align: center;
			  padding: 14px 16px;
			  text-decoration: none;
			  font-size: 17px;
			}
			
			.topnav a:hover 
			{
			  background-color: #ddd;
			  color: black;
			}
			
			.topnav .icon 
			{
			  float: right;
			}
			
			
		
		
		</style>
		<title>GameCenter</title>
	</head>
	<body>

		<div class="topnav">
			<a href="index.jsp"><img src="LayoutSito/img/Logo1_Scontornato.png" alt="Home" width="150" height="20"></a>
			<div class="icon">
				<a href="Carrello.jsp"><img src="LayoutSito/img/carrello.png" alt="Account" width="25" height="20"></a>
				<a href="./OrdiniControl"><img src="LayoutSito/img/profilo.png" alt="Account" width="25" height="20"></a>
				<%
					if(EmailUtente != null)
				  	{
				%>
						<a href="./UserControl?action=Logout"><img src="LayoutSito/img/logout.png" alt="Account" width="25" height="20"></a>
				<%
				  	}
				%>
			</div>
		</div>

	</body>
</html>
