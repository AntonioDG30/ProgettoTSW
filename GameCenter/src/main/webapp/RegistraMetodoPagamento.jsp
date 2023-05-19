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
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<h2>Registra nuovo metodo di pagamento</h2>
		<form method="post" action="./UserControl?action=NuovoMetodoPagamento">
			<pre>
				Inserisci i dati della tua nuova carta:
				Numero Carta: <input type="text" name="NumeroCarta" placeholder="Inserisci Numero Carta" required>	
				Intestatario Carta: <input type="text" name="Titolare" placeholder="Inserisci Intestatario Carta" required>
				Data Scadenza: <input type="date" name="DataScadenza" placeholder="Inserisci Data Scadenza" required>			
				<input type="submit" > <input type="reset">
				<a href="./OrdiniControl?action=Checkout">Annulla Operazione</a>
			</pre>
		</form>
</body>
</html>