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
		<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
		<title>W3.CSS Template</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	<body class="w3-light-grey">
		<%@include file="NavBar.jsp" %>	
		
		<!-- Page Container -->
		<div class="w3-content w3-margin-top" style="max-width:1400px;">
		
		  <!-- The Grid -->
		  <div class="w3-row-padding">
		  
		    <!-- Left Column -->
		    <div class="w3-third">
		    
		      <div class="w3-white w3-text-grey w3-card-4">
		        <div class="w3-display-container">
		          <img src="LayoutSito/img/Avatar.png" style="width:100%" alt="Avatar">
		          <div class="w3-display-bottomleft w3-container w3-text-black">
		          </div>
		        </div>
		        <div class="w3-container">
		        <br>
		          <p><i class="fa fa-briefcase fa-fw w3-margin-right w3-large w3-text-teal"></i><%=Utente.getNome()%> <%=Utente.getCognome()%></p>
		          <p><i class="fa fa-home fa-fw w3-margin-right w3-large w3-text-teal"></i><%=Utente.getVia()%> <%=Utente.getCivico()%>, <%=Utente.getCitta()%>, <%=Utente.getCAP()%></p>
		          <p><i class="fa fa-envelope fa-fw w3-margin-right w3-large w3-text-teal"></i><%=Utente.getEmail()%></p>
		          <p><i class="fa fa-phone fa-fw w3-margin-right w3-large w3-text-teal"></i><%=Utente.getNumeroTelefono() %></p>
		          <hr>
		        </div>
		      </div><br>
		
		    <!-- End Left Column -->
		    </div>
		
		    <!-- Right Column -->
		    <div class="w3-twothird">
		    
		      <div class="w3-container w3-card w3-white w3-margin-bottom">
		        <h2 class="w3-text-grey w3-padding-16"><i class="fa fa-suitcase fa-fw w3-margin-right w3-xxlarge w3-text-teal"></i>Ordini Effettuati</h2>
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
		          <a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>&PrezzoEffettivo=<%=PrezzoTotaleString%>">Dettagli</a>
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
	
	</body>
</html>
