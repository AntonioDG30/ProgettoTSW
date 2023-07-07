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
		<title>GameCenter Login</title>
		<link href="LayoutSito/css/Login.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/animejs/2.2.0/anime.min.js"></script>
		<link rel="shortcut icon" href="Immagini/favicon.ico" />
	</head>
	<body>
		<%@include file="NavBar.jsp" %>	
		<% 
			if(Email != null)
			{
					response.sendRedirect("./index.jsp");
			}
		%>
		
			
		<div class="page">
	  		<div class="container">
	   			<div class="left">
		     		<div class="login">Login</div>
		      		<div class="eula">
		      			Non sei ancora registrato? <a href="Registrati.jsp">Registrati ora!</a><br>
				        <%
							if(Result != null) 
							{
						%>
								<h3><%=Result%></h3>
						<% 
							}
						%>
					</div>
	    		</div>
	    		<div class="right">
	      			<svg viewBox="0 0 320 300">
	       				<defs>
	       					
				          	<linearGradient
					                         inkscape:collect="always"
					                         id="linearGradient"
					                         x1="13"
					                         y1="193.49992"
					                         x2="307"
					                         y2="193.49992"
					                         gradientUnits="userSpaceOnUse">
					            <stop
					                  style="stop-color:#ff00ff;"
					                  offset="0"
					                  id="stop876" />
					            <stop
					                  style="stop-color:#ff0000;"
					                  offset="1"
					                  id="stop878" />
	         				 </linearGradient>
	        			</defs>
	        			<path d="m 40,120.00016 239.99984,-3.2e-4 c 0,0 24.99263,0.79932 25.00016,35.00016 0.008,34.20084 -25.00016,35 -25.00016,35 h -239.99984 c 0,-0.0205 -25,4.01348 -25,38.5 0,34.48652 25,38.5 25,38.5 h 215 c 0,0 20,-0.99604 20,-25 0,-24.00396 -20,-25 -20,-25 h -190 c 0,0 -20,1.71033 -20,25 0,24.00396 20,25 20,25 h 168.57143" />
	      			</svg>
			        <div class="form">
			      		<form method="post" name="invio" action = "./UserControl?action=Login">
				        	<label for="email">Email</label>
				        	<input type="email" name="email"  id="email">
				        	<label for="password">Password</label>
				        	<input type="password" name="password" id="password">
				        	<input type="submit" id="submit" value="Submit">
			        	</form>
			        </div>
	    		</div>
	  		</div>
		</div>

		<script type="text/javascript">

				// Variabili associate ai campi del modulo
			   	var email = document.invio.email.value;
			   	var password = document.invio.password.value;
				alert("qui");
				// Espressione regolare dell'email
			   	var email_valid = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-]{2,})+.)+([a-zA-Z0-9]{2,})+$/;
			   	if (!email_valid.test(email) || (email == "") || (email == "undefined")) 
			   	{
			      	alert("Devi inserire un indirizzo mail corretto");
			      	document.invio.email.focus();
			      	return false;
			   	}
			  	if (password.length < 4 || (password == "") || (password == "undefined") ) 
				{
			    	alert("Scegli una password, minimo 6 caratteri");
			    	document.invio.password.focus();
			    	return false;
			   	}
			  	else
			  	{
			  		document.invio.action = "./UserControl?action=Login"; 
				    document.invio.submit();
			  	}
		</script>
		<script>
			var current = null;
			document.querySelector('#email').addEventListener('focus', function(e) 
			{
			  	if (current) current.pause();
			  	current = anime
			  	({
				    targets: 'path',
				    strokeDashoffset: 
				    {
				     	value: 0,
				      	duration: 700,
				      	easing: 'easeOutQuart'
				    },
				    strokeDasharray: 
				    {
				      value: '240 1386',
				      duration: 700,
				      easing: 'easeOutQuart'
				    }
			  	});
			});
			
			document.querySelector('#password').addEventListener('focus', function(e) 
			{
			  	if (current) current.pause();
			  	current = anime
			  	({
				    targets: 'path',
				    strokeDashoffset: 
				    {
				      	value: -336,
				      	duration: 700,
				      	easing: 'easeOutQuart'
				    },
				    strokeDasharray: 
				    {
				      	value: '240 1386',
				      	duration: 700,
				      	easing: 'easeOutQuart'
				    }
			  	});
			});
			
			document.querySelector('#submit').addEventListener('focus', function(e) 
			{
			  	if (current) current.pause();
			  	current = anime
			  	({
			    	targets: 'path',
			    	strokeDashoffset: 
			    	{
			      		value: -730,
			      		duration: 700,
			      		easing: 'easeOutQuart'
			   		},
			   		strokeDasharray: 
			   		{
			      		value: '530 1386',
			      		duration: 700,
			      		easing: 'easeOutQuart'
			    	}
			  	});
			});
		</script>

	</body>
</html>