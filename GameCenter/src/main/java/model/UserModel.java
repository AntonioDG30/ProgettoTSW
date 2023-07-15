package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserModel 
{
	static Logger logger = Logger.getLogger(UserModel.class.getName());
	private static DataSource ds;

	static 
	{
		try 
		{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/GameCenter");

		} 
		catch (NamingException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	
	
	private static final String TABLE_NAME_UTENTE = "Utente";
	private static final String TABLE_NAME_DATI = "DatiSensibileUtente";
	private static final String TABLE_NAME_INDIRIZZI = "IndirizziSpedizione";
	private static final String TABLE_NAME_METODIPAGAMENTO = "MetodoPagamento";
	

	
	public synchronized UserBean ricercaUtente(String email,String password) throws SQLException 
	{
		PreparedStatement ps = null;
		UserBean bean = new UserBean();
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ? AND PasswordUtente = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				bean.setEmail(rs.getString("Email"));
				bean.setPassword(rs.getString("PasswordUtente"));
				bean.setPuntiFedelta(rs.getInt("PuntiFedelta"));
			    bean.setTipo(rs.getBoolean("Tipo"));
			}
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		if(bean.getEmail().trim().equalsIgnoreCase("") || bean==null)
		{
			return null;
		}
		else
		{
			return bean;
		}
			
	}
	
	
	
	
	public synchronized boolean ricercaEmail(String email) throws SQLException 
	{
		PreparedStatement ps = null;
		boolean trovato = false;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) 
			{
				trovato = true;
			}
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		return trovato;			
	}
	
	
	public synchronized boolean registraUtente(UserBean utente) throws SQLException 
	{
		PreparedStatement ps = null;
		int rs = 0;
		boolean result = false;
		String sql = "INSERT INTO " + UserModel.TABLE_NAME_UTENTE + " (Email, PasswordUtente, PuntiFedelta, Tipo) VALUES (?,?,0,1) ";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, utente.getEmail());
			ps.setString(2, utente.getPassword());
			rs = ps.executeUpdate();
			if (rs != 0)
			{
				result = registraDatiSensibili(utente);
			}
			
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		

		return result;
			
	}
	
	
	public synchronized boolean registraDatiSensibili(UserBean utente) throws SQLException 
	{
		PreparedStatement ps = null;

		int rs = 0;
		String sql = "INSERT INTO " + UserModel.TABLE_NAME_DATI + " (CodiceFiscale, Nome, Cognome, Immagine, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, utente.getCodiceFiscale());
			ps.setString(2, utente.getNome());
			ps.setString(3, utente.getCognome());
			ps.setString(4, utente.getImmagine());
			ps.setInt(5, utente.getCAP());
			ps.setString(6, utente.getVia());
			ps.setInt(7, utente.getCivico());
			ps.setString(8, utente.getCitta());
			ps.setString(9, utente.getProvincia());
			ps.setString(10, utente.getNumeroTelefono());
			ps.setString(11, utente.getEmail());
			rs = ps.executeUpdate();
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		return (rs != 0);
			
	}
	
	
	public static synchronized  int getPuntiFedelta(String email) throws SQLException 
	{
		PreparedStatement ps = null;
		
		int puntiFedelta = 0;
		
		String sql = "SELECT PuntiFedelta FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
		   	ResultSet rs = ps.executeQuery();
			rs.next();
			puntiFedelta = rs.getInt("PuntiFedelta");
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		

		return puntiFedelta;
			
	}
	
	
	public synchronized Collection<IndirizziSpedizioneBean> getIndirizziSpedizione(String email) throws SQLException 
	{
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_INDIRIZZI + " WHERE Email = ?";
		Collection<IndirizziSpedizioneBean> indirizzi = new LinkedList<>();
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				IndirizziSpedizioneBean bean = new IndirizziSpedizioneBean();
				bean.setCodIndirizzo(rs.getInt("CodIndirizzo"));
				bean.setEmail(rs.getString("Email"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setCAP(rs.getInt("CAP"));
				bean.setVia(rs.getString("Via"));
				bean.setCivico(rs.getInt("Civico"));
				bean.setCitta(rs.getString("Citta"));
				bean.setProvincia(rs.getString("Provincia"));
				bean.setNumeroTelefono(rs.getString("NumeroTelefono"));
				indirizzi.add(bean);
			}

		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		return indirizzi;
			
	}
	
	
	
	public synchronized Collection<MetodiPagamentoBean> getMetodiPagamento(String email) throws SQLException 
	{
		PreparedStatement ps = null;
		
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_METODIPAGAMENTO + " WHERE Email = ?";

		Collection<MetodiPagamentoBean> metodoPagamento = new LinkedList<>();
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				MetodiPagamentoBean bean = new MetodiPagamentoBean();
				bean.setNumeroCarta(rs.getString("NumeroCarta"));
				bean.setTitolareCarta(rs.getString("TitolareCarta"));
				bean.setScadenza(rs.getString("Scadenza"));
				bean.setEmail(rs.getString("Email"));
				metodoPagamento.add(bean);
			}

		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		return metodoPagamento;
			
	}
	
	public synchronized UserBean ricercaDatiSensibili(String email) throws SQLException 
	{
		PreparedStatement ps = null;
		UserBean bean = new UserBean();
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				bean.setCodiceFiscale(rs.getString("CodiceFiscale"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setCAP(rs.getInt("CAP"));
				bean.setVia(rs.getString("Via"));
				bean.setCivico(rs.getInt("Civico"));
				bean.setCitta(rs.getString("Citta"));
				bean.setProvincia(rs.getString("Provincia"));
				bean.setNumeroTelefono(rs.getString("NumeroTelefono"));
				
			}

		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		return bean;			
	}
	
	
	
	public synchronized Collection<UserBean> elencoClienti() throws SQLException 
	{
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Tipo = 1";
		Collection<UserBean> clienti = new LinkedList<>();
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				UserBean bean = new UserBean();
				bean.setEmail(rs.getString("Email"));
				bean.setPassword(rs.getString("PasswordUtente"));
				bean.setPuntiFedelta(rs.getInt("PuntiFedelta"));
			    bean.setTipo(rs.getBoolean("Tipo"));
			    bean = ottieniDatiUtente(bean, bean.getEmail());
			    clienti.add(bean);
			}

		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		return clienti;
			
	}
	
	public synchronized UserBean ottieniDatiUtente(UserBean bean,String email) throws SQLException 
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				bean.setCodiceFiscale(rs.getString("CodiceFiscale"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImmagine(rs.getString("Immagine"));
				bean.setCAP(rs.getInt("CAP"));
				bean.setVia(rs.getString("Via"));
				bean.setCivico(rs.getInt("Civico"));
				bean.setCitta(rs.getString("Citta"));
				bean.setProvincia(rs.getString("Provincia"));
				bean.setNumeroTelefono(rs.getString("NumeroTelefono"));
			}
		}
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		
		return bean;
		
	}
	
	
	public synchronized UserBean ricercaCliente(String email) throws SQLException 
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		UserBean cliente = new UserBean();
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				cliente.setEmail(rs.getString("Email"));
				cliente.setPuntiFedelta(rs.getInt("PuntiFedelta"));
				cliente.setTipo(rs.getBoolean("Tipo"));
				cliente = ottieniDatiUtente(cliente, cliente.getEmail());
			}	  
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}

		return cliente;
			
	}

			
	public synchronized boolean registraNuovoIndirizzo(String nome, String cognome, int cap, String citta, String provincia, String via, int civico, String telefono, String email) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int rs = 0;

		String sql = "INSERT INTO " + UserModel.TABLE_NAME_INDIRIZZI + " (Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, cognome);
			ps.setInt(3, cap);
			ps.setString(4, via);
			ps.setInt(5, civico);
			ps.setString(6, citta);
			ps.setString(7, provincia);
			ps.setString(8, telefono);
			ps.setString(9, email);

			rs = ps.executeUpdate();
			


		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}

		}
		

		return (rs != 0);
			
	}
	
	
	
	public synchronized boolean registraNuovoMetodoPagamento(String numeroCarta, String intestatario, String dataScadenza, String email) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int rs = 0;
		String sql = "INSERT INTO " + UserModel.TABLE_NAME_METODIPAGAMENTO + " (NumeroCarta, TitolareCarta, Scadenza, Email) VALUES (?, ?, ?, ?) ";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, numeroCarta);
			ps.setString(2, intestatario);
			ps.setString(3, dataScadenza);
			ps.setString(4, email);
			rs = ps.executeUpdate();
			
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		

		return (rs != 0);
			
	}
	
	
	public synchronized boolean modCodiceFiscale(String email,String codiceFiscale) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET CodiceFiscale = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, codiceFiscale);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                           
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());        
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}

		}
		return (result != 0);
		
	}
	
	public synchronized boolean modNome(String email,String nome) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Nome = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
               
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}	
		}
		return (result != 0);
	}
	
	public synchronized boolean modCognome(String email,String cognome) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Cognome = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, cognome);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                 
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}

		}
		return (result != 0);
	}
	
	public synchronized boolean modCap(String email,int cap) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET CAP = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1, cap);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                    
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}

		}
		return (result != 0);
	}
	
	public synchronized boolean modCitta(String email,String citta) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Citta = ? " + "WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, citta);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                    
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}

		}
		return (result != 0);
	}
	
	public synchronized boolean modProvincia(String email,String provincia) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Provincia = ? " + "WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, provincia);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                  
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modVia(String email,String via) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = " UPDATE " + UserModel.TABLE_NAME_DATI + " SET Via = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, via);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                    
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modCivico(String email,int civico) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Civico = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1, civico);
            pstmt.setString(2, email);  
            result = pstmt.executeUpdate();
             
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }	
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modTelefono(String email,String telefono) throws SQLException 
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try(Connection con = ds.getConnection())
		{
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET NumeroTelefono = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, telefono);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
                    
        } 
		catch (SQLException e) 
		{
			logger.log(Level.WARNING, e.getMessage());
        }
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
		}
		return (result != 0);
	}

}
