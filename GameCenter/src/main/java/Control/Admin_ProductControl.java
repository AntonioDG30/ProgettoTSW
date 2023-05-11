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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/Admin_ProductControl")
public class Admin_ProductControl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	
	static ProductModel model = new ProductModel();
	String CodSerialeMod ="";
	boolean Tipologia;
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XboxSerieX = "XBOX Series X";
	private static final String PIATTAFORMA_XboxSerieS = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
       
   
    public Admin_ProductControl() 
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
					String CodSeriale = request.getParameter("CodSeriale");
					if(model.Elimina(CodSeriale))
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
						String TipologiaString = request.getParameter("Tipologia");
						if(TipologiaString.contentEquals("hardware"))
						{
							Tipologia=true;
							request.setAttribute("TipologiaInserimento", "Hardware");
						}
						else
						{
							Tipologia=false;
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
						Part ImmaginePart = request.getPart("Immagine");
						String ImmagineFileName = ImmaginePart.getSubmittedFileName();
						String Path = "C:/Users/anton/eclipse-workspace/GameCenter.zip_expanded/GameCenter/src/main/webapp/Immagini/" +ImmagineFileName;
						try
						{
							FileOutputStream fos = new FileOutputStream(Path);
							InputStream is = ImmaginePart.getInputStream();
							byte[] data = new byte[is.available()];
							is.read(data);
							fos.write(data);
							fos.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						product.setImmagine(ImmagineFileName);
						product.setPrezzo(Float.parseFloat(request.getParameter("Prezzo")));
						product.setDataUscita(request.getParameter("DataUscita"));	
						product.setDescrizioneRidotta(request.getParameter("DescrizioneRidotta"));
						product.setDescrizioneCompleta(request.getParameter("DescrizioneCompleta"));
						product.setTipologia(Tipologia);
						if(!(product.getTipologia()))
						{
							product.setPEGI(Integer.parseInt(request.getParameter("PEGI")));
							product.setGenere(request.getParameter("Genere"));
							product.setDisp_Ps5_Digitale(Integer.parseInt(request.getParameter("PS5Digitale")));
							product.setDisp_Ps4_Digitale(Integer.parseInt(request.getParameter("PS4Digitale")));
							product.setDisp_XboxX_Digitale(Integer.parseInt(request.getParameter("XboxXDigitale")));
							product.setDisp_Pc_Digitale(Integer.parseInt(request.getParameter("PcDigitale")));
							product.setDisp_XboxS_Digitale(Integer.parseInt(request.getParameter("XboxSDigitale")));
						}		
						product.setDisp_Pc_Fisico(Integer.parseInt(request.getParameter("PcFisico")));
						product.setDisp_Ps5_Fisico(Integer.parseInt(request.getParameter("PS5Fisico")));
						product.setDisp_Ps4_Fisico(Integer.parseInt(request.getParameter("PS4Fisico")));
						product.setDisp_XboxX_Fisico(Integer.parseInt(request.getParameter("XboxXFisico")));
						if(model.Inserisci(product))
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
						CodSerialeMod = request.getParameter("CodSeriale");
						request.removeAttribute("product");
						request.setAttribute("product", model.Dettagli(CodSerialeMod));
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
						Boolean RsCodSeriale = false, RsNome = false, RsImmagine = false, RsPrezzo = false, 
								RsDataUscita = false, RsDescrizioneRidotta = false, RsDescrizioneCompleta = false, 
								RsPEGI = false, RsGenere = false,Disp_XboxX_Fisico = false, 
								Disp_XboxX_Digitale = false, Disp_XboxS_Digitale = false, 
								Disp_Ps5_Fisico = false, Disp_Ps5_Digitale = false, Disp_Ps4_Fisico = false,
								Disp_Ps4_Digitale = false, Disp_Pc_Fisico = false, Disp_Pc_Digitale = false;
						
						if(!(request.getParameter("Nome").isEmpty()))
						{
							RsNome = model.ModNome(CodSerialeMod, request.getParameter("Nome"));
						}
						else 
						{
							RsNome = true;
						}
						
						if(request.getPart("Immagine") != null && request.getPart("Immagine").getSize() != 0)
						{
							Part ImmaginePart = request.getPart("Immagine");
							String ImmagineFileName = ImmaginePart.getSubmittedFileName();
							String Path = "C:/Users/anton/eclipse-workspace/GameCenter.zip_expanded/GameCenter/src/main/webapp/Immagini/" +ImmagineFileName;
							try
							{
								FileOutputStream fos = new FileOutputStream(Path);
								InputStream is = ImmaginePart.getInputStream();
								byte[] data = new byte[is.available()];
								is.read(data);
								fos.write(data);
								fos.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							
							RsImmagine = model.ModImmagine(CodSerialeMod, ImmagineFileName);
						}
						else
						{
							RsImmagine = true;
						}


						if(!(request.getParameter("Prezzo").isEmpty()))
						{
							RsPrezzo = model.ModPrezzo(CodSerialeMod, Float.parseFloat(request.getParameter("Prezzo")));
						}
						else 
						{
							RsPrezzo = true;
						}
						
						if(!(request.getParameter("DataUscita").isEmpty()))			
						{
							RsDataUscita = model.ModDataUscita(CodSerialeMod, request.getParameter("DataUscita"));
						}
						else 
						{
							RsDataUscita = true;
						}
						
						if(!(request.getParameter("DescrizioneRidotta").isEmpty()))				
						{
							RsDescrizioneRidotta = model.ModDescrizioneRidotta(CodSerialeMod, request.getParameter("DescrizioneRidotta"));
						}
						else 
						{
							RsDescrizioneRidotta = true;
						}
						
						if(!(request.getParameter("DescrizioneCompleta").isEmpty()))		
						{
							RsDescrizioneCompleta = model.ModDescrizioneCompleta(CodSerialeMod, request.getParameter("DescrizioneCompleta"));
						}
						else 
						{
							RsDescrizioneCompleta = true;
						}
						
						if(!(request.getParameter("PEGI").isEmpty() || request.getParameter("PEGI").contentEquals("NonModificare")))			
						{
							RsPEGI = model.ModPEGI(CodSerialeMod, Integer.parseInt(request.getParameter("PEGI")));
						}
						else 
						{
							RsPEGI = true;
						}
						
						if(!(request.getParameter("Genere").isEmpty() || request.getParameter("Genere").contentEquals("NonModificare")))			
						{
							RsGenere = model.ModGenere(CodSerialeMod, request.getParameter("Genere"));
						}
						else 
						{
							RsGenere = true;
						}
						
						if(!(request.getParameter("XboxXDigitale").isEmpty()))				
						{
							Disp_XboxX_Digitale = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("XboxXDigitale")), PIATTAFORMA_XboxSerieX, FORMATO_DIGITALE);
						}
						else 
						{
							Disp_XboxX_Digitale = true;
						}
						
						if(!(request.getParameter("XboxSDigitale").isEmpty()))			
						{
							Disp_XboxS_Digitale = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("XboxSDigitale")), PIATTAFORMA_XboxSerieS, FORMATO_DIGITALE);
						}
						else 
						{
							Disp_XboxS_Digitale = true;
						}
						
						if(!(request.getParameter("PS5Digitale").isEmpty()))		
						{
							Disp_Ps5_Digitale = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PS5Digitale")), PIATTAFORMA_PS5, FORMATO_DIGITALE);
						}
						else 
						{
							Disp_Ps5_Digitale = true;
						}
						
						if(!(request.getParameter("PS4Digitale").isEmpty()))			
						{
							Disp_Ps4_Digitale = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PS4Digitale")), PIATTAFORMA_PS4, FORMATO_DIGITALE);
						}
						else 
						{
							Disp_Ps4_Digitale = true;
						}
						
						if(!(request.getParameter("PcDigitale").isEmpty()))		
						{
							Disp_Pc_Digitale = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PcDigitale")), PIATTAFORMA_PC, FORMATO_DIGITALE);
						}
						else 
						{
							Disp_Pc_Digitale = true;
						}

						if(!(request.getParameter("XboxXFisico").isEmpty()))		
						{
							Disp_XboxX_Fisico = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("XboxXFisico")), PIATTAFORMA_XboxSerieX, FORMATO_FISICO);
						}
						else 
						{
							Disp_XboxX_Fisico = true;
						}
						
						if(!(request.getParameter("PS5Fisico").isEmpty()))		
						{
							Disp_Ps5_Fisico = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PS5Fisico")), PIATTAFORMA_PS5, FORMATO_FISICO);
						}
						else 
						{
							Disp_Ps5_Fisico = true;
						}
						
						if(!(request.getParameter("PS4Fisico").isEmpty()))	
						{
							Disp_Ps4_Fisico = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PS4Fisico")), PIATTAFORMA_PS4, FORMATO_FISICO);
						}
						else 
						{
							Disp_Ps4_Fisico = true;
						}
						
						if(!(request.getParameter("PcFisico").isEmpty()))		
						{
							Disp_Pc_Fisico = model.ModDisponibilita(CodSerialeMod, Integer.parseInt(request.getParameter("PcFisico")), PIATTAFORMA_PC, FORMATO_FISICO);
						}
						else 
						{
							Disp_Pc_Fisico = true;
						}
						
						if(!(request.getParameter("CodSeriale").isEmpty()))
						{
							RsCodSeriale = model.ModCodSeriale(CodSerialeMod, request.getParameter("CodSeriale"));
						}
						else 
						{
							RsCodSeriale = true;
						}
						
						request.removeAttribute("Result");
						if(RsCodSeriale && RsNome && RsImmagine && RsPrezzo && RsDataUscita && RsDescrizioneRidotta && RsDescrizioneCompleta && 
								RsPEGI && RsGenere && Disp_XboxX_Fisico && Disp_XboxX_Digitale && Disp_XboxS_Digitale && 
								Disp_Ps5_Fisico && Disp_Ps5_Digitale && Disp_Ps4_Fisico && Disp_Ps4_Digitale && Disp_Pc_Fisico && Disp_Pc_Digitale)
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
			System.out.println("Error:" + e.getMessage());
		}
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
