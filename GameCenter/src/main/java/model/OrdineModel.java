package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrdineModel 
{
	private static final String TABLE_NAME_ORDINE = "Ordine";
	private static final String TABLE_NAME_PRODOTTI_INCLUSI_ORDINE = "Include";
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	private static final String TABLE_NAME_UTENTE = "Utente";
	private static final String TABLE_NAME_DISPONIBILITA = "Disponibilita";
	private static final String TABLE_NAME_METODI_INDIRIZZI_ORDINE = "Comprende";
	private static final String TABLE_NAME_RECENSIONE = "Recensione";
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XBOX_SERIE_X = "XBOX Series X";
	private static final String PIATTAFORMA_XBOX_SERIE_S = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
	
	Logger logger = Logger.getLogger(OrdineModel.class.getName());
	
	
	public synchronized Collection<OrdineBean> elencoOrdini() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE;
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setSconto(rs.getInt("Sconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				ordini.add(bean);
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
		return ordini;
	}
	
	
	public synchronized Collection<OrdineBean> elencoOrdiniByCliente(String Email) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE Email = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, Email);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setSconto(rs.getInt("Sconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				ordini.add(bean);
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
		return ordini;
	}
	
	
	public synchronized Collection<OrdineBean> elencoOrdiniByPeriodo(String DataInizio, String DataFine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE (DataAcquisto BETWEEN ? AND ?)";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, DataInizio);
			ps.setString(2, DataFine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setSconto(rs.getInt("Sconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				ordini.add(bean);
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
		return ordini;
	}
	
	
	
	
	public synchronized Collection<ProductBean> dettagliOrdine(int codOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE+ " WHERE CodOrdine = ?";		
		String sql2 = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		String sql3 = "SELECT * FROM " + OrdineModel.TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				ProductBean bean = new ProductBean();
				bean.setQuantita(rs.getInt("Quantita"));
				bean.setCodSeriale(rs.getString("CodSeriale"));
				try
				{
					ps2 = con.prepareStatement(sql2);
					ps2.setString(1, rs.getString("CodSeriale"));
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setNome(rs2.getString("Nome"));
						bean.setPrezzo(rs2.getFloat("Prezzo"));
						bean.setDescrizioneCompleta(rs2.getString("DescrizioneCompleta"));
						bean.setImmagine(rs2.getString("Immagine"));
						bean.setTipologia(rs2.getBoolean("FlagTipologia"));
						if(!(bean.getTipologia()))
						{
							try
							{
								ps3 = con.prepareStatement(sql3);
								ps3.setString(1, rs.getString("CodSeriale"));
								ResultSet rs3 = ps3.executeQuery();
								while(rs3.next()) 
								{
									bean.setPEGI(rs3.getInt("CodPEGI"));
									bean.setGenere(rs3.getString("NomeGenere"));
								}
							}
							catch(SQLException e)
							{
								logger.log(Level.WARNING, e.getMessage());
							}
							finally
							{
								if(ps3 != null)
								{
									ps3.close();
								}
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
					if(ps2 != null)
					{
						ps2.close();
					}
				}
				products.add(bean);
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
		return products;
	}
	
	
	
	
	
	public synchronized int acquisto(CarrelloBean Carrello, float PrezzoTotale, float PuntiFedeltàUsati, String Email) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		
		int result1 = 0, result2 = 0, result3 = 0, result4 = 0;
		int codOrdine = 0;
		int puntiFedelta =0;

		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_ORDINE + " (Sconto, DataAcquisto, PrezzoTotale, StatoOrdine, Email) VALUES (?, ?, ?, 'In Lavorazione', ?)";
		String sql2 = "SELECT LAST_INSERT_ID() AS CodOrdine";
		String sql3 = "INSERT INTO " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE + " (Quantita, CodSeriale, CodOrdine) VALUES (?, ?, ?)";
		String sql4 = "SELECT PuntiFedelta FROM " + OrdineModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		String sql5 = "UPDATE " + OrdineModel.TABLE_NAME_UTENTE + " SET PuntiFedelta = ? WHERE Email = ?";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps1 = con.prepareStatement(sql);
			ps1.setFloat(1, -(PuntiFedeltàUsati/100));
			ps1.setString(2, LocalDate.now().toString());
			ps1.setFloat(3, (PrezzoTotale - (PuntiFedeltàUsati/100)));
			ps1.setString(4, Email);
			result1 = ps1.executeUpdate();
	   		
	   		
	   		try
	   		{
	   			ps2 = con.prepareStatement(sql2);
		   		ResultSet rs2 = ps2.executeQuery();
				rs2.next();
				codOrdine = rs2.getInt("CodOrdine");
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
			
			
			List<ProductBean> prodottoCarrello = Carrello.getListaCarrello(); 	
		   	for(ProductBean prod: prodottoCarrello) 
		   	{
		   		try
		   		{
		   			ps2 = con.prepareStatement(sql3);
			   		ps2.setInt(1, prod.getQuantita());
			   		ps2.setString(2, prod.getCodSeriale());
			   		ps2.setInt(3, codOrdine);
			   		result2 = ps2.executeUpdate();
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

		   		String piattaforma="";
		   		String formato="";

		   		if (prod.getPiattaforma().contentEquals("Ps5 Digitale"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PS5;
		   			formato = OrdineModel.FORMATO_DIGITALE;
		   		}
		   		if (prod.getPiattaforma().contentEquals("Ps5 Fisico"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PS5;
		   			formato = OrdineModel.FORMATO_FISICO;
		   		}
		   		if (prod.getPiattaforma().contentEquals("Ps4 Digitale"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PS4;
		   			formato = OrdineModel.FORMATO_DIGITALE;
		   		}
		   		if (prod.getPiattaforma().contentEquals("Ps4 Fisico"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PS4;
		   			formato = OrdineModel.FORMATO_FISICO;
		   		}
		   		if (prod.getPiattaforma().contentEquals("XboxX Digitale"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_XBOX_SERIE_X ;
		   			formato = OrdineModel.FORMATO_DIGITALE;
		   		}
		   		if (prod.getPiattaforma().contentEquals("XboxX Fisico"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_XBOX_SERIE_X;
		   			formato = OrdineModel.FORMATO_FISICO;
		   		}
		   		if (prod.getPiattaforma().contentEquals("XboxS Digitale"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_XBOX_SERIE_S ;
		   			formato = OrdineModel.FORMATO_DIGITALE;
		   		}
		   		if (prod.getPiattaforma().contentEquals("Pc Digitale"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PC ;
		   			formato = OrdineModel.FORMATO_DIGITALE;
		   		}
		   		if (prod.getPiattaforma().contentEquals("Pc Fisico"))
		   		{
		   			piattaforma = OrdineModel.PIATTAFORMA_PC;
		   			formato = OrdineModel.FORMATO_FISICO;
		   		}
		   		
		   		if(modDisponibilita(prod.getCodSeriale(), prod.getQuantita(), piattaforma, formato))
		   		{
		   			result3 = 1;
		   		}
		   	}
			
		   	
			try
			{
				ps2 = con.prepareStatement(sql4);
			   	ps2.setString(1, Email);
			   	ResultSet rs2 = ps2.executeQuery();
				rs2.next();
				puntiFedelta = rs2.getInt("PuntiFedelta");
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
		   	
			
			
			try
			{
				puntiFedelta = (int) (puntiFedelta + (PrezzoTotale - PuntiFedeltàUsati));
			   	ps2 = con.prepareStatement(sql5);
		   		ps2.setInt(1, puntiFedelta);
		   		ps2.setString(2, Email);
		   		result4 = ps2.executeUpdate();
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
			

	   		
		   	con.commit();
			
		} 
		catch(SQLException e)
		{
			logger.log(Level.WARNING, e.getMessage());
		}
		finally
		{
			if(ps1 != null)
			{
				ps1.close();
			}
			if(con != null)
			{
				DBConnectionPool.releaseConnection(con);
			}
		}
		
        if (result1 != 0 && result2 != 0 && result3 != 0 && result4 != 0)
        {
        	return codOrdine;
        }
        else
        {
        	return 0;
        }
		
	}
	
	
	public synchronized boolean modDisponibilita(String CodSerialeMod, int quantita, String piattaforma, String formato) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + OrdineModel.TABLE_NAME_DISPONIBILITA + " SET QuantitaDisponibile = QuantitaDisponibile - ? " + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantita);
			ps.setString(2, CodSerialeMod);
			ps.setString(3, piattaforma);
			ps.setString(4, formato);
			result = ps.executeUpdate();
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
		return (result != 0);
	}
	
	public synchronized void updateComprende(int codOrdine,  int CodIndirizzo, String NumeroCarta) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;		

		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_METODI_INDIRIZZI_ORDINE + " (CodOrdine, CodIndirizzo, NumeroCarta) VALUES (?, ?, ?)";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);
			ps.setInt(2, CodIndirizzo);
			ps.setString(3, NumeroCarta);
			ps.executeUpdate();
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
		
	}
	
	
	
	public synchronized void updateFattura(int codOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String path = "Fattura" + codOrdine + ".pdf";
		

		String sql = "UPDATE " + OrdineModel.TABLE_NAME_ORDINE + " SET Fattura = ? WHERE CodOrdine = ?";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, path);
			ps.setInt(2, codOrdine);
			ps.executeUpdate();
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
		
	}
	
	
	
	
	public synchronized String ricercaFattura(int codOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String pdf = "";
		
		String sql = "SELECT Fattura FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);
			ResultSet rs = ps.executeQuery();
			rs.next();
			pdf = rs.getString("Fattura");
			
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
		return pdf;
	}
	
	
	
	public synchronized OrdineBean ordineByCodOrdine(int codOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		OrdineBean bean = new OrdineBean();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setSconto(rs.getInt("Sconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
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
	
	
	public synchronized boolean recensione(int Valutazione, String Descrizione, String CodProdotto, String Email) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;

		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_RECENSIONE + " (Descrizione, Valutazione, CodSeriale, Email) VALUES (?, ?, ?, ?)";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, Descrizione);
			ps.setInt(2, Valutazione);
			ps.setString(3, CodProdotto);
			ps.setString(4, Email);
			result = ps.executeUpdate();
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
		
		return (result != 0);
	}

}
