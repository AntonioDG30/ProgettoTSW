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
        	<form method="post" action="./AdminProductControl?action=Modifica" name="modifica" onsubmit="return validate()" enctype="multipart/form-data">
				<div class="form">
      				<div class="subtitle">Modifica un prodotto</div>
			      	<div class="input-container2">
				        CodSeriale: <input id="CodSerialeNew" name="CodSerialeNew" class="input" type="text" placeholder="<%=product.getCodSeriale()%>"/>
				    </div>
				    <div class="input-container2">
				        Nome: <input id="Nome" name="Nome" class="input" type="text" placeholder="<%=product.getNome()%>"/>
				    </div>
				    <div class="input-container-Foto">
				        Foto: <input id="Immagine" name="Immagine" class="inputFoto" type="file" />
				    </div>
				    <div class="input-container2">
				        Prezzo: <input id="Prezzo" name="Prezzo" class="input" type="number" placeholder="<%=product.getPrezzo()%>" min="0"/>

				    </div>
				    <div class="input-container2">
				        Data Uscita: <input id="DataUscita" name="DataUscita" class="input" type="date" placeholder="<%=product.getDataUscita()%>" />
				    </div>
				    <div class="input-container2">
				        Descrizione Ridotta: <textarea id="DescrizioneRidotta" name="DescrizioneRidotta" rows="5" cols="40"  placeholder="<%=product.getDescrizioneRidotta()%>"></textarea>
				    </div>
				    <br><br>
				    <div class="input-container2">
				        Descrizione Completa: <textarea id="DescrizioneCompleta" name="DescrizioneCompleta" rows="5" cols="40"  placeholder="<%=product.getDescrizioneCompleta()%>"></textarea>
				    </div>
				    <br><br>
				    <%
						if(!(product.getTipologia()))  
						{
					%>
							<div class="box">
								PEGI: <select name="PEGI" id="PEGI">
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
								Genere: <select name="Genere" id="Genere">
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
						        PS5 Digitale: <input id="PS5Digitale" name="PS5Digitale" class="input" type="number" placeholder="<%=product.getDispPs5Digitale()%>" min="0"/>
						    </div>
						    <div class="input-container2">
						        PS4 Digitale: <input id="PS4Digitale" name="PS4Digitale" class="input" type="number" placeholder="<%=product.getDispPs4Digitale()%>" min="0"/>
						    </div>
						    <div class="input-container2">
						        Xbox Series X Digitale: <input id="XboxXDigitale" name="XboxXDigitale" class="input" type="number" placeholder="<%=product.getDispXboxXDigitale()%>" min="0"/>
						    </div>
						    <div class="input-container2">
						        Xbox Series S Digitale: <input id="XboxSDigitale" name="XboxSDigitale" class="input" type="number" placeholder="<%=product.getDispXboxSDigitale()%>" min="0"/>
						    </div>
						    <div class="input-container2">
						        Pc Digitale: <input id="PcDigitale" name="PcDigitale" class="input" type="number" placeholder="<%=product.getDispPcDigitale()%>" min="0"/>
						    </div>
				    <% 
						}
					%>
				    <div class="input-container">
				        PS5 Fisico: <input id="PS5Fisico" name="PS5Fisico" class="input" type="number" placeholder="<%=product.getDispPs5Fisico()%>" min="0"/>
				    </div>
				    <div class="input-container">
				        PS4 Fisico: <input id="PS4Fisico" name="PS4Fisico" class="input" type="number" placeholder="<%=product.getDispPs4Fisico()%>" min="0"/>
				    </div>
				    <div class="input-container">
				        Xbox Series X Fisico: <input id="XboxXFisico" name="XboxXFisico" class="input" type="number" placeholder="<%=product.getDispXboxXFisico()%>" min="0"/>
				    </div>
				    <div class="input-container">
				        Pc Fisico: <input id="PcFisico" name="PcFisico" class="input" type="number" placeholder="<%=product.getDispPcFisico()%>" min="0"/>
				    </div>	    
				    <input name="CodSeriale" type="hidden" value="<%=product.getCodSeriale()%>" />
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
		%>
		<script>
		 	function validate() 
		    {

 		  		var CodSerialeNewInput = document.modifica.CodSerialeNew.value;
 		  		var nomeInput = document.modifica.Nome.value;
 		  	
		    	
		    	if (nomeInput.length>50) 
		    	{
		    		alert("Il campo Nome è troppo lungo max 50");
		    		document.modifica.Nome.focus();
		    		return false;
		    	}
		    	
		    	if (CodSerialeNewInput.length>20) 
		    	{
		    		alert("Il campo CodSeriale è troppo lungo max 20");
		    		document.modifica.CodSerialeNew.focus();
		    		return false;
		    	}
		    	return true;
		    }
		</script>
	</body>
</html>