package control;

import model.*;

import java.io.IOException;
import java.io.File;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;





@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
	static OrdineModel ordineModel = new OrdineModel();
	static UserModel userModel = new UserModel();   
	
	
	Logger logger = Logger.getLogger(OrdiniControl.class.getName());
	
    
    public OrdiniControl() 
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		try
		{
			if(action != null) 
			{	
				if (action.equalsIgnoreCase("Dettagli")) 
				{
					int codOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
					float prezzoEffettivo = Float.parseFloat(request.getParameter("PrezzoEffettivo"));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", ordineModel.dettagliOrdine(codOrdine));
					request.removeAttribute("PrezzoEffettivo");
					request.setAttribute("PrezzoEffettivo", prezzoEffettivo);
					request.removeAttribute("Fattura");
					request.setAttribute("Fattura", ordineModel.ricercaFattura(codOrdine));

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliOrdine.jsp");
					dispatcher.forward(request, response);	
				}
				
				if (action.equalsIgnoreCase("Recensioni")) 
				{
					request.removeAttribute("codSeriale");
					request.setAttribute("codSeriale", request.getParameter("Prodotto"));					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Recensione.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				if (action.equalsIgnoreCase("Recensione")) 
				{
					String email=(String) request.getSession().getAttribute("Email");
					String codProdotto = request.getParameter("CodProdotto");
					int valutazione = Integer.parseInt(request.getParameter("Valutazione"));
					String descrizione = request.getParameter("Descrizione");
					
					request.removeAttribute("Result");
					if(ordineModel.recensione(valutazione, descrizione, codProdotto, email))
					{
						request.setAttribute("Result", "Recensione inserita Correttamente");
					    request.removeAttribute("PuntiFedelta");
						request.setAttribute("PuntiFedelta", UserModel.getPuntiFedelta(email));
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
						dispatcher.forward(request, response);						
					}
					else
					{
						request.setAttribute("Result", "Recensione non inserita. Riprova!");
						request.removeAttribute("CodProdotto");
						request.setAttribute("CodProdotto", codProdotto);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Recensione.jsp");
						dispatcher.forward(request, response);
					}
						
				}
				
				if (action.equalsIgnoreCase("Checkout")) 
				{
					if (request.getSession().getAttribute("Email") != null)
					{
						String email=(String) request.getSession().getAttribute("Email");
						CarrelloBean carrello=(CarrelloBean) request.getSession().getAttribute("Carrello");
						request.removeAttribute("PuntiFedelta");
						request.setAttribute("PuntiFedelta", UserModel.getPuntiFedelta(email));
						request.removeAttribute("Indirizzi");
						request.setAttribute("Indirizzi", userModel.getIndirizziSpedizione(email));
						request.removeAttribute("MetodiPagamento");
						request.setAttribute("MetodiPagamento", userModel.getMetodiPagamento(email));
						request.removeAttribute("Prodotti");
						request.setAttribute("Prodotti", carrello);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Checkout.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						response.sendRedirect("./Login.jsp");
					}
				}
				
				if (action.equalsIgnoreCase("Acquista")) 
				{
					if (request.getSession().getAttribute("Email") != null)
					{
						String email=(String) request.getSession().getAttribute("Email");
						CarrelloBean carrello=(CarrelloBean) request.getSession().getAttribute("Carrello");
						float prezzoTotale =  Float.parseFloat(request.getParameter("PrezzoTotale"));
						float puntiFedeltaUsati = Float.parseFloat(request.getParameter("Sconto"));						
						
						request.removeAttribute("Result");
						int codOrdine = ordineModel.acquisto(carrello, prezzoTotale, puntiFedeltaUsati, email);
						if(codOrdine != 0)
						{
							int codIndirizzo = Integer.parseInt(request.getParameter("IndirizzoScelto"));
							String numeroCarta = request.getParameter("MetodoScelto");
							ordineModel.updateComprende(codOrdine, codIndirizzo, numeroCarta);
							request.removeAttribute("PuntiFedelta");
							request.setAttribute("PuntiFedelta", UserModel.getPuntiFedelta(email));
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
							request.setAttribute("Result", "Grazie per aver acquistato sul nostro sito");
							generaFattura(codOrdine, puntiFedeltaUsati, email, request);
							ordineModel.updateFattura(codOrdine);
							request.getSession().setAttribute("Carrello", null);
						}
						else
						{
							request.setAttribute("Result", "Errore imprevisto, riprova.");
						}
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						response.sendRedirect("./Login.jsp");
					}
				}
				
				if (action.equalsIgnoreCase("VisualizzaOrdini")) 
				{
					if(request.getParameter("Visual") != null && request.getParameter("Visual").contentEquals("tutti"))
					{
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", ordineModel.elencoOrdini());
					}
					else if (request.getParameter("Visual") != null && (request.getParameter("Visual").contentEquals("cliente") || request.getParameter("Visual").contentEquals("periodo")))
					{
						request.removeAttribute("Visual");
						request.setAttribute("Visual", request.getParameter("Visual"));
					}
					if (request.getParameter("email") != null )
					{
						String email = request.getParameter("email");
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
					}
					else if (request.getParameter("DataInizio") != null &&
							request.getParameter("DataFine") != null)
					{
						String dataInizio = request.getParameter("DataInizio");
						String dataFine = request.getParameter("DataFine");
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", ordineModel.elencoOrdiniByPeriodo(dataInizio, dataFine));
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzazioneOrdini.jsp");
					dispatcher.forward(request, response);	
				}

			}
			else
			{
			    String email=(String) request.getSession().getAttribute("Email");
			    if(email == null)
			    {
			    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
					dispatcher.forward(request, response);
			    }
			    else
			    {
			    	request.removeAttribute("PuntiFedelta");
					request.setAttribute("PuntiFedelta", UserModel.getPuntiFedelta(email));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
					dispatcher.forward(request, response);
			    }    
			}
		}
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		
	}

	private void generaFattura(int codOrdine, float puntiFedeltaUsati, String email, HttpServletRequest request) throws IOException 
	{
		
		float x=0;
		float y=0;
		int limit = 31;
		int numProd = 0;
		
		try 
		{
			String servletPath = request.getServletContext().getRealPath("");
			String totalPath = servletPath + File.separator  + "Fatture" + File.separator  + "Fattura" + codOrdine + ".pdf";
			Float subTotale = 0.02f;
		
			String pdf = ordineModel.ricercaFattura(codOrdine);
		
		
		
			if(pdf == null)
			{
			    File file = new File(servletPath + File.separator  + "TemplateFattura.pdf");
			    PDDocument fattura = PDDocument.load(file);    
			    PDPage page = (PDPage)fattura.getDocumentCatalog().getPages().get(0);											
			    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
			    PDType1Font font = PDType1Font.TIMES_ROMAN;
			    
			    
			    
			    //Inserimento numero fattura
			    x = 450;
			    y = 768.55f;
		 	    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Integer.toString(codOrdine)); 
			    contentStream.endText();
	
			    
			    //inserimento data fattura
			    OrdineBean ordine = ordineModel.ordineByCodOrdine(codOrdine);
			    y = 754;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(ordine.getDataAcquisto()); 
			    contentStream.endText();
			    
			    
			    //inserimento Dati Utente
			    UserBean utente = userModel.ricercaDatiSensibili(email); 
			    
			    
			    x = 395;
			    y = 710;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(utente.getNome() + " " + utente.getCognome()); 
			    contentStream.endText();
			    
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(utente.getVia() + ", " + utente.getCivico()); 
			    contentStream.endText();
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(utente.getCitta() + ", (" + utente.getProvincia() + "), " + utente.getCAP()); 
			    contentStream.endText();
			    
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(email); 
			    contentStream.endText();
			    
			    //inserimento prodotti
			    y = 604;
			    Collection<?> ordini =ordineModel.dettagliOrdine(codOrdine);
			    if (ordini != null && (!(ordini.isEmpty()))) 
				{
					Iterator<?> it = ordini.iterator();
					while (it.hasNext()) 
					{
						numProd++;
						if(numProd > limit )
			    	    {
			    	    	file = new File(servletPath + File.separator  + "Template_Page2.pdf");
				    		page = (PDPage)PDDocument.load(file).getDocumentCatalog().getPages().get(0);
				    		
				    		fattura.addPage(page);
				    		
				 		    contentStream.close();
				    		
				    		contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
			  	    	  	y = 752; 
			  	    	  	numProd = 1;
			  	    	  	limit = 40; 
			    	    }
						x = 83;
						ProductBean bean = (ProductBean) it.next();
						contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Integer.toString(bean.getQuantita())); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    x = 124;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(bean.getNome()); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    x = 390;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Float.toString(bean.getPrezzo())); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    float prezzoTotRiga = bean.getPrezzo() * bean.getQuantita();
			    	    x = 485;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Float.toString(prezzoTotRiga)); 
			    	    contentStream.endText();
			    	    subTotale = subTotale + prezzoTotRiga;
			    	    y = y - 16.42f;  	   			    	    
					}
					
				}
			    
			    contentStream.addRect(448.7f, 89, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    
			    
			    
			    x = 451;
			    y = 94;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y);
			    contentStream.showText("subTotale: " + subTotale); 
			    contentStream.endText();
			    
			    
			    
			    contentStream.addRect(448.7f, 72.5f, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 77.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Sconto: -" + (puntiFedeltaUsati/100)); 
			    contentStream.endText();
			    
			    Locale.setDefault(Locale.US);
				float totale = subTotale -  (puntiFedeltaUsati/100);
			    String totaleS = String.format("%.2f", totale);
			    
			    contentStream.addRect(448.7f, 56, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 61;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Totale: " + totaleS); 
			    contentStream.endText();
			    
			    String iva = String.format("%.2f", (totale * 0.22f));
			    Locale.setDefault(Locale.ITALY);
			    
			    contentStream.addRect(448.7f, 39.5f, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 44.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Di cui IVA: " + iva); 
			    contentStream.endText();
			    
			    
			    contentStream.close();
			    fattura.save(totalPath);
			    fattura.close();

			}
		}
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
