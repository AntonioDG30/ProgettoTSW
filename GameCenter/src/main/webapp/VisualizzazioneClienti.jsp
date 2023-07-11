<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	String Visual="";
%>

<%
	Collection<?> Clienti = (Collection<?>) request.getAttribute("Clienti");
	UserBean Cliente = (UserBean) request.getAttribute("Cliente");
	Visual = (String) request.getAttribute("Visual");
	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	}	
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<link href="LayoutSito/css/formAdmin.css" rel="stylesheet" type="text/css">
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
        <% 
	        if (Visual != null && Visual.contentEquals("cliente")) 
			{ 
		%>
        <div class="formCliente">
        	<form method="post" action="./UserControl?action=VisualizzaUtenti">
				<div class="form">
			      <div class="subtitle">Inserisci l'Email del cliente di cui visualizzare i dati:</div>
			      <div class="input-container ic1">
			      	<div class="subtitle">Email: 
			        	<input type="email" name="email" placeholder="Inserisci Email cliente" required>	
			        </div>
			      </div>
			      <button type="text" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
	        else if (Clienti != null && Clienti.size() != 0) 
			{
		%>
		<div class="container px-3 my-5 clearfix">
		    <div class="card">
		        <div class="card-header">
		            <h2>Visualizzazione Clienti</h2>
		        </div>
		        <div class="card-body">
		            <div class="table-responsive">
		              <table class="table table-bordered m-0">
		              	<caption></caption>
		                <thead>
		                  <tr>
		                    <!-- Set columns width -->
		                    <th class="text-center py-3 px-4" style="width: 100px;">Nome e Cognome</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Email</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Codice Fiscale</th>
		                    <th class="text-center py-3 px-4" style="width: 250px;">Indirizzo</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Numero Telefono</th>
		                  </tr>
		                </thead>
		                <tbody>
		        		<% 
			        		Iterator<?> it = Clienti.iterator();
							while (it.hasNext()) 
							{
								UserBean bean = (UserBean) it.next();
						%>
		                  <tr>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getNome()%> <%=bean.getCognome()%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getEmail()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getCodiceFiscale()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getVia()%> <%=bean.getCivico()%>, <%=bean.getCitta()%> (<%=bean.getProvincia()%>), <%=bean.getCAP()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getNumeroTelefono()%></td>
		                  </tr>
		                 <% 
						   	}
						%>
		                </tbody>
		              </table>
		            </div>
		          </div>
		      </div>
		  </div>
		  <% 
				}
		        else if (Cliente != null) 
				{
		   %>
		   <div class="container px-3 my-5 clearfix">
		    <div class="card">
		        <div class="card-header">
		            <h2>Visualizzazione Cliente</h2>
		        </div>
		        <div class="card-body">
		            <div class="table-responsive">
		              <table class="table table-bordered m-0">
		              	<caption></caption>
		                <thead>
		                  <tr>
		                    <!-- Set columns width -->
		                    <th class="text-center py-3 px-4" style="width: 100px;">Nome e Cognome</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Email</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Codice Fiscale</th>
		                    <th class="text-center py-3 px-4" style="width: 250px;">Indirizzo</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Numero Telefono</th>
		                  </tr>
		                </thead>
		                <tbody>
		                  <tr>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=Cliente.getNome()%> <%=Cliente.getCognome()%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=Cliente.getEmail()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=Cliente.getCodiceFiscale()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=Cliente.getVia()%> <%=Cliente.getCivico()%>, <%=Cliente.getCitta()%> (<%=Cliente.getProvincia()%>), <%=Cliente.getCAP()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=Cliente.getNumeroTelefono()%></td>
		                  </tr>
		                </tbody>
		              </table>
		            </div>
		        	<% 
	
						}
						else
						{
					%>
							<h2>Nessun cliente trovato</h2>
					<%					
						}
					%>
		          </div>
		      </div>
		  </div>
	
		
		
	</body>
</html>