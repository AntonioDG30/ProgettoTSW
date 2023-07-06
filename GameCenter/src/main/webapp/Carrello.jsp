<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!	
	String Email="";
	float PrezzoTotale=0;
%>

<%
	CarrelloBean Carrello = null; 

	synchronized(session) 
	{
		session = request.getSession();
	    Email=(String)session.getAttribute("Email");
	    Carrello=(CarrelloBean)session.getAttribute("Carrello");
	}
	
	PrezzoTotale=0;
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
		<% 
			if(Carrello != null && Carrello.getListaCarrello().size() != 0) 
			{ 
		%>
				<div class="container px-3 my-5 clearfix">
				    <!-- Shopping cart table -->
				    <div class="card">
				        <div class="card-header">
				            <h2>Carrello</h2>
				        </div>
				        <div class="card-body">
				            <div class="table-responsive">
				              <table class="table table-bordered m-0">
				                <thead>
				                  <tr>
				                    <!-- Set columns width -->
				                    <th class="text-center py-3 px-4" style="min-width: 300px;">Product Name &amp; Details</th>
				                    <th class="text-right py-3 px-4" style="width: 100px;">Piattaforma</th>
				                    <th class="text-right py-3 px-4" style="width: 100px;">Price</th>
				                    <th class="text-center py-3 px-4" style="width: 100px;">Quantity</th>
				                    <th class="text-right py-3 px-4" style="width: 100px;">Total</th>
				                    <th class="text-center align-middle py-3 px-0" style="width: 40px;"><a href="#" class="shop-tooltip float-none text-light" title="" data-original-title="Clear cart"><i class="ino ion-md-trash"></i></a></th>
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
				                        <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=Prod.getCodSeriale()%>"><img src="Immagini/<%=Prod.getImmagine()%>" class="d-block ui-w-40 ui-bordered mr-4" alt=""></a>
				                        <div class="media-body">
				                          <a href="./GeneralProductControl?action=Dettagli&CodSeriale=<%=Prod.getCodSeriale()%>" class="d-block text-dark"><%=Prod.getNome()%></a>
				                        </div>
				                      </div>
				                    </td>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getPiattaforma()%></td>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getPrezzo()%></td>
				                   	<td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getQuantita()%></td>
				                    <td class="text-right font-weight-semibold align-middle p-4"><%=Prod.getPrezzo()*Prod.getQuantita()%></td>
				                    <td class="text-center align-middle px-0"><a href="#" class="shop-tooltip close float-none text-danger" title="" data-original-title="Remove">×</a></td>
				                  </tr>
				                 <% 
								   	}
								%>
				                </tbody>
				              </table>
				            </div>
				            <!-- / Shopping cart table -->
				        
				            <div class="d-flex flex-wrap justify-content-between align-items-center pb-4">
				              <div class="d-flex">
				                <div class="text-right mt-4 mr-5">
				                  <label class="text-muted font-weight-normal m-0">Discount</label>
				                  <div class="text-large"><strong>$20</strong></div>
				                </div>
				                <div class="text-right mt-4">
				                  <label class="text-muted font-weight-normal m-0">Total price</label>
				                  <div class="text-large"><strong>PREZOOOOOOOO</strong></div>
				                </div>
				              </div>
				            </div>
				        
				            <div class="float-right">
				              <a href="./OrdiniControl?action=Checkout"><button type="button" class="btn btn-lg btn-primary mt-2">Checkout</button></a>
				            </div>
				        
				          </div>
				      </div>
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
		
		
	</body>
</html>