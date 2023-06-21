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




public class ProductModel 
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
	
	
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	private static final String TABLE_NAME_DISPONIBILITA = "Disponibilita";
	private static final String TABLE_NAME_GENERE = "Genere";
	private static final String TABLE_NAME_PEGI = "PEGI";
	
	
	
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XBOX_SERIE_X = "XBOX Series X";
	private static final String PIATTAFORMA_XBOX_SERIE_S = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
	
	
	Logger logger = Logger.getLogger(ProductModel.class.getName());
	
	
	
	
	public synchronized Collection<ProductBean> doAll() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE FlagVisibita = 1";
		
		try
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);

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
		return products;
	}
	
	
	public synchronized ProductBean dettagli(String codSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet  rs2 = null;


		ProductBean bean = new ProductBean();

		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		String sql2 = "SELECT * FROM " + TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		String sql3 = "SELECT * FROM " + TABLE_NAME_DISPONIBILITA + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);

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
					try
					{
						ps2 = con.prepareStatement(sql2);
						ps2.setString(1, rs.getString("CodSeriale"));
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setPegi(rs2.getInt("CodPEGI"));
							bean.setGenere(rs2.getString("NomeGenere"));
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
					
					
					
					try
					{
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, rs.getString("CodSeriale"));
						ps2.setString(2, PIATTAFORMA_PS5);
						ps2.setString(3, FORMATO_DIGITALE);
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setDispPs5Digitale(rs2.getInt("QuantitaDisponibile"));
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
					
					
					
					try
					{
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, rs.getString("CodSeriale"));
						ps2.setString(2, PIATTAFORMA_PS4);
						ps2.setString(3, FORMATO_DIGITALE);
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setDispPs4Digitale(rs2.getInt("QuantitaDisponibile"));
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
					
					
					
					try
					{
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, rs.getString("CodSeriale"));
						ps2.setString(2, PIATTAFORMA_XBOX_SERIE_X);
						ps2.setString(3, FORMATO_DIGITALE);
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setDispXboxXDigitale(rs2.getInt("QuantitaDisponibile"));
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
					
					
					try
					{
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, rs.getString("CodSeriale"));
						ps2.setString(2, PIATTAFORMA_XBOX_SERIE_S);
						ps2.setString(3, FORMATO_DIGITALE);
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setDispXboxSDigitale(rs2.getInt("QuantitaDisponibile"));
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
					
					
					try
					{
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, rs.getString("CodSeriale"));
						ps2.setString(2, PIATTAFORMA_PC);
						ps2.setString(3, FORMATO_DIGITALE);
						rs2 = ps2.executeQuery();
						while(rs2.next()) 
						{
							bean.setDispPcDigitale(rs2.getInt("QuantitaDisponibile"));
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
				
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, rs.getString("CodSeriale"));
					ps2.setString(2, PIATTAFORMA_PS5);
					ps2.setString(3, FORMATO_FISICO);
					rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setDispPs5Fisico(rs2.getInt("QuantitaDisponibile"));
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, rs.getString("CodSeriale"));
					ps2.setString(2, PIATTAFORMA_PS4);
					ps2.setString(3, FORMATO_FISICO);
					rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setDispPs4Fisico(rs2.getInt("QuantitaDisponibile"));
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, rs.getString("CodSeriale"));
					ps2.setString(2, PIATTAFORMA_XBOX_SERIE_X);
					ps2.setString(3, FORMATO_FISICO);
					rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setDispXboxXFisico(rs2.getInt("QuantitaDisponibile"));
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, rs.getString("CodSeriale"));
					ps2.setString(2, PIATTAFORMA_XBOX_SERIE_S);
					ps2.setString(3, FORMATO_FISICO);
					rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setDispXboxSFisico(rs2.getInt("QuantitaDisponibile"));
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, rs.getString("CodSeriale"));
					ps2.setString(2, PIATTAFORMA_PC);
					ps2.setString(3, FORMATO_FISICO);
					rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						bean.setDispPcFisico(rs2.getInt("QuantitaDisponibile"));
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
		
		
		if(bean.getCodSeriale() != "")
		{
			return bean;
		}
		else
		{
			return null;
		}
	}
	
	
	public synchronized boolean elimina(String codSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET FlagVisibita = 0 " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);
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
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	public synchronized Collection<GenereBean> getGenereElements() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<GenereBean> Genere = new LinkedList<GenereBean>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_GENERE;
		
		try
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);

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
		return Genere;
	}
	
	
	public synchronized Collection<PegiBean> getPegiElements() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		Collection<PegiBean> PEGI = new LinkedList<PegiBean>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PEGI;
		
		try
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);

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
		return PEGI;
	}
	
	
	public synchronized boolean inserisci(ProductBean product) throws SQLException 
	{

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		
		int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, result6 = 0, result7 = 0, result8 = 0, result9 = 0, result10 = 0, result11 = 0;

		String sql = "INSERT INTO " + ProductModel.TABLE_NAME_PRODOTTO + " (CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
		String sql2 = "INSERT INTO " + ProductModel.TABLE_NAME_CARATTERISTICHE + " (CodSeriale, NomeGenere, CodPEGI) VALUES (?, ?, ?)";
		String sql3 = "INSERT INTO " + ProductModel.TABLE_NAME_DISPONIBILITA + " (QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato) VALUES (?, ?, ?, ?)";

		try 
		{
			con = ds.getConnection();
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, product.getCodSeriale());
			ps1.setString(2, product.getNome());
			ps1.setFloat(3, product.getPrezzo());
			ps1.setString(4, product.getDataUscita()); 
			ps1.setString(5, product.getDescrizioneRidotta());
			ps1.setString(6, product.getDescrizioneCompleta());
			ps1.setString(7, product.getImmagine());
			ps1.setBoolean(8, product.getTipologia());
			result1 = ps1.executeUpdate();
			
			
			
			if(!(product.getTipologia()))
			{
				try
				{
					ps2 = con.prepareStatement(sql2);
					ps2.setString(1, product.getCodSeriale());
					ps2.setString(2, product.getGenere());
					ps2.setInt(3, product.getPegi());
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setInt(1, product.getDispPs5Digitale());
					ps2.setString(2, product.getCodSeriale());
					ps2.setString(3, PIATTAFORMA_PS5);
					ps2.setString(4, FORMATO_DIGITALE);
					result3 = ps2.executeUpdate();
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
					ps2 = con.prepareStatement(sql3);
					ps2.setInt(1, product.getDispPs4Digitale());
					ps2.setString(2, product.getCodSeriale());
					ps2.setString(3, PIATTAFORMA_PS4);
					ps2.setString(4, FORMATO_DIGITALE);
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
				
				
				try
				{
					ps2 = con.prepareStatement(sql3);
					ps2.setInt(1, product.getDispXboxXDigitale());
					ps2.setString(2, product.getCodSeriale());
					ps2.setString(3, PIATTAFORMA_XBOX_SERIE_X);
					ps2.setString(4, FORMATO_DIGITALE);
					result5 = ps2.executeUpdate();
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
					ps2 = con.prepareStatement(sql3);
					ps2.setInt(1, product.getDispXboxSDigitale());
					ps2.setString(2, product.getCodSeriale());
					ps2.setString(3, PIATTAFORMA_XBOX_SERIE_S);
					ps2.setString(4, FORMATO_DIGITALE);
					result6 = ps2.executeUpdate();
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
					ps2 = con.prepareStatement(sql3);
					ps2.setInt(1, product.getDispPcDigitale());
					ps2.setString(2, product.getCodSeriale());
					ps2.setString(3, PIATTAFORMA_PC);
					ps2.setString(4, FORMATO_DIGITALE);
					result7 = ps2.executeUpdate();
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
			
			try
			{
				ps2 = con.prepareStatement(sql3);
				ps2.setInt(1, product.getDispPs5Fisico());
				ps2.setString(2, product.getCodSeriale());
				ps2.setString(3, PIATTAFORMA_PS5);
				ps2.setString(4, FORMATO_FISICO);
				result8 = ps2.executeUpdate();
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
				ps2 = con.prepareStatement(sql3);
				ps2.setInt(1, product.getDispPs4Fisico());
				ps2.setString(2, product.getCodSeriale());
				ps2.setString(3, PIATTAFORMA_PS4);
				ps2.setString(4, FORMATO_FISICO);
				result9 = ps2.executeUpdate();
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
				ps2 = con.prepareStatement(sql3);
				ps2.setInt(1, product.getDispXboxXFisico());
				ps2.setString(2, product.getCodSeriale());
				ps2.setString(3, PIATTAFORMA_XBOX_SERIE_X);
				ps2.setString(4, FORMATO_FISICO);
				result10 = ps2.executeUpdate();
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
				ps2 = con.prepareStatement(sql3);
				ps2.setInt(1, product.getDispPcFisico());
				ps2.setString(2, product.getCodSeriale());
				ps2.setString(3, PIATTAFORMA_PC);
				ps2.setString(4, FORMATO_FISICO);
				result11 = ps2.executeUpdate();
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
				con.close();
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
	
	
	public synchronized boolean modNome(String codSerialeMod, String nome) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Nome = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modPrezzo(String codSerialeMod, Float prezzo) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Prezzo = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setFloat(1, prezzo);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modDataUscita(String codSerialeMod, String dataUscita) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DataUscita = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, dataUscita);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modDescrizioneRidotta(String codSerialeMod, String descrizioneRidotta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneRidotta = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, descrizioneRidotta);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	
	public synchronized boolean modDescrizioneCompleta(String codSerialeMod, String descrizioneCompleta) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneCompleta = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, descrizioneCompleta);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	/*public synchronized boolean modTipologia(String codSerialeMod, Boolean tipologia) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET FlagTipologia = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, tipologia);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}*/
	
	public synchronized boolean modPEGI(String codSerialeMod, int pegi) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET CodPEGI = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, pegi);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modGenere(String codSerialeMod, String genere) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET NomeGenere = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, genere);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modImmagine(String codSerialeMod, String immagine) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Immagine = ? " + " WHERE codSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, immagine);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	public synchronized boolean modDisponibilita(String codSerialeMod, int  disponibilita, String piattaforma, String formato) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_DISPONIBILITA + " SET QuantitaDisponibile = ? " + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, disponibilita);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modCodSeriale(String codSerialeMod, String codSeriale) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET CodSeriale = ? " + " WHERE CodSeriale = ?";

		try 
		{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);
			ps.setString(2, codSerialeMod);
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
			if(con != null)
			{
				con.close();
			}
		}
		return (result != 0);
	}
	
	
	
	
	

}
