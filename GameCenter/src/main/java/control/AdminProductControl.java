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

	
	static ProductModel productModel = new ProductModel();
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
				
				if (action.equalsIgnoreCase("Catalogo")) 
				{
					request.removeAttribute("products");
					request.setAttribute("products", productModel.doAll());
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
					dispatcher.forward(request, response);
				}
				
				
				if (action.equalsIgnoreCase("Elimina")) 
				{
					String codSeriale = request.getParameter("CodSeriale");
					productModel.elimina(codSeriale);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				
				if (action.equalsIgnoreCase("Inserisci")) 
				{
					if(request.getParameter("ParteMod").contentEquals("Parte1"))
					{
						request.removeAttribute("TipologiaInserimento");
						if(request.getParameter("Tipologia").contentEquals("hardware"))
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
						request.setAttribute("PEGI", productModel.getPegiElements());
						request.removeAttribute("Genere");
						request.setAttribute("Genere", productModel.getGenereElements());
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AggiungiProdotto.jsp");
						dispatcher.forward(request, response);
					}
					else if(request.getParameter("ParteMod").contentEquals("Parte1"))
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
							product.setPegi(Integer.parseInt(request.getParameter("PEGI")));
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
						productModel.inserisci(product);
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
						request.setAttribute("product", productModel.dettagli(codSerialeMod));
						request.removeAttribute("PEGI");
						request.setAttribute("PEGI", productModel.getPegiElements());
						request.removeAttribute("Genere");
						request.setAttribute("Genere", productModel.getGenereElements());
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ModificaProdotto.jsp");
						dispatcher.forward(request, response);
					}
					else if(request.getParameter("ParteMod").contentEquals("Parte2"))
					{
						Boolean rsCodSeriale = false;
						Boolean rsNome = false;
						Boolean rsImmagine = false; 
						Boolean rsPrezzo = false; 
						Boolean rsDataUscita = false;
						Boolean rsDescrizioneRidotta = false;
						Boolean rsDescrizioneCompleta = false; 
						Boolean rsPEGI = false;
						Boolean rsGenere = false;
						Boolean dispXboxXFisico = false;
						Boolean dispXboxXDigitale = false;
						Boolean dispXboxSDigitale = false; 
						Boolean dispPs5Fisico = false;
						Boolean dispPs5Digitale = false;
						Boolean dispPs4Fisico = false;
						Boolean dispPs4Digitale = false;
						Boolean dispPcFisico = false;
						Boolean dispPcDigitale = false;
						
						if(!(request.getParameter("Nome").isEmpty()))
						{
							rsNome = productModel.modNome(codSerialeMod, request.getParameter("Nome"));
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
							rsImmagine = productModel.modImmagine(codSerialeMod, immagineFileName);
						}
						else
						{
							rsImmagine = true;
						}


						if(!(request.getParameter("Prezzo").isEmpty()))
						{
							rsPrezzo = productModel.modPrezzo(codSerialeMod, Float.parseFloat(request.getParameter("Prezzo")));
						}
						else 
						{
							rsPrezzo = true;
						}
						
						if(!(request.getParameter("DataUscita").isEmpty()))			
						{
							rsDataUscita = productModel.modDataUscita(codSerialeMod, request.getParameter("DataUscita"));
						}
						else 
						{
							rsDataUscita = true;
						}
						
						if(!(request.getParameter("DescrizioneRidotta").isEmpty()))				
						{
							rsDescrizioneRidotta = productModel.modDescrizioneRidotta(codSerialeMod, request.getParameter("DescrizioneRidotta"));
						}
						else 
						{
							rsDescrizioneRidotta = true;
						}
						
						if(!(request.getParameter("DescrizioneCompleta").isEmpty()))		
						{
							rsDescrizioneCompleta = productModel.modDescrizioneCompleta(codSerialeMod, request.getParameter("DescrizioneCompleta"));
						}
						else 
						{
							rsDescrizioneCompleta = true;
						}
						
						if(!(request.getParameter("PEGI").isEmpty() || request.getParameter("PEGI").contentEquals("NonModificare")))			
						{
							rsPEGI = productModel.modPEGI(codSerialeMod, Integer.parseInt(request.getParameter("PEGI")));
						}
						else 
						{
							rsPEGI = true;
						}
						
						if(!(request.getParameter("Genere").isEmpty() || request.getParameter("Genere").contentEquals("NonModificare")))			
						{
							rsGenere = productModel.modGenere(codSerialeMod, request.getParameter("Genere"));
						}
						else 
						{
							rsGenere = true;
						}
						
						if(!(request.getParameter("XboxXDigitale").isEmpty()))				
						{
							dispXboxXDigitale = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXDigitale")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_DIGITALE);
						}
						else 
						{
							dispXboxXDigitale = true;
						}
						
						if(!(request.getParameter("XboxSDigitale").isEmpty()))			
						{
							dispXboxSDigitale = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxSDigitale")), PIATTAFORMA_XBOX_SERIE_S, FORMATO_DIGITALE);
						}
						else 
						{
							dispXboxSDigitale = true;
						}
						
						if(!(request.getParameter("PS5Digitale").isEmpty()))		
						{
							dispPs5Digitale = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Digitale")), PIATTAFORMA_PS5, FORMATO_DIGITALE);
						}
						else 
						{
							dispPs5Digitale = true;
						}
						
						if(!(request.getParameter("PS4Digitale").isEmpty()))			
						{
							dispPs4Digitale = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Digitale")), PIATTAFORMA_PS4, FORMATO_DIGITALE);
						}
						else 
						{
							dispPs4Digitale = true;
						}
						
						if(!(request.getParameter("PcDigitale").isEmpty()))		
						{
							dispPcDigitale = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcDigitale")), PIATTAFORMA_PC, FORMATO_DIGITALE);
						}
						else 
						{
							dispPcDigitale = true;
						}

						if(!(request.getParameter("XboxXFisico").isEmpty()))		
						{
							dispXboxXFisico = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXFisico")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_FISICO);
						}
						else 
						{
							dispXboxXFisico = true;
						}
						
						if(!(request.getParameter("PS5Fisico").isEmpty()))		
						{
							dispPs5Fisico = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Fisico")), PIATTAFORMA_PS5, FORMATO_FISICO);
						}
						else 
						{
							dispPs5Fisico = true;
						}
						
						if(!(request.getParameter("PS4Fisico").isEmpty()))	
						{
							dispPs4Fisico = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Fisico")), PIATTAFORMA_PS4, FORMATO_FISICO);
						}
						else 
						{
							dispPs4Fisico = true;
						}
						
						if(!(request.getParameter("PcFisico").isEmpty()))		
						{
							dispPcFisico = productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcFisico")), PIATTAFORMA_PC, FORMATO_FISICO);
						}
						else 
						{
							dispPcFisico = true;
						}
						
						if(!(request.getParameter("CodSeriale").isEmpty()))
						{
							rsCodSeriale = productModel.modCodSeriale(codSerialeMod, request.getParameter("CodSeriale"));
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
