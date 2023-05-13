package Control;

import Model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.text.DecimalFormat;


@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
	static OrdineModel Omodel = new OrdineModel();
	static ProductModel Pmodel = new ProductModel();
	static UserModel Umodel = new UserModel();   
    
    public OrdiniControl() 
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		
		if(action != null) 
		{	
			if (action.equalsIgnoreCase("Dettagli")) 
			{
				try 
				{
					int CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", Omodel.DettagliOrdine(CodOrdine));
					request.removeAttribute("Fattura");
					request.setAttribute("Fattura", Omodel.RicercaFattura(CodOrdine));
					
				} 
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliOrdine.jsp");
				dispatcher.forward(request, response);	
			}
			
			if (action.equalsIgnoreCase("Acquista")) 
			{
				try 
				{
					if (request.getSession().getAttribute("Email") != null)
					{
						String Email=(String) request.getSession().getAttribute("Email");
						CarrelloBean Carrello=(CarrelloBean) request.getSession().getAttribute("Carrello");
						float PrezzoTotale =  Float.parseFloat(request.getParameter("PrezzoTotale"));
						
						request.removeAttribute("Result");
						int CodOrdine = Omodel.Acquisto(Carrello, PrezzoTotale, Email);
						if(CodOrdine != 0)
						{
							request.removeAttribute("PuntiFedelta");
							request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
							request.setAttribute("Result", "Grazie per aver acquistato sul nostro sito");
							GeneraFattura(CodOrdine, PrezzoTotale, Email);
							Omodel.UpdateFattura(CodOrdine);
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
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
			}
			
			
			if (action.equalsIgnoreCase("VisualizzaOrdini")) 
			{
				if(request.getParameter("ParteMod").contentEquals("Parte1"))
				{
					try 
					{
						String Visualizzazione = request.getParameter("Visualizzazione");
						
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
					catch (SQLException e) 
					{
						System.out.println("Error:" + e.getMessage());
					}
				}
				else if(request.getParameter("ParteMod").contentEquals("Parte2"))
				{
					try 
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
					catch (SQLException e) 
					{
						System.out.println("Error:" + e.getMessage());
					}
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
				dispatcher.forward(request, response);	
			}
		}
		else
		{
			try 
			{
			    String Email=(String) request.getSession().getAttribute("Email");
			    request.removeAttribute("PuntiFedelta");
				request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
				request.removeAttribute("Ordini");
				request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
			} 
			catch (SQLException e) 
			{
				System.out.println("Error:" + e.getMessage());
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void GeneraFattura(int CodOrdine, float PrezzoTotale, String Email) throws IOException 
	{
		
		float x=0, y=0;
		int limit = 3, numProd = 0;
		
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
			    	    	System.out.println("Finito spazio su questa pagina ");
			    	    	file = new File(servletPath + "/Template_Page2.pdf");
				    		page = (PDPage)PDDocument.load(file).getDocumentCatalog().getPages().get(0);
				    		
				    		fattura.addPage(page);
				    		
				 		    contentStream.close();
				    		
				    		contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
			  	    	  	y = 752; 
			  	    	  	numProd = 1;
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
			    	    System.out.println(numProd);
			    	   
			    	    
					}
					
				}
			    
			    contentStream.addRect(448.7f, 89, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    
			    DecimalFormat df = new DecimalFormat("#.##"); 
				String SubTotaleString = df.format(SubTotale);
			    
			    x = 451;
			    y = 94;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Sconto: " + SubTotaleString); 
			    contentStream.endText();
			    
			    
			    contentStream.addRect(448.7f, 72.5f, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 77.5f;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Totale: " + SubTotaleString); 
			    contentStream.endText();
			    
			    
			    
			    contentStream.addRect(448.7f, 56, 93, 17);
			    contentStream.stroke();
			    contentStream.fill();
			    x = 451;
			    y = 61;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText("Di cui IVA: " + SubTotaleString); 
			    contentStream.endText();
			    
			    
			    
			    contentStream.close();
			    fattura.save(TotalPath);
			    fattura.close();

			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
