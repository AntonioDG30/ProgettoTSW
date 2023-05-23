package control;

import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet("/AdminProductControl")
public class AdminProductControl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	
	static ProductModel model = new ProductModel();
	String codSerialeMod ="";
	boolean tipologia;
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XBOX_SERIE_X = "XBOX Series X";
	private static final String PIATTAFORMA_XBOX_SERIE_S = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
	
	
	Logger logger = Logger.getLogger(AdminProductControl.class.getName());
       
   
    public AdminProductControl() 
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
				
				
				if (action.equalsIgnoreCase("Elimina")) 
				{
					request.removeAttribute("Result");
					String codSeriale = request.getParameter("CodSeriale");
					if(model.elimina(codSeriale))
					{
						request.setAttribute("Result", "Il prodotto è stato eliminato correttamente.");
					}
					else
					{
						request.setAttribute("Result", "CodSeriale non valido, riprova.");
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				
				if (action.equalsIgnoreCase("Inserisci")) 
				{
					if(request.getParameter("ParteMod").contentEquals("Parte1"))
					{
						request.removeAttribute("TipologiaInserimento");
						String tipologiaString = request.getParameter("Tipologia");
						if(tipologiaString.contentEquals("hardware"))
						{
							tipologia=true;
							request.setAttribute("TipologiaInserimento", "Hardware");
						}
						else
						{
							tipologia=false;
							request.setAttribute("TipologiaInserimento", "Videogioco");
						}
						request.removeAttribute("PEGI");
						request.setAttribute("PEGI", model.getPegiElements());
						request.removeAttribute("Genere");
						request.setAttribute("Genere", model.getGenereElements());
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
						dispatcher.forward(request, response);
					}
					
					
					if(request.getParameter("ParteMod").contentEquals("Parte2"))
					{
						ProductBean product = new ProductBean();
						product.setCodSeriale(request.getParameter("CodSeriale"));
						product.setNome(request.getParameter("Nome"));
						Part immaginePart = request.getPart("Immagine");
						String immagineFileName = immaginePart.getSubmittedFileName();
						String path = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp/Immagini/" +immagineFileName;
						FileOutputStream fos = new FileOutputStream(path);
						InputStream is = immaginePart.getInputStream();
						byte[] data = new byte[is.available()];
						if(is.read(data) > 0)
						{
							fos.write(data);
						}
						fos.close();
						product.setImmagine(immagineFileName);
						product.setPrezzo(Float.parseFloat(request.getParameter("Prezzo")));
						product.setDataUscita(request.getParameter("DataUscita"));	
						product.setDescrizioneRidotta(request.getParameter("DescrizioneRidotta"));
						product.setDescrizioneCompleta(request.getParameter("DescrizioneCompleta"));
						product.setTipologia(tipologia);
						if(!(product.getTipologia()))
						{
							product.setPEGI(Integer.parseInt(request.getParameter("PEGI")));
							product.setGenere(request.getParameter("Genere"));
							product.setDispPs5Digitale(Integer.parseInt(request.getParameter("PS5Digitale")));
							product.setDispPs4Digitale(Integer.parseInt(request.getParameter("PS4Digitale")));
							product.setDispXboxXDigitale(Integer.parseInt(request.getParameter("XboxXDigitale")));
							product.setDispPcDigitale(Integer.parseInt(request.getParameter("PcDigitale")));
							product.setDispXboxSDigitale(Integer.parseInt(request.getParameter("XboxSDigitale")));
						}		
						product.setDispPcFisico(Integer.parseInt(request.getParameter("PcFisico")));
						product.setDispPs5Fisico(Integer.parseInt(request.getParameter("PS5Fisico")));
						product.setDispPs4Fisico(Integer.parseInt(request.getParameter("PS4Fisico")));
						product.setDispXboxXFisico(Integer.parseInt(request.getParameter("XboxXFisico")));
						if(model.inserisci(product))
						{
							request.setAttribute("Result", "Il prodotto è stato inserito correttamente.");
						}
						else
						{
							request.setAttribute("Result", "Errore imprevisto, riprova.");
						}
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
						dispatcher.forward(request, response);
					}
				}
				
				
				
				
				if (action.equalsIgnoreCase("Modifica")) 
				{
					
					if(request.getParameter("ParteMod").contentEquals("Parte1"))
					{
						codSerialeMod = request.getParameter("CodSeriale");
						request.removeAttribute("product");
						request.setAttribute("product", model.dettagli(codSerialeMod));
						request.removeAttribute("Result");
						if (request.getAttribute("product") == null)
						{
							request.setAttribute("Result", "il CodSeriale inserito non esiste, riprova.");
						}
						request.removeAttribute("PEGI");
						request.setAttribute("PEGI", model.getPegiElements());
						request.removeAttribute("Genere");
						request.setAttribute("Genere", model.getGenereElements());
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
						dispatcher.forward(request, response);
					}
					else if(request.getParameter("ParteMod").contentEquals("Parte2"))
					{
						Boolean rsCodSeriale = false, rsNome = false, rsImmagine = false, rsPrezzo = false, 
								rsDataUscita = false, rsDescrizioneRidotta = false, rsDescrizioneCompleta = false, 
								rsPEGI = false, rsGenere = false, dispXboxXFisico = false, 
								dispXboxXDigitale = false, dispXboxSDigitale = false, 
								dispPs5Fisico = false, dispPs5Digitale = false, dispPs4Fisico = false,
								dispPs4Digitale = false, dispPcFisico = false, dispPcDigitale = false;
						
						if(!(request.getParameter("Nome").isEmpty()))
						{
							rsNome = model.modNome(codSerialeMod, request.getParameter("Nome"));
						}
						else 
						{
							rsNome = true;
						}
						
						if(request.getPart("Immagine") != null && request.getPart("Immagine").getSize() != 0)
						{
							Part immaginePart = request.getPart("Immagine");
							String immagineFileName = immaginePart.getSubmittedFileName();
							String path = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp/Immagini/" +immagineFileName;
							FileOutputStream fos = new FileOutputStream(path);
							InputStream is = immaginePart.getInputStream();
							byte[] data = new byte[is.available()];
							if(is.read(data) > 0)
							{
								fos.write(data);
							}
							fos.close();
							rsImmagine = model.modImmagine(codSerialeMod, immagineFileName);
						}
						else
						{
							rsImmagine = true;
						}


						if(!(request.getParameter("Prezzo").isEmpty()))
						{
							rsPrezzo = model.modPrezzo(codSerialeMod, Float.parseFloat(request.getParameter("Prezzo")));
						}
						else 
						{
							rsPrezzo = true;
						}
						
						if(!(request.getParameter("DataUscita").isEmpty()))			
						{
							rsDataUscita = model.modDataUscita(codSerialeMod, request.getParameter("DataUscita"));
						}
						else 
						{
							rsDataUscita = true;
						}
						
						if(!(request.getParameter("DescrizioneRidotta").isEmpty()))				
						{
							rsDescrizioneRidotta = model.modDescrizioneRidotta(codSerialeMod, request.getParameter("DescrizioneRidotta"));
						}
						else 
						{
							rsDescrizioneRidotta = true;
						}
						
						if(!(request.getParameter("DescrizioneCompleta").isEmpty()))		
						{
							rsDescrizioneCompleta = model.modDescrizioneCompleta(codSerialeMod, request.getParameter("DescrizioneCompleta"));
						}
						else 
						{
							rsDescrizioneCompleta = true;
						}
						
						if(!(request.getParameter("PEGI").isEmpty() || request.getParameter("PEGI").contentEquals("NonModificare")))			
						{
							rsPEGI = model.modPEGI(codSerialeMod, Integer.parseInt(request.getParameter("PEGI")));
						}
						else 
						{
							rsPEGI = true;
						}
						
						if(!(request.getParameter("Genere").isEmpty() || request.getParameter("Genere").contentEquals("NonModificare")))			
						{
							rsGenere = model.modGenere(codSerialeMod, request.getParameter("Genere"));
						}
						else 
						{
							rsGenere = true;
						}
						
						if(!(request.getParameter("XboxXDigitale").isEmpty()))				
						{
							dispXboxXDigitale = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXDigitale")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_DIGITALE);
						}
						else 
						{
							dispXboxXDigitale = true;
						}
						
						if(!(request.getParameter("XboxSDigitale").isEmpty()))			
						{
							dispXboxSDigitale = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxSDigitale")), PIATTAFORMA_XBOX_SERIE_S, FORMATO_DIGITALE);
						}
						else 
						{
							dispXboxSDigitale = true;
						}
						
						if(!(request.getParameter("PS5Digitale").isEmpty()))		
						{
							dispPs5Digitale = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Digitale")), PIATTAFORMA_PS5, FORMATO_DIGITALE);
						}
						else 
						{
							dispPs5Digitale = true;
						}
						
						if(!(request.getParameter("PS4Digitale").isEmpty()))			
						{
							dispPs4Digitale = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Digitale")), PIATTAFORMA_PS4, FORMATO_DIGITALE);
						}
						else 
						{
							dispPs4Digitale = true;
						}
						
						if(!(request.getParameter("PcDigitale").isEmpty()))		
						{
							dispPcDigitale = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcDigitale")), PIATTAFORMA_PC, FORMATO_DIGITALE);
						}
						else 
						{
							dispPcDigitale = true;
						}

						if(!(request.getParameter("XboxXFisico").isEmpty()))		
						{
							dispXboxXFisico = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXFisico")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_FISICO);
						}
						else 
						{
							dispXboxXFisico = true;
						}
						
						if(!(request.getParameter("PS5Fisico").isEmpty()))		
						{
							dispPs5Fisico = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Fisico")), PIATTAFORMA_PS5, FORMATO_FISICO);
						}
						else 
						{
							dispPs5Fisico = true;
						}
						
						if(!(request.getParameter("PS4Fisico").isEmpty()))	
						{
							dispPs4Fisico = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Fisico")), PIATTAFORMA_PS4, FORMATO_FISICO);
						}
						else 
						{
							dispPs4Fisico = true;
						}
						
						if(!(request.getParameter("PcFisico").isEmpty()))		
						{
							dispPcFisico = model.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcFisico")), PIATTAFORMA_PC, FORMATO_FISICO);
						}
						else 
						{
							dispPcFisico = true;
						}
						
						if(!(request.getParameter("CodSeriale").isEmpty()))
						{
							rsCodSeriale = model.modCodSeriale(codSerialeMod, request.getParameter("CodSeriale"));
						}
						else 
						{
							rsCodSeriale = true;
						}
						
						request.removeAttribute("Result");
						if(rsCodSeriale && rsNome && rsImmagine && rsPrezzo && rsDataUscita && rsDescrizioneRidotta && rsDescrizioneCompleta && 
								rsPEGI && rsGenere && dispXboxXFisico && dispXboxXDigitale && dispXboxSDigitale && 
								dispPs5Fisico && dispPs5Digitale && dispPs4Fisico && dispPs4Digitale && dispPcFisico && dispPcDigitale)
						{
							request.setAttribute("Result", "Il prodotto è stato modificato correttamente.");
						}
						else
						{
							request.setAttribute("Result", "Errore imprevisto, riprova.");
						}
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
						dispatcher.forward(request, response);
					}
				}				
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
				dispatcher.forward(request, response);
			}
				
		}
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		catch (Exception e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
