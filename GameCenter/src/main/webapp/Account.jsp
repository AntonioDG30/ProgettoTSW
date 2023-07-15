<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>



<%!	
	String Email="";
	String Result="";
	String FormMod="";
	Boolean Tipo;
%>
<%	
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	UserBean Utente = (UserBean) request.getAttribute("Cliente");
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Tipo=(Boolean)session.getAttribute("Tipo");
	}
	
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
		<title>Pagina Utente</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="LayoutSito/css/Account.css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />

	</head>
	<body class="w3-light-grey">
		<%@include file="NavBar.jsp" %>	
		<% 
			if(Email == null) 
			{
				response.sendRedirect("./Login.jsp");
			}
			else if(!(Tipo))
			{
				response.sendRedirect("./Admin.jsp");
			}
			
		%>
		<!-- Page Container -->
		<div class="w3-content w3-margin-top" style="max-width:1400px;">
		
		  <!-- The Grid -->
		  <div class="w3-row-padding">
		  
		    <!-- Left Column -->
		    <div class="w3-third">
		    
		      <div class="w3-white w3-text-grey w3-card-4">
		        <div class="w3-display-container">
		          <img src="ImgUser/<%=Utente.getImmagine()%>" style="width:100%" alt="Foto assente">
		          <div class="w3-display-bottomleft w3-container w3-text-black">
		          </div>
		        </div>
		        <div class="w3-container">
		        <br>
		          <p><img src="LayoutSito/img/Utente.png" alt="errore immagine">&nbsp;&nbsp;<%=Utente.getNome()%> <%=Utente.getCognome()%> </p>
		          <p><img src="LayoutSito/img/Casa.png" alt="errore immagine">&nbsp;&nbsp;<%=Utente.getVia()%> <%=Utente.getCivico()%>, <%=Utente.getCitta()%> (<%=Utente.getProvincia()%>) <%=Utente.getCAP()%></p>
		          <p><img src="LayoutSito/img/email.png" alt="errore immagine">&nbsp;&nbsp;<%=Utente.getEmail()%></p>
		          <p><img src="LayoutSito/img/telefono.png" alt="errore immagine">&nbsp;&nbsp;<%=Utente.getNumeroTelefono() %></p>
		          <p><img src="LayoutSito/img/punti.png" alt="errore immagine">&nbsp;&nbsp; <span id="PuntiFedelta"></span></p>
		          <hr>
		          <a href="UserControl?action=RicercaIndirizzi">
		          	<button type="button" class="button">Indirizzi di spedizione registrati</button>
		          </a>
		          <hr>
		          <a href="UserControl?action=RicercaMetodi">
		         	<button type="button" class="button">Metodi di pagamento registrati</button>
		          </a>
		          <hr>
		        </div>
		      </div><br>
		
		    <!-- End Left Column -->
		    </div>
		
		    <!-- Right Column -->
		    <div class="w3-twothird">
		    
		      <div class="w3-container w3-card w3-white w3-margin-bottom">
		        <h2 class="w3-text-grey w3-padding-16"><img src="LayoutSito/img/ordini.png" alt="errore immagine">&nbsp;&nbsp;Ordini Effettuati</h2>
		        <%
					if (Ordini != null && Ordini.size() != 0) 
					{
						Iterator<?> it = Ordini.iterator();
						while (it.hasNext()) 
						{
							OrdineBean bean = (OrdineBean) it.next();
							Locale.setDefault(Locale.US);
							String PrezzoTotaleString = String.format("%.2f", bean.getPrezzoTotale());
							Locale.setDefault(Locale.ITALY);
				%>
		        <div class="w3-container">
		          <h5 class="w3-opacity"><b>Ordine del: <%=bean.getDataAcquisto()%></b></h5>
		          <h6 class="w3-opacity">Prezzo Totale: <%=PrezzoTotaleString%></h6>
		          <h6 class="w3-opacity">Stato Ordine: <%=bean.getStatoOrdine()%></h6>
		          <a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>&PrezzoEffettivo=<%=PrezzoTotaleString%>">
		          	<button type="button" class="button">Visualizza dettagli</button>
		          </a>
		          <hr>
		        </div>
		      
		      <%
						}
					}
				%>
		</div>
		
		
		    <!-- End Right Column -->
		    </div>
		    
		  <!-- End Grid -->
		  </div>
		  
		  <!-- End Page Container -->
		</div>
		
		<%@include file="Footer.jsp" %>	
		<script>
			$(document).ready(function() {
				$.ajax({
					url: "UserControl?action=PuntiFedelta",
					type: "POST",
					dataType: 'json',
					success: function(response) {
						var PuntiFedelta = response.PF;
						document.getElementById("PuntiFedelta").innerText = PuntiFedelta;
					},
					error: function(xhr, status, error) {
						console.error(error);
					}
				});
			});
		</script>
	</body>
</html>
