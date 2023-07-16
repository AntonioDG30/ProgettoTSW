package control;

import model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

@MultipartConfig
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
				
				if (action.equalsIgnoreCase("RicercaEmail")) 
				{
					String email = request.getParameter("email");
					boolean trovato = userModel.ricercaEmail(email);
			        response.setContentType("text/plain");
			        response.setCharacterEncoding("UTF-8");
			        if(trovato)
					{
			        	PrintWriter out = response.getWriter();
				        out.print("exists");
				        out.flush();
					}
			        else
			        {
			        	PrintWriter out = response.getWriter();
				        out.print("non exists");
				        out.flush();
			        }
			        
				}
				
				if (action.equalsIgnoreCase("RicercaCF")) 
				{
					String cf = request.getParameter("CF");
					boolean trovato = userModel.ricercaCF(cf);
			        response.setContentType("text/plain");
			        response.setCharacterEncoding("UTF-8");
			        if(trovato)
					{
			        	PrintWriter out = response.getWriter();
				        out.print("exists");
				        out.flush();
					}
			        else
			        {
			        	PrintWriter out = response.getWriter();
				        out.print("non exists");
				        out.flush();
			        }
			        
				}
				
				
				if (action.equalsIgnoreCase("Registrati")) 
				{

					UserBean utente = new UserBean();
					utente.setEmail(request.getParameter("Email"));
					utente.setPassword(request.getParameter("Password"));
					utente.setCodiceFiscale(request.getParameter("CF"));
					utente.setNome(request.getParameter("Nome"));
					utente.setCognome(request.getParameter("Cognome"));

					Part immaginePart = request.getPart("Immagine");
					String immagineFileName = immaginePart.getSubmittedFileName();
					String path = request.getServletContext().getRealPath("") + "ImgUser" + File.separator  +immagineFileName;
					FileOutputStream fos = new FileOutputStream(path);
					InputStream is = immaginePart.getInputStream();
					byte[] data = new byte[is.available()];
					if(is.read(data) > 0)
					{
						fos.write(data);
					}
					fos.close();
					utente.setImmagine(immagineFileName);
					
					utente.setCAP(Integer.parseInt(request.getParameter("CAP")));
					utente.setCitta(request.getParameter("Citta"));
					utente.setProvincia(request.getParameter("Provincia"));
					utente.setVia(request.getParameter("Via"));
					utente.setCivico(Integer.parseInt(request.getParameter("Civico")));
					utente.setNumeroTelefono(request.getParameter("Telefono"));
					if(userModel.registraUtente(utente))
					{
						UserBean user = userModel.ricercaUtente(request.getParameter("Email"), request.getParameter("Password"));
						if(user != null)
						{
							request.getSession().setAttribute("Email", user.getEmail());
							request.getSession().setAttribute("Tipo", user.getTipo());
							request.getSession().setAttribute("PuntiFedelta", user.getPuntiFedelta());
						}
						response.sendRedirect("./index.jsp");
					}
					else
					{
						request.removeAttribute("Result");
						request.setAttribute("Result", "Mi dispiace c'è stato un errore, controlla i dati e riprova");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registrati.jsp");
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
			        int pf = UserModel.getPuntiFedelta(email);
			        
			        response.setContentType("application/json");
			        response.setCharacterEncoding("UTF-8");
			        PrintWriter out = response.getWriter();
			        out.print("{\"PF\": " + new Gson().toJson(pf) + "}");
			        out.flush();
				}
				
				
				if (action.equalsIgnoreCase("VisualizzaDati"))
				{
					String email = (String) request.getSession().getAttribute("Email");
					request.removeAttribute("Cliente");	
					request.setAttribute("Cliente", userModel.ricercaCliente(email));
					request.removeAttribute("Ordini");
					request.setAttribute("Ordini", ordineModel.elencoOrdiniByCliente(email));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Account.jsp");
					dispatcher.forward(request, response);	
				}
				
				if (action.equalsIgnoreCase("EliminaIndirizzo")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					int codIndirizzo = Integer.parseInt(request.getParameter("CodIndirizzo"));
					if(userModel.eliminaIndirizzo(codIndirizzo))
					{
						request.removeAttribute("Indirizzi");
						request.setAttribute("Indirizzi", userModel.getIndirizziSpedizione(email));					
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IndirizziSpedizione.jsp");
						dispatcher.forward(request, response);
					}
				}
				
				if (action.equalsIgnoreCase("EliminaMetodo")) 
				{
					String email = (String) request.getSession().getAttribute("Email");
					String numeroCarta = request.getParameter("NumeroCarta");
					if(userModel.eliminaMetodo(numeroCarta))
					{
						request.removeAttribute("MetodiPagamento");
						request.setAttribute("MetodiPagamento", userModel.getMetodiPagamento(email));
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MetodiPagamento.jsp");
						dispatcher.forward(request, response);
					}
				}
				
				if (action.equalsIgnoreCase("NuovoIndirizzo")) 
				{
					IndirizziSpedizioneBean indirizzo = new IndirizziSpedizioneBean();
					String email = (String) request.getSession().getAttribute("Email");
					indirizzo.setEmail(email);
					indirizzo.setNome(request.getParameter("Nome"));
					indirizzo.setCognome(request.getParameter("Cognome"));
					indirizzo.setCAP(Integer.parseInt(request.getParameter("CAP")));
					indirizzo.setCitta(request.getParameter("Citta"));
					indirizzo.setProvincia(request.getParameter("Provincia"));
					indirizzo.setVia(request.getParameter("Via"));
					indirizzo.setCivico(Integer.parseInt(request.getParameter("Civico")));
					indirizzo.setNumeroTelefono(request.getParameter("Telefono"));


					if(userModel.registraNuovoIndirizzo(indirizzo))
					{
						response.sendRedirect("./UserControl?action=RicercaIndirizzi");
					}
					else
					{
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
						response.sendRedirect("./UserControl?action=RicercaMetodi");
					}
					else
					{
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
