<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>



<%!	
	String Email="";
	String Result="";
	String FormMod="";
	int PuntiFedelta=0;
%>
<%	
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	if(Ordini == null) 
	{
		response.sendRedirect("./OrdiniControl");	
		return;
	}
	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	PuntiFedelta = (int) request.getAttribute("PuntiFedelta");
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
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
				<% Locale.setDefault(Locale.US);
				String PrezzoTotaleString = String.format("%.2f", bean.getPrezzoTotale());
				Locale.setDefault(Locale.ITALY);%>
				<td><%=PrezzoTotaleString%></td>
				<td><%=bean.getStatoOrdine()%></td>
				<td>
					<a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>">Dettagli</a>
				</td>
				
			</tr>
			<%
					}
				}
				else
				{
			%>
					<tr>
						<td colspan="4">Nessun ordine effettuato</td>
					</tr>
			<%
				}
			%>
		</table>	
	</body>
</html>