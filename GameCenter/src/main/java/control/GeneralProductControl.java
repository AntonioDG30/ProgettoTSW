package control;

import model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

@MultipartConfig
@WebServlet("/GeneralProductControl")
public class GeneralProductControl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	
	static ProductModel productModel = new ProductModel();
	
	Logger logger = Logger.getLogger(GeneralProductControl.class.getName());
       
   
    public GeneralProductControl() 
    {
        super(); 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		CarrelloBean carrello = (CarrelloBean)request.getSession().getAttribute("Carrello");
		if(carrello == null)
		{
			carrello = new CarrelloBean();
			request.getSession().setAttribute("Carrello", carrello);
		}
		
		String action = request.getParameter("action");
		
		try
		{
			if(action != null) 
			{
				
				if (action.equalsIgnoreCase("RicercaSuggerimenti")) 
				{
					String ricerca = request.getParameter("ricerca");
			        List<String> suggerimenti = ProductModel.getSuggerimentiProdotti(ricerca);
			        response.setContentType("application/json");
			        response.setCharacterEncoding("UTF-8");
			        PrintWriter out = response.getWriter();
			        out.print("{\"suggerimenti\": " + new Gson().toJson(suggerimenti) + "}");
			        out.flush();
				}
				
				if (action.equalsIgnoreCase("Ricerca")) 
				{
					String ricerca = request.getParameter("ricerca");
					request.removeAttribute("products");
					request.setAttribute("products", ProductModel.getProdottiRicerca(ricerca));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Catalogo.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("Genere")) 
				{
					String nomeGenere = request.getParameter("nomeGenere");
					request.removeAttribute("products");
					request.setAttribute("products", ProductModel.getProdottiPerGenere(nomeGenere));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Catalogo.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("piattaforma")) 
				{
					String nomePiattaforma = request.getParameter("nomePiattaforma");
					request.removeAttribute("products");
					request.setAttribute("products", ProductModel.getProdottiPerPiattaforma(nomePiattaforma));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Catalogo.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("Catalogo")) 
				{
					request.removeAttribute("products");
					request.setAttribute("products", productModel.doAll());
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Catalogo.jsp");
					dispatcher.forward(request, response);
				}
				
				
				if (action.equalsIgnoreCase("Dettagli")) 
				{
					String codSeriale = request.getParameter("CodSeriale");
					request.removeAttribute("product");
					request.setAttribute("product", productModel.dettagli(codSeriale));
					request.setAttribute("recensioni", productModel.getRecensione(codSeriale));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliProdotto.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				
				if (action.equalsIgnoreCase("AggiungiCarrello")) 
				{
					String codSeriale = request.getParameter("CodSeriale");
					String piattaforma = request.getParameter("piattaforma");
					carrello.aggiungiProdotto(productModel.dettagli(codSeriale), piattaforma);
					request.getSession().setAttribute("Carrello", carrello);
					request.setAttribute("product", productModel.dettagli(codSeriale));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				
				if (action.equalsIgnoreCase("RimuoviCarrello")) 
				{
					String codSeriale = request.getParameter("CodSeriale");
					carrello.rimuoviProdotto(productModel.dettagli(codSeriale));
					request.getSession().setAttribute("Carrello", carrello);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
					dispatcher.forward(request, response);
				}
				
			}
			else
			{
				request.removeAttribute("products");
				request.setAttribute("products", productModel.doTop8());
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
