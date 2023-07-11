<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
	<head>	
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>GameCenter</title>
		<link href="LayoutSito/css/catalogoBar.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<nav role="navigation" class="sottoNav" aria-label="navigation">
		    <ul class="sottoNav-items">
		        <li class="sottoNav-item">
		            <a href="index.jsp" class="sottoNav-link"><span>Home</span></a>
		        </li>
		        <li class="sottoNav-item">
		            <a href="Catalogo.jsp" class="sottoNav-link"><span>Catalogo</span></a>
		        </li> 
		        <li class="sottoNav-item dropdown">
		            <a href="#" class="sottoNav-link"><span>Categorie</span></a>
		            <nav class="sottoMenu" aria-label="sottoMenu">
		                <ul class="sottoMenu-items">
		                    <li class="sottoMenu-item"><a href="#" class="sottoMenu-link">Visualizza tutti i clienti</a></li>
		                    <li class="sottoMenu-item"><a href="#" class="sottoMenu-link">Visualizza un determinato cliente</a></li>
		                </ul>
		            </nav>
		        </li>  
		    </ul>
		</nav>

		<script>
			[].slice.call(document.querySelectorAll('.dropdown .sottoNav-link')).forEach(function(el)
			{
			    el.addEventListener('click', onClick, false);
			});
			
			function onClick(e)
			{
			    e.preventDefault();
			    var el = this.parentNode;
			    el.classList.contains('show-sottoMenu') ? hideSubMenu(el) : showSubMenu(el);
			}
			
			function showSubMenu(el)
			{
			    el.classList.add('show-sottoMenu');
			   
			}
			
			function hideSubMenu(el){
			    el.classList.remove('show-sottoMenu');
			}
		</script>
	</body>
</html>
