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
		<title>GameCenter</title>
		<link href="LayoutSito/css/navBar.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="navbar2">
			<div class="logo">
				<a href="index.jsp">
					<img src="LayoutSito/img/Logo1_Scontornato.png" alt="Home">
				</a>
			</div>
			
			<div class="boxRicerca">
				 <form action="" method="get">
			   		 <div class="ricerca">
			     		 <input type="search" class="barraRicerca" placeholder="Cerca su GameCenter" name = "ricerca">
			     		 <button type="submit" class="pulsanteRicerca">
			      			 <img src="LayoutSito/img/ricerca.png" alt="Cerca">
			    		 </button>
			  		 </div>
				</form>
			</div>
			
			<div class="icone">
				<a href="./UserControl?action=VisualizzaDati">
					<img src="LayoutSito/img/profilo.png" alt="Account">
				</a>
				<a href="Carrello.jsp">
					<img src="LayoutSito/img/carrello.png" alt="Carrello">
				</a>
				<%
					if(EmailUtente != null)
				  	{
				%>
						<a href="./UserControl?action=Logout">
							<img src="LayoutSito/img/logout.png" alt="Account">
						</a>
				<%
				  	}
				%>
			</div>
			
		</div>
	</body>
</html>
