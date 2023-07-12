<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
%>
<%	
	ProductBean product = (ProductBean) request.getAttribute("product");
	Collection<?> Genere = (Collection<?>) request.getAttribute("Genere");
	Collection<?> PEGI = (Collection<?>) request.getAttribute("PEGI");
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
	<head>
		<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
		<title>GameCenter</title>
		<link href="LayoutSito/css/registrati.css" rel="stylesheet" type="text/css">	
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<% 
			if(Email == null)
			{
					response.sendRedirect("./index.jsp");
			}
		%>
		<%@include file="NavBar.jsp" %>
		<% 
			if (product != null) 
			{
		%>
		<div class="formCliente">
        	<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<div class="form">
      				<div class="subtitle">Aggiungi un nuovo prodotto</div>
			      	<div class="input-container2">
				        CodSeriale: <input id="CodSeriale" class="input" type="text" placeholder="<%=product.getCodSeriale()%>" required/>
				    </div>
				    <div class="input-container2">
				        Nome: <input id="Nome" class="input" type="text" placeholder=" " required/>
				    </div>
				    <div class="input-container-Foto">
				        Foto: <input id="Immagine" class="inputFoto" type="file" />
				    </div>
				    <div class="input-container2">
				        Prezzo: <input id="Prezzo" class="input" type="number" placeholder=" " required min="0"/>

				    </div>
				    <div class="input-container2">
				        Data Uscita: <input id="DataUscita" class="input" type="date" placeholder=" " />
				    </div>
				    <div class="input-container2">
				        Descrizione Ridotta: <input id="DescrizioneRidotta" class="input" type="text" placeholder=" " required min="1"/>
				    </div>
				    <div class="input-container2">
				        Descrizione Completa: <input id="DescrizioneCompleta" class="input" type="text" placeholder=" " required/>
				    </div>
				    <%
						if(!(product.getTipologia()))  
						{
					%>
							<div class="box">
								PEGI: <select name="PEGI" required>
									<%
										if (PEGI != null && PEGI.size() != 0) 
										{
											Iterator<?> it = PEGI.iterator();
									%>
											<option selected="selected"  value="<%=product.getPegi()%>"><%=product.getPegi()%></option>
									<%
											while (it.hasNext()) 
											{
												PegiBean bean = (PegiBean) it.next();
									%>
									   		<option value="<%=bean.getCodPEGI() %>"><%=bean.getCodPEGI()%></option>
									<%
											}
										}
									%>
									   </select>
									   
							</div>
							<div class="box">
								Genere: <select name="Genere" required>
									<%
										if (Genere != null && Genere.size() != 0) 
										{
											Iterator<?> it = Genere.iterator();
									%>
											<option selected="selected"  value="<%=product.getGenere()%>"><%=product.getGenere()%></option>
									<%
											while (it.hasNext()) 
											{
												GenereBean bean = (GenereBean) it.next();
									%>
									   		<option value="<%=bean.getNomeGenere() %>"><%=bean.getNomeGenere()%></option>
									<%
											}
										}
									%>
									
									 </select>
							</div>
						    <div class="input-container2">
						        PS5 Digitale: <input id="PS5Digitale" class="input" type="number" placeholder=" " required min="0"/>
						    </div>
						    <div class="input-container2">
						        PS4 Digitale: <input id="PS4Digitale" class="input" type="number" placeholder=" " required min="0"/>
						    </div>
						    <div class="input-container2">
						        Xbox Series X Digitale: <input id="XboxXDigitale" class="input" type="number" placeholder=" " required min="0"/>
						    </div>
						    <div class="input-container2">
						        >Xbox Series S Digitale: <input id="XboxSDigitale" class="input" type="number" placeholder=" " required min="0"/>
						    </div>
						    <div class="input-container2">
						        Pc Digitale: <input id="PcDigitale" class="input" type="number" placeholder=" " required min="0"/>
						    </div>
				    <% 
						}
					%>
				    <div class="input-container">
				        PS5 Fisico: <input id="PS5Fisico" class="input" type="number" placeholder=" " required min="0"/>
				    </div>
				    <div class="input-container">
				        PS4 Fisico: <input id="PS4Fisico" class="input" type="number" placeholder=" " required min="0"/>
				    </div>
				    <div class="input-container">
				        Xbox Series X Fisico: <input id="XboxXFisico" class="input" type="number" placeholder=" " required min="0"/>
				    </div>
				    <div class="input-container">
				        Pc Fisico: <input id="PcFisico" class="input" type="number" placeholder=" " required min="0"/>
				    </div>	    
				    <input name="ParteMod" type="hidden" value="Parte2" />
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
		%>
	</body>
</html>