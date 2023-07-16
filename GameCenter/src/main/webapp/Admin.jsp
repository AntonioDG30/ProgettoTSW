<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	Boolean Tipo;
%>
<%	
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) 
	{
		response.sendRedirect("./AdminProductControl?action=Catalogo");	
		return;
	}
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Tipo=(Boolean)session.getAttribute("Tipo");
	}
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin Page</title>
		<link href="LayoutSito/css/prodotti.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<%@include file="AdminBar.jsp" %>
		<% 
			if(Tipo != null)
			{
				if(Tipo)
				{
					response.sendRedirect("./index.jsp");
				}
			}
		%>
		<div class="prodotti">
		<%
			if (products != null && products.size() != 0) 
			{
				Iterator<?> it = products.iterator();
				while (it.hasNext()) 
				{
					ProductBean bean = (ProductBean) it.next();
		%>
		
			<div class="prodotti2">
				<%
					if (bean.getImmagine() != null && bean.getImmagine() != "") 
					{						
				%>
					<a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>"><img src="Immagini/<%=bean.getImmagine()%>" alt="errore immagine"></a>
				<%
					}						
				%>
					<br>
					<a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>"><h3><%=bean.getNome()%></h3></a>
					<p> <%=bean.getDescrizioneRidotta()%> </p>
					<h3>€<%=bean.getPrezzo()%></h3>
					<div class="operazioniAdmin">
						<form method="post" id="elimina" action="./AdminProductControl?action=Elimina">
								<input type="hidden" name="CodSeriale" value="<%=bean.getCodSeriale()%>">				
								<input type="image" src="LayoutSito/img/elimina.png" alt="errore immagine">
						</form>
						<form method="post" id="modifica" action="./AdminProductControl?action=ModificaPage">
								<input type="hidden" name="CodSeriale" value="<%=bean.getCodSeriale()%>">				
								<input type="image" src="LayoutSito/img/modifica.png" alt="errore immagine">
						</form>
						
					</div>
			</div>
		
		<br>
		<%
				}
			}
		%>
		</div>
		<%@include file="Footer.jsp" %>
	</body>
</html>