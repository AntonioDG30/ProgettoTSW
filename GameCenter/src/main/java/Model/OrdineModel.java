package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class OrdineModel 
{
	private static final String TABLE_NAME_ORDINE = "Ordine";
	private static final String TABLE_NAME_PRODOTTI_INCLUSI_ORDINE = "Include";
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	
	
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

}
