<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	ProductBean product = (ProductBean) request.getAttribute("product");
%>

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
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="LayoutSito/css/dettagli.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<title>Dettagli Prodotto</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<%
			if (product != null) 
			{
		%>
		<div class="prodotti">
			<img src="Immagini/<%=product.getImmagine()%>">
			<div class="dettagli">
				<%=product.getNome()%>
				<%=product.getPrezzo()%>
			</div>
		</div>
		<%
			}
		%>
		<%@include file="Footer.jsp" %>
	</body>
</html>