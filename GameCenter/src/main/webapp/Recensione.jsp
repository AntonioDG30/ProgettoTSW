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
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<h2>Recensisci prodotto: <%=CodSeriale%></h2>
		
		<form method="post" action="./OrdiniControl?action=Recensione">
			<pre>
				Compila la tua valutazione:
				Valutazione: <input type="number" name="Valutazione" min = 1 max = 5 required>	
				Descrizione: 
					<textarea name="Descrizione" rows="5" cols="33" required>Scrivi qui la tua recensione</textarea>
				<input type="hidden" name="CodProdotto" value="<%=CodSeriale%>">	
				<input type="submit" > <input type="reset">
				<a href="./OrdiniControl?action=DettagliOrdine">Annulla Operazione</a>
			</pre>
		</form>
	</body>
</html>