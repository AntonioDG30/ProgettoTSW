<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!	
	String Email="";
	String Result="";
%>
<%	
	Result = (String) request.getAttribute("Result");	
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
		<title>GameCenter Registrati</title>
		<link href="LayoutSito/css/registrati.css" rel="stylesheet" type="text/css">	
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<% 
			if(Email != null)
			{
					response.sendRedirect("./index.jsp");
			}
		%>
		<%@include file="NavBar.jsp" %>
		<div class="formCliente">
        	<form method="post" name="registrati" action="./UserControl?action=Registrati" onsubmit="return validate()" enctype="multipart/form-data">
				<div class="form">
			        <div class="title">Benvenuto</div>
			        <% 
						if(Result != null)
						{
					%>
						<div class="subtitle" style="color:red;"><%=Result %></div>
					<%		
						}
						else
						{
					%>
      				<div class="subtitle">Registra il tuo nuovo account</div>
      				<%		
						}
					%>
			      	<div class="input-container">
				        <input id="Email" name="Email" class="input" type="email" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Email" class="placeholder">Email</label>
				    </div>
				    <div class="input-container">
				        <input id="Password" name="Password" class="input" type="password" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Password" class="placeholder">Password</label>
				    </div>
				    <div class="input-container">
				        <input id="Nome" name="Nome" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Nome" class="placeholder">Nome</label>
				    </div>
				    <div class="input-container">
				        <input id="Cognome" name="Cognome" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Cognome" class="placeholder">Cognome</label>
				    </div>
				    <div class="input-container-Foto">
				        Foto: <input id="Immagine" name="Immagine" class="inputFoto" type="file" required/>
				    </div>
				    <div class="input-container">
				        <input id="CF" name="CF" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="CF" class="placeholder">Codice Fiscale</label>
				    </div>
				    <div class="input-container">
				        <input id="Via" name="Via" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Via" class="placeholder">Via</label>
				    </div>
				    <div class="input-container">
				        <input id="Civico" name="Civico" class="input" type="number" placeholder=" " min="1" required/>
				        <div class="cut"></div>
				        <label for="Civico" class="placeholder">Civico</label>
				    </div>
				    <div class="input-container">
					    <select id="Provincia" name="Provincia" class="input" name="Provincia" required>
					        <option value="" selected disabled>Seleziona una provincia</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Provincia" class="placeholder">Provincia</label>
					</div>
					<div class="input-container">
					    <select id="Citta" name="Citta" class="input" name="Citta" disabled required>
					        <option value="" selected disabled>Seleziona una città</option>
					    </select>
					    <div class="cut"></div>
					    <label for="Citta" class="placeholder">Città</label>
					</div>
				    <div class="input-container">
				        <input id="CAP" name="CAP" class="input" type="number" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="CAP" class="placeholder">CAP</label>
				    </div>
				    <div class="input-container">
				        <input id="Telefono" name="Telefono" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Telefono" class="placeholder">Numero Telefono</label>
				    </div>
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        
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
		    	var Email = document.registrati.Email.value;
		    	var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    		  	var passwordInput = document.registrati.Password.value;
    		  	var passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    		  	var nomeInput = document.registrati.Nome.value;
    		  	var str = /^[a-zA-Z]+$/;
    		  	var cognomeInput = document.registrati.Cognome.value;
    		  	var fotoInput = document.registrati.Immagine.value
    		  	var cfInput = document.registrati.CF.value;
    		  	var viaInput = document.registrati.Via.value;
    		  	var civicoInput = document.registrati.Civico.value;
    		  	var provinciaInput = document.registrati.Provincia.value;
    		  	var cittaInput = document.registrati.Citta.value;
    		  	var capInput = document.registrati.CAP.value;
    		  	var telefonoInput = document.registrati.Telefono.value;
    		  	var telefonoRegex = /^\+?\d[0-9 .]{7,12}\d$/
		    	
		    	if (Email === "") 
		    	{
		    		alert("Il campo Email è obbligatorio");
		    		document.registrati.Email.focus();
		    		return false;
		    	}
		    	else if (!emailRegex.test(Email)) 
		    	{
		    		alert("Il campo Email è errato, riprova!");
		    		document.registrati.Email.focus();
		    		return false;
		    	}
		    	else
		    	{
		    		$.ajax({
	    	            url: "./UserControl?action=RicercaEmail", // Replace with the actual URL for checking email in the database
	    	            type: "POST",
	    	            data: { email: Email },
	    	            success: function(response) {
	    	                if (response === "exists") 
	    	                {
	    	                	alert("La mail inserita risulta già registrata");
	    	                    return false;
	    	                } 
	    	            },
	    	            error: function() {
	    	                alert("Si è verificato un errore durante la verifica dell'email");
	    	                return false;
	    	            }
	    	        });
		    	}
    		  	
    		  	
		    	
		    	if (passwordInput === "") 
		    	{
		    		alert("Il campo Password è obbligatorio");
		    		document.registrati.Password.focus();
		    		return false;
		    	}
		    	else if (!passwordRegex.test(passwordInput)) 
		    	{
		    		alert("Il campo Password è errato, deve contenere almeno 8 caratteri" 
		    				+ "di cui almeno uno speciale, una maiuscola, una minuscola e un numero");
		    		document.registrati.Password.focus();
		    		return false;
		    	}
		    	
		    	if (nomeInput === "") 
		    	{
		    		alert("Il campo Nome è obbligatorio");
		    		document.registrati.Nome.focus();
		    		return false;
		    	}
		    	else if (!str.test(nomeInput)) 
		    	{
		    		alert("Il campo Nome è errato, riprova!");
		    		document.registrati.Nome.focus();
		    		return false;
		    	}
		    	
		    	if (cognomeInput === "") 
		    	{
		    		alert("Il campo Cognome è obbligatorio");
		    		document.registrati.Cognome.focus();
		    		return false;
		    	}
		    	else if (!str.test(cognomeInput)) 
		    	{
		    		alert("Il campo Cognome è errato, riprova!");
		    		document.registrati.Cognome.focus();
		    		return false;
		    	}
		    	
		    	if (fotoInput === "") 
		    	{
		    		alert("Il campo Immagine è obbligatorio");
		    		document.registrati.Immagine.focus();
		    		return false;
		    	}
		    	
		    	if (cfInput === "") 
		    	{
		    		alert("Il campo CF è obbligatorio");
		    		document.registrati.CF.focus();
		    		return false;
		    	}
		    	else
		    	{
		    		var validi, i, s, set1, set2, setpari, setdisp;
		    	    cf = cfInput.toUpperCase();
		    	    if (cf.length != 16)
	
		    	        alert("La lunghezza del codice fiscale non è\n"

		    	        + "corretta: il codice fiscale dovrebbe essere lungo\n"

		    	        + "esattamente 16 caratteri.\n");

		    	    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		    	    for (i = 0; i < 16; i++) 
		    	    {

		    	        if (validi.indexOf(cf.charAt(i)) == -1)

		    	        	alert("Il codice fiscale contiene un carattere non valido `" +

		    	                cf.charAt(i) +

		    	                "'.\nI caratteri validi sono le lettere e le cifre.\n");

		    	    }

		    	    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		    	    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";

		    	    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		    	    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";

		    	    s = 0;

		    	    for (i = 1; i <= 13; i += 2)

		    	        s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));

		    	    for (i = 0; i <= 14; i += 2)

		    	        s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));

		    	    if (s % 26 != cf.charCodeAt(15) - 'A'.charCodeAt(0))

		    	    	alert("Il codice fiscale non è corretto in quanto il codice di controllo non corrisponde");
		    	}
		    	
		    	$.ajax({
    	            url: "./UserControl?action=RicercaCF", 
    	            type: "POST",
    	            data: { CF: cfInput },
    	            success: function(response) {
    	                if (response === "exists") 
    	                {
    	                	alert("il CF inserito risulta già registrato");
    	                    return false;
    	                } 
    	            },
    	            error: function() {
    	                alert("Si è verificato un errore durante la verifica del CF");
    	                return false;
    	            }
    	        });

		    		

	    		

		    	
		    	
		    	if (viaInput === "") 
		    	{
		    		alert("Il campo Via è obbligatorio");
		    		document.registrati.Via.focus();
		    		return false;
		    	}
		    	
		    	if (civicoInput === "") 
		    	{
		    		alert("Il campo Civico è obbligatorio");
		    		document.registrati.Civico.focus();
		    		return false;
		    	}
		    	
		    	if (provinciaInput === "") 
		    	{
		    		alert("Il campo Provincia è obbligatorio");
		    		document.registrati.Provincia.focus();
		    		return false;
		    	}
		    	
		    	if (cittaInput === "") 
		    	{
		    		alert("Il campo Citta è obbligatorio");
		    		document.registrati.Citta.focus();
		    		return false;
		    	}
		    	
		    	if (capInput === "") 
		    	{
		    		alert("Il campo CAP è obbligatorio");
		    		document.registrati.CAP.focus();
		    		return false;
		    	}
		    	
		    	if (telefonoInput === "") 
		    	{
		    		alert("Il campo Telefono è obbligatorio");
		    		document.registrati.Telefono.focus();
		    		return false;
		    	}
		    	else if (!telefonoRegex.test(telefonoInput)) 
		    	{
		    		alert("Il campo Telefono è errato, riprova!");
		    		document.registrati.Telefono.focus();
		    		return false;
		    	}

		    	return true;
		    }
		</script>
	</body>
</html>