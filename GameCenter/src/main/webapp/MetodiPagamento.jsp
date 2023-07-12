<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	String Result="";
%>

<%
	Result = (String) request.getAttribute("Result");
	Collection<?> MetodiPagamento = (Collection<?>) request.getAttribute("MetodiPagamento");
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
		<title>GameCenter</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		<div class="container px-3 my-5 clearfix" style="min-height:600px;">
		    <!-- Shopping cart table -->
		    <div class="card">
		        <div class="card-header">
		            <h2>Indirizzi di spedizione memorizzati</h2>
		        </div>
		        <%
			        if (MetodiPagamento != null && MetodiPagamento.size() != 0) 
					{
						
				%>
		        <div class="card-body">
		            <div class="table-responsive">
		              <table class="table table-bordered m-0">
		              	<caption></caption>
		                <thead>
		                  <tr>
		                    <!-- Set columns width -->
		                    <th class="text-center py-3 px-4" style="width: 200px;">Numero carta</th>
		                    <th class="text-right py-3 px-4" style="width: 200px;">Titolare carta</th>
		                    <th class="text-center py-3 px-4" style="width: 200px;">Scadenza carta</th>
		                    <th class="text-center py-3 px-4" style="width: 200px;">Elimina</th>
		                  </tr>
		                </thead>
		                <tbody>
		        		<% 
			        		Iterator<?> it = MetodiPagamento.iterator();
							while (it.hasNext()) 
							{
								MetodiPagamentoBean bean = (MetodiPagamentoBean) it.next();
						%>
		                  <tr>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getNumeroCarta()%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getTitolareCarta()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getScadenza()%></td>	
		                    <td class="text-right font-weight-semibold align-middle p-4">
		                    	<div class="float-right">
		              				<a href="">
		              					<button type="button" class="btn btn-lg btn-primary mt-2">Elimina</button>
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
		            				        
		            <div class="float-right">
		              <a href=""><button type="button" class="btn btn-lg btn-primary mt-2">Aggiungi nuovo metodo</button></a>
		            </div>
		        	<% 
	
						}
						else
						{
					%>
							<h2>Errore nel reperire le informazioni dell'utente selezionato</h2>
					<%					
						}
					%>
		          </div>
		      </div>
		  </div>
		  <%@include file="Footer.jsp" %>
	</body>
</html>