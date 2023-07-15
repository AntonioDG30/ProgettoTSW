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


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class OrdineModel 
{
	static Logger logger = Logger.getLogger(OrdineModel.class.getName());
	
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
	
	
	
	
	
	public synchronized Collection<OrdineBean> elencoOrdini() throws SQLException
	{
		
		PreparedStatement ps = null;
		Collection<OrdineBean> ordini = new LinkedList<>();
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE;
		try(Connection con = ds.getConnection())
		{
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
		}
		return ordini;
	}
	
	
	public synchronized Collection<OrdineBean> elencoOrdiniByCliente(String email) throws SQLException
	{
		
		PreparedStatement ps = null;
		Collection<OrdineBean> ordini = new LinkedList<>();
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE Email = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
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
		}
		return ordini;
	}
	
	
	public synchronized Collection<OrdineBean> elencoOrdiniByPeriodo(String dataInizio, String dataFine) throws SQLException
	{
		
		PreparedStatement ps = null;
		Collection<OrdineBean> ordini = new LinkedList<>();
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE (DataAcquisto BETWEEN ? AND ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, dataInizio);
			ps.setString(2, dataFine);
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
		}
		return ordini;
	}
	
	
	
	
	public synchronized Collection<ProductBean> dettagliOrdine(int codOrdine) throws SQLException
	{
		
		PreparedStatement ps = null;
		Collection<ProductBean> products = new LinkedList<>();
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE+ " WHERE CodOrdine = ?";		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				ProductBean bean = new ProductBean();
				bean.setQuantita(rs.getInt("Quantita"));
				bean.setCodSeriale(rs.getString("CodSeriale"));
				bean.setPrezzo(rs.getFloat("PrezzoMomento"));
				bean.setPiattaforma(rs.getString("Piattaforma"));
				bean = dettagliOrdineProdotto(bean);
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
		}
		return products;
	}
	
	
	public synchronized ProductBean dettagliOrdineProdotto(ProductBean bean) throws SQLException
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getCodSeriale());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				bean.setNome(rs.getString("Nome"));
				bean.setDescrizioneCompleta(rs.getString("DescrizioneCompleta"));
				bean.setImmagine(rs.getString("Immagine"));
				bean.setTipologia(rs.getBoolean("FlagTipologia"));
				if(!(bean.getTipologia()))
				{
					bean = dettagliOrdineInclude(bean);
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
		}
		
		return bean;
	}
	
	
	public synchronized ProductBean dettagliOrdineInclude(ProductBean bean) throws SQLException
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getCodSeriale());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				bean.setPegi(rs.getInt("CodPEGI"));
				bean.setGenere(rs.getString("NomeGenere"));
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
	
	
	
	public synchronized int acquisto(CarrelloBean carrello, float prezzoTotale, float puntiFedeltaUsati, String email) throws SQLException
	{
		
		PreparedStatement ps = null;

		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		int result4 = 0;
		int puntiFedelta =0;
		int codOrdine = 0;
		String piattaforma="";
   		String formato="";
		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_ORDINE + " (Sconto, DataAcquisto, PrezzoTotale, StatoOrdine, Email) VALUES (?, ?, ?, 'In Lavorazione', ?)";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setFloat(1, -(puntiFedeltaUsati/100));
			ps.setString(2, LocalDate.now().toString());
			ps.setFloat(3, (prezzoTotale - (puntiFedeltaUsati/100)));
			ps.setString(4, email);
			result1 = ps.executeUpdate();
			codOrdine = ottieniUltimoCodOrdine();	
			
			List<ProductBean> prodottoCarrello = carrello.getListaCarrello(); 	
		   	for(ProductBean prod: prodottoCarrello) 
		   	{
		   			result2 = acquistaInInclude(prod, codOrdine, prod.getPiattaforma());
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
		   	
		   	puntiFedelta = ottieniPuntiFedelta(email);
		   	puntiFedelta = (int) (puntiFedelta + (prezzoTotale - puntiFedeltaUsati));
		   	result4 = modPuntiFedelta(puntiFedelta, email);

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
		
        if (result1 != 0 && result2 != 0 && result3 != 0 && result4 != 0)
        {
        	return codOrdine;
        }
        else
        {
        	return 0;
        }
		
	}


	
	public synchronized int ottieniUltimoCodOrdine() throws SQLException
	{
		
		PreparedStatement ps = null;
		int codOrdine = 5;
		String sql = "SELECT MAX(codOrdine) as codOrdine FROM ordine";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
	   		ResultSet rs = ps.executeQuery();
			rs.next();
			codOrdine = rs.getInt("CodOrdine");
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
		return codOrdine;
	}
	
	
	public synchronized int acquistaInInclude(ProductBean prod, int codOrdine, String piattaforma) throws SQLException
	{
		
		PreparedStatement ps = null;
		int result = 0;
		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE + " (Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma) VALUES (?, ?, ?, ?, ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
	   		ps.setInt(1, prod.getQuantita());
	   		ps.setString(2, prod.getCodSeriale());
	   		ps.setInt(3, codOrdine);
	   		ps.setFloat(4, prod.getPrezzo());
	   		ps.setString(5, piattaforma);
	   		result = ps.executeUpdate();
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
	
	
	
	public synchronized int ottieniPuntiFedelta(String email) throws SQLException
	{
		
		PreparedStatement ps = null;
		int puntiFedelta = 0;
		String sql = "SELECT PuntiFedelta FROM " + OrdineModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
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
	
	
	public synchronized int modPuntiFedelta(int puntiFedelta, String email) throws SQLException
	{
		
		PreparedStatement ps = null;
		int result = 0;
		String sql = "UPDATE " + OrdineModel.TABLE_NAME_UTENTE + " SET PuntiFedelta = ? WHERE Email = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
	   		ps.setInt(1, puntiFedelta);
	   		ps.setString(2, email);
	   		result = ps.executeUpdate();
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
	
	
	
	public synchronized boolean modDisponibilita(String codSerialeMod, int quantita, String piattaforma, String formato) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + OrdineModel.TABLE_NAME_DISPONIBILITA + " SET QuantitaDisponibile = QuantitaDisponibile - ? " + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantita);
			ps.setString(2, codSerialeMod);
			ps.setString(3, piattaforma);
			ps.setString(4, formato);
			result = ps.executeUpdate();
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
		return (result != 0);
	}
	
	public synchronized void updateComprende(int codOrdine,  int codIndirizzo, String numeroCarta) throws SQLException
	{
		
		PreparedStatement ps = null;		

		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_METODI_INDIRIZZI_ORDINE + " (CodOrdine, CodIndirizzo, NumeroCarta) VALUES (?, ?, ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setInt(1, codOrdine);
			ps.setInt(2, codIndirizzo);
			ps.setString(3, numeroCarta);
			ps.executeUpdate();
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
		
	}
	
	
	
	public synchronized void updateFattura(int codOrdine) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		String path = "Fattura" + codOrdine + ".pdf";
		

		String sql = "UPDATE " + OrdineModel.TABLE_NAME_ORDINE + " SET Fattura = ? WHERE CodOrdine = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, path);
			ps.setInt(2, codOrdine);
			ps.executeUpdate();
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
		
	}
	
	
	
	
	public synchronized String ricercaFattura(int codOrdine) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		String pdf = "";
		
		String sql = "SELECT Fattura FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try(Connection con = ds.getConnection())
		{
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
		}
		return pdf;
	}
	
	
	
	
	
	
	
	public synchronized OrdineBean ordineByCodOrdine(int codOrdine) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		OrdineBean bean = new OrdineBean();
		
		String sql = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try(Connection con = ds.getConnection())
		{
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
		}
		return bean;
	}
	
	
	public synchronized boolean recensione(int valutazione, String descrizione, String codProdotto, String email) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		int result = 0;

		String sql = "INSERT INTO " + OrdineModel.TABLE_NAME_RECENSIONE + " (Descrizione, Valutazione, CodSeriale, Email) VALUES (?, ?, ?, ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, descrizione);
			ps.setInt(2, valutazione);
			ps.setString(3, codProdotto);
			ps.setString(4, email);
			result = ps.executeUpdate();	
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
		
		return (result != 0);
	}

}
