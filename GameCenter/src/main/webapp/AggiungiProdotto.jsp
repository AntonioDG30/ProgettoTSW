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
        	<form method="post" action="./AdminProductControl?action=Inserisci">
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
			      <input name="ParteMod" type="hidden" value="Parte1" />
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
        	<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<div class="form">
      				<div class="subtitle">Aggiungi un nuovo prodotto</div>
			      	<div class="input-container ic1">
				        <input id="CodSeriale" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="CodSeriale" class="placeholder">CodSeriale</label>
				    </div>
				    <div class="input-container">
				        <input id="Nome" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Nome" class="placeholder">Nome</label>
				    </div>
				    <div class="input-container-Foto">
				        Foto: <input id="Immagine" class="inputFoto" type="file" />
				    </div>
				    <div class="input-container">
				        <input id="Prezzo" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="Prezzo" class="placeholder">Prezzo</label>
				    </div>
				    <div class="input-container">
				        <input id="DataUscita" class="input" type="date" placeholder=" " />
				        <div class="cut"></div>
				        <label for="DataUscita" class="placeholder">Data Uscita</label>
				    </div>
				    <div class="input-container">
				        Descrizione Ridotta: <textarea name="Descrizione" rows="5" cols="40" required placeholder="Scrivi qui la tua descrizione ridotta"></textarea>	
				    </div>
				    <br><br>
				    <div class="input-container">
				        Descrizione Completa: <textarea name="Descrizione" rows="5" cols="40" required placeholder="Scrivi qui la tua descrizione ridotta"></textarea>
				    </div>
				    <br><br>
				    <%
						if(TipologiaInserimento.contentEquals("Videogioco")) 
						{
					%>
							<div class="box">
								PEGI: <select name="PEGI" required>
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
								Genere: <select name="Genere" required>
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
						        <input id="PS5Digitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PS5Digitale" class="placeholder">PS5 Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="PS4Digitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PS4Digitale" class="placeholder">PS4 Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="XboxXDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="XboxXDigitale" class="placeholder">Xbox Series X Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="XboxSDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="XboxSDigitale" class="placeholder">Xbox Series S Digitale</label>
						    </div>
						    <div class="input-container">
						        <input id="PcDigitale" class="input" type="number" placeholder=" " required min="0"/>
						        <div class="cut"></div>
						        <label for="PcDigitale" class="placeholder">Pc Digitale</label>
						    </div>
				    <% 
						}
					%>
				    <div class="input-container">
				        <input id="PS5Fisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PS5Fisico" class="placeholder">PS5 Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="PS4Fisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PS4Fisico" class="placeholder">PS4 Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="XboxXFisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="XboxXFisico" class="placeholder">Xbox Series X Fisico</label>
				    </div>
				    <div class="input-container">
				        <input id="PcFisico" class="input" type="number" placeholder=" " required min="0"/>
				        <div class="cut"></div>
				        <label for="PcFisico" class="placeholder">Pc Fisico</label>
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