<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
%>

<%
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
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
		<title>GameCenter</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		
				<div class="container px-3 my-5 clearfix">
				    <!-- Shopping cart table -->
				    <div class="card">
				        <div class="card-header">
				            <h2>Visualizzazione Ordini</h2>
				        </div>
				        
				        
				        <% 
					        if (Ordini != null && Ordini.size() != 0) 
							{ 
						%>
				        <div class="card-body">
				            <div class="table-responsive">
				              <table class="table table-bordered m-0">
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
				                    		<a href="OrdiniControl?action=Dettagli&CodOrdine=<%=bean.getCodOrdine()%>">
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