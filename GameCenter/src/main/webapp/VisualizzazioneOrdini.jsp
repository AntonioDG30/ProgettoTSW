<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	String Visual="";
%>

<%
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
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
        	<form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
				<div class="form">
			      <div class="subtitle">Inserisci l'Email del cliente di cui visualizzare gli ordini:</div>
			      <div class="input-container ic1">
			      	<div class="subtitle">Email: 
			        	<input type="email" name="email" placeholder="Inserisci Email cliente" required>	
			        </div>
			      </div>
			      <button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
	        else if (Visual != null && Visual.contentEquals("periodo")) 
			{ 
		%>
        <div class="formPeriodo">
	        <form method="post" action="./OrdiniControl?action=VisualizzaOrdini">
		        <div class="form">
			      <div class="subtitle">Inserisci il periodo di cui visualizzare gli ordini:</div>
			      <div class="input-container ic1">
			      	<div class="subtitle">Data inizio: 
			        	<input type="date" name="DataInizio" placeholder="Inserisci Data inizio periodo" required>	
			        </div>
			      </div>
			      <div class="input-container ic2">
			      	<div class="subtitle">Data fine: 
			        	<input type="date" name="DataFine" placeholder="Inserisci Data fine periodo" required>
			        </div>
			      </div>
			      <button type="submit" class="submit">submit</button>
			    </div>
			</form>
        </div>
        <% 
			}
	        else if (Ordini != null && Ordini.size() != 0) 
			{
		%>
		<div class="container px-3 my-5 clearfix">
		    <div class="card">
		        <div class="card-header">
		            <h2>Visualizzazione Ordini</h2>
		        </div>
		        <div class="card-body">
		            <div class="table-responsive">
		              <table class="table table-bordered m-0">
		              	<caption></caption>
		                <thead>
		                  <tr>
		                    <!-- Set columns width -->
		                    <th class="text-center py-3 px-4" style="width: 200px;">Cliente</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Data Acquisto</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Prezzo</th>
		                    <th class="text-center py-3 px-4" style="width: 100px;">Stato Ordine</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Operazioni</th>
		                  </tr>
		                </thead>
		                <tbody>
		        		<% 
			        		Iterator<?> it = Ordini.iterator();
							while (it.hasNext()) 
							{
								OrdineBean bean = (OrdineBean) it.next();
						%>
		                  <tr>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getEmail()%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getDataAcquisto()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getPrezzoTotale()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getStatoOrdine()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4">
		                    	<div class="float-right">
		                    		<a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>&PrezzoEffettivo=<%=bean.getPrezzoTotale()%>">
		                    			<button type="button" class="btn btn-lg btn-primary mt-2">Dettagli</button>
		                  			</a>
		                  		</div>
		                   	</td>

		                  </tr>
		                 <% 
						   	}
						%>
		                </tbody>
		              </table>
		            </div>
		        	<% 
	
						}
						else
						{
					%>
							<h2>Nessun ordine trovato</h2>
					<%					
						}
					%>
		          </div>
		      </div>
		  </div>
	
		
		
	</body>
</html>