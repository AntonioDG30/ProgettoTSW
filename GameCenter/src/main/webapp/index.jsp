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
		<link href="LayoutSito/css/index.css" rel="stylesheet" type="text/css">
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
		  <a href="Login.jsp"><img class="mySlides" src="LayoutSito/img/PS5_Slide.jpg"></a>
		  <a href="Registrati.jsp"><img class="mySlides" src="LayoutSito/img/NVIDIA4090_Slide.jpg"></a>
		  <a href="Login.jsp"><img class="mySlides" src="LayoutSito/img/TLOU2_Slide.jpg"></a>
		</div>
		
		
		<h2 align="center">I prodotti più ricercati:</h2>
		<%
			if (products != null && products.size() != 0) 
			{
				Iterator<?> it = products.iterator();
				while (it.hasNext()) 
				{
					ProductBean bean = (ProductBean) it.next();
		%>
		<div class="prodotti">
			<%
				if (bean.getImmagine() != null && bean.getImmagine() != "") 
				{						
			%>
				<img src="Immagini/<%=bean.getImmagine()%>">
			<%
				}						
			%>
			<div class="descrizione">
				<h3><%=bean.getNome()%></h3>
				<%=bean.getDescrizioneRidotta()%>
				<div class="prezzo">
					<%=bean.getPrezzo()%>
				</div>				
			</div>
		</div>
		<br>
		<%
				}
			}
		%>
		<br>
		

		
		
		
		<script>
			var myIndex = 0;
			carousel();
			
			function carousel() 
			{
			  	var i;
			  	var x = document.getElementsByClassName("mySlides");
			  	for (i = 0; i < x.length; i++) 
			  	{
			    	x[i].style.display = "none";  
			  	}
			  	myIndex++;
			  	if (myIndex > x.length) 
			  	{
			  		myIndex = 1
			  	}    
			  	x[myIndex-1].style.display = "block";  
			  	setTimeout(carousel, 6000); // Change image every 2 seconds
			}
		</script>
		
			
		<%@include file="Footer.jsp" %>
	</body>
</html>