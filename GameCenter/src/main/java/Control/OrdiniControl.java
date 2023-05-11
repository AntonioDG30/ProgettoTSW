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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.fdf.FDFPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.commons.logging.LogFactory;

@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	static OrdineModel Model = new OrdineModel();
	static UserModel Umodel = new UserModel();
       
    
    public OrdiniControl() 
    {
        super();
    }
    
    public void csprintln(PDPageContentStream cs, String string) throws IOException
    {
    	cs.showText(string);
    	cs.newLine();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		
		if(action != null) 
		{	
			if (action.equalsIgnoreCase("Fattura")) 
			{
				response.setHeader("Content-disposition", "attachment; filename=Fattura.pdf");
				String servletPath = getServletContext().getRealPath("/");
			    System.out.println("Servlet path: " + servletPath + "TemplateFattura.pdf");
			    File file = new File(servletPath + "TemplateFattura.pdf");
			    PDDocument fattura = PDDocument.load(file);
			    System.out.println("Aperto il documento");
			    
			    
			    OrdineModel Omodel = new OrdineModel();
			    ProductModel Pmodel = new ProductModel();
			    
			    
			    
			    
			    
			    PDPage page = fattura.getPage(0); //carico la prima pagina del documento TemplateFattura12
													//aggiungi e non sostituire, non comprimere
			    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, false);
			    
			    
			    
			    contentStream.beginText(); //inizio a scrivere
			    
			    contentStream.newLineAtOffset(220, 10); //prende le coordinate (x,y) del documento in cui iniziare a scrivere
			    csprintln(contentStream,"Antonio1 Di Giorgio");//NOME DESTINATARIO
			    contentStream.endText();
			    contentStream.close();
			    System.out.println("ooooo");  
			    fattura.save("Fatture1.pdf"); //salva il documeto in una fonte di output in questo caso response
			    fattura.close();
			    System.out.println("Chiuso il documento");
			}
			
			if (action.equalsIgnoreCase("Dettagli")) 
			{
				
				try 
				{
					int CodOrdine = Integer.parseInt(request.getParameter("CodOrdine"));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", Model.DettagliOrdine(CodOrdine));
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
						if(Model.Acquisto(Carrello, PrezzoTotale, Email))
						{
							request.removeAttribute("PuntiFedelta");
							request.setAttribute("PuntiFedelta", Umodel.getPuntiFedelta(Email));
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Model.ElencoOrdiniByCliente(Email));
							request.setAttribute("Result", "Grazie per aver acquistato sul nostro sito");
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
							request.setAttribute("Ordini", Model.ElencoOrdini());
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
							request.setAttribute("Ordini", Model.ElencoOrdiniByCliente(Email));
						}
						else if (request.getParameter("DataInizio") != null &&
								request.getParameter("DataFine") != null)
						{
							String DataInizio = request.getParameter("DataInizio");
							String DataFine = request.getParameter("DataFine");
							request.removeAttribute("Ordini");
							request.setAttribute("Ordini", Model.ElencoOrdiniByPeriodo(DataInizio, DataFine));
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
				request.setAttribute("Ordini", Model.ElencoOrdiniByCliente(Email));
			} 
			catch (SQLException e) 
			{
				System.out.println("Error:" + e.getMessage());
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
