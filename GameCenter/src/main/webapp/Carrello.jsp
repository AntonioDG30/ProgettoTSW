<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	float PrezzoTotale=0;
%>

<%
	CarrelloBean Carrello = null; 

	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Carrello=(CarrelloBean)session.getAttribute("Carrello");
	}
	
	PrezzoTotale=0;
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Carrello</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Carrello != null && Carrello.getListaCarrello().size() != 0) 
			{ 
		%>
				<table border="1">
					<caption>Carrello</caption>
					<tr>
						<th>Nome</th>
						<th>Quantita</th>
						<th>Prezzo</th>
						<th>Piattaforma</th>
						<th>Azione</th>
					</tr>
					<% 
						List<ProductBean> ProdottoCarrello = Carrello.getListaCarrello(); 	
					   	for(ProductBean Prod: ProdottoCarrello) 
					   	{
					%>
					<tr>
						<td><%=Prod.getNome()%></td>
						<td><%=Prod.getQuantita()%></td>
						<td><%=Prod.getPrezzo()%></td>
						<td><%=Prod.getPiattaforma()%></td>
						<%PrezzoTotale = PrezzoTotale + (Prod.getPrezzo() * Prod.getQuantita());%>
						<td><a href="GeneralProductControl?action=RimuoviCarrello&CodSeriale=<%=Prod.getCodSeriale()%>">Rimuovi dal Carrello</a></td>
					</tr>
				<%
					} 
				   	/*Tronchiamo float a solo due cifre decimali*/
					Locale.setDefault(Locale.US);
					String PrezzoTotaleString = String.format("%.2f", PrezzoTotale);
					Locale.setDefault(Locale.ITALY);
				%>
				<tr>
					<td colspan="3">Prezzo Totale: <%=PrezzoTotaleString%></td>
				    <td colspan="2"><a href="./OrdiniControl?action=Checkout"><input type="button" value="Acquista"></a></td>
			</tr>
				</table>		
		<% 
			}
			else
			{
		%>
				<h2>Ancora nessun prodotto inserito nel carrello</h2>
		<%					
			}
		%>
		
		
	</body>
</html>