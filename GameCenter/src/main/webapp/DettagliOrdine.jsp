<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>



<%!	
	String Email="";
	int CodOrdine=0;
    float PrezzoTotale;
    String Fattura="";
%>

<%
	CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
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
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*,java.text.DecimalFormat"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		
		<h2>Dettagli ordine: <%=CodOrdine%></h2>

		<table border="1">
			<tr>
				<th>Foto</th>
				<th>Quantità</th>
				<th>Nome</th>
				<th>Prezzo</th>
				<th>DescrizioneCompleta</th>
				<th>PEGI</th>
				<th>Genere</th>
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
				<td><%=bean.getPEGI()%></td>
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
			</tr>
				
			<%
					}
					//Tronchiamo float a solo due cifre decimali
					DecimalFormat df = new DecimalFormat("#.##"); 
					String PrezzoTotaleString = df.format(PrezzoTotale);
			%>
					<tr>
					<td colspan="4">Prezzo Totale: <%=PrezzoTotaleString%></td>
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