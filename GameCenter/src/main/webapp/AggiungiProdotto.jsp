<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	String Visual="";
	String TipologiaInserimento;
%>
<%	
	TipologiaInserimento = (String) request.getAttribute("TipologiaInserimento");
	Visual = (String) request.getParameter("Visual");
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
	        if (Visual != null && Visual.contentEquals("form")) 
			{ 
		%>
        <div class="formRegistraProdotto">
        	<form method="post" action="./AdminProductControl?action=InserisciForm">
				<div class="form">
			      <div class="subtitle">Inserisci la tipologia del nuovo prodotto:</div>
			      <div class="input-container">
				      <div class="box">
				      	Tipologia: <select name="Tipologia" required>
								   		<option value="videogioco">Videogioco</option>
								   		<option value="hardware">Hardware</option>
								   </select>
				      </div>
			      </div>
			      <button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
	        else 
			{
		%>
		<div class="formCliente">
        	<form method="post" action="./AdminProductControl?action=Inserisci" name="inserisci" onsubmit="return validate()" enctype="multipart/form-data">
				<div class="form">
      				<div class="subtitle">Aggiungi un nuovo prodotto</div>
			      	<div class="input-container ic1">
				        <input id="CodSeriale" name="CodSeriale" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="CodSeriale" class="placeholder">CodSeriale</label>
				    </div>
				    <div class="input-container">
				        <input id="Nome" name="Nome" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Nome" class="placeholder">Nome</label>
				    </div>
				    <div class="input-container-Foto">
				        Foto: <input id="Immagine" name="Immagine" class="inputFoto" type="file" />
				    </div>
				    <div class="input-container">
				        <input id="Prezzo" name="Prezzo" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="Prezzo" class="placeholder">Prezzo</label>
				    </div>
				    <div class="input-container">
				        <input id="DataUscita" name="DataUscita" class="input" type="date" placeholder=" " />
				        <div class="cut"></div>
				        <label for="DataUscita" class="placeholder">Data Uscita</label>
				    </div>
				    <div class="input-container">
				        Descrizione Ridotta: <textarea name="DescrizioneRidotta" id="DescrizioneRidotta" rows="5" cols="40" required placeholder="Scrivi qui la tua descrizione ridotta"></textarea>	
				    </div>
				    <br><br>
				    <div class="input-container">
				        Descrizione Completa: <textarea name="DescrizioneCompleta" id="DescrizioneCompleta" rows="5" cols="40" required placeholder="Scrivi qui la tua descrizione ridotta"></textarea>
				    </div>
				    <br><br><br>
				    <%
						if(TipologiaInserimento.contentEquals("Videogioco")) 
						{
					%>
							<div class="box">
								PEGI: <select name="PEGI" id="PEGI" required>
									<%
										if (PEGI != null && PEGI.size() != 0) 
										{
											Iterator<?> it = PEGI.iterator();
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
								Genere: <select name="Genere" id="Genere" required>
									<%
										if (Genere != null && Genere.size() != 0) 
										{
											Iterator<?> it = Genere.iterator();
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
						    <div class="input-container">
						        <input id="PS5Digitale" name="PS5Digitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PS5Digitale" class="placeholder">PS5 Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="PS4Digitale" name="PS4Digitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PS4Digitale" class="placeholder">PS4 Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="XboxXDigitale" name="XboxXDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="XboxXDigitale" class="placeholder">Xbox Series X Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="XboxSDigitale" name="XboxSDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="XboxSDigitale" class="placeholder">Xbox Series S Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="PcDigitale" name="PcDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PcDigitale" class="placeholder">Pc Digitale</label>
						    </div>
				    <% 
						}
					%>
				    <div class="input-container">
				        <input id="PS5Fisico" name="PS5Fisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PS5Fisico" class="placeholder">PS5 Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="PS4Fisico" name="PS4Fisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PS4Fisico" class="placeholder">PS4 Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="XboxXFisico" name="XboxXFisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="XboxXFisico" class="placeholder">Xbox Series X Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="PcFisico" name="PcFisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PcFisico" class="placeholder">Pc Fisico</label>
				    </div>	    
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

 		  		var CodSerialeInput = document.inserisci.CodSeriale.value;
 		  		var nomeInput = document.inserisci.Nome.value;
 		  		var fotoInput = document.inserisci.Immagine.value;
 		  		var prezzoInput = document.inserisci.Prezzo.value;
 		  		var dataUscitaInput = document.inserisci.DataUscita.value;
 		  		var descRidottaInput = document.inserisci.DescrizioneRidotta.value;
 		  		var descCompletaInput = document.inserisci.DescrizioneCompleta.value;
 		  		var pegiInput = document.inserisci.PEGI.value;
 		  		var genereInput = document.inserisci.Genere.value;
 		  		var PS5DigitaleInput = document.inserisci.PS5Digitale.value;
 		  		var PS4DigitaleInput = document.inserisci.PS4Digitale.value;
 		  		var XboxXDigitaleInput = document.inserisci.XboxXDigitale.value;
 		  		var XboxSDigitaleInput = document.inserisci.XboxSDigitale.value;
 		  		var PcDigitaleInput = document.inserisci.PcDigitale.value;
 		  		var PS5FisicoInput = document.inserisci.PS5Fisico.value;
 		  		var PS4FisicoInput = document.inserisci.PS4Fisico.value;
 		  		var XboxXFisicoInput = document.inserisci.XboxXFisico.value;
 		  		var PcFisicoInput = document.inserisci.PcFisico.value;
 		  	
		    	
		    	
		    	
		    	if (CodSerialeInput === null ) 
		    	{
		    		alert("Il campo CodSeriale è è obbligatorio");
		    		document.inserisci.CodSeriale.focus();
		    		return false;
		    	}
		    	else if (CodSerialeInput.length>20) 
		    	{
		    		alert("Il campo Nome è troppo lungo max 50");
		    		document.inserisci.Nome.focus();
		    		return false;
		    	}
		    	
		    	if (nomeInput === null) 
		    	{
		    		alert("Il campo Nome è obbligatorio");
		    		document.inserisci.Nome.focus();
		    		return false;
		    	}
		    	else if (nomeInput.length>50) 
		    	{
		    		alert("Il campo Nome è troppo lungo max 50");
		    		document.inserisci.Nome.focus();
		    		return false;
		    	}
		    	
		    	if (fotoInput === null) 
		    	{
		    		alert("Il campo Immagine è obbligatorio");
		    		document.inserisci.Immagine.focus();
		    		return false;
		    	}
		    	if (prezzoInput === null) 
		    	{
		    		alert("Il campo Prezzo è obbligatorio");
		    		document.inserisci.Prezzo.focus();
		    		return false;
		    	}
		    	
		    	if (dataUscitaInput === null) 
		    	{
		    		alert("Il campo Data Uscita è obbligatorio");
		    		document.inserisci.DataUscita.focus();
		    		return false;
		    	}
		    	
		    	if (descRidottaInput === null) 
		    	{
		    		alert("Il campo Descrizione Ridotta è obbligatorio");
		    		document.inserisci.DescrizioneRidotta.focus();
		    		return false;
		    	}
		    	
		    	if (descCompletaInput === null) 
		    	{
		    		alert("Il campo Descrizione Completa è obbligatorio");
		    		document.inserisci.DescrizioneCompleta.focus();
		    		return false;
		    	}
		    	
		    	if (pegiInput === null) 
		    	{
		    		alert("Il campo PEGI è obbligatorio");
		    		document.inserisci.PEGI.focus();
		    		return false;
		    	}
		    	
		    	if (genereInput === null) 
		    	{
		    		alert("Il campo Genere è obbligatorio");
		    		document.inserisci.Genere.focus();
		    		return false;
		    	}
		    	
		    	if (PS5DigitaleInput === null) 
		    	{
		    		alert("Il campo PS5Digitale è obbligatorio");
		    		document.inserisci.PS5Digitale.focus();
		    		return false;
		    	}
		    	
		    	if (PS4DigitaleInput === null) 
		    	{
		    		alert("Il campo PS4Digitale è obbligatorio");
		    		document.inserisci.PS4Digitale.focus();
		    		return false;
		    	}
		    	
		    	if (XboxXDigitaleInput === null) 
		    	{
		    		alert("Il campo XboxXDigitale è obbligatorio");
		    		document.inserisci.XboxXDigitale.focus();
		    		return false;
		    	}
		    	
		    	if (XboxSDigitaleInput === null) 
		    	{
		    		alert("Il campo XboxSDigitale è obbligatorio");
		    		document.inserisci.XboxSDigitale.focus();
		    		return false;
		    	}
		    	
		    	if (PcDigitaleInput === null) 
		    	{
		    		alert("Il campo PcDigitale è obbligatorio");
		    		document.inserisci.PcDigitale.focus();
		    		return false;
		    	}
		    	
		    	if (PS5FisicoInput === null) 
		    	{
		    		alert("Il campo PS5Fisico è obbligatorio");
		    		document.inserisci.PS5Fisico.focus();
		    		return false;
		    	}
		    	
		    	if (PS4FisicoInput === null) 
		    	{
		    		alert("Il campo PS4Fisico è obbligatorio");
		    		document.inserisci.PS4Fisico.focus();
		    		return false;
		    	}
		    	
		    	if (XboxXFisicoInput === null) 
		    	{
		    		alert("Il campo XboxXFisico è obbligatorio");
		    		document.inserisci.XboxXFisico.focus();
		    		return false;
		    	}
		    	
		    	if (PcFisicoInput === null) 
		    	{
		    		alert("Il campo PcFisico è obbligatorio");
		    		document.inserisci.PcFisico.focus();
		    		return false;
		    	}
		    	return true;
		    }
		</script>
	</body>
</html>