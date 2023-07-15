package control;

import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
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
				
				
				if (action.equalsIgnoreCase("InserisciForm")) 
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
				
				if (action.equalsIgnoreCase("Inserisci")) 
				{

					ProductBean product = new ProductBean();
					product.setCodSeriale(request.getParameter("CodSeriale"));
					product.setNome(request.getParameter("Nome"));
					Part immaginePart = request.getPart("Immagine");
					String immagineFileName = immaginePart.getSubmittedFileName();
					String path = request.getServletContext().getRealPath("") + "Immagini" + File.separator  +immagineFileName;
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
				
				if (action.equalsIgnoreCase("ModificaPage")) 
				{
					String codSeriale = request.getParameter("CodSeriale");
					request.removeAttribute("product");
					request.setAttribute("product", productModel.dettagli(codSeriale));
					request.removeAttribute("PEGI");
					request.setAttribute("PEGI", productModel.getPegiElements());
					request.removeAttribute("Genere");
					request.setAttribute("Genere", productModel.getGenereElements());
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ModificaProdotto.jsp");
					dispatcher.forward(request, response);
				}			
				
				
				if (action.equalsIgnoreCase("Modifica")) 
				{

					String codSerialeMod = request.getParameter("CodSeriale");					
					if(!(request.getParameter("Nome").isEmpty()))
					{
						productModel.modNome(codSerialeMod, request.getParameter("Nome"));
					}
					
					if(request.getPart("Immagine") != null && request.getPart("Immagine").getSize() != 0)
					{
						Part immaginePart = request.getPart("Immagine");
						String immagineFileName = immaginePart.getSubmittedFileName();
						String path = request.getServletContext().getRealPath("") + "Immagini" + File.separator  +immagineFileName;
						FileOutputStream fos = new FileOutputStream(path);
						InputStream is = immaginePart.getInputStream();
						byte[] data = new byte[is.available()];
						if(is.read(data) > 0)
						{
							fos.write(data);
						}
						fos.close();
						productModel.modImmagine(codSerialeMod, immagineFileName);
					}

					if(!(request.getParameter("Prezzo").isEmpty()))
					{
						productModel.modPrezzo(codSerialeMod, Float.parseFloat(request.getParameter("Prezzo")));
					}
					
					if(!(request.getParameter("DataUscita").isEmpty()))			
					{
						productModel.modDataUscita(codSerialeMod, request.getParameter("DataUscita"));
					}
					
					if(!(request.getParameter("DescrizioneRidotta").isEmpty()))				
					{
						productModel.modDescrizioneRidotta(codSerialeMod, request.getParameter("DescrizioneRidotta"));
					}
					
					if(!(request.getParameter("DescrizioneCompleta").isEmpty()))		
					{
						productModel.modDescrizioneCompleta(codSerialeMod, request.getParameter("DescrizioneCompleta"));
					}
					
					if(!(request.getParameter("PEGI").isEmpty() || request.getParameter("PEGI").contentEquals("NonModificare")))			
					{
						productModel.modPEGI(codSerialeMod, Integer.parseInt(request.getParameter("PEGI")));
					}
					
					if(!(request.getParameter("Genere").isEmpty() || request.getParameter("Genere").contentEquals("NonModificare")))			
					{
						productModel.modGenere(codSerialeMod, request.getParameter("Genere"));
					}
					
					if(!(request.getParameter("XboxXDigitale").isEmpty()))				
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXDigitale")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_DIGITALE);
					}
					
					if(!(request.getParameter("XboxSDigitale").isEmpty()))			
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxSDigitale")), PIATTAFORMA_XBOX_SERIE_S, FORMATO_DIGITALE);
					}
					
					if(!(request.getParameter("PS5Digitale").isEmpty()))		
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Digitale")), PIATTAFORMA_PS5, FORMATO_DIGITALE);
					}

					
					if(!(request.getParameter("PS4Digitale").isEmpty()))			
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Digitale")), PIATTAFORMA_PS4, FORMATO_DIGITALE);
					}
					
					if(!(request.getParameter("PcDigitale").isEmpty()))		
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcDigitale")), PIATTAFORMA_PC, FORMATO_DIGITALE);
					}

					if(!(request.getParameter("XboxXFisico").isEmpty()))		
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("XboxXFisico")), PIATTAFORMA_XBOX_SERIE_X, FORMATO_FISICO);
					}

					
					if(!(request.getParameter("PS5Fisico").isEmpty()))		
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS5Fisico")), PIATTAFORMA_PS5, FORMATO_FISICO);
					}

					
					if(!(request.getParameter("PS4Fisico").isEmpty()))	
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PS4Fisico")), PIATTAFORMA_PS4, FORMATO_FISICO);
					}

					
					if(!(request.getParameter("PcFisico").isEmpty()))		
					{
						productModel.modDisponibilita(codSerialeMod, Integer.parseInt(request.getParameter("PcFisico")), PIATTAFORMA_PC, FORMATO_FISICO);
					}

					
					if(!(request.getParameter("CodSerialeNew").isEmpty()))
					{
						productModel.modCodSeriale(codSerialeMod, request.getParameter("CodSerialeNew"));
					}

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
					dispatcher.forward(request, response);
				}		
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
				dispatcher.forward(request, response);
			}
				
		}
		catch (SQLException|IOException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
