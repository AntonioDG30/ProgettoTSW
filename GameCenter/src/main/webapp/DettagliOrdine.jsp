<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>



<%!	
	String Email="";
	int CodOrdine=0;
    float PrezzoTotale;
    float Prezzo;
    float PrezzoEffettivo; 
    String Fattura="";
    String PrezzoTotaleString="";
    
%>

<%
	CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
	PrezzoEffettivo = Float.parseFloat(request.getParameter("PrezzoEffettivo"));
	Fattura = (String) request.getAttribute("Fattura");
	Collection<?> Ordini = (Collection<?>) request.getAttribute("Ordini");
	PrezzoTotale = 0;
	Prezzo = 0;
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
		<div class="container px-3 my-5 clearfix">
		    <!-- Shopping cart table -->
		    <div class="card">
		        <div class="card-header">
		            <h2>Dettagli Ordine</h2>
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
		                    <th class="text-center py-3 px-4" style="min-width: 300px;">Prodotto</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Piattaforma</th>
		                    <th class="text-right py-3 px-4" style="width: 100px;">Prezzo</th>
		                    <th class="text-center py-3 px-4" style="width: 50px;">Quantità</th>
		                    <th class="text-center py-3 px-4" style="width: 100px;">Totale</th>
		                    <th class="text-center py-3 px-4" style="width: 150px;">Recensisci</th>
		                  </tr>
		                </thead>
		                <tbody>
		        		<% 
			        		Iterator<?> it = Ordini.iterator();
							while (it.hasNext()) 
							{
								ProductBean bean = (ProductBean) it.next();
						%>
		                  <tr>
		                    <td class="p-4">
		                      <div class="media align-items-center">
		                        <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>"><img src="Immagini/<%=bean.getImmagine()%>" class="d-block ui-w-40 ui-bordered mr-4" alt="" width="250px" height="250px"></a>
		                        <div class="media-body">
		                          <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=bean.getCodSeriale()%>" class="d-block text-dark"><%=bean.getNome()%></a>
		                        </div>
		                      </div>
		                    </td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getPiattaforma()%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=bean.getPrezzo()%></td>
		                   	<td class="text-right font-weight-semibold align-middle p-4"><%=bean.getQuantita()%></td>	
							<%
								Prezzo = bean.getPrezzo()*bean.getQuantita();
								/*Tronchiamo float a solo due cifre decimali*/
								Locale.setDefault(Locale.US);
								String PrezzoString = String.format("%.2f", Prezzo);
								Locale.setDefault(Locale.ITALY);
							%>
		                    <td class="text-right font-weight-semibold align-middle p-4"><%=PrezzoString%></td>
		                    <td class="text-right font-weight-semibold align-middle p-4">
		                    	<div class="float-right">
		              				<a href="./Recensione.jsp?Prodotto=<%=bean.getCodSeriale()%>">
		              					<button type="button" class="btn btn-lg btn-primary mt-2">Recensisci</button>
		              				</a>
		            			</div>
		            		</td>
		                    <%PrezzoTotale = PrezzoTotale + Prezzo;%>
		                  </tr>
		                 <% 
						   	}
						   	/*Tronchiamo float a solo due cifre decimali*/
							Locale.setDefault(Locale.US);
							PrezzoTotaleString = String.format("%.2f", PrezzoTotale - PrezzoEffettivo);
							Locale.setDefault(Locale.ITALY);
						%>
		                </tbody>
		              </table>
		            </div>
		            <!-- / Shopping cart table -->
		        	
		            <div class="d-flex flex-wrap justify-content-between align-items-center pb-4">
		              <div class="d-flex">
		             	<div class="text-right mt-4">
		                  <label class="text-muted font-weight-normal m-0">Sconto</label>
		                  <div class="text-large"><strong>-<%=PrezzoTotaleString%></strong></div>
		                </div>
		                <div class="text-right mt-4">
		                  <label class="text-muted font-weight-normal m-0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prezzo Totale</label>
		                  <div class="text-large"><strong>&nbsp;<%=PrezzoEffettivo%></strong></div>
		                </div>
		              </div>
		            </div>
		            
		            				        
		            <div class="float-right">
		              <a href="Fatture/<%=Fattura%>" download="Fattura.pdf"><button type="button" class="btn btn-lg btn-primary mt-2">Download fattura</button></a>
		            </div>
		        	<% 
	
						}
						else
						{
					%>
							<h2>Errore nel reperire le informazioni dell'ordine selezionato</h2>
					<%					
						}
					%>
		          </div>
		      </div>
		  </div>
		  <%@include file="Footer.jsp" %>
	</body>
</html>