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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Registra dati sesnibili</title>
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
		</style>
	</head>
<body>
	<%@include file="NavBar.jsp" %>
	<% 
		if(Email != null) 
		{
	%>
	<h2><%=Email%>, grazie per esserci registrato su GameCenter</h2>
	<% 
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
	<pre>
		Vuoi completare il tuo account?:
		<a href="./index.jsp"><input type="button" value="No torna alla Homepage"></a>
		<input type="button" value="Si inserisci dati" onclick="showForm('theFormDiv1'); ">
	</pre>
	
	
	<div id="theFormDiv1">
		<form method="post" action="./UserControl?action=RegistraDatiSensibili">
			<pre>
				Inserisci i tuoi dati personali:
				Codice Fiscale: <input type="text" name="CF" placeholder="Inserisci Codice Fiscale" required>	
				Nome: <input type="text" name="Nome" placeholder="Inserisci Nome" required>	
				Cognome: <input type="text" name="Cognome" placeholder="Inserisci Cognome" required>	
				CAP: <input type="number" name="CAP" placeholder="Inserisci CAP" required>
				Città: <input type="text" name="Citta" placeholder="Inserisci Città" required>	
				Provincia: <input type="text" name="Provincia" placeholder="Inserisci Provincia" required>
				Via: <input type="text" name="Via" placeholder="Inserisci Via" required>	
				Civico: <input type="number" name="Civico" placeholder="Inserisci Civico" required>	
				Telefono: <input type="text" name="Telefono" placeholder="Inserisci Telefono" required>
				<input type="hidden" name="email" value="<%=Email%>">			
				<input type="submit" > <input type="reset">
				<input type="button" id="bCancel" name="bCancel" value="Annulla Operazione" onclick="hideForm('theFormDiv1');">
			</pre>
		</form>
	</div>
</body>
</html>