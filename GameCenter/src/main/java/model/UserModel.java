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
			System.out.println("Error:" + e.getMessage());
		}
	}
	
	
	private static final String TABLE_NAME_UTENTE = "Utente";
	private static final String TABLE_NAME_DATI = "DatiSensibileUtente";
	private static final String TABLE_NAME_INDIRIZZI = "IndirizziSpedizione";
	private static final String TABLE_NAME_METODIPAGAMENTO = "MetodoPagamento";
	
	Logger logger = Logger.getLogger(UserModel.class.getName());
	
	public synchronized UserBean ricercaUtente(String email,String password) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		UserBean bean = new UserBean();

		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ? AND PasswordUtente = ?";

		try 
		{
			con = ds.getConnection();
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
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
			if(con != null)
			{
				con.close();
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
		Connection con = null;
		PreparedStatement ps = null;
		boolean trovato = false;
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";

		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}
		return trovato;			
	}
	
	
	public synchronized boolean registraUtente(String email,String password) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String sql = "INSERT INTO " + UserModel.TABLE_NAME_UTENTE + " (Email, PasswordUtente, PuntiFedelta, Tipo) VALUES (?,?,0,1) ";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);

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
			if(con != null)
			{
				con.close();
			}
		}
		

		return (rs != 0);
			
	}
	
	public synchronized boolean registraDatiSensibili(String email, String codiceFiscale, String nome, String cognome, int cap, String citta, String provincia, String via, int civico, String telefono) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String sql = "INSERT INTO " + UserModel.TABLE_NAME_DATI + " (CodiceFiscale, Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, codiceFiscale);
			ps.setString(2, nome);
			ps.setString(3, cognome);
			ps.setInt(4, cap);
			ps.setString(5, via);
			ps.setInt(6, civico);
			ps.setString(7, citta);
			ps.setString(8, provincia);
			ps.setString(9, telefono);
			ps.setString(10, email);

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
			if(con != null)
			{
				con.close();
			}
		}
		

		return (rs != 0);
			
	}
	
	
	public synchronized int getPuntiFedelta(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		int puntiFedelta = 0;
		
		String sql = "SELECT PuntiFedelta FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";

		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}
		

		return puntiFedelta;
			
	}
	
	
	public synchronized Collection<IndirizziSpedizioneBean> getIndirizziSpedizione(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_INDIRIZZI + " WHERE Email = ?";

		Collection<IndirizziSpedizioneBean> Indirizzi = new LinkedList<IndirizziSpedizioneBean>();
		
		try 
		{
			con = ds.getConnection();
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
				Indirizzi.add(bean);
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
			if(con != null)
			{
				con.close();
			}
		}

		return Indirizzi;
			
	}
	
	
	
	public synchronized Collection<MetodiPagamentoBean> getMetodiPagamento(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_METODIPAGAMENTO + " WHERE Email = ?";

		Collection<MetodiPagamentoBean> metodoPagamento = new LinkedList<MetodiPagamentoBean>();
		
		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}

		return metodoPagamento;
			
	}
	
	
	
	public synchronized UserBean ricercaDatiSensibili(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		UserBean bean = new UserBean();
		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";

		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}
		return bean;			
	}
	
	
	public synchronized Collection<UserBean> elencoClienti() throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Tipo = 1";
		String sql2 = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";
		
		Collection<UserBean> Clienti = new LinkedList<UserBean>();
		
		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				UserBean bean = new UserBean();
				bean.setEmail(rs.getString("Email"));
				bean.setPassword(rs.getString("PasswordUtente"));
				bean.setPuntiFedelta(rs.getInt("PuntiFedelta"));
			    bean.setTipo(rs.getBoolean("Tipo"));
			    
			    try
			    {
			    	ps2 = con.prepareStatement(sql2);
					ps2.setString(1, rs.getString("Email"));
					
					ResultSet rs2 = ps2.executeQuery();
					while (rs2.next()) 
					{
						bean.setCodiceFiscale(rs2.getString("CodiceFiscale"));
						bean.setNome(rs2.getString("Nome"));
						bean.setCognome(rs2.getString("Cognome"));
						bean.setCAP(rs2.getInt("CAP"));
						bean.setVia(rs2.getString("Via"));
						bean.setCivico(rs2.getInt("Civico"));
						bean.setCitta(rs2.getString("Citta"));
						bean.setProvincia(rs2.getString("Provincia"));
						bean.setNumeroTelefono(rs2.getString("NumeroTelefono"));
					}
			    }
			    catch(SQLException e)
				{
					logger.log(Level.WARNING, e.getMessage());
				}
				finally
				{
					if(ps2 != null)
					{
						ps2.close();
					}
				}
				Clienti.add(bean);
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
			if(con != null)
			{
				con.close();
			}
		}

		return Clienti;
			
	}
	
	
	
	public synchronized UserBean ricercaCliente(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		String sql = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		String sql2 = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";
		
		UserBean Cliente = new UserBean();
		
		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				Cliente.setEmail(rs.getString("Email"));
				Cliente.setPuntiFedelta(rs.getInt("PuntiFedelta"));
				Cliente.setTipo(rs.getBoolean("Tipo"));
			    
				try
				{
					ps2 = con.prepareStatement(sql2);
					ps2.setString(1, email);
					ResultSet rs2 = ps2.executeQuery();
					if(rs2.next())
					{
						Cliente.setCodiceFiscale(rs2.getString("CodiceFiscale"));
						Cliente.setNome(rs2.getString("Nome"));
						Cliente.setCognome(rs2.getString("Cognome"));
						Cliente.setCAP(rs2.getInt("CAP"));
						Cliente.setVia(rs2.getString("Via"));
						Cliente.setCivico(rs2.getInt("Civico"));
						Cliente.setCitta(rs2.getString("Citta"));
						Cliente.setProvincia(rs2.getString("Provincia"));
						Cliente.setNumeroTelefono(rs2.getString("NumeroTelefono"));
					}			
				}
				catch(SQLException e)
				{
					logger.log(Level.WARNING, e.getMessage());
				}
				finally
				{
					if(ps2 != null)
					{
						ps2.close();
					}
				} 
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
			if(con != null)
			{
				con.close();
			}
		}

		return Cliente;
			
	}

			
	public synchronized boolean registraNuovoIndirizzo(String nome, String cognome, int cap, String citta, String provincia, String via, int civico, String telefono, String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String sql = "INSERT INTO " + UserModel.TABLE_NAME_INDIRIZZI + " (Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}
		

		return (rs != 0);
			
	}
	
	
	
	public synchronized boolean registraNuovoMetodoPagamento(String numeroCarta, String intestatario, String dataScadenza, String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;
		String sql = "INSERT INTO " + UserModel.TABLE_NAME_METODIPAGAMENTO + " (NumeroCarta, TitolareCarta, Scadenza, Email) VALUES (?, ?, ?, ?) ";

		try 
		{
			con = ds.getConnection();
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
			if(con != null)
			{
				con.close();
			}
		}
		

		return (rs != 0);
			
	}
	
	
	public synchronized boolean modCodiceFiscale(String email,String codiceFiscale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
		
	}
	
	public synchronized boolean modNome(String email,String nome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modCognome(String email,String cognome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modCap(String email,int cap) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modCitta(String email,String citta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modProvincia(String email,String provincia) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modVia(String email,String via) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modCivico(String email,int civico) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modTelefono(String email,String telefono) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = ds.getConnection();
			
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}

}
