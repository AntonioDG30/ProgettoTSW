package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;


public class OrdineModel 
{
	private static final String TABLE_NAME_ORDINE = "Ordine";
	private static final String TABLE_NAME_PRODOTTI_INCLUSI_ORDINE = "Include";
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	private static final String TABLE_NAME_UTENTE = "Utente";
	
	
	public synchronized Collection<OrdineBean> ElencoOrdini() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> Ordini = new LinkedList<OrdineBean>();
		
		String SQL = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE;
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setPercentualeSconto(rs.getInt("PercentualeSconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				Ordini.add(bean);
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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
		return Ordini;
	}
	
	
	public synchronized Collection<OrdineBean> ElencoOrdiniByCliente(String Email) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> Ordini = new LinkedList<OrdineBean>();
		
		String SQL = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE Email = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Email);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setPercentualeSconto(rs.getInt("PercentualeSconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				Ordini.add(bean);
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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
		return Ordini;
	}
	
	
	public synchronized Collection<OrdineBean> ElencoOrdiniByPeriodo(String DataInizio, String DataFine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<OrdineBean> Ordini = new LinkedList<OrdineBean>();
		
		String SQL = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE (DataAcquisto BETWEEN ? AND ?)";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, DataInizio);
			ps.setString(2, DataFine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				OrdineBean bean = new OrdineBean();
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setPercentualeSconto(rs.getInt("PercentualeSconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
				Ordini.add(bean);
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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
		return Ordini;
	}
	
	
	
	
	public synchronized Collection<ProductBean> DettagliOrdine(int CodOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
		String SQL = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE+ " WHERE CodOrdine = ?";		
		String SQL2 = "SELECT * FROM " + OrdineModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		String SQL3 = "SELECT * FROM " + OrdineModel.TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, CodOrdine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				ProductBean bean = new ProductBean();
				bean.setQuantita(rs.getInt("Quantita"));
				bean.setCodSeriale(rs.getString("CodSeriale"));
				
				ps2 = con.prepareStatement(SQL2);
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
						ps3 = con.prepareStatement(SQL3);
						ps3.setString(1, rs.getString("CodSeriale"));
						ResultSet rs3 = ps3.executeQuery();
						while(rs3.next()) 
						{
							bean.setPEGI(rs3.getInt("CodPEGI"));
							bean.setGenere(rs3.getString("NomeGenere"));
						}
					}
				}
				
				products.add(bean);
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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
	
	
	public synchronized int Acquisto(CarrelloBean Carrello, float PrezzoTotale, String Email) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		
		
		int result1 = 0, result2 = 0, result3 = 1;
		int CodOrdine = 0;

		String SQL = "INSERT INTO " + OrdineModel.TABLE_NAME_ORDINE + " (PercentualeSconto, DataAcquisto, PrezzoTotale, StatoOrdine, Email) VALUES (0, ?, ?, 'In Lavorazione', ?)";
		String SQL2 = "SELECT LAST_INSERT_ID() AS CodOrdine";
		String SQL3 = "INSERT INTO " + OrdineModel.TABLE_NAME_PRODOTTI_INCLUSI_ORDINE + " (Quantita, CodSeriale, CodOrdine) VALUES (?, ?, ?)";
		String SQL4 = "SELECT PuntiFedelta FROM " + OrdineModel.TABLE_NAME_UTENTE + " WHERE Email = ?";
		String SQL5 = "UPDATE " + OrdineModel.TABLE_NAME_UTENTE + " SET PuntiFedelta = ? WHERE Email = ?";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps1 = con.prepareStatement(SQL);
			ps1.setString(1, LocalDate.now().toString());
			ps1.setFloat(2, PrezzoTotale);
			ps1.setString(3, Email);
			result1 = ps1.executeUpdate();
	   		
	   		
	   		
	   		ps2 = con.prepareStatement(SQL2);
	   		ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			CodOrdine = rs2.getInt("CodOrdine");
			
			
			
			List<ProductBean> ProdottoCarrello = Carrello.getListaCarrello(); 	
		   	for(ProductBean Prod: ProdottoCarrello) 
		   	{
		   		ps3 = con.prepareStatement(SQL3);
		   		ps3.setInt(1, Prod.getQuantita());
		   		ps3.setString(2, Prod.getCodSeriale());
		   		ps3.setInt(3, CodOrdine);
		   		result2 = ps3.executeUpdate();
		   	}
		   	
		   
		   	ps4 = con.prepareStatement(SQL4);
		   	ps4.setString(1, Email);
		   	ResultSet rs4 = ps4.executeQuery();
			rs4.next();
			int PuntiFedelta = rs4.getInt("PuntiFedelta");
			
			
		   	
			PuntiFedelta = (int) (PuntiFedelta + (PrezzoTotale/10));
		   	ps5 = con.prepareStatement(SQL5);
	   		ps5.setInt(1, PuntiFedelta);
	   		ps5.setString(2, Email);
	   		result3 = ps5.executeUpdate();
		   	
		   	con.commit();
			
		} 
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		finally
		{
			if(ps1 != null)
			{
				ps1.close();
			}
			if(ps2 != null)
			{
				ps2.close();
			}
			if(ps3 != null)
			{
				ps3.close();
			}
			if(con != null)
			{
				DBConnectionPool.releaseConnection(con);
			}
		}
		
        if (result1 != 0 && result2 != 0 && result3 != 0)
        {
        	return CodOrdine;
        }
        else
        {
        	return 0;
        }
		
	}
	
	
	
	
	public synchronized String RicercaFattura(int CodOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		String PDF = "";
		
		String SQL = "SELECT Fattura FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, CodOrdine);
			ResultSet rs = ps.executeQuery();
			rs.next();
			PDF = rs.getString("Fattura");
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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
		return PDF;
	}
	
	
	
	public synchronized OrdineBean OrdineByCodOrdine(int CodOrdine) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		OrdineBean bean = new OrdineBean();
		
		String SQL = "SELECT * FROM " + OrdineModel.TABLE_NAME_ORDINE + " WHERE CodOrdine = ?";
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, CodOrdine);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				
				bean.setCodOrdine(rs.getInt("CodOrdine"));
				bean.setPercentualeSconto(rs.getInt("PercentualeSconto"));
				bean.setDataAcquisto(rs.getString("DataAcquisto"));
				bean.setFattura(rs.getString("Fattura"));
				bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
				bean.setStatoOrdine(rs.getString("StatoOrdine"));
				bean.setEmail(rs.getString("Email"));
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
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

}
