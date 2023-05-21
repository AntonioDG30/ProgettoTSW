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
	
	
	static OrdineModel Omodel = new OrdineModel();
	static ProductModel Pmodel = new ProductModel();
	static UserModel Umodel = new UserModel();   
	
	
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
					int CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
					float PrezzoEffettivo = Float.parseFloat(request.getParameter("PrezzoEffettivo"));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", Omodel.DettagliOrdine(CodOrdine));
					request.removeAttribute("PrezzoEffettivo");
					request.setAttribute("PrezzoEffettivo", PrezzoEffettivo);
					request.removeAttribute("Fattura");
					request.setAttribute("Fattura", Omodel.RicercaFattura(CodOrdine));

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliOrdine.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				if (action.equalsIgnoreCase("Recensione")) 
				{
					String Email=(String) request.getSession().getAttribute("Email");
					String CodProdotto = request.getParameter("CodProdotto");
					int Valutazione = Integer.parseInt(request.getParameter("Valutazione"));
					String Descrizione = request.getParameter("Descrizione");
					
					request.removeAttribute("Result");
					if(Omodel.Recensione(Valutazione, Descrizione, CodProdotto, Email))
					{
						request.setAttribute("Result", "Recensione inserita Correttamente");
					    request.removeAttribute("PuntiFedelta");
						request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
						dispatcher.forward(request, response);						
					}
					else
					{
						request.setAttribute("Result", "Recensione non inserita. Riprova!");
						request.removeAttribute("CodProdotto");
						request.setAttribute("CodProdotto", CodProdotto);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Recensione.jsp");
						dispatcher.forward(request, response);
					}
						
				}
				
				if (action.equalsIgnoreCase("Checkout")) 
				{
					if (request.getSession().getAttribute("Email") != null)
					{
						String Email=(String) request.getSession().getAttribute("Email");
						CarrelloBean Carrello=(CarrelloBean) request.getSession().getAttribute("Carrello");
						request.removeAttribute("PuntiFedelta");
						request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
						request.removeAttribute("Indirizzi");
						request.setAttribute("Indirizzi", Umodel.getIndirizziSpedizione(Email));
						request.removeAttribute("MetodiPagamento");
						request.setAttribute("MetodiPagamento", Umodel.getMetodiPagamento(Email));
						request.removeAttribute("Prodotti");
						request.setAttribute("Prodotti", Carrello);
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
						String Email=(String) request.getSession().getAttribute("Email");
						CarrelloBean Carrello=(CarrelloBean) request.getSession().getAttribute("Carrello");
						float PrezzoTotale =  Float.parseFloat(request.getParameter("PrezzoTotale"));
						float PuntiFedeltaUsati = Float.parseFloat(request.getParameter("Sconto"));						
						
						request.removeAttribute("Result");
						int CodOrdine = Omodel.Acquisto(Carrello, PrezzoTotale, PuntiFedeltaUsati, Email);
						if(CodOrdine != 0)
						{
							int codIndirizzo = Integer.parseInt(request.getParameter("IndirizzoScelto"));
							String NumeroCarta = request.getParameter("MetodoScelto");
							Omodel.UpdateComprende(CodOrdine, codIndirizzo, NumeroCarta);
							request.removeAttribute("PuntiFedelta");
							request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
							request.setAttribute("Result", "Grazie per aver acquistato sul nostro sito");
							GeneraFattura(CodOrdine, PrezzoTotale, PuntiFedeltaUsati, Email);
							Omodel.UpdateFattura(CodOrdine);
							request.getSession().setAttribute("Carrello", null);
						}
						else
						{
							request.setAttribute("Result", "Errore imprevisto, riprova.");
						}
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						response.sendRedirect("./Login.jsp");
					}
				}
				
				
				if (action.equalsIgnoreCase("VisualizzaOrdini")) 
				{
					if(request.getParameter("ParteMod").contentEquals("Parte1"))
					{
						String Visualizzazione = request.getParameter("VisualizzazioneOrdini");
						if(Visualizzazione.contentEquals("Tutti"))
						{
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Omodel.ElencoOrdini());
						}
						else if (Visualizzazione.contentEquals("Cliente"))
						{
							request.removeAttribute("Visual");
							request.setAttribute("Visual", Visualizzazione);
						}
						else if (Visualizzazione.contentEquals("Periodo"))
						{
							request.removeAttribute("Visual");
							request.setAttribute("Visual", Visualizzazione);
						}
					}
					else if(request.getParameter("ParteMod").contentEquals("Parte2"))
					{

						if (request.getParameter("email") != null )
						{
							String Email = request.getParameter("email");
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
						}
						else if (request.getParameter("DataInizio") != null &&
								request.getParameter("DataFine") != null)
						{
							String DataInizio = request.getParameter("DataInizio");
							String DataFine = request.getParameter("DataFine");
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Omodel.ElencoOrdiniByPeriodo(DataInizio, DataFine));
						}
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
					dispatcher.forward(request, response);	
				}
			}
			else
			{
			    String Email=(String) request.getSession().getAttribute("Email");
			    request.removeAttribute("PuntiFedelta");
				request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
				request.removeAttribute("Ordini");
				request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
				dispatcher.forward(request, response);
			}
		}
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		
	}

	private void GeneraFattura(int CodOrdine, float PrezzoTotale, float PuntiFedeltaUsati, String Email) throws IOException 
	{
		
		float x=0, y=0;
		int limit = 31, numProd = 0;
		
		try 
		{
			
			String servletPath = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp";
			String TotalPath = servletPath + "/Fatture/Fattura" + CodOrdine + ".pdf";
			Float SubTotale = 0.0f;
		
			String PDF = Omodel.RicercaFattura(CodOrdine);
		
		
		
			if(!(PDF != null))
			{
			    File file = new File(servletPath + "/TemplateFattura.pdf");
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
			    contentStream.showText(Integer.toString(CodOrdine)); 
			    contentStream.endText();
	
			    
			    //inserimento data fattura
			    OrdineBean Ordine = Omodel.OrdineByCodOrdine(CodOrdine);
			    y = 754;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Ordine.getDataAcquisto()); 
			    contentStream.endText();
			    
			    
			    //inserimento Dati Utente
			    UserBean Utente = Umodel.RicercaDatiSensibili(Email); 
			    
			    x = 395;
			    y = 710;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getNome() + " " + Utente.getCognome()); 
			    contentStream.endText();
			    
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getVia() + ", " + Utente.getCivico()); 
			    contentStream.endText();
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getCitta() + ", " + Utente.getProvincia() + ", " + Utente.getCAP()); 
			    contentStream.endText();
			    
			    
			    y = y - 14.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Email); 
			    contentStream.endText();
			    
			    //inserimento prodotti
			    y = 604;
			    Collection<?> Ordini = (Collection<?>) Omodel.DettagliOrdine(CodOrdine);
			    if (Ordini != null && Ordini.size() != 0) 
				{
			    	
					Iterator<?> it = Ordini.iterator();
					while (it.hasNext()) 
					{
						numProd++;
						if(numProd > limit )
			    	    {
			    	    	file = new File(servletPath + "/Template_Page2.pdf");
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
			    	    
			    	    
			    	    float PrezzoTotRiga = (float) bean.getPrezzo() * bean.getQuantita();
			    	    x = 485;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Float.toString(PrezzoTotRiga)); 
			    	    contentStream.endText();
			    	    SubTotale = SubTotale + PrezzoTotRiga;
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
			    contentStream.showText("SubTotale: " + SubTotale); 
			    contentStream.endText();
			    
			    
			    
			    contentStream.addRect(448.7f, 72.5f, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 77.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Sconto: -" + (PuntiFedeltaUsati/100)); 
			    contentStream.endText();
			    
			    Locale.setDefault(Locale.US);
				float Totale = SubTotale -  (PuntiFedeltaUsati/100);
			    String TotaleS = String.format("%.2f", Totale);
			    
			    contentStream.addRect(448.7f, 56, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 61;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Totale: " + TotaleS); 
			    contentStream.endText();
			    
			    String IVA = String.format("%.2f", (Totale * 0.22f));
			    Locale.setDefault(Locale.ITALY);
			    
			    contentStream.addRect(448.7f, 39.5f, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 44.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Di cui IVA: " + IVA); 
			    contentStream.endText();
			    
			    
			    contentStream.close();
			    fattura.save(TotalPath);
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
