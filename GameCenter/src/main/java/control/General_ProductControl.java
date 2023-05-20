package control;

import model.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet("/General_ProductControl")
public class General_ProductControl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	
	static ProductModel model = new ProductModel();
       
   
    public General_ProductControl() 
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
					request.setAttribute("product", model.Dettagli(CodSeriale));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliProdotto.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				
				if (action.equalsIgnoreCase("AggiungiCarrello")) 
				{
					String CodSeriale = request.getParameter("CodSeriale");
					String Piattaforma = request.getParameter("Piattaforma");
					Carrello.AggiungiProdotto(model.Dettagli(CodSeriale), Piattaforma);
					request.getSession().setAttribute("Carrello", Carrello);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				
				if (action.equalsIgnoreCase("RimuoviCarrello")) 
				{
					String CodSeriale = request.getParameter("CodSeriale");
					Carrello.RimuoviProdotto(model.Dettagli(CodSeriale));
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
			System.out.println("Error:" + e.getMessage());
		}
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
