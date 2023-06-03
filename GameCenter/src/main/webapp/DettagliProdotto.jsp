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
		<title>Dettagli Prodotto</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<%
			if (product != null) 
			{
		%>

				<table border="1">
					<caption>Dettagli</caption>
					<tr>
						<th>Nome</th>
						<th>Prezzo</th>
						<th>DataUscita</th>
						<th>DescrizioneCompleta</th>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<th>PEGI</th>
							<th>Genere</th>
						<%
							}
						%>
					</tr>
					<tr>
						<td><%=product.getNome()%></td>
						<td><%=product.getPrezzo()%></td>
						<td><%=product.getDataUscita()%></td>
						<td><%=product.getDescrizioneCompleta()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getPegi()%></td>
							<td><%=product.getGenere()%></td>
						<%
							}
						%>
					</tr>
				</table>
				<br>
				<table border="1">
					<caption>Disponibilità per: <%=product.getNome()%></caption>
					<tr>
						<th>Piattaforma</th>
						<th>Formato Fisico</th>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<th>Formato Digitale</th>
						<%
							}
						%>
					</tr>
					<tr>
						<td>PlayStation 5</td>
						<td><%=product.getDispPs5Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDispPs5Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>PlayStation 4</td>
						<td><%=product.getDispPs4Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDispPs4Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>Xbox Series X</td>
						<td><%=product.getDispXboxXFisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDispXboxXDigitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>Xbox Series S</td>
						<td><%=product.getDispXboxSFisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDispXboxSDigitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>PC</td>
						<td><%=product.getDispPcFisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDispPcDigitale()%></td>
						<%
							}
						%>
					</tr>
				</table>
				<h2>Vuoi acquistare questo prodotto?</h2>
				<form method="post" action="GeneralProductControl?action=AggiungiCarrello&CodSeriale=<%=product.getCodSeriale()%>">
					Seleziona la piattaforma desiderata:<br>
					<select name="Piattaforma" required>
						<%
							if (product.getDispPs5Fisico() > 0) 
							{
						%>
				   		<option value="Ps5 Fisico">PS5 Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDispPs4Fisico() > 0) 
							{
						%>
				   		<option value="Ps4 Fisico">PS4 Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDispXboxXFisico() > 0) 
							{
						%>
				   		<option value="XboxX Fisico">Xbox Series X Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDispPcFisico() > 0) 
							{
						%>
				   		<option value="Pc Fisico">Pc Fisico</option>
				   		<%
							}
						%>
						
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<%
								if (product.getDispPs5Digitale() > 0) 
								{
							%>
					   		<option value="Ps5 Digitale">Ps5 Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDispPs4Digitale() > 0) 
								{
							%>
					   		<option value="Ps4 Digitale">Ps4 Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDispXboxXDigitale() > 0) 
								{
							%>
					   		<option value="XboxX Digitale">Xbox Series X Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDispXboxSDigitale() > 0) 
								{
							%>
					   		<option value="XboxS Digitale">Xbox Series S Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDispPcDigitale() > 0) 
								{
							%>
					   		<option value="Pc Digitale">PcDigitale</option>
					   		<%
								}
							%>
						<%
							}
						%>
						</select>
						<br><input type="submit" value="Aggiungi al carrello">

				   
				</form>
				
		<%
			}
		%>
	</body>
</html>