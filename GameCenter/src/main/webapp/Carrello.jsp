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
%>
<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*,java.text.DecimalFormat"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Carrello != null) 
			{ 
		%>
				<h2>Carrello</h2>
				<table border="1">
				<tr>
					<th>Nome</th>
					<th>Quantita</th>
					<th>Prezzo</th>
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
							<%PrezzoTotale = PrezzoTotale + (Prod.getPrezzo() * Prod.getQuantita());%>
							<td><a href="General_ProductControl?action=RimuoviCarrello&CodSeriale=<%=Prod.getCodSeriale()%>">Rimuovi dal Carrello</a></td>
						</tr>
				<%
					} 
				   	/*Tronchiamo float a solo due cifre decimali*/
					DecimalFormat df = new DecimalFormat("#.##"); 
					String PrezzoTotaleString = df.format(PrezzoTotale);
				%>
				<tr>
					<td colspan="2">Prezzo Totale: <%=PrezzoTotaleString%></td>
				    <td colspan="2"><a href="./OrdiniControl?action=Acquista&PrezzoTotale=<%=PrezzoTotale%>"><input type="button" value="Acquista"></a></td>
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