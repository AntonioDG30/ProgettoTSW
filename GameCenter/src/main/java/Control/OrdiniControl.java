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
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
