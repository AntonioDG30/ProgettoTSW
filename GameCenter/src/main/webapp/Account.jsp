<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>



<%!	
	String Email="";
	String Result="";
	String FormMod="";
	int PuntiFedelta=0;
%>
<%	
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	PuntiFedelta = (int) request.getAttribute("PuntiFedelta");
	UserBean Utente = (UserBean) request.getAttribute("Cliente");
	
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
					<a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>&PrezzoEffettivo=<%=PrezzoTotaleString%>">Dettagli</a>
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

		
		<form method="post" action="./UserControl?action=VisualizzaDati">
			<pre>		
				<input type="submit" name="Visual1" value="VisualizzaDati">
			</pre>
		</form>
    
    <%
			if (Utente != null)
	{
	%>
	
	<h2>Cliente</h2>
	<table border="1">
		<tr>
			<th>Cliente</th>
			<th>Punti Fedeltà</th>
			<th>Codice Fiscale</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Indirizzo</th>
			<th>Telefono</th>
		</tr>
		
		<tr>
			<td><%=Utente.getEmail()%></td>
			<td><%=Utente.getPuntiFedelta()%></td>
			<td><%=Utente.getCodiceFiscale()%></td>
			<td><%=Utente.getNome()%></td>
			<td><%=Utente.getCognome()%></td>
			<td><%=Utente.getVia()%>,<%=Utente.getCivico()%>,<%=Utente.getCitta()%>, <%=Utente.getCAP()%> </td>
			<td><%=Utente.getNumeroTelefono() %></td>
		</tr>
	</table>
 	
 	
 	<form action="./UserControl?action=ModificaDati" method="post">
 		<h2>Dati sensibili</h2>
		<h5>(inserisci solo i dati che desideri modificare)</h5>
 		<pre> 	
		    CodiceFiscale: <input type="text" id="CodiceFiscale" name="CodiceFiscale">
		    Nome: <input type="text" id="Nome" name="Nome">
		    Cognome: <input type="text" id="Cognome" name="Cognome">
		    CAP: <input type="number" id="CAP" name="CAP">
		    Via: <input type="text" id="Via" name="Via">
		    Civico: <input type="number" id="Civico" name="Civico">
		    Citta:  <input type="text" id="Citta" name="Citta">
		    Provincia: <input type="text" id="Provincia" name="Provincia">
		    Telefono: <input type="text" id="NumeroTelefono" name="NumeroTelefono">
		    Email: <input type="text" id="Email" name="Email">
		   	<input type="submit" name="Visual2" value="Aggiorna dati">  
		</pre>
    </form>
 	<% 
 		} 
 	%>	
	</body>
</html>