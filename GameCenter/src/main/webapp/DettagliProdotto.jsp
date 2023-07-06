<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	ProductBean product = (ProductBean) request.getAttribute("product");
	Collection<?> recensioni = (Collection<?>) request.getAttribute("recensioni");
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
				    <form class="form cf" method="post" action="GeneralProductControl?action=AggiungiCarrello&CodSeriale=<%=product.getCodSeriale()%>">
				    	<section class="plan cf">
				        	<h2>Seleziona la piattaforma e il formato che desideri acquistare:</h2>
				        	<%
								if (product.getDispPs5Fisico() >0) 
								{
							%>
								<input type="radio" name="piattaforma" id="PS5 Fisico" value="PS5 Fisico"><label class="PS5-label four col" for="PS5 Fisico">PS5 Fisico</label>
							<%
								}
							%>
							<%
								if (product.getDispPs5Digitale() >0) 
								{
							%>
								<input type="radio" name="piattaforma" id="PS5 Digitale" value="PS5 Digitale"><label class="PS5-label four col" for="PS5 Digitale">PS5 Digitale</label>
							<%
								}
							%>
					        <%
								if (product.getDispPs4Fisico()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="Ps4 Fisico" value="Ps4 Fisico"><label class="PS4-label four col" for="Ps4 Fisico">Ps4 Fisico</label>
							<%
								}
							%>
							 <%
								if (product.getDispPs4Digitale()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="Ps4 Digitale" value="Ps4 Digitale"><label class="PS4-label four col" for="Ps4 Digitale">Ps4 Digitale</label>
							<%
								}
							%>
							<%
								if (product.getDispXboxXFisico() >0) 
								{
							%>
								<input type="radio" name="piattaforma" id="XboxX Fisico" value="XboxX Fisico"><label class="XboxX-label four col" for="XboxX Fisico">Xbox X Fisico</label>
							<%
								}
							%>
							<%
								if (product.getDispXboxXDigitale()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="XboxX Digitale" value="XboxX Digitale"><label class="XboxX-label four col" for="XboxX Digitale">Xbox X Digitale</label>
							<%
								}
							%>
							<%
								if (product.getDispXboxSDigitale()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="XboxS Digitale" value="XboxS Digitale"><label class="XboxS-label four col" for="XboxS Digitale">Xbox S</label>
							<%
								}
							%>
							<%
								if (product.getDispPcFisico()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="Pc Fisico" value="Pc Fisico"><label class="four col" for="Pc Fisico">Pc Fisico</label>
							<%
								}
							%>
							<%
								if (product.getDispPcDigitale()>0) 
								{
							%>
								<input type="radio" name="piattaforma" id="Pc Digitale" value="Pc Digitale"><label class="four col" for="Pc Digitale">Pc Digitale</label>
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
			
			<div class = "recensione">
				<h1>Recensioni</h1>
				
				<%
					if (recensioni != null && recensioni.size() != 0) 
					{
						Iterator<?> it = recensioni.iterator();
						while (it.hasNext()) 
						{
				%>
							<div class = "recensione2">
								<%
									RecensioneBean bean = (RecensioneBean) it.next();
									for(int i=1; i<6; i++)
									{
										if(bean.getValutazione()<i)
										{
								%>
											<img src="LayoutSito/img/StellaGrigia.png">
								<%
										}
										else
										{
								%>
											<img src="LayoutSito/img/StellaRossa.png">
								<%		
										}
									}
								%>
									<p><%=bean.getDescrizione()%></p>
							</div>
				<%
						}
					}
					else
					{
				%>
						<h2>Non ci sono recensioni per questo prodotto.</h2>
				<%
					}
				%>
			</div>
		</div>
		<%
			}
		%>
		<%@include file="Footer.jsp" %>
	</body>
</html>