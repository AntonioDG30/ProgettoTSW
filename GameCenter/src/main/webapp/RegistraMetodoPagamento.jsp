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
		<div class="formMetodiPagamento">
        	<form method="post" action="./UserControl?action=NuovoMetodoPagamento" name="metodi" onsubmit="return validate()">
				<div class="form">
      				<div class="subtitle">Registra nuovo metodo di pagamento</div>
			      	<div class="input-container ic1">
				        <input id="NumeroCarta" name="NumeroCarta" class="input" type="number" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="NumeroCarta" class="placeholder">Numero Carta</label>
				    </div>
				    <div class="input-container">
				        <input id="Titolare" name="Titolare" class="input" type="text" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="Titolare" class="placeholder">Titolare Carta</label>
				    </div>
				    <div class="input-container">
				        <input id="DataScadenza" name="DataScadenza" class="input" type="date" placeholder=" " required/>
				        <div class="cut"></div>
				        <label for="DataScadenza" class="placeholder">Data Scadenza Carta</label>
				    </div>
			      	<button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <%@include file="Footer.jsp" %>
        
        <script>
        function validate() 
	    {		    	
		  	var numeroCartaInput = document.metodi.NumeroCarta.value;
		  	var titolareInput = document.metodi.Titolare.value;
		  	var dataScadenzaInput = document.metodi.DataScadenza.value;
		  	var str = /^[a-zA-Z]+$/;
	    	
	    	
	    	
	    	if (numeroCartaInput === "") 
	    	{
	    		alert("Il campo NumeroCarta è obbligatorio");
	    		document.metodi.NumeroCarta.focus();
	    		return false;
	    	}
	    	else if(numeroCartaInput.length !== 16)
	    	{
	    		alert("Il campo NumeroCarta è errato");
	    		document.metodi.NumeroCarta.focus();
	    		return false;
	    	}
	    	
	    	if (titolareInput === "") 
	    	{
	    		alert("Il campo Titolare è obbligatorio");
	    		document.metodi.Titolare.focus();
	    		return false;
	    	}
	    	else if (!str.test(titolareInput)) 
	    	{
	    		alert("Il campo Titolare è errato, riprova!");
	    		document.registrati.Titolare.focus();
	    		return false;
	    	}

	    	if (dataScadenzaInput === "") 
	    	{
	    		alert("Il campo DataScadenza è obbligatorio");
	    		document.metodi.DataScadenza.focus();
	    		return false;
	    	}
	    	else
	    	{
	    		
	    		let today = new Date();
	    		let selectedDate = new Date(dataScadenzaInput);
				if (isNaN(selectedDate.getTime())) 
				{
					alert("Il campo DataScadenza è errato, riprova!");
	    		    return false; 
	    		}
				if (selectedDate < today) 
				{
					alert("la Data di Scadenza non può essere passata, riprova!");
	    		    return false;
	    		}
	    	}

	    	return true;
	    }
        </script>
	</body>
</html>