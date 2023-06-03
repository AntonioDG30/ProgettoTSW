<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	Boolean Tipo;
	String Result="";
	String FormMod="";
	String TipologiaInserimento="";
	String Visual="";
%>
<%	

	ProductBean product = (ProductBean) request.getAttribute("product");
	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	TipologiaInserimento = (String) request.getAttribute("TipologiaInserimento");
	Collection<?> Genere = (Collection<?>) request.getAttribute("Genere");
	Collection<?> PEGI = (Collection<?>) request.getAttribute("PEGI");
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	Collection<?> Clienti = (Collection<?>) request.getAttribute("Clienti");
	UserBean Cliente = (UserBean) request.getAttribute("Cliente");
	Visual = (String) request.getAttribute("Visual");
	
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
		<meta charset="ISO-8859-1">
		<title>Admin Page</title>
		<script type="text/javascript">
			function showForm(FormID) 
			{
				oDiv = document.getElementById(FormID);
			  	oDiv.style.display='block';
			  	return false;
			}
		
			function hideForm(FormID) 
			{
			  	oDiv = document.getElementById(FormID);
			  	oDiv.style.display='none';
			  	return false;
			}
		</script>
		<style type="text/css" >
			#theFormDiv1
			{
		 	display:none; 	
			}
			#theFormDiv2
			{
		 	display:none; 	
			}
			#theFormDiv3
			{
		 	display:none; 	
			}
			#theFormDiv6
			{
		 	display:none; 	
			}
			#theFormDiv9
			{
		 	display:none; 	
			}
		</style>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Email != null && (!Tipo)) 
			{
		%>
		<h2><%=Email%>, benvenuto nella pagina admin</h2>
		<% 
			}
			else
			{
				response.sendRedirect("./index.jsp");
			}
		%>
		<% 
			if(Result != null) 
			{
		%>
		<%=Result%>
		<% 
			}
		%>		
			<h2>Quale operazione vuoi eseguire?</h2>
			<div id="theExample">
  				<p><a title="open form" href="" onclick="return showForm('theFormDiv1');">Elimina Prodotto</a></p>
  				<p><a title="open form" href="" onclick="return showForm('theFormDiv2');">Aggiungi Prodotto</a></p>
  				<p><a title="open form" href="" onclick="return showForm('theFormDiv3');">Modifica Prodotto</a></p>
  				<p><a title="open form" href="" onclick="return showForm('theFormDiv6');">Visualizza Elenco Ordini</a></p>
  				<p><a title="open form" href="" onclick="return showForm('theFormDiv9');">Visualizza Elenco Clienti</a></p>
			</div>
		
		
		<div id="theFormDiv1">
			<form method="post" action="./AdminProductControl?action=Elimina">
				<pre>
					Inserisci il CodSeriale del prodotto da eliminare:
					CodSeriale: <input type="text" name="CodSeriale" placeholder="Inserisci" required>				
					<input type="submit" > <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv1');">
				</pre>
			</form>
		</div>
		
		<div id="theFormDiv2">
			<form method="post" action="./AdminProductControl?action=Inserisci" enctype="multipart/form-data">
				<pre>
					Inserisci la tipologia del nuovo prodotto:	
					Tipologia: <select name="Tipologia" required>
							   		<option value="videogioco">Videogioco</option>
							   		<option value="hardware">Hardware</option>
							   </select>
					<input type="hidden" name="ParteMod" value="Parte1">
					<input type="submit"> <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv2'); ">
				</pre>
			</form>
		</div>
		
		
		
		<div id="theFormDiv5">
			<% 
				if(TipologiaInserimento != null)
				{
			%>
				<form method="post" action="./AdminProductControl?action=Inserisci" enctype="multipart/form-data">
					<pre>
						Inserisci le caratteristiche del nuovo prodotto:
						CodSeriale: <input type="text" name="CodSeriale" placeholder="Inserisci CodSeriale" required>
						Nome: <input type="text" name="Nome" placeholder="Inserisci Nome" required>
						Foto: <input type="file"  name="Immagine" required>
						Prezzo: <input type="number" name="Prezzo" placeholder="Inserisci Prezzo" step="any" required>
						Data Uscita: <input type="date" name="DataUscita" placeholder="Inserisci Data Uscita" required>
						Descrizione Ridotta: <input type="text" name="DescrizioneRidotta" placeholder="Inserisci Descrizione Ridotta" required>
						Descrizione Completa: <input type="text" name="DescrizioneCompleta" placeholder="Inserisci Descrizione Completa" required>
						<%
								if(TipologiaInserimento.contentEquals("Videogioco")) 
								{
						%>
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
						Disponibilità per PlayStation 5 formato Digitale: <input type="number" name="PS5Digitale" placeholder="Inserisci Disponibilità" required>
						Disponibilità per PlayStation 4 formato Digitale: <input type="number" name="PS4Digitale" placeholder="Inserisci Disponibilità" required>
						Disponibilità per Xbox Series X formato Digitale: <input type="number" name="XboxXDigitale" placeholder="Inserisci Disponibilità" required>
						Disponibilità per Xbox Series S formato Digitale: <input type="number" name="XboxSDigitale" placeholder="Inserisci Disponibilità" required>
						Disponibilità per PC formato Digitale: <input type="number" name="PcDigitale" placeholder="Inserisci Disponibilità" required>
						<% 
								}
						%>	
						Disponibilità per PlayStation 5 formato Fisico: <input type="number" name="PS5Fisico" placeholder="Inserisci Disponibilità" required>
						Disponibilità per PlayStation 4 formato Fisico: <input type="number" name="PS4Fisico" placeholder="Inserisci Disponibilità" required>
						Disponibilità per Xbox Series X formato Fisico: <input type="number" name="XboxXFisico" placeholder="Inserisci Disponibilità" required>
						Disponibilità per PC formato Fisico: <input type="number" name="PcFisico" placeholder="Inserisci Disponibilità" required>
						<input type="hidden" name="ParteMod" value="Parte2">
						<input type="submit"> <input type="reset">
						<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv5'); ">
					</pre>
				</form>
			<% 
				}
			%>
		</div>
		
		<div id="theFormDiv3">
			<form method="post" action="./AdminProductControl?action=Modifica">
				<pre>
					Inserisci il CodSeriale del prodotto da modificare:
					CodSeriale: <input type="text" name="CodSeriale" placeholder="Inserisci" required>
					<input type="hidden" name="ParteMod" value="Parte1">				
					<input type="submit"> <input type="reset"> 
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv3');">
				</pre>
			</form>
		</div>
		
		
		
		<div id="theFormDiv4">
			<%
				if (product != null) 
				{
			%>
					<table border="1">
					<caption>Dettagli</caption>
						<tr>
							<th>Nome</th>
							<th>Prezzo</th>
							<th>DataUscita</th>
							<th>DescrizioneCompleta</th>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<th>PEGI</th>
								<th>Genere</th>
							<%
								}
							%>
						</tr>
						<tr>
							<td><%=product.getNome()%></td>
							<td><%=product.getPrezzo()%></td>
							<td><%=product.getDataUscita()%></td>
							<td><%=product.getDescrizioneCompleta()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getPegi()%></td>
								<td><%=product.getGenere()%></td>
							<%
								}
							%>
						</tr>
					</table>
				
					<br>
					<table border="1">
						<caption>Disponibilità per: <%=product.getNome()%></caption>
						<tr>
							<th>Piattaforma</th>
							<th>Formato Fisico</th>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<th>Formato Digitale</th>
							<%
								}
							%>
						</tr>
						<tr>
							<td>PlayStation 5</td>
							<td><%=product.getDispPs5Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDispPs5Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>PlayStation 4</td>
							<td><%=product.getDispPs4Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDispPs4Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>Xbox Series X</td>
							<td><%=product.getDispXboxXFisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDispXboxXDigitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>Xbox Series S</td>
							<td><%=product.getDispXboxSFisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDispXboxSDigitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>PC</td>
							<td><%=product.getDispPcFisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDispPcDigitale()%></td>
							<%
								}
							%>
						</tr>
					</table>			
						<form method="post" action="./AdminProductControl?action=Modifica" enctype="multipart/form-data">
							<pre>
								Compila i form delle SOLE caratteristiche che vuoi modificare del prodotto:
								CodSeriale: <input type="text" name="CodSeriale" placeholder="Inserisci nuovo CodSeriale">
								Nome: <input type="text" name="Nome" placeholder="Inserisci nuovo Nome">
								Foto: <input type="file"  name="Immagine">
								Prezzo: <input type="number" name="Prezzo" placeholder="Inserisci nuovo Prezzo" step="any">
								Data Uscita: <input type="date" name="DataUscita" placeholder="Inserisci nuova Data Uscita">
								Descrizione Ridotta: <input type="text" name="DescrizioneRidotta" placeholder="Inserisci nuova Descrizione Ridotta">
								Descrizione Completa: <input type="text" name="DescrizioneCompleta" placeholder="Inserisci nuova Descrizione Completa">
								<%
										if(!(product.getTipologia())) 
										{
								%>
								PEGI: <select name="PEGI" required>
									<option value="NonModificare">Non Modificare</option>
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
								Genere: <select name="Genere" required>
									<option value="NonModificare">Non Modificare</option>
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
								Disponibilità per PlayStation 5 formato Digitale: <input type="number" name="PS5Digitale" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per PlayStation 4 formato Digitale: <input type="number" name="PS4Digitale" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per Xbox Series X formato Digitale: <input type="number" name="XboxXDigitale" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per Xbox Series S formato Digitale: <input type="number" name="XboxSDigitale" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per PC formato Digitale: <input type="number" name="PcDigitale" placeholder="Inserisci nuova Disponibilità">
								<% 
										}
								%>	
								Disponibilità per PlayStation 5 formato Fisico: <input type="number" name="PS5Fisico" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per PlayStation 4 formato Fisico: <input type="number" name="PS4Fisico" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per Xbox Series X formato Fisico: <input type="number" name="XboxXFisico" placeholder="Inserisci nuova Disponibilità">
								Disponibilità per PC formato Fisico: <input type="number" name="PcFisico" placeholder="Inserisci nuova Disponibilità">
								<input type="hidden" name="ParteMod" value="Parte2">
								<input type="hidden" name="Tipologia" value="<%=product.getTipologia()%>">
								<input type="submit"> <input type="reset">
								<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv4'); ">
							</pre>
						</form>
				<%
					}	
				%>
		</div>
		
		<div id="theFormDiv6">
			<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<pre>
					Quali ordini vuoi visualizzare:	<select name="VisualizzazioneOrdini" required>
							   		<option value="Tutti">Visualizza tutti gli ordini</option>
							   		<option value="Cliente">Visualizza ordini di un determinato cliente</option>
							   		<option value="Periodo">Visualizza gli ordini di un determinato periodo</option>
							   </select>
					<input type="hidden" name="ParteMod" value="Parte1">
					<input type="submit"> <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv6'); ">
				</pre>
			</form>
		</div>
		
		
		
		<div id="theFormDiv7">
		<%
			if(Visual != null && Visual.contentEquals("Cliente"))
			{
		%>
			<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<pre>
					Inserisci l'Email del cliente di cui visualizzare gli ordini:
					Email: <input type="email" name="email" placeholder="Inserisci Email cliente" required>	
					<input type="hidden" name="ParteMod" value="Parte2">			
					<input type="submit" > <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv7');">
				</pre>
			</form>
		<%	
			}
			else if(Visual != null && Visual.contentEquals("Periodo"))
			{
		%>
			<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<pre>
					Inserisci il periodo di cui visualizzare gli ordini:
					Data inizio: <input type="date" name="DataInizio" placeholder="Inserisci Data inizio periodo" required>	
					Data Fine: <input type="date" name="DataFine" placeholder="Inserisci Data fine periodo" required>	
					<input type="hidden" name="ParteMod" value="Parte2">			
					<input type="submit" > <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv7');">
				</pre>
			</form>
		<%		
			}
		
		%>
			
			
		</div>
		
		
		
		<div id="theFromDiv8">
		<%
			if (Ordini != null && Ordini.size() != 0) 
			{
		%>
			<table border="1">
				<caption>Ordini Effettuati</caption>
				<tr>
					<th>Cliente</th>
					<th>DataAcquisto</th>
					<th>PrezzoTotale</th>
					<th>StatoOrdine</th>
					<th>Azioni</th>
				</tr>
				<%
					
						Iterator<?> it = Ordini.iterator();
						while (it.hasNext()) 
						{
							OrdineBean bean = (OrdineBean) it.next();
				%>
				
				<tr>
					<td><%=bean.getEmail()%></td>
					<td><%=bean.getDataAcquisto()%></td>
					<td><%=bean.getPrezzoTotale()%></td>
					<td><%=bean.getStatoOrdine()%></td>
					<td>
						<a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>">Dettagli</a>
					</td>
					
				</tr>
				<%
						}
					}
				%>
			</table>
		</div>
		
		
		
		
		<div id="theFormDiv9">
			<form method="post" action="./UserControl?action=VisualizzaUtenti">
				<pre>
					Cosa vuoi visualizzare?	<select name="VisualizzazioneUtente" required>
							   		<option value="Tutti">Visualizza tutti i clienti</option>
							   		<option value="ClienteSpecifico">Visualizza un determinato cliente</option>
							   </select>
					<input type="hidden" name="ParteMod" value="Parte1">
					<input type="submit"> <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv6'); ">
				</pre>
			</form>
		</div>
		
		<div id="theFormDiv10">
		<%
			if(Visual != null && Visual.contentEquals("ClienteSpecifico"))
			{
		%>
			<form method="post" action="./UserControl?action=VisualizzaUtenti">
				<pre>
					Inserisci l'Email del cliente che vuoi visualizzare:
					Email: <input type="email" name="email" placeholder="Inserisci Email cliente" required>	
					<input type="hidden" name="ParteMod" value="Parte2">			
					<input type="submit" > <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv10');">
				</pre>
			</form>
		<%	
			}
		%>
		
		<div id="theFromDiv11">
		<%
			if (Clienti != null && Clienti.size() != 0) 
			{
		%>
			<table border="1">
				<caption>Elenco Clienti:</caption>
				<tr>
					<th>Cliente</th>
					<th>Punti Fedeltà</th>
					<th>Codice Fiscale</th>
					<th>Nome</th>
					<th>Cognome</th>
					<th>Indirizzo</th>
					<th>Telefono</th>
				</tr>
				<%
					
						Iterator<?> it = Clienti.iterator();
						while (it.hasNext()) 
						{
							UserBean bean = (UserBean) it.next();
				%>
				
				<tr>
					<td><%=bean.getEmail()%></td>
					<td><%=bean.getPuntiFedelta()%></td>
					<td><%=bean.getCodiceFiscale()%></td>
					<td><%=bean.getNome()%></td>
					<td><%=bean.getCognome()%></td>
					<td><%=bean.getVia()%> <%=bean.getCivico()%>, <%=bean.getCitta()%> (<%=bean.getProvincia()%>), <%=bean.getCAP()%></td>
					<td><%=bean.getNumeroTelefono()%></td>

					
				</tr>
			
				<%
						}
						%>
			</table>
			
				<%
					}
		
			else if (Cliente != null)
			{
				%>
				<table border="1">
				<caption>Cliente:</caption>
					<tr>
						<th>Cliente</th>
						<th>Punti Fedeltà</th>
						<th>Codice Fiscale</th>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Indirizzo</th>
						<th>Telefono</th>
					</tr>
					<tr>
						<td><%=Cliente.getEmail()%></td>
						<td><%=Cliente.getPuntiFedelta()%></td>
						<td><%=Cliente.getCodiceFiscale()%></td>
						<td><%=Cliente.getNome()%></td>
						<td><%=Cliente.getCognome()%></td>
						<td><%=Cliente.getVia()%> <%=Cliente.getCivico()%>, <%=Cliente.getCitta()%> (<%=Cliente.getProvincia()%>), <%=Cliente.getCAP()%></td>
						<td><%=Cliente.getNumeroTelefono()%></td>
	
						
					</tr>
				
			</table>
			
			<%
						}
				%>
		</div>
		
	</body>
</html>