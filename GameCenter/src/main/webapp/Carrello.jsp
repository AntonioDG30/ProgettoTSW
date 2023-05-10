<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	CarrelloBean Carrello = null; 
%>

<%!	
	String Email="";
%>

<%
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Carrello=(CarrelloBean)session.getAttribute("Carrello");
	}
%>
<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
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
							<td><a href="General_ProductControl?action=RimuoviCarrello&CodSeriale=<%=Prod.getCodSeriale()%>">Rimuovi dal Carrello</a></td>
						</tr>
				<%
					} 
				%>
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