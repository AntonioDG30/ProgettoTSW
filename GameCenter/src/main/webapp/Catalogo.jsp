<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) 
	{
		response.sendRedirect("./GeneralProductControl?action=Catalogo");	
		return;
	}
%>

<%!	
	String Email="";
	Boolean Tipo;
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
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="LayoutSito/css/prodotti.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>	
		<%@include file="catalogoBar.jsp" %>
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