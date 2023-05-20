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
<html>
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
				<h2>Dettagli</h2>
				<table border="1">
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
							<td><%=product.getPEGI()%></td>
							<td><%=product.getGenere()%></td>
						<%
							}
						%>
					</tr>
				</table>
				<br>
				<h2>Disponibilità per: <%=product.getNome()%></h2>
				<table border="1">
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
						<td><%=product.getDisp_Ps5_Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDisp_Ps5_Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>PlayStation 4</td>
						<td><%=product.getDisp_Ps4_Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDisp_Ps4_Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>Xbox Series X</td>
						<td><%=product.getDisp_XboxX_Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDisp_XboxX_Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>Xbox Series S</td>
						<td><%=product.getDisp_XboxS_Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDisp_XboxS_Digitale()%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>PC</td>
						<td><%=product.getDisp_Pc_Fisico()%></td>
						<%
							if (!(product.getTipologia())) 
							{
						%>
							<td><%=product.getDisp_Pc_Digitale()%></td>
						<%
							}
						%>
					</tr>
				</table>
				<h2>Vuoi acquistare questo prodotto?</h2>
				<form method="post" action="General_ProductControl?action=AggiungiCarrello&CodSeriale=<%=product.getCodSeriale()%>">
					Seleziona la piattaforma desiderata:<br>
					<select name="Piattaforma" required>
						<%
							if (product.getDisp_Ps5_Fisico() > 0) 
							{
						%>
				   		<option value="Ps5 Fisico">PS5 Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDisp_Ps4_Fisico() > 0) 
							{
						%>
				   		<option value="Ps4 Fisico">PS4 Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDisp_XboxX_Fisico() > 0) 
							{
						%>
				   		<option value="XboxX Fisico">Xbox Series X Fisico</option>
				   		<%
							}
						%>
						<%
							if (product.getDisp_Pc_Fisico() > 0) 
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
								if (product.getDisp_Ps5_Digitale() > 0) 
								{
							%>
					   		<option value="Ps5 Digitale">Ps5 Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDisp_Ps4_Digitale() > 0) 
								{
							%>
					   		<option value="Ps4 Digitale">Ps4 Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDisp_XboxX_Digitale() > 0) 
								{
							%>
					   		<option value="XboxX Digitale">Xbox Series X Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDisp_XboxS_Digitale() > 0) 
								{
							%>
					   		<option value="XboxS Digitale">Xbox Series S Digitale</option>
					   		<%
								}
							%>
							<%
								if (product.getDisp_Pc_Digitale() > 0) 
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