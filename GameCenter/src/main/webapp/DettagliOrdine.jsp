<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>



<%!	
	String Email="";
	int CodOrdine=0;
    float PrezzoTotale;
    float PrezzoEffettivo; 
    String Fattura="";
    String PrezzoTotaleString="";
%>

<%
	CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
	PrezzoEffettivo = Float.parseFloat(request.getParameter("PrezzoEffettivo"));
	Fattura = (String) request.getAttribute("Fattura");
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	PrezzoTotale = 0;
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
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>

		<table border="1">
			<caption>Dettagli ordine: <%=CodOrdine%></caption>
			<tr>
				<th>Foto</th>
				<th>Quantità</th>
				<th>Nome</th>
				<th>Prezzo</th>
				<th>DescrizioneCompleta</th>
				<th>PEGI</th>
				<th>Genere</th>
				<th>Azioni</th>
			</tr>
			<%
				if (Ordini != null && Ordini.size() != 0) 
				{
					Iterator<?> it = Ordini.iterator();
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
				<td><%=bean.getQuantita()%></td>
				<td><%=bean.getNome()%></td>
				<td><%=bean.getPrezzo()%></td>
				<%PrezzoTotale = PrezzoTotale + (bean.getPrezzo() * bean.getQuantita());%>
				<td><%=bean.getDescrizioneCompleta()%></td>
					<%
						if (!(bean.getTipologia())) 
						{
					%>
				<td><%=bean.getPegi()%></td>
				<td><%=bean.getGenere()%></td>
					<%
						}
						else
						{
					%>
				<td>Non Disponibile</td>
				<td>Non Disponibile</td>
					<%
						}
					%>
					<td>
						<form method=post action="./Recensione.jsp">
							<input type="hidden" name="Prodotto" value="<%=bean.getCodSeriale()%>">
							<input type="submit" value="Recensisci prodotto">
						</form>
					</td>
			</tr>
				
			<%
					}
					Locale.setDefault(Locale.US);
					PrezzoTotaleString = String.format("%.2f", PrezzoTotale - PrezzoEffettivo);
					Locale.setDefault(Locale.ITALY);
			%>
					<tr>
					<td colspan="4">
						Prezzo Totale: <%=PrezzoEffettivo%><br>
						Sconto: -<%=PrezzoTotaleString%>
					</td>
					<td colspan="3">
						<a href="Fatture/<%=Fattura%>" download="Fattura.pdf">
							Download Fattura
						</a>
					</td>
				</tr>
			<%
				}
				else
				{
			%>
					<tr>
						<td>Errore</td>
					</tr>
			<%
				}
				
			%>
			
		</table>
	</body>
</html>