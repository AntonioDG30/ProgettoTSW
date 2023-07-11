<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%	
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) 
	{
		response.sendRedirect("./GeneralProductControl");	
		return;
	}
%>

<%!	
	String Email="";
	Boolean Tipo;
%>

<%
	synchronized(session) 
	{ 
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Tipo=(Boolean)session.getAttribute("Tipo");
	}
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
		<link href="LayoutSito/css/prodotti.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<title>GameCenter</title>
	</head>
	<body>
		<% 
			if(Tipo != null)
			{
				if(!(Tipo))
				{
					response.sendRedirect("./Admin.jsp");
				}
			}
		%>
		<%@include file="NavBar.jsp" %>	
		<%@include file="catalogoBar.jsp" %>		
		<div class="slideShow">
		  <a href="Login.jsp"><img class="mySlides" src="LayoutSito/img/PS5_Slide.jpg" alt="errore immagine"></a>
		  <a href="Registrati.jsp"><img class="mySlides" src="LayoutSito/img/NVIDIA4090_Slide.jpg" alt="errore immagine"></a>
		  <a href="prova.jsp"><img class="mySlides" src="LayoutSito/img/TLOU2_Slide.jpg" alt="errore immagine"></a>
		</div>
		<h2>I prodotti più ricercati:</h2><br>
		
		
		<div class="prodotti">
		<%
			if (products != null && products.size() != 0) 
			{
				Iterator<?> it = products.iterator();
				while (it.hasNext()) 
				{
					ProductBean bean = (ProductBean) it.next();
		%>
		
			<div class="prodotti2">
				<%
					if (bean.getImmagine() != null && bean.getImmagine() != "") 
					{						
				%>
					<a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>"><img src="Immagini/<%=bean.getImmagine()%>" alt="errore immagine"></a>
				<%
					}						
				%>
					<a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>"><h3><%=bean.getNome()%></h3></a>
					<p> <%=bean.getDescrizioneRidotta()%> </p>
					<h3>€<%=bean.getPrezzo()%></h3>
			</div>
		
		<br>
		<%
				}
			}
		%>
		</div>

		
		<br>
		<h2>Cerca tramite la tua piattaforma preferita:</h2><br>
		<div class="categorie">
			<a href="Catalogo.jsp"><img src="LayoutSito/img/PS5.jpg" alt="errore immagine"></a>
			<a href="#"><img src="LayoutSito/img/PS4.jpg" alt="errore immagine"></a>
			<a href="#"><img src="LayoutSito/img/xboxsx.jpg" alt="errore immagine"></a>
			<a href="#"><img src="LayoutSito/img/xboxss.jpg" alt="errore immagine"></a>
			<a href="#"><img src="LayoutSito/img/pc.jpg" alt="errore immagine"></a>
		</div>
		
		
		
		<script>
			var slides = document.querySelectorAll('.mySlides');
			var currentSlide = 0;
			
			function showSlide() 
			{
				for (var i = 0; i < slides.length; i++) 
				{
					slides[i].classList.remove('active');
			  	}
			  	slides[currentSlide].classList.add('active');
			}
	
			function nextSlide() 
			{
				currentSlide++;
				if (currentSlide >= slides.length) 
				{
					currentSlide = 0;
				}
				showSlide();
			}
	
			var slideInterval = setInterval(nextSlide, 3000);			
			showSlide();
		</script>
		
			
		<%@include file="Footer.jsp" %>
	</body>
</html>