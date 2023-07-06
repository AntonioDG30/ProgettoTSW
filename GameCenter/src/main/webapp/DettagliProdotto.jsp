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
		<link href="LayoutSito/css/dettagli.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<title>Dettagli Prodotto</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<%
			if (product != null) 
			{
		%>
		<div class="contenitore">
			<div class="prodotti">
				<img src="Immagini/<%=product.getImmagine()%>">
				<div class="dettagli">
					<h1><%=product.getNome()%></h1>
					<h2>Data Uscita: <h2 class="elementi"> <%=product.getDataUscita()%></h2></h2>
					<%
						if (!(product.getTipologia())) 
						{
					%>
						<h2>Pegi:<h2 class="elementi"><%=product.getPegi()%></h2></h2> 
						<h2>Genere: <h2 class="elementi"> <%=product.getGenere()%></h2></h2>
					<%
						}
					%>
					<h2>Disponibile per:</h2>
					<div class="elementi">
					<ul>
						<%
							if ((product.getDispPs5Fisico() + product.getDispPs5Digitale())>0) 
							{
						%>
							<li><h2>PlayStation 5</h2></li>
						<%
							}
						%>
						<%
							if ((product.getDispPs4Fisico() + product.getDispPs4Digitale())>0) 
							{
						%>
							<li><h2>PlayStation 4</h2></li>
						<%
							}
						%>
						<%
							if ((product.getDispXboxXFisico() + product.getDispXboxXDigitale())>0) 
							{
						%>
							<li><h2>Xbox Series X</h2></li>
						<%
							}
						%>
						<%
							if ((product.getDispXboxSFisico() + product.getDispXboxSDigitale())>0) 
							{
						%>
							<li><h2>Xbox Series S</h2></li>
						<%
							}
						%>
						<%
							if ((product.getDispPcFisico() + product.getDispPcDigitale())>0) 
							{
						%>
							<li><h2>PC</h2></li>
						<%
							}
						%>
					</ul>
					</div>
					<h2>Prezzo: <h2 class="prezzo"><%=product.getPrezzo()%></h2></h2>
				</div>
				<div class="carrello">					
				    <h1>Aggiungi al carrello</h1>
				    <form class="form cf" method="post" name="piattaformaModulo">
				    	<section class="plan cf">
				        	<h2>Seleziona la piattaforma che desideri acquistare:</h2>
				        	<%
								if ((product.getDispPs5Fisico() + product.getDispPs5Digitale())>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="PS5" value="PS5"><label class="PS5-label four col" for="PS5">PlayStation 5</label>
							<%
								}
							%>
					        <%
								if ((product.getDispPs4Fisico() + product.getDispPs4Digitale())>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="PS4" value="PS4"><label class="PS4-label four col" for="PS4">PlayStation 4</label>
							<%
								}
							%>
							<%
								if ((product.getDispXboxXFisico() + product.getDispXboxXDigitale())>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="XboxX" value="XboxX"><label class="XboxX-label four col" for="XboxX">Xbox Series X</label>
							<%
								}
							%>
							<%
								if ((product.getDispXboxSFisico() + product.getDispXboxSDigitale())>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="XboxS" value="XboxS"><label class="XboxS-label four col" for="XboxS">Xbox Series S</label>
							<%
								}
							%>
							<%
								if ((product.getDispPcFisico() + product.getDispPcDigitale())>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="PC" value="PC"><label class="PC-label four col" for="PC">PC</label>
							<%
								}
							%>
				      	</section>  
				      	<input class="submit" type="submit" value="Submit">   
				    </form>
				</div>
			</div>
			<div class = "descrizione">
				<h1>Descrizione</h1>
				<p><%=product.getDescrizioneCompleta()%></p>
			</div>
		</div>
		<%
			}
		%>
		
		<script>
			var piattaforma = document.piattaformaModulo.piattaforma.value;
			
			
			if (piattaforma.equals("PS5")) 
			{
				alert("PS5");
			}
			else
				{
				alert("nada");
				}
			//document.modulo.action = "./UserControl?action=Login";
			//document.modulo.submit();
		</script>
		<%@include file="Footer.jsp" %>
	</body>
</html>