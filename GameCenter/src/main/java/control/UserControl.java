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
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;


@WebServlet("/UserControl")
public class UserControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	static UserModel userModel = new UserModel();
	static OrdineModel ordineModel = new OrdineModel();
	
	Logger logger = Logger.getLogger(UserControl.class.getName());
       
    public UserControl() 
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
				if (action.equalsIgnoreCase("Login")) 
				{
					UserBean utente = null;
					String email = request.getParameter("email");
					String pass = request.getParameter("password");
					
					if (email==null || pass==null )
					{
						response.sendRedirect("./index.jsp");
					}
						
					else 
					{
						utente = userModel.ricercaUtente(email, pass);
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
					boolean trovato = userModel.ricercaEmail(email);
					if(trovato)
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "L'email risulta già registarata. Riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registrati.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						if(userModel.registraUtente(email, pass))
						{
							utente = userModel.ricercaUtente(email, pass);
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
				
				
				
				if (action.equalsIgnoreCase("RegistraDatiSensibili")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					String codiceFiscale = request.getParameter("CF");
					String nome = request.getParameter("Nome");
					String cognome = request.getParameter("Cognome");
					int cap = Integer.parseInt(request.getParameter("CAP"));
					String citta = request.getParameter("Citta");
					String provincia = request.getParameter("Provincia");
					String via = request.getParameter("Via");
					int civico = Integer.parseInt(request.getParameter("Civico"));
					String telefono = request.getParameter("Telefono");

					if(userModel.registraDatiSensibili(email, codiceFiscale, nome, cognome, cap, citta, provincia, via, civico, telefono))
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
				
				if (action.equalsIgnoreCase("VisualizzaUtenti")) 
				{
					if(request.getParameter("Visual") != null && request.getParameter("Visual").contentEquals("tutti"))
					{
						request.removeAttribute("Clienti");
						request.setAttribute("Clienti", userModel.elencoClienti());
					}
					else if (request.getParameter("Visual") != null && request.getParameter("Visual").contentEquals("cliente") )
					{
						request.removeAttribute("Visual");
						request.setAttribute("Visual", request.getParameter("Visual"));
					}
					if (request.getParameter("email") != null )
					{
						String email = request.getParameter("email");
						request.removeAttribute("Cliente");
						request.setAttribute("Cliente", userModel.ricercaCliente(email));
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzazioneClienti.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				if (action.equalsIgnoreCase("PuntiFedelta"))
				{
					String email = (String) request.getSession().getAttribute("Email");
			        int PF = UserModel.getPuntiFedelta(email);
			        
			        response.setContentType("application/json");
			        response.setCharacterEncoding("UTF-8");
			        PrintWriter out = response.getWriter();
			        out.print("{\"PF\": " + new Gson().toJson(PF) + "}");
			        out.flush();
				}
				
				
				if (action.equalsIgnoreCase("VisualizzaDati"))
				{
					String email = (String) request.getSession().getAttribute("Email");
					request.removeAttribute("Cliente");	
					request.setAttribute("Cliente", userModel.ricercaCliente(email));
					request.removeAttribute("PuntiFedelta");
					request.setAttribute("PuntiFedelta", userModel.getPuntiFedelta(email));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
					dispatcher.forward(request, response);	
				}
				
				
				
				if (action.equalsIgnoreCase("ModificaDati"))
				{
						String email = (String) request.getSession().getAttribute("Email");
						Boolean rsCodiceFiscale = false;
						Boolean rsNome = false;
						Boolean rsCognome = false; 
						Boolean rsCap = false; 
						Boolean rsCitta = false;
						Boolean rsProvincia = false;
						Boolean rsVia = false;
						Boolean rsCivico = false;
						Boolean rsNumeroTelefono = false;

						if(!(request.getParameter("CodiceFiscale").isEmpty()))
						{	
							rsCodiceFiscale = userModel.modCodiceFiscale(email, request.getParameter("CodiceFiscale"));
						}
						else
						{
							rsCodiceFiscale = true;
						}
						
						if(!(request.getParameter("Nome").isEmpty()))
						{	
							rsNome = userModel.modNome(email, request.getParameter("Nome"));
						}
						else
						{
							rsNome = true;
						}
						
						if(!(request.getParameter("Cognome").isEmpty()))
						{	
							rsCognome = userModel.modCognome(email, request.getParameter("Cognome"));
						}
						else
						{
							rsCognome = true;
						}
						
						if(!(request.getParameter("CAP").isEmpty()))
						{	
							int cap = Integer.parseInt(request.getParameter("CAP"));
							rsCap = userModel.modCap(email, cap);
						}
						else
						{
							rsCap = true;
						}
						
						if(!(request.getParameter("Citta").isEmpty()))
						{	
							rsCitta = userModel.modCitta(email, request.getParameter("Citta"));
						}
						else
						{
							rsCitta = true;
						}
						
						if(!(request.getParameter("Provincia").isEmpty()))
						{	
							rsProvincia = userModel.modProvincia(email, request.getParameter("Provincia"));
						}
						else
						{
							rsProvincia = true;
						}
						
						if(!(request.getParameter("Via").isEmpty()))
						{	
							rsVia = userModel.modVia(email, request.getParameter("Via"));
						}
						else
						{
							rsVia = true;
						}
						
						if(!(request.getParameter("Civico").isEmpty()))
						{	
							int civico = Integer.parseInt(request.getParameter("Civico"));
							rsCivico = userModel.modCivico(email, civico);
						}
						else
						{
							rsCivico = true;
						}
						
						if(!(request.getParameter("NumeroTelefono").isEmpty()))
						{	
							rsNumeroTelefono = userModel.modTelefono(email, request.getParameter("NumeroTelefono"));
						}
						else
						{
							rsNumeroTelefono = true;
						}
						
						
						
						if(rsCodiceFiscale && rsNome && rsCognome && rsCap && rsCitta && rsProvincia && rsVia && rsCivico && rsNumeroTelefono)
						{
							request.setAttribute("Result", "I tuoi dati sono stati modificati correttamente.");
						}
						else
						{
							request.setAttribute("Result", "Errore imprevisto, riprova.");
						}			
						
						request.removeAttribute("Cliente");	
						request.setAttribute("Cliente", userModel.ricercaCliente(email));
						request.removeAttribute("PuntiFedelta");
						request.setAttribute("PuntiFedelta", userModel.getPuntiFedelta(email));
						request.removeAttribute("Ordini");
						request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
						dispatcher.forward(request, response);
				}
				
				
				
				if (action.equalsIgnoreCase("NuovoIndirizzo")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					String nome = request.getParameter("Nome");
					String cognome = request.getParameter("Cognome");
					int cap = Integer.parseInt(request.getParameter("CAP"));
					String citta = request.getParameter("Citta");
					String provincia = request.getParameter("Provincia");
					String via = request.getParameter("Via");
					int civico = Integer.parseInt(request.getParameter("Civico"));
					String telefono = request.getParameter("Telefono");

					if(userModel.registraNuovoIndirizzo(nome, cognome, cap, citta, provincia, via, civico, telefono, email))
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
				
				if (action.equalsIgnoreCase("RicercaIndirizzi")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					request.removeAttribute("Indirizzi");
					request.setAttribute("Indirizzi", userModel.getIndirizziSpedizione(email));					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IndirizziSpedizione.jsp");
					dispatcher.forward(request, response);
				}
				
				if (action.equalsIgnoreCase("RicercaMetodi")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					request.removeAttribute("MetodiPagamento");
					request.setAttribute("MetodiPagamento", userModel.getMetodiPagamento(email));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MetodiPagamento.jsp");
					dispatcher.forward(request, response);
				}
				
				
				
				if (action.equalsIgnoreCase("NuovoMetodoPagamento")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					String numeroCarta = request.getParameter("NumeroCarta");
					String titolare = request.getParameter("Titolare");
					String dataScadenza = request.getParameter("DataScadenza");

					if(userModel.registraNuovoMetodoPagamento(numeroCarta, titolare, dataScadenza, email))
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
