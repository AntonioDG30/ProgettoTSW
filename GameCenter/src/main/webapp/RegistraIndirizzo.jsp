<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

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
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GameCenter</title>
		<script src="LayoutSito/js/jQuery3.6.0.js"></script>
		<link href="LayoutSito/css/registrati.css" rel="stylesheet" type="text/css">	
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<% 
			if(Email == null)
			{
					response.sendRedirect("./Login.jsp");
			}
		%>
		<%@include file="NavBar.jsp" %>
		<div class="formIndirizzi">
        	<form method="post" action="./UserControl?action=NuovoIndirizzo">
				<div class="form">
      				<div class="subtitle">Registra nuovo indirizzo di spedizione</div>
				    <div class="input-container">
				        <input id="Nome" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Nome" class="placeholder">Nome</label>
				    </div>
				    <div class="input-container">
				        <input id="Cognome" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Cognome" class="placeholder">Cognome</label>
				    </div>
				    <div class="input-container">
				        <input id="Via" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Via" class="placeholder">Via</label>
				    </div>
				    <div class="input-container">
				        <input id="Civico" class="input" type="number" placeholder=" " min="1"/>
				        <div class="cut"></div>
				        <label for="Civico" class="placeholder">Civico</label>
				    </div>
				    <div class="input-container">
					    <select id="Provincia" class="input" name="Provincia">
					        <option value="" selected disabled>Seleziona una provincia</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Provincia" class="placeholder">Provincia</label>
					</div>
					<div class="input-container">
					    <select id="Citta" class="input" name="Citta" disabled>
					        <option value="" selected disabled>Seleziona una città</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Citta" class="placeholder">Città</label>
					</div>
				    <div class="input-container">
				        <input id="CAP" class="input" type="number" placeholder=" " />
				        <div class="cut"></div>
				        <label for="CAP" class="placeholder">CAP</label>
				    </div>
				    <div class="input-container">
				        <input id="Telefono" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Telefono" class="placeholder">Numero Telefono</label>
				    </div>
			      	<button type="text" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <%@include file="Footer.jsp" %>
        
        
		<script>
		    $(document).ready(function() {
		        // Carica il file JSON contenente i dati dei comuni e delle province d'Italia
		        $.getJSON("LayoutSito/js/citta.json", function(data) {
		            // Popola il menu a tendina delle province
		            var provinceSelect = $("#Provincia");
		            $.each(data.province, function(index, provincia) {
		                provinceSelect.append($("<option></option>")
		                    .attr("value", provincia)
		                    .text(provincia));
		            });
		
		            // Aggiorna il menu a tendina delle città quando viene selezionata una provincia
		            provinceSelect.on("change", function() {
		                var selectedProvincia = $(this).val();
		                var comuniSelect = $("#Citta");
		                comuniSelect.empty().prop("disabled", true);
		                comuniSelect.append($("<option></option>")
		                    .attr("value", "")
		                    .text("Seleziona una città"));
		                if (selectedProvincia !== "") {
		                    $.each(data.comuni[selectedProvincia], function(index, comune) {
		                        comuniSelect.append($("<option></option>")
		                            .attr("value", comune)
		                            .text(comune));
		                    });
		                    comuniSelect.prop("disabled", false);
		                }
		            });
		        });
		    });
		</script>
	</body>
</html>