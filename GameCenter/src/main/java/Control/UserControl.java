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
	static OrdineModel Omodel = new OrdineModel();
	static ProductModel Pmodel = new ProductModel();
       
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
			
			
			
			
			
			if (action.equalsIgnoreCase("VisualizzaUtenti")) 
			{
				if(request.getParameter("ParteMod").contentEquals("Parte1"))
				{
					try 
					{
						String Visualizzazione = request.getParameter("VisualizzazioneUtente");
						
						if(Visualizzazione.contentEquals("Tutti"))
						{
							request.removeAttribute("Clienti");
							request.setAttribute("Clienti", model.ElencoClienti());
						}
						else if (Visualizzazione.contentEquals("ClienteSpecifico"))
						{
							request.removeAttribute("Clienti");
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
						String Email = request.getParameter("email");
						request.removeAttribute("Cliente");
						request.setAttribute("Cliente", model.RicercaCliente(Email));
					} 
					catch (SQLException e) 
					{
						System.out.println("Error:" + e.getMessage());
					}
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin.jsp");
				dispatcher.forward(request, response);	
			}
			
			
			
			if (action.equalsIgnoreCase("VisualizzaDati"))
			{
				try 
				{
					String Email = (String) request.getSession().getAttribute("Email");
					request.removeAttribute("Cliente");	
					request.setAttribute("Cliente", model.RicercaCliente(Email));
					request.removeAttribute("PuntiFedelta");
					request.setAttribute("PuntiFedelta", model.getPuntiFedelta(Email));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(Email));
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
				dispatcher.forward(request, response);	
			}
			
			
			
			if (action.equalsIgnoreCase("ModificaDati"))
			{
					String email = (String) request.getSession().getAttribute("Email");
					String CF = request.getParameter("CodiceFiscale");
					String Nome = request.getParameter("Nome");
					String Cognome = request.getParameter("Cognome");
					int CAP = Integer.parseInt(request.getParameter("CAP"));
					String Citta = request.getParameter("Citta");
					String Provincia = request.getParameter("Provincia");
					String Via = request.getParameter("Via");
					int Civico = Integer.parseInt(request.getParameter("Civico"));
					String Telefono = request.getParameter("NumeroTelefono");
				try
				{
					if(model.ModificaCliente(email,CF,Nome,Cognome,CAP,Citta,Provincia,Via,Civico,Telefono))
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Dati modificati correttamente");
					}
					else
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Errore modifica dati sesnsibili. Riprova");
					}
					request.removeAttribute("Cliente");	
					request.setAttribute("Cliente", model.RicercaCliente(email));
					request.removeAttribute("PuntiFedelta");
					request.setAttribute("PuntiFedelta", model.getPuntiFedelta(email));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", Omodel.ElencoOrdiniByCliente(email));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
					dispatcher.forward(request, response);	
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			
			
			if (action.equalsIgnoreCase("NuovoIndirizzo")) 
			{
				String email = (String) request.getSession().getAttribute("Email");
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
					if(model.RegistraNuovoIndirizzo(Nome, Cognome, CAP, Citta, Provincia, Via, Civico, Telefono, email))
					{
						response.sendRedirect("./OrdiniControl?action=Checkout");
					}
					else
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Errore salvataggio Indirizzo. Riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistraIndirizzo.jsp");
						dispatcher.forward(request, response);
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("Error:" + e.getMessage());
				}
				
			
			}
			
			
			
			if (action.equalsIgnoreCase("NuovoMetodoPagamento")) 
			{
				String email = (String) request.getSession().getAttribute("Email");
				String NumeroCarta = request.getParameter("NumeroCarta");
				String Titolare = request.getParameter("Titolare");
				String DataScadenza = request.getParameter("DataScadenza");
				try 
				{
					if(model.RegistraNuovoMetodoPagamento(NumeroCarta, Titolare, DataScadenza, email))
					{
						response.sendRedirect("./OrdiniControl?action=Checkout");
					}
					else
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Errore salvataggio Metodo Pagamento. Riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistraMetodoPagamento.jsp");
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
