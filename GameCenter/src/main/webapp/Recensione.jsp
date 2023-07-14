<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>



<%!	
	String CodSeriale="";
	String Email="";
%>

<%
	CodSeriale = (String) request.getAttribute("codSeriale");
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	}
	
%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="ISO-8859-1">
		<title>GameCenter</title>
		<link href="LayoutSito/css/registrati.css" rel="stylesheet" type="text/css">	
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<div class="formMetodiPagamento">
        	<form method="post" action="./OrdiniControl?action=Recensione">
				<div class="form">
      				<div class="subtitle">Recensisci prodotto: <%=CodSeriale%></div>
			      	<div class="input-container ic1">
				        <input id="Valutazione" name="NumeroCarta" class="input" type="number" min="1" max="5" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Valutazione" class="placeholder">Valutazione</label>
				    </div>
				    <div class="input-container">
				    	Descrizione: <textarea name="Descrizione" rows="5" cols="35" required placeholder="Scrivi qui la tua recensione"></textarea>				    
				    </div>
				    <input type="hidden" name="CodProdotto" value="<%=CodSeriale%>">
				    <br>
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <%@include file="Footer.jsp" %>
	</body>
</html>