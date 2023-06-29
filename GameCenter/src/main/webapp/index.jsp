<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) 
	{
		response.sendRedirect("./GeneralProductControl");	
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
	    Tipo=(Boolean)session.getAttribute("Tipo");
	}
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Tipo != null)
			{
				if(!(Tipo))
				{
					response.sendRedirect("./Admin.jsp");
				}
			}
			if(Email != null) 
			{
		%>
		<h2><%=Email%>, benvenuto su Gamecenter</h2>
		<% 
			}
			else
			{
		%>
		<h2>Benvenuto su Gamecenter</h2>
		<%		
			}
		%>
		

		<table border="1">
			<tr>
				<th>Foto</th>
				<th>Nome</th>
				<th>Prezzo</th>
				<th>DescrizioneRidotta</th>
				<th>Azioni</th>
			</tr>
			<%
				if (products != null && products.size() != 0) 
				{
					Iterator<?> it = products.iterator();
					while (it.hasNext()) 
					{
						ProductBean bean = (ProductBean) it.next();
			%>
			
			<tr>
				<td>
					<%
						if (bean.getImmagine() != null && bean.getImmagine() != "") 
						{						
					%>
						<img src="Immagini/<%=bean.getImmagine()%>" alt="Immagine non disponibile" width="150" height="170">
					<%
						}						
					%>
				</td>
				<td><%=bean.getNome()%></td>
				<td><%=bean.getPrezzo()%></td>
				<td><%=bean.getDescrizioneRidotta()%></td>
				<td>
					<a href="GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>">Dettagli</a><br>
				</td>
				
			</tr>
			<%
					}
				}
				else
				{
			%>
					<tr>
						<td>Nessun prodotto disponibile in catalogo</td>
					</tr>
			<%
				}
			%>
		</table>
		
		
		
		
			
		<%@include file="Footer.jsp" %>
	</body>
</html>