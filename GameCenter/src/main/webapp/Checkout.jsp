<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%!
    String Email = "";
    String Result = "";
    float PuntiFedelta = 0.02f;
    float ScontoMax = 0.02f;
    float PrezzoTotale;
    float ScontoApplicato = 0.0f;
    CarrelloBean Carrello = null;
%>

<%
    PrezzoTotale = 0;
    PuntiFedelta = (int) request.getAttribute("PuntiFedelta");
    Result = (String) request.getAttribute("Result");
    Collection<?> Indirizzi = (Collection<?>) request.getAttribute("Indirizzi");
    Collection<?> MetodiPagamento = (Collection<?>) request.getAttribute("MetodiPagamento");
    synchronized (session) {
        session = request.getSession();
        Email = (String) session.getAttribute("Email");
        Carrello = (CarrelloBean) session.getAttribute("Carrello");
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>GameCenter</title>
    <link rel="stylesheet" href="LayoutSito/css/checkout.css">
</head>
<body>
<%@include file="NavBar.jsp" %>
<form method="post" action="./OrdiniControl?action=Acquista">
<div class='container'>
    <div class='window'>
        <div class='order-info'>
            <div class='order-info-content'>
                <h2>Ordine</h2>
                <div class='line'></div>
                <%
                    if (Carrello != null) {
                        List<ProductBean> ProdottoCarrello = Carrello.getListaCarrello();
                        for (ProductBean Prod : ProdottoCarrello) {
                %>
				
                <table class='order-table'>
                    <tbody>
                    <tr>
                        <td><img src="Immagini/<%=Prod.getImmagine()%>" alt="Immagine non disponibile"
                                 class='full-width'>
                        </td>
                        <td>
                            <br> <span class='thin'><%=Prod.getNome()%></span>
                            <br><span class='thin small'> Piattaforma: <%=Prod.getPiattaforma()%><br></span>
                            <span class='thin small'> Quantità: <%=Prod.getQuantita()%><br><br></span>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class='price'>$<%=(Prod.getPrezzo() * Prod.getQuantita())%></div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class='line'></div>
                <%
                    PrezzoTotale = PrezzoTotale + (Prod.getPrezzo() * Prod.getQuantita());
                }
            }
            /*Tronchiamo float a solo due cifre decimali*/
            Locale.setDefault(Locale.US);
            String PrezzoTotaleString = String.format("%.2f", PrezzoTotale);
            ScontoMax = Math.min(PrezzoTotale*100, PuntiFedelta);
            Locale.setDefault(Locale.ITALY);
            %>

            Punti Fedeltà (max <%=ScontoMax %>):
            <input id="sconto-input" class='input-field' type="number" name="Sconto" min="0" max="<%= ScontoMax %>" required>
            <div id="sconto-info" class='thin dense'></div>
            <div class='line'></div>
            <div class='total'>
                <span style='float:left;'>
                    <div class='thin dense'>Prezzo Totale</div>
                </span>
                <span id="prezzo-totale" style='float:right; text-align:right;'>
                    €<%=PrezzoTotaleString%>
                </span>
            </div>
        </div>
    </div>
    <div class='credit-info'>
        <div class='credit-info-content'>
            <section class="radio-section">
                <div class="radio-list">
                    <h1>Metodo Pagamento</h1>
                    <%
                        if (MetodiPagamento != null && MetodiPagamento.size() != 0) 
                        {
                            Iterator<?> it = MetodiPagamento.iterator();
                            while (it.hasNext()) 
                            {
                                MetodiPagamentoBean bean = (MetodiPagamentoBean) it.next();
                    %>
                    <div class="radio-item">
                        <input name="MetodoScelto" value="<%=bean.getNumeroCarta()%>" id="<%=bean.getNumeroCarta()%>" type="radio" required>
                        <label for="<%=bean.getNumeroCarta()%>">
                            Carta: <%=bean.getNumeroCarta()%>, <%=bean.getTitolareCarta()%>, <%=bean.getScadenza()%>
                        </label>
                    </div>
                    <%
                            }
                     %>     
                        <a href="RegistraMetodoPagamento.jsp" class="link">Vuoi aggiungere un nuovo metodo di pagamento?</a>
                    <%
                        } 
                        else 
                        {
                    %>
                    Nessun Metodo di pagamento memorizzato
                    <%
                        }
                    %>
                </div>
            </section>
            <div class='line'></div>
            <section class="radio-section">
                <div class="radio-list">
                    <h1>Indirizzo di Spedizione:</h1>
                    <%
                        if (Indirizzi != null && Indirizzi.size() != 0) 
                        {
                            Iterator<?> it = Indirizzi.iterator();
                            while (it.hasNext()) 
                            {
                                IndirizziSpedizioneBean bean = (IndirizziSpedizioneBean) it.next();
                    %>
                    <div class="radio-item">
                        <input id="<%=bean.getCodIndirizzo()%>" name="IndirizzoScelto" value="<%=bean.getCodIndirizzo()%>" type="radio" required>
                        <label for="<%=bean.getCodIndirizzo()%>">
                            <%=bean.getNome()%> <%=bean.getCognome()%>, Via <%=bean.getVia()%>, <%=bean.getCivico()%>,
                            <%=bean.getCitta()%>, <%=bean.getCAP()%>
                        </label>
                    </div>
                    <%
                            }
                   	%>             
                        <a href="RegistraIndirizzo.jsp" class="link">Vuoi aggiungere un nuovo indirizzo di spedizione?</a>
                    <%
                        }
                        else 
                        {
                    %>
                    Nessun Indirizzo Memorizzato
                    <%
                        }
                    %>
                </div>
            </section>
            <input type="hidden" name="PrezzoTotale" value="<%=PrezzoTotaleString%>">
            <button type="submit" class='pay-btn'>Checkout</button>

        </div>
</div>
    </div>
</div>
</form>

<script>
    // Funzione per calcolare il prezzo totale scontato
    function calcolaPrezzoScontato() {
        var prezzoTotale = parseFloat(<%=PrezzoTotale%>);
        var sconto = parseFloat(document.getElementById("sconto-input").value/100);
        var scontoMax = parseFloat(<%=ScontoMax%>);
        var scontoApplicato = Math.min(sconto, scontoMax);
        var prezzoScontato = prezzoTotale - scontoApplicato;
        document.getElementById("sconto-info").innerHTML = "Sconto applicato: €" + scontoApplicato.toFixed(2);
        document.getElementById("prezzo-totale").innerHTML = "€" + prezzoScontato.toFixed(2);
    }

    // Event listener per il cambio del valore di sconto
    document.getElementById("sconto-input").addEventListener("input", calcolaPrezzoScontato);
</script>
</body>
</html>
