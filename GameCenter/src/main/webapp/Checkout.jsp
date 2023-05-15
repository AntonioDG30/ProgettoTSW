<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>



<%!	
	String Email="";
	String Result="";
	int PuntiFedelta=0;
	Float ScontoMax = 0.0f; 
    float PrezzoTotale;
    CarrelloBean Carrello = null; 
%>

<%
	PrezzoTotale = 0;
	PuntiFedelta = (int) request.getAttribute("PuntiFedelta");
	Result = (String) request.getAttribute("Result");
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Carrello=(CarrelloBean)session.getAttribute("Carrello");
	}
	
%>
<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>


		<form method="post" action="./OrdiniControl?action=Acquista">
			<h1>Pagina di Checkout</h1>
			<h3>Indirizzo di Spedizione:</h3>
			
			da creare
			
			
			<h3>Metodo Pagamento:</h3>
			
			da creare
			
			
			
			
			<h3>Dettagli Prodotti che stai acquistando:</h3>
	
			<%
				if(Carrello != null) 
				{ 
			%>
					<table border="1">
					<tr>
						<th>Immagine</th>
						<th>Nome</th>
						<th>Prezzo</th>
						<th>Descrizione Ridotta</th>
						<th>Piattaforma</th>
					</tr>
					<% 
						List<ProductBean> ProdottoCarrello = Carrello.getListaCarrello(); 	
					   	for(ProductBean Prod: ProdottoCarrello) 
					   	{
					%>
							<tr>
								<td>
									<%
										if (Prod.getImmagine() != null && Prod.getImmagine() != "") 
										{						
									%>
										<img src="Immagini/<%=Prod.getImmagine()%>" alt="Immagine non disponibile" width="150" height="170">
									<%
										}						
									%>
								</td>
								<td><%=Prod.getNome()%></td>
								<td><%=Prod.getPrezzo()%></td>
								<td><%=Prod.getDescrizioneRidotta()%></td>
								<td><%=Prod.getPiattaforma()%></td>
								<%PrezzoTotale = PrezzoTotale + (Prod.getPrezzo() * Prod.getQuantita());%>
							</tr>
					<%
						} 
					   	
					%>
						
					   
					</table>		
			<% 				
				}
				else
				{
					//response.sendRedirect("./index.jsp");
				}
			

				/*Tronchiamo float a solo due cifre decimali*/
				Locale.setDefault(Locale.US);
				String PrezzoTotaleString = String.format("%.2f", PrezzoTotale);
				ScontoMax = Math.min((PrezzoTotale* 100), PuntiFedelta);
				Locale.setDefault(Locale.ITALY);
				
			%>
			
			<h3>Punti Fedeltà:</h3>
			
			Possiedi <%= PuntiFedelta %> Punti Fedeltà, ogni punto equivale ad 1 centesimo di sconto.<br>
			Quanti punti vuoi utilizzare per il tuo acquisto (0 se non vuoi utilizzarli )?<br>
			
			<input type="number" name="Sconto"  min=0 max=<%= ScontoMax  %> required>
			
			
			<h3>Resoconto Ordine: </h3>
			Prezzo Totale: <%=PrezzoTotaleString%> <br>
			<input type="hidden" name="PrezzoTotale" value="<%=PrezzoTotaleString%>"> 
			<input type="submit" value="Concludi Ordine">
		</form>
	</body>
</html>