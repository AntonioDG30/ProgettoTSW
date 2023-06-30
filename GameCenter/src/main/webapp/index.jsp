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
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link href="LayoutSito/css/index.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
		<title>GameCenter</title>
		<style>
			.mySlides {display:none;}
		</style>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Tipo != null)
			{
				if(!(Tipo))
				{
					response.sendRedirect("./Admin.jsp");
				}
			}
		%>
		
		<div class="w3-content w3-section" style="max-width:500px">
		  <a href="Login.jsp"><img class="mySlides" src="Immagini/FIFA23.jpg" style="width:100%"></a>
		  <a href="Registrati.jsp"><img class="mySlides" src="Immagini/GTA5.jpg" style="width:100%"></a>
		  <a href="Login.jsp"><img class="mySlides" src="Immagini/TLOU2.jpg" style="width:100%"></a>
		</div>

		<table border="1">
			<tr>
				<th>Foto</th>
				<th>Nome</th>
				<th>Prezzo</th>
				<th>DescrizioneRidotta</th>
				<th>Azioni</th>
			</tr>
			<%
				if (products != null && products.size() != 0) 
				{
					Iterator<?> it = products.iterator();
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
				<td><%=bean.getNome()%></td>
				<td><%=bean.getPrezzo()%></td>
				<td><%=bean.getDescrizioneRidotta()%></td>
				<td>
					<a href="GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>">Dettagli</a><br>
				</td>
				
			</tr>
			<%
					}
				}
				else
				{
			%>
					<tr>
						<td>Nessun prodotto disponibile in catalogo</td>
					</tr>
			<%
				}
			%>
		</table>
		
		
		<script>
			var myIndex = 0;
			carousel();
			
			function carousel() {
			  var i;
			  var x = document.getElementsByClassName("mySlides");
			  for (i = 0; i < x.length; i++) {
			    x[i].style.display = "none";  
			  }
			  myIndex++;
			  if (myIndex > x.length) {myIndex = 1}    
			  x[myIndex-1].style.display = "block";  
			  setTimeout(carousel, 6000); // Change image every 2 seconds
			}
		</script>
		
			
		<%@include file="Footer.jsp" %>
	</body>
</html>