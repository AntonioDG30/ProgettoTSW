package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ProductModel 
{
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	private static final String TABLE_NAME_DISPONIBILITA = "Disponibilita";
	private static final String TABLE_NAME_GENERE = "Genere";
	private static final String TABLE_NAME_PEGI = "PEGI";
	
	
	
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XboxSerieX = "XBOX Series X";
	private static final String PIATTAFORMA_XboxSerieS = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
	
	public synchronized Collection<ProductBean> doAll() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
		String SQL = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE FlagVisibita = 1";
		
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				ProductBean bean = new ProductBean();
				bean.setCodSeriale(rs.getString("CodSeriale"));
				bean.setNome(rs.getString("Nome"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setDescrizioneRidotta(rs.getString("DescrizioneRidotta"));
				bean.setImmagine(rs.getString("Immagine"));
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
	
	
	public synchronized ProductBean Dettagli(String CodSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		ProductBean bean = new ProductBean();

		String SQL = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		String SQL2 = "SELECT * FROM " + TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		String SQL3 = "SELECT * FROM " + TABLE_NAME_DISPONIBILITA + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, CodSeriale);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				bean.setCodSeriale(rs.getString("CodSeriale"));
				bean.setNome(rs.getString("Nome"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setDataUscita(rs.getString("DataUscita"));
				bean.setDescrizioneRidotta(rs.getString("DescrizioneRidotta"));
				bean.setDescrizioneCompleta(rs.getString("DescrizioneCompleta"));
				bean.setImmagine(rs.getString("Immagine"));
				bean.setTipologia(rs.getBoolean("FlagTipologia"));
				if(!rs.getBoolean("FlagTipologia"))
				{
					ps2 = con.prepareStatement(SQL2);
					ps2.setString(1, rs.getString("CodSeriale"));
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setPEGI(rs2.getInt("CodPEGI"));
						bean.setGenere(rs2.getString("NomeGenere"));
					}
					ps3 = con.prepareStatement(SQL3);
					ps3.setString(1, rs.getString("CodSeriale"));
					ps3.setString(2, PIATTAFORMA_PS5);
					ps3.setString(3, FORMATO_DIGITALE);
					ResultSet  rs3 = ps3.executeQuery();
					while(rs3.next()) 
					{
						bean.setDisp_Ps5_Digitale(rs3.getInt("QuantitaDisponibile"));
					}
					ps3 = con.prepareStatement(SQL3);
					ps3.setString(1, rs.getString("CodSeriale"));
					ps3.setString(2, PIATTAFORMA_PS4);
					ps3.setString(3, FORMATO_DIGITALE);
					rs3 = ps3.executeQuery();
					while(rs3.next()) 
					{
						bean.setDisp_Ps4_Digitale(rs3.getInt("QuantitaDisponibile"));
					}
					ps3 = con.prepareStatement(SQL3);
					ps3.setString(1, rs.getString("CodSeriale"));
					ps3.setString(2, PIATTAFORMA_XboxSerieX);
					ps3.setString(3, FORMATO_DIGITALE);
					rs3 = ps3.executeQuery();
					while(rs3.next()) 
					{
						bean.setDisp_XboxX_Digitale(rs3.getInt("QuantitaDisponibile"));
					}
					ps3 = con.prepareStatement(SQL3);
					ps3.setString(1, rs.getString("CodSeriale"));
					ps3.setString(2, PIATTAFORMA_XboxSerieS);
					ps3.setString(3, FORMATO_DIGITALE);
					rs3 = ps3.executeQuery();
					while(rs3.next()) 
					{
						bean.setDisp_XboxS_Digitale(rs3.getInt("QuantitaDisponibile"));
					}
					ps3 = con.prepareStatement(SQL3);
					ps3.setString(1, rs.getString("CodSeriale"));
					ps3.setString(2, PIATTAFORMA_PC);
					ps3.setString(3, FORMATO_DIGITALE);
					rs3 = ps3.executeQuery();
					while(rs3.next()) 
					{
						bean.setDisp_Pc_Digitale(rs3.getInt("QuantitaDisponibile"));
					}
				}
				ps3 = con.prepareStatement(SQL3);
				ps3.setString(1, rs.getString("CodSeriale"));
				ps3.setString(2, PIATTAFORMA_PS5);
				ps3.setString(3, FORMATO_FISICO);
				ResultSet rs3 = ps3.executeQuery();
				while(rs3.next()) 
				{
					bean.setDisp_Ps5_Fisico(rs3.getInt("QuantitaDisponibile"));
				}
				ps3 = con.prepareStatement(SQL3);
				ps3.setString(1, rs.getString("CodSeriale"));
				ps3.setString(2, PIATTAFORMA_PS4);
				ps3.setString(3, FORMATO_FISICO);
				rs3 = ps3.executeQuery();
				while(rs3.next()) 
				{
					bean.setDisp_Ps4_Fisico(rs3.getInt("QuantitaDisponibile"));
				}
				ps3 = con.prepareStatement(SQL3);
				ps3.setString(1, rs.getString("CodSeriale"));
				ps3.setString(2, PIATTAFORMA_XboxSerieX);
				ps3.setString(3, FORMATO_FISICO);
				rs3 = ps3.executeQuery();
				while(rs3.next()) 
				{
					bean.setDisp_XboxX_Fisico(rs3.getInt("QuantitaDisponibile"));
				}
				ps3 = con.prepareStatement(SQL3);
				ps3.setString(1, rs.getString("CodSeriale"));
				ps3.setString(2, PIATTAFORMA_XboxSerieS);
				ps3.setString(3, FORMATO_FISICO);
				rs3 = ps3.executeQuery();
				while(rs3.next()) 
				{
					bean.setDisp_XboxS_Fisico(rs3.getInt("QuantitaDisponibile"));
				}
				ps3 = con.prepareStatement(SQL3);
				ps3.setString(1, rs.getString("CodSeriale"));
				ps3.setString(2, PIATTAFORMA_PC);
				ps3.setString(3, FORMATO_FISICO);
				rs3 = ps3.executeQuery();
				while(rs3.next()) 
				{
					bean.setDisp_Pc_Fisico(rs3.getInt("QuantitaDisponibile"));
				}
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
		
		
		if(bean.getCodSeriale() != "")
		{
			return bean;
		}
		else
		{
			return null;
		}
	}
	
	
	public synchronized boolean Elimina(String CodSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET FlagVisibita = 0 " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, CodSeriale);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	public synchronized Collection<GenereBean> getGenereElements() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<GenereBean> Genere = new LinkedList<GenereBean>();
		
		String SQL = "SELECT * FROM " + ProductModel.TABLE_NAME_GENERE;
		
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				GenereBean bean = new GenereBean();
				bean.setNomeGenere(rs.getString("NomeGenere"));
				Genere.add(bean);
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
		return Genere;
	}
	
	
	public synchronized Collection<PegiBean> getPegiElements() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<PegiBean> PEGI = new LinkedList<PegiBean>();
		
		String SQL = "SELECT * FROM " + ProductModel.TABLE_NAME_PEGI;
		
		try
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				PegiBean bean = new PegiBean();
				bean.setCodPEGI(rs.getInt("CodPEGI"));
				PEGI.add(bean);
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
		return PEGI;
	}
	
	
	public synchronized boolean Inserisci(ProductBean product) throws SQLException 
	{

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		PreparedStatement ps8 = null;
		PreparedStatement ps9 = null;
		PreparedStatement ps10 = null;
		PreparedStatement ps11 = null;
		
		int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, result6 = 0, result7 = 0, result8 = 0, result9 = 0, result10 = 0, result11 = 0;

		String SQL = "INSERT INTO " + ProductModel.TABLE_NAME_PRODOTTO + " (CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
		try 
		{
			con = DBConnectionPool.getConnection();
			ps1 = con.prepareStatement(SQL);
			ps1.setString(1, product.getCodSeriale());
			ps1.setString(2, product.getNome());
			ps1.setFloat(3, product.getPrezzo());
			ps1.setString(4, product.getDataUscita());
			ps1.setString(5, product.getDescrizioneRidotta());
			ps1.setString(6, product.getDescrizioneCompleta());
			ps1.setString(7, product.getImmagine());
			ps1.setBoolean(8, product.getTipologia());
			result1 = ps1.executeUpdate();
			con.commit();
			
			
			if(!(product.getTipologia()))
			{
				String SQL2 = "INSERT INTO " + ProductModel.TABLE_NAME_CARATTERISTICHE + " (CodSeriale, NomeGenere, CodPEGI) VALUES (?, ?, ?)";
				ps2 = con.prepareStatement(SQL2);
				ps2.setString(1, product.getCodSeriale());
				ps2.setString(2, product.getGenere());
				ps2.setInt(3, product.getPEGI());
				result2 = ps2.executeUpdate();
				
				String SQL3 = "INSERT INTO " + ProductModel.TABLE_NAME_DISPONIBILITA + " (QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato) VALUES (?, ?, ?, ?)";
				ps3 = con.prepareStatement(SQL3);
				ps3.setInt(1, product.getDisp_Ps5_Digitale());
				ps3.setString(2, product.getCodSeriale());
				ps3.setString(3, PIATTAFORMA_PS5);
				ps3.setString(4, FORMATO_DIGITALE);
				result3 = ps3.executeUpdate();
				
				ps4 = con.prepareStatement(SQL3);
				ps4.setInt(1, product.getDisp_Ps4_Digitale());
				ps4.setString(2, product.getCodSeriale());
				ps4.setString(3, PIATTAFORMA_PS4);
				ps4.setString(4, FORMATO_DIGITALE);
				result4 = ps4.executeUpdate();
				
				ps5 = con.prepareStatement(SQL3);
				ps5.setInt(1, product.getDisp_XboxX_Digitale());
				ps5.setString(2, product.getCodSeriale());
				ps5.setString(3, PIATTAFORMA_XboxSerieX);
				ps5.setString(4, FORMATO_DIGITALE);
				result5 = ps5.executeUpdate();
				
				ps6 = con.prepareStatement(SQL3);
				ps6.setInt(1, product.getDisp_XboxS_Digitale());
				ps6.setString(2, product.getCodSeriale());
				ps6.setString(3, PIATTAFORMA_XboxSerieS);
				ps6.setString(4, FORMATO_DIGITALE);
				result6 = ps6.executeUpdate();
				
				ps7 = con.prepareStatement(SQL3);
				ps7.setInt(1, product.getDisp_Pc_Digitale());
				ps7.setString(2, product.getCodSeriale());
				ps7.setString(3, PIATTAFORMA_PC);
				ps7.setString(4, FORMATO_DIGITALE);
				result7 = ps7.executeUpdate();
			}
			
			String SQL3 = "INSERT INTO " + ProductModel.TABLE_NAME_DISPONIBILITA + " (QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato) VALUES (?, ?, ?, ?)";
			ps8 = con.prepareStatement(SQL3);
			ps8.setInt(1, product.getDisp_Ps5_Fisico());
			ps8.setString(2, product.getCodSeriale());
			ps8.setString(3, PIATTAFORMA_PS5);
			ps8.setString(4, FORMATO_FISICO);
			result8 = ps8.executeUpdate();
			
			ps9 = con.prepareStatement(SQL3);
			ps9.setInt(1, product.getDisp_Ps4_Fisico());
			ps9.setString(2, product.getCodSeriale());
			ps9.setString(3, PIATTAFORMA_PS4);
			ps9.setString(4, FORMATO_FISICO);
			result9 = ps9.executeUpdate();
			
			ps10 = con.prepareStatement(SQL3);
			ps10.setInt(1, product.getDisp_XboxX_Fisico());
			ps10.setString(2, product.getCodSeriale());
			ps10.setString(3, PIATTAFORMA_XboxSerieX);
			ps10.setString(4, FORMATO_FISICO);
			result10 = ps10.executeUpdate();
			
			ps11 = con.prepareStatement(SQL3);
			ps11.setInt(1, product.getDisp_Pc_Fisico());
			ps11.setString(2, product.getCodSeriale());
			ps11.setString(3, PIATTAFORMA_PC);
			ps11.setString(4, FORMATO_FISICO);
			result11 = ps11.executeUpdate();
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
			if(ps4 != null)
			{
				ps4.close();
			}
			if(ps5 != null)
			{
				ps5.close();
			}
			if(ps6 != null)
			{
				ps6.close();
			}
			if(ps7 != null)
			{
				ps7.close();
			}
			if(ps8 != null)
			{
				ps8.close();
			}
			if(ps9 != null)
			{
				ps9.close();
			}
			if(ps10 != null)
			{
				ps10.close();
			}
			if(ps11 != null)
			{
				ps11.close();
			}
			if(con != null)
			{
				DBConnectionPool.releaseConnection(con);
			}
		}
		
		if(!(product.getTipologia()))
		{
			return (result1 != 0 && result2 != 0 && result3 != 0 && result4 != 0 && result5 != 0 && result6 != 0 && result7 != 0 &&
				result8 != 0 && result9 != 0 && result10 != 0 && result11 != 0);
		}
		else
		{
			return (result1 != 0 &&	result8 != 0 && result9 != 0 && result10 != 0 && result11 != 0);
		}
		
		
	}
	
	
	public synchronized boolean ModNome(String CodSerialeMod, String Nome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Nome = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Nome);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	public synchronized boolean ModPrezzo(String CodSerialeMod, Float Prezzo) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Prezzo = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setFloat(1, Prezzo);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	public synchronized boolean ModDataUscita(String CodSerialeMod, String DataUscita) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DataUscita = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, DataUscita);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	public synchronized boolean ModDescrizioneRidotta(String CodSerialeMod, String DescrizioneRidotta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneRidotta = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, DescrizioneRidotta);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	
	public synchronized boolean ModDescrizioneCompleta(String CodSerialeMod, String DescrizioneCompleta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneCompleta = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, DescrizioneCompleta);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	/*public synchronized boolean ModTipologia(String CodSerialeMod, Boolean Tipologia) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET FlagTipologia = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setBoolean(1, Tipologia);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}*/
	
	public synchronized boolean ModPEGI(String CodSerialeMod, int PEGI) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET CodPEGI = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, PEGI);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	public synchronized boolean ModGenere(String CodSerialeMod, String Genere) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET NomeGenere = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Genere);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	public synchronized boolean ModImmagine(String CodSerialeMod, String Immagine) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Immagine = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, Immagine);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	public synchronized boolean ModDisponibilita(String CodSerialeMod, int  Disp, String Piattaforma, String Formato) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_DISPONIBILITA + " SET QuantitaDisponibile = ? " + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, Disp);
			ps.setString(2, CodSerialeMod);
			ps.setString(3, Piattaforma);
			ps.setString(4, Formato);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	public synchronized boolean ModCodSeriale(String CodSerialeMod, String CodSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String SQL = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET CodSeriale = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = DBConnectionPool.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, CodSeriale);
			ps.setString(2, CodSerialeMod);
			result = ps.executeUpdate();
			con.commit();
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
		return (result != 0);
	}
	
	
	
	
	

}
