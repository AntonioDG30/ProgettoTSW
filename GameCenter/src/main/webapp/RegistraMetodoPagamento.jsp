<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
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
		<title>GameCenter</title>
		<link href="LayoutSito/css/registrati.css" rel="stylesheet" type="text/css">	
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<% 
			if(Email == null)
			{
					response.sendRedirect("./Login.jsp");
			}
		%>
		<%@include file="NavBar.jsp" %>
		<div class="formMetodiPagamento">
        	<form method="post" action="./UserControl?action=NuovoMetodoPagamento">
				<div class="form">
      				<div class="subtitle">Registra nuovo metodo di pagamento</div>
			      	<div class="input-container ic1">
				        <input id="NumeroCarta" name="NumeroCarta" class="input" type="email" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="NumeroCarta" class="placeholder">Numero Carta</label>
				    </div>
				    <div class="input-container">
				        <input id="Titolare" name="Titolare" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Titolare" class="placeholder">Titolare Carta</label>
				    </div>
				    <div class="input-container">
				        <input id="DataScadenza" name="DataScadenza" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="DataScadenza" class="placeholder">Data Scadenza Carta</label>
				    </div>
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <%@include file="Footer.jsp" %>
	</body>
</html>