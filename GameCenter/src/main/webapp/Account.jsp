<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%!	
	String Email="";
	String Result="";
	String FormMod="";
	int PuntiFedelta=0;
%>
<%	

	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    PuntiFedelta = (int)session.getAttribute("PuntiFedelta");
	}

%>
<%	
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	if(Ordini == null) 
	{
		response.sendRedirect("./OrdiniControl");	
		return;
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
		<title>Account page</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Email != null) 
			{
		%>
		<h2><%=Email%>, benvenuto nella tua pagina personale</h2>
		<% 
			}
			else
			{
				response.sendRedirect("./Login.jsp");
			}
		%>
		<h2>Salto punti fedeltà: <%=PuntiFedelta%></h2>
		<% 
			if(Result != null) 
			{
		%>
		<%=Result%>
		<% 
			}
		%>
		
		<h2>Ordini Effettuati</h2>
		<table border="1">
			<tr>
				<th>DataAcquisto</th>
				<th>PrezzoTotale</th>
				<th>StatoOrdine</th>
				<th>Azioni</th>
			</tr>
			<%
				if (Ordini != null && Ordini.size() != 0) 
				{
					Iterator<?> it = Ordini.iterator();
					while (it.hasNext()) 
					{
						OrdineBean bean = (OrdineBean) it.next();
			%>
			
			<tr>
				<td><%=bean.getDataAcquisto()%></td>
				<td><%=bean.getPrezzoTotale()%></td>
				<td><%=bean.getStatoOrdine()%></td>
				<td>
					<a href="OrdiniControl?action=Dettagli">Dettagli</a>
				</td>
				
			</tr>
			<%
					}
				}
				else
				{
			%>
					<tr>
						<td>Nessun ordine effettuato</td>
					</tr>
			<%
				}
			%>
		</table>	
	</body>
</html>