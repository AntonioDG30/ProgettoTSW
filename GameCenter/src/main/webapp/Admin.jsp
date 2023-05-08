<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	String Result="";
	String FormMod="";
	String TipologiaInserimento="";
%>
<%	

	ProductBean product = (ProductBean) request.getAttribute("product");
	Result = (String) request.getAttribute("Result");
	FormMod = (String) request.getAttribute("FormMod");
	TipologiaInserimento = (String) request.getAttribute("TipologiaInserimento");
	Collection<?> Genere = (Collection<?>) request.getAttribute("Genere");
	Collection<?> PEGI = (Collection<?>) request.getAttribute("PEGI");
	
	
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	}
%>
<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
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
		</style>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<% 
			if(Email != null) 
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
			</div>
		
		
		<div id="theFormDiv1">
			<form method="post" action="./Admin_ProductControl?action=Elimina">
				<pre>
					Inserisci il CodSeriale del prodotto da eliminare:
					CodSeriale: <input type="text" name="CodSeriale" placeholder="Inserisci" required>				
					<input type="submit" > <input type="reset">
					<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv1');">
				</pre>
			</form>
		</div>
		
		<div id="theFormDiv2">
			<form method="post" action="./Admin_ProductControl?action=Inserisci" enctype="multipart/form-data">
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
				<form method="post" action="./Admin_ProductControl?action=Inserisci" enctype="multipart/form-data">
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
			<form method="post" action="./Admin_ProductControl?action=Modifica">
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
					<h2>Dettagli</h2>
					<table border="1">
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
								<td><%=product.getPEGI()%></td>
								<td><%=product.getGenere()%></td>
							<%
								}
							%>
						</tr>
					</table>
					<br>
					<h2>Disponibilità per: <%=product.getNome()%></h2>
					<table border="1">
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
							<td><%=product.getDisp_Ps5_Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDisp_Ps5_Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>PlayStation 4</td>
							<td><%=product.getDisp_Ps4_Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDisp_Ps4_Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>Xbox Series X</td>
							<td><%=product.getDisp_XboxX_Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDisp_XboxX_Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>Xbox Series S</td>
							<td><%=product.getDisp_XboxS_Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDisp_XboxS_Digitale()%></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td>PC</td>
							<td><%=product.getDisp_Pc_Fisico()%></td>
							<%
								if (!(product.getTipologia())) 
								{
							%>
								<td><%=product.getDisp_Pc_Digitale()%></td>
							<%
								}
							%>
						</tr>
					</table>			
						<form method="post" action="./Admin_ProductControl?action=Modifica" enctype="multipart/form-data">
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
		
	</body>
</html>