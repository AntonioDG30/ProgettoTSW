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


@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	static OrdineModel model = new OrdineModel();
       
    
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
				request.removeAttribute("Result");
				request.setAttribute("Result", "Andremo ai dettagli");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
				dispatcher.forward(request, response);	
			}
		}
		else
		{
			try 
			{
			    String Email=(String) request.getSession().getAttribute("Email");
				request.removeAttribute("Ordini");
				request.setAttribute("Ordini", model.ElencoOrdiniByCliente(Email));
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
