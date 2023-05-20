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
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<h2>Registra nuovo indirizzo di spedizione</h2>
		<form method="post" action="./UserControl?action=NuovoIndirizzo">
			<pre>
				Inserisci i dati del tuo nuovo indirizzo di spedizione:
				Nome: <input type="text" name="Nome" placeholder="Inserisci Nome" required>	
				Cognome: <input type="text" name="Cognome" placeholder="Inserisci Cognome" required>	
				CAP: <input type="number" name="CAP" placeholder="Inserisci CAP" required>
				Città: <input type="text" name="Citta" placeholder="Inserisci Città" required>	
				Provincia: <input type="text" name="Provincia" placeholder="Inserisci Provincia" required>
				Via: <input type="text" name="Via" placeholder="Inserisci Via" required>	
				Civico: <input type="number" name="Civico" placeholder="Inserisci Civico" required>	
				Telefono: <input type="text" name="Telefono" placeholder="Inserisci Telefono" required>
				<input type="hidden" name="email" value="<%=Email%>">			
				<input type="submit" > <input type="reset">		
				<a href="./OrdiniControl?action=Checkout">Annulla Operazione</a>			
			</pre>
		</form>
</body>
</html>