package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel 
{
	private static final String TABLE_NAME_UTENTE = "Utente";
	private static final String TABLE_NAME_DATI = "DatiSensibileUtente";
	private static final String TABLE_NAME_INDIRIZZI = "IndirizziSpedizione";
	private static final String TABLE_NAME_METODIPAGAMENTO = "MetodoPagamento";
	
	Logger logger = Logger.getLogger(UserModel.class.getName());
	
	public synchronized UserBean RicercaUtente(String email,String password) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		UserBean bean = new UserBean();

		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ? AND PasswordUtente = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
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
			if(con != null)
			{
				DBConnectionPool.releaseConnection(con);
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
	
	
	
	
	public synchronized boolean RicercaEmail(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		boolean trovato = false;
		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return trovato;			
	}
	
	
	public synchronized boolean RegistraUtente(String email,String password) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String SQL = "INSERT INTO " + UserModel.TABLE_NAME_UTENTE + " (Email, PasswordUtente, PuntiFedelta, Tipo) VALUES (?,?,0,1) ";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, email);
			ps.setString(2, password);

			rs = ps.executeUpdate();
			con.commit();


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
				DBConnectionPool.releaseConnection(con);
			}
		}
		

		return (rs != 0);
			
	}
	
	public synchronized boolean RegistraDatiSensibili(String email, String CF, String Nome, String Cognome, int CAP, String Citta, String Provincia, String Via, int Civico, String Telefono) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String SQL = "INSERT INTO " + UserModel.TABLE_NAME_DATI + " (CodiceFiscale, Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, CF);
			ps.setString(2, Nome);
			ps.setString(3, Cognome);
			ps.setInt(4, CAP);
			ps.setString(5, Via);
			ps.setInt(6, Civico);
			ps.setString(7, Citta);
			ps.setString(8, Provincia);
			ps.setString(9, Telefono);
			ps.setString(10, email);

			rs = ps.executeUpdate();
			con.commit();


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
				DBConnectionPool.releaseConnection(con);
			}
		}
		

		return (rs != 0);
			
	}
	
	
	public synchronized int getPuntiFedelta(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		int PuntiFedelta = 0;
		
		String SQL = "SELECT PuntiFedelta FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, email);
		   	ResultSet rs = ps.executeQuery();
			rs.next();
			PuntiFedelta = rs.getInt("PuntiFedelta");


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
				DBConnectionPool.releaseConnection(con);
			}
		}
		

		return PuntiFedelta;
			
	}
	
	
	public synchronized Collection<IndirizziSpedizioneBean> getIndirizziSpedizione(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_INDIRIZZI + " WHERE Email = ?";

		Collection<IndirizziSpedizioneBean> Indirizzi = new LinkedList<IndirizziSpedizioneBean>();
		
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
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
				DBConnectionPool.releaseConnection(con);
			}
		}

		return Indirizzi;
			
	}
	
	
	
	public synchronized Collection<MetodiPagamentoBean> getMetodiPagamento(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_METODIPAGAMENTO + " WHERE Email = ?";

		Collection<MetodiPagamentoBean> MetodoPagamento = new LinkedList<MetodiPagamentoBean>();
		
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				MetodiPagamentoBean bean = new MetodiPagamentoBean();
				bean.setNumeroCarta(rs.getString("NumeroCarta"));
				bean.setTitolareCarta(rs.getString("TitolareCarta"));
				bean.setScadenza(rs.getString("Scadenza"));
				bean.setEmail(rs.getString("Email"));
				MetodoPagamento.add(bean);
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
				DBConnectionPool.releaseConnection(con);
			}
		}

		return MetodoPagamento;
			
	}
	
	
	
	public synchronized UserBean RicercaDatiSensibili(String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		UserBean bean = new UserBean();
		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return bean;			
	}
	
	
	public synchronized Collection<UserBean> ElencoClienti() throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Tipo = 1";
		String SQL2 = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";
		
		Collection<UserBean> Clienti = new LinkedList<UserBean>();
		
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);

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
			    	ps2 = con.prepareStatement(SQL2);
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
				DBConnectionPool.releaseConnection(con);
			}
		}

		return Clienti;
			
	}
	
	
	
	public synchronized UserBean RicercaCliente(String Email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		String SQL = "SELECT * FROM " + UserModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		String SQL2 = "SELECT * FROM " + UserModel.TABLE_NAME_DATI + " WHERE Email = ?";
		
		UserBean Cliente = new UserBean();
		
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Email);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				Cliente.setEmail(rs.getString("Email"));
				Cliente.setPuntiFedelta(rs.getInt("PuntiFedelta"));
				Cliente.setTipo(rs.getBoolean("Tipo"));
			    
				try
				{
					ps2 = con.prepareStatement(SQL2);
					ps2.setString(1, Email);
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
				DBConnectionPool.releaseConnection(con);
			}
		}

		return Cliente;
			
	}

			
	public synchronized boolean RegistraNuovoIndirizzo(String Nome, String Cognome, int CAP, String Citta, String Provincia, String Via, int Civico, String Telefono, String email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;

		String SQL = "INSERT INTO " + UserModel.TABLE_NAME_INDIRIZZI + " (Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Nome);
			ps.setString(2, Cognome);
			ps.setInt(3, CAP);
			ps.setString(4, Via);
			ps.setInt(5, Civico);
			ps.setString(6, Citta);
			ps.setString(7, Provincia);
			ps.setString(8, Telefono);
			ps.setString(9, email);

			rs = ps.executeUpdate();
			con.commit();


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
				DBConnectionPool.releaseConnection(con);
			}
		}
		

		return (rs != 0);
			
	}
	
	
	
	public synchronized boolean RegistraNuovoMetodoPagamento(String NumeroCarta, String Intestatario, String DataScadenza, String Email) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int rs = 0;
		String SQL = "INSERT INTO " + UserModel.TABLE_NAME_METODIPAGAMENTO + " (NumeroCarta, TitolareCarta, Scadenza, Email) VALUES (?, ?, ?, ?) ";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, NumeroCarta);
			ps.setString(2, Intestatario);
			ps.setString(3, DataScadenza);
			ps.setString(4, Email);
			rs = ps.executeUpdate();
			con.commit();
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		

		return (rs != 0);
			
	}
	
	
	public synchronized boolean ModCodiceFiscale(String email,String CF) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET CodiceFiscale = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, CF);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();               
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
		
	}
	
	public synchronized boolean ModNome(String email,String Nome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Nome = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Nome);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();   
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModCognome(String email,String Cognome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Cognome = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Cognome);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();     
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModCAP(String email,int CAP) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET CAP = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1, CAP);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();        
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModCitta(String email,String Citta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Citta = ? " + "WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Citta);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();        
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModProvincia(String email,String Provincia) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Provincia = ? " + "WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Provincia);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();      
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModVia(String email,String Via) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = " UPDATE " + UserModel.TABLE_NAME_DATI + " SET Via = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Via);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();        
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModCivico(String email,int Civico) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET Civico = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1, Civico);
            pstmt.setString(2, email);  
            result = pstmt.executeUpdate();
            con.commit(); 
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean ModTelefono(String email,String Telefono) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try 
		{
			con = DBConnectionPool.getConnection();
			
			String updateQuery = "UPDATE " + UserModel.TABLE_NAME_DATI + " SET NumeroTelefono = ? " + " WHERE email = ?";

            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, Telefono);
            pstmt.setString(2, email);
            result = pstmt.executeUpdate();
            con.commit();        
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
				DBConnectionPool.releaseConnection(con);
			}
		}
		return (result != 0);
	}

}
