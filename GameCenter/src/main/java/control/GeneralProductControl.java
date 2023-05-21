package control;

import model.*;

import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet("/GeneralProductControl")
public class GeneralProductControl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	
	static ProductModel model = new ProductModel();
	
	Logger logger = Logger.getLogger(GeneralProductControl.class.getName());
       
   
    public GeneralProductControl() 
    {
        super(); 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		CarrelloBean Carrello = (CarrelloBean)request.getSession().getAttribute("Carrello");
		if(Carrello == null)
		{
			Carrello = new CarrelloBean();
			request.getSession().setAttribute("Carrello", Carrello);
		}
		
		String action = request.getParameter("action");
		
		try
		{
			if(action != null) 
			{
				
				
				if (action.equalsIgnoreCase("Dettagli")) 
				{
					String CodSeriale = request.getParameter("CodSeriale");
					request.removeAttribute("product");
					request.setAttribute("product", model.dettagli(CodSeriale));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliProdotto.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				
				if (action.equalsIgnoreCase("AggiungiCarrello")) 
				{
					String CodSeriale = request.getParameter("CodSeriale");
					String Piattaforma = request.getParameter("Piattaforma");
					Carrello.AggiungiProdotto(model.dettagli(CodSeriale), Piattaforma);
					request.getSession().setAttribute("Carrello", Carrello);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				
				if (action.equalsIgnoreCase("RimuoviCarrello")) 
				{
					String CodSeriale = request.getParameter("CodSeriale");
					Carrello.RimuoviProdotto(model.dettagli(CodSeriale));
					request.getSession().setAttribute("Carrello", Carrello);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
					dispatcher.forward(request, response);
				}
				
			}
			else
			{
				request.removeAttribute("products");
				request.setAttribute("products", model.doAll());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
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
