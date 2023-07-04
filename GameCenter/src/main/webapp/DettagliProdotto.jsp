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
				    <form class="form cf">
				    	<section class="plan cf">
				        	<h2>Seleziona la piattaforma che desideri acquistare:</h2>
				        	<%
								if ((product.getDispPs5Fisico() + product.getDispPs5Digitale())>0) 
								{
							%>
								<input type="radio" name="radio1" id="free" value="free"><label class="free-label four col" for="free">PlayStation 5</label>
							<%
								}
							%>
					        <%
								if ((product.getDispPs4Fisico() + product.getDispPs4Digitale())>0) 
								{
							%>
								<input type="radio" name="radio1" id="basic" value="free"><label class="basic-label four col" for="basic">PlayStation 4</label>
							<%
								}
							%>
							<%
								if ((product.getDispXboxXFisico() + product.getDispXboxXDigitale())>0) 
								{
							%>
								<input type="radio" name="radio1" id="premium" value="free"><label class="premium-label four col" for="premium">Xbox Series X</label>
							<%
								}
							%>
							<%
								if ((product.getDispXboxSFisico() + product.getDispXboxSDigitale())>0) 
								{
							%>
								<input type="radio" name="radio1" id="pippo" value="free"><label class="pippo-label four col" for="pippo">Xbox Series S</label>
							<%
								}
							%>
							<%
								if ((product.getDispPcFisico() + product.getDispPcDigitale())>0) 
								{
							%>
								<input type="radio" name="radio1" id="cavolo" value="free"><label class="cavolo-label four col" for="cavolo">PC</label>
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
		<%@include file="Footer.jsp" %>
	</body>
</html>