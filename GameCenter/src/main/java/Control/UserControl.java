package Control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.*;


@WebServlet("/UserControl")
public class UserControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	static UserModel model = new UserModel();
       
    public UserControl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action != null) 
		{
			if (action.equalsIgnoreCase("Login")) 
			{
				UserBean utente = null;
				try
				{
					String email = request.getParameter("email");
					String pass = request.getParameter("password");
					
					if (email==null || pass==null )
					{
						response.sendRedirect("./index.jsp");
					}
						
					else 
					{
						utente = model.RicercaUtente(email, pass);
						if(utente == null)
						{
							request.removeAttribute("Result");
							request.setAttribute("Result", "Credenziali Sbagliate. Riprova");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
							dispatcher.forward(request, response);
						}
						else
						{
							request.getSession().setAttribute("Email", utente.getEmail());
							request.getSession().setAttribute("Tipo", utente.getTipo());
							
							if(!(utente.getTipo()))
							{
								response.sendRedirect("./Admin.jsp");
							}
							else
							{
								request.getSession().setAttribute("PuntiFedelta", utente.getPuntiFedelta());
								response.sendRedirect("./index.jsp");
							}
						}								
					}					
				}
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
			}
			
			if (action.equalsIgnoreCase("Logout")) 
			{
				request.getSession().invalidate();
				response.sendRedirect("./index.jsp");
			}
			
			
			if (action.equalsIgnoreCase("Registrati")) 
			{
				String email = request.getParameter("email");
				String pass = request.getParameter("password");
				UserBean utente = null;
				try 
				{
					boolean Trovato = model.RicercaEmail(email);
					if(Trovato)
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "L'email risulta già registarata. Riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registrati.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						if(model.RegistraUtente(email, pass))
						{
							utente = model.RicercaUtente(email, pass);
							request.getSession().setAttribute("Email", utente.getEmail());
							request.getSession().setAttribute("Tipo", utente.getTipo());
							request.getSession().setAttribute("PuntiFedelta", utente.getPuntiFedelta());
							response.sendRedirect("./RegistraDatiSensibili.jsp");
						}
						else
						{
							request.removeAttribute("Result");
							request.setAttribute("Result", "Errore creazione utente. Riprova");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registrati.jsp");
							dispatcher.forward(request, response);
						}
						
					}
					
				}
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
			}
			
			
			
			if (action.equalsIgnoreCase("RegistraDatiSensibili")) 
			{
				String email = (String) request.getSession().getAttribute("Email");
				String CF = request.getParameter("CF");
				String Nome = request.getParameter("Nome");
				String Cognome = request.getParameter("Cognome");
				int CAP = Integer.parseInt(request.getParameter("CAP"));
				String Citta = request.getParameter("Citta");
				String Provincia = request.getParameter("Provincia");
				String Via = request.getParameter("Via");
				int Civico = Integer.parseInt(request.getParameter("Civico"));
				String Telefono = request.getParameter("Telefono");
				try 
				{
					if(model.RegistraDatiSensibili(email, CF, Nome, Cognome, CAP, Citta, Provincia, Via, Civico, Telefono))
					{
						response.sendRedirect("./index.jsp");
					}
					else
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Errore salvataggio dati sesnsibili. Riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistraDatiSensibili.jsp");
						dispatcher.forward(request, response);
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
				
			
			}
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
