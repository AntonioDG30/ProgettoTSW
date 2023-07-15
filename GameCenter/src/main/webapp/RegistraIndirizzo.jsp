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
        	<form method="post" name="indirizzi" action="./UserControl?action=NuovoIndirizzo" onsubmit="return validate()">
				<div class="form">
      				<div class="subtitle">Registra nuovo indirizzo di spedizione</div>
				    <div class="input-container">
				        <input id="Nome" name="Nome" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Nome" class="placeholder">Nome</label>
				    </div>
				    <div class="input-container">
				        <input id="Cognome" name="Cognome" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Cognome" class="placeholder">Cognome</label>
				    </div>
				    <div class="input-container">
				        <input id="Via" name="Via" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Via" class="placeholder">Via</label>
				    </div>
				    <div class="input-container">
				        <input id="Civico" name="Civico" class="input" type="number" placeholder=" " min="1"/>
				        <div class="cut"></div>
				        <label for="Civico" class="placeholder">Civico</label>
				    </div>
				    <div class="input-container">
					    <select id="Provincia" name="Provincia" class="input" name="Provincia">
					        <option value="" selected disabled>Seleziona una provincia</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Provincia" class="placeholder">Provincia</label>
					</div>
					<div class="input-container">
					    <select id="Citta" name="Citta" class="input" name="Citta" disabled>
					        <option value="" selected disabled>Seleziona una città</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Citta" class="placeholder">Città</label>
					</div>
				    <div class="input-container">
				        <input id="CAP" name="CAP" class="input" type="number" placeholder=" " />
				        <div class="cut"></div>
				        <label for="CAP" class="placeholder">CAP</label>
				    </div>
				    <div class="input-container">
				        <input id="Telefono" name="Telefono" class="input" type="text" placeholder=" " />
				        <div class="cut"></div>
				        <label for="Telefono" class="placeholder">Numero Telefono</label>
				    </div>
			      	<button type="submit" class="submit">submit</button>
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
		    
		    

	        function validate() 
		    {		    	
			  	var nomeInput = document.indirizzi.Nome.value;
			  	var cognomeInput = document.indirizzi.Cognome.value;
			  	var str = /^[a-zA-Z]+$/;
			  	var viaInput = document.indirizzi.Via.value;
			  	var civicoInput = document.indirizzi.Civico.value;
			  	var provinciaInput = document.indirizzi.Provincia.value;
			  	var cittaInput = document.indirizzi.Citta.value;
			  	var capInput = document.indirizzi.CAP.value;
			  	var telefonoInput = document.indirizzi.Telefono.value;
			  	var telefonoRegex = /^\+?\d[0-9 .]{7,12}\d$/
		    	
		    	
		    	
		  		if (nomeInput === "") 
		    	{
		    		alert("Il campo Nome è obbligatorio");
		    		document.indirizzi.Nome.focus();
		    		return false;
		    	}
		    	else if (!str.test(nomeInput)) 
		    	{
		    		alert("Il campo Nome è errato, riprova!");
		    		document.indirizzi.Nome.focus();
		    		return false;
		    	}
		    	
		    	if (cognomeInput === "") 
		    	{
		    		alert("Il campo Cognome è obbligatorio");
		    		document.indirizzi.Cognome.focus();
		    		return false;
		    	}
		    	else if (!str.test(cognomeInput)) 
		    	{
		    		alert("Il campo Cognome è errato, riprova!");
		    		document.indirizzi.Cognome.focus();
		    		return false;
		    	}
		    		    	
		    	if (viaInput === "") 
		    	{
		    		alert("Il campo Via è obbligatorio");
		    		document.indirizzi.Via.focus();
		    		return false;
		    	}
		    	
		    	if (civicoInput === "") 
		    	{
		    		alert("Il campo Civico è obbligatorio");
		    		document.indirizzi.Civico.focus();
		    		return false;
		    	}
		    	
		    	if (provinciaInput === "") 
		    	{
		    		alert("Il campo Provincia è obbligatorio");
		    		document.indirizzi.Provincia.focus();
		    		return false;
		    	}
		    	
		    	if (cittaInput === "") 
		    	{
		    		alert("Il campo Citta è obbligatorio");
		    		document.indirizzi.Citta.focus();
		    		return false;
		    	}
		    	
		    	if (capInput === "") 
		    	{
		    		alert("Il campo CAP è obbligatorio");
		    		document.indirizzi.CAP.focus();
		    		return false;
		    	}
		    	
		    	if (telefonoInput === "") 
		    	{
		    		alert("Il campo Telefono è obbligatorio");
		    		document.indirizzi.Telefono.focus();
		    		return false;
		    	}
		    	else if (!telefonoRegex.test(telefonoInput)) 
		    	{
		    		alert("Il campo Telefono è errato, riprova!");
		    		document.indirizzi.Telefono.focus();
		    		return false;
		    	}

		    	return true;
		    }
		</script>
	</body>
</html>