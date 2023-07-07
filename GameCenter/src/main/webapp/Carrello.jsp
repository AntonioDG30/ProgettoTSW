<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	float PrezzoTotale=0;
	float Prezzo=0;
%>

<%
	CarrelloBean Carrello = null; 

	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Carrello=(CarrelloBean)session.getAttribute("Carrello");
	}	
%>
<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<title>Carrello</title>
	</head>
	<body>
		<%@include file="NavBar.jsp" %>
		
				<div class="container px-3 my-5 clearfix">
				    <!-- Shopping cart table -->
				    <div class="card">
				        <div class="card-header">
				            <h2>Carrello</h2>
				        </div>
				        <% 
							if(Carrello != null && Carrello.getListaCarrello().size() != 0) 
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
				                    <th class="text-center py-3 px-4" style="width: 100px;">Quantità</th>
				                    <th class="text-center align-middle py-3 px-0" style="width: 100px;"><i class="ino ion-md-trash">Modifica quantità</i></th>
				                    <th class="text-right py-3 px-4" style="width: 100px;">Totale</th>
				                  </tr>
				                </thead>
				                <tbody>
				        		<% 
									List<ProductBean> ProdottoCarrello = Carrello.getListaCarrello(); 	
								   	for(ProductBean Prod: ProdottoCarrello) 
								   	{
								%>
				                  <tr>
				                    <td class="p-4">
				                      <div class="media align-items-center">
				                        <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=Prod.getCodSeriale()%>"><img src="Immagini/<%=Prod.getImmagine()%>" class="d-block ui-w-40 ui-bordered mr-4" alt="" width="250px" height="250px"></a>
				                        <div class="media-body">
				                          <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=Prod.getCodSeriale()%>" class="d-block text-dark"><%=Prod.getNome()%></a>
				                        </div>
				                      </div>
				                    </td>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getPiattaforma()%></td>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getPrezzo()%></td>
				                   	<td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getQuantita()%></td>
				                   	<td class="text-right font-weight-semibold align-middle p-4">
				                   		<a href="GeneralProductControl?action=AggiungiCarrello&CodSeriale=<%=Prod.getCodSeriale()%>&piattaforma=<%=Prod.getPiattaforma()%>" class="shop-tooltip close float-none text-danger" title="" data-original-title="Remove">	
				                   			<img src="LayoutSito/img/piu.png" class="d-block ui-w-40 ui-bordered mr-4" alt="" width="25px" height="25px">
										</a>
										<a href="GeneralProductControl?action=RimuoviCarrello&CodSeriale=<%=Prod.getCodSeriale()%>" class="shop-tooltip close float-none text-danger" title="" data-original-title="Remove">	
				                   			<img src="LayoutSito/img/meno.png" class="d-block ui-w-40 ui-bordered mr-4" alt="" width="25px" height="25px">
										</a>
									</td>	
									<%
										Prezzo = Prod.getPrezzo()*Prod.getQuantita();
										/*Tronchiamo float a solo due cifre decimali*/
										Locale.setDefault(Locale.US);
										String PrezzoeString = String.format("%.2f", Prezzo);
										Locale.setDefault(Locale.ITALY);
									%>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=PrezzoeString%></td>
				                    <%PrezzoTotale = PrezzoTotale + Prezzo;%>
				                  </tr>
				                 <% 
								   	}
								   	/*Tronchiamo float a solo due cifre decimali*/
									Locale.setDefault(Locale.US);
									String PrezzoTotaleString = String.format("%.2f", PrezzoTotale);
									Locale.setDefault(Locale.ITALY);
								%>
				                </tbody>
				              </table>
				            </div>
				            <!-- / Shopping cart table -->
				        	
				            <div class="d-flex flex-wrap justify-content-between align-items-center pb-4">
				              <div class="d-flex">
				                <div class="text-right mt-4">
				                  <label class="text-muted font-weight-normal m-0">Prezzo Totale</label>
				                  <div class="text-large"><strong><%=PrezzoTotaleString%></strong></div>
				                </div>
				              </div>
				            </div>
				        
				            <div class="float-right">
				              <a href="./OrdiniControl?action=Checkout"><button type="button" class="btn btn-lg btn-primary mt-2">Checkout</button></a>
				            </div>
				        	<% 
			
								}
								else
								{
							%>
									<h2>Ancora nessun prodotto inserito nel carrello</h2>
							<%					
								}
							%>
				          </div>
				      </div>
				  </div>
	
		
		
	</body>
</html>