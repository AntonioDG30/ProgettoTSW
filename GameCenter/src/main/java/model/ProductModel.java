package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class ProductModel 
{
	static Logger logger = Logger.getLogger(ProductModel.class.getName());
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
	
	
	private static final String TABLE_NAME_PRODOTTO = "Prodotto";
	private static final String TABLE_NAME_CARATTERISTICHE = "Caratteristiche";
	private static final String TABLE_NAME_DISPONIBILITA = "Disponibilita";
	private static final String TABLE_NAME_INCLUDE = "Include";
	private static final String TABLE_NAME_GENERE = "Genere";
	private static final String TABLE_NAME_PEGI = "PEGI";
	private static final String TABLE_NAME_RECENSIONE = "Recensione";
	
	
	
	
	private static final String PIATTAFORMA_PS5 = "PlayStation 5";
	private static final String PIATTAFORMA_PS4 = "PlayStation 4";
	private static final String PIATTAFORMA_XBOX_SERIE_X = "XBOX Series X";
	private static final String PIATTAFORMA_XBOX_SERIE_S = "XBOX Series S";
	private static final String PIATTAFORMA_PC = "PC";
	
	
	private static final String FORMATO_DIGITALE = "Digitale";
	private static final String FORMATO_FISICO = "Fisico";
	
	
	
	
	
	
	
	public synchronized Collection<ProductBean> doAll() throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE FlagVisibita = 1";
		
		try(Connection con = ds.getConnection())
		{
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
		}
		return products;
	}
	
	
	public static synchronized Collection<ProductBean> getProdottiRicerca(String ricerca) throws SQLException
	{
		
		PreparedStatement ps = null;
		ricerca = "%" + ricerca + "%";
		
		Collection<ProductBean> products = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE Nome LIKE ? AND FlagVisibita = 1";
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, ricerca);

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
		}
		return products;
	}
	
	public static synchronized Collection<ProductBean> getProdottiPerGenere(String nomeGenere) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE FlagVisibita = 1 "
				+ "AND CodSeriale IN (SELECT CodSeriale FROM "+ ProductModel.TABLE_NAME_CARATTERISTICHE + " WHERE NomeGenere = ?)";
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, nomeGenere);

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
		}
		return products;
	}
	
	public static synchronized Collection<ProductBean> getProdottiPerPiattaforma(String nomePiattaforma) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE FlagVisibita = 1 "
				+ "AND CodSeriale IN (SELECT CodSeriale FROM "+ ProductModel.TABLE_NAME_DISPONIBILITA + " WHERE NomePiattaforma = ?)";
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, nomePiattaforma);

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
		}
		return products;
	}
	
	
	public synchronized Collection<ProductBean> doTop6() throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<ProductBean> products = new LinkedList<>();
		String sql = "SELECT p.CodSeriale, p.Nome, p.Prezzo, p.DescrizioneRidotta, p.Immagine FROM " + ProductModel.TABLE_NAME_PRODOTTO + " AS p, " + ProductModel.TABLE_NAME_INCLUDE +" AS i " 
						+ "WHERE p.CodSeriale = i.CodSeriale AND FlagVisibita = 1 GROUP BY i.CodSeriale ORDER BY sum(i.quantita) DESC LIMIT 6";
		
		try(Connection con = ds.getConnection())
		{
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
		}
		return products;
	}
	
	public static synchronized List<String> getSuggerimentiProdotti(String ricerca) throws SQLException
	{
		
		PreparedStatement ps = null;
		ricerca = "%" + ricerca + "%";
		
		List<String> suggerimenti = new ArrayList<>();
		String sql = "SELECT Nome FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE Nome LIKE ? AND FlagVisibita = 1";
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, ricerca);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				suggerimenti.add(rs.getString("Nome"));
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
		return suggerimenti;
	}
	
	
	public synchronized ProductBean dettagli(String codSeriale) throws SQLException 
	{
		
		PreparedStatement ps = null;
		ProductBean bean = new ProductBean();
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PRODOTTO + " WHERE CodSeriale = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) 
			{
				bean.setCodSeriale(codSeriale);
				bean.setNome(rs.getString("Nome"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setDataUscita(rs.getString("DataUscita"));
				bean.setDescrizioneRidotta(rs.getString("DescrizioneRidotta"));
				bean.setDescrizioneCompleta(rs.getString("DescrizioneCompleta"));
				bean.setImmagine(rs.getString("Immagine"));
				bean.setTipologia(rs.getBoolean("FlagTipologia"));
				if(!rs.getBoolean("FlagTipologia"))
				{
					bean = dettagliOrdineInclude(bean);
					bean.setDispPs5Digitale(ottieniDisponibilita(codSeriale, PIATTAFORMA_PS5, FORMATO_DIGITALE));			
					bean.setDispPs4Digitale(ottieniDisponibilita(codSeriale, PIATTAFORMA_PS4, FORMATO_DIGITALE));
					bean.setDispXboxXDigitale(ottieniDisponibilita(codSeriale, PIATTAFORMA_XBOX_SERIE_X, FORMATO_DIGITALE));
					bean.setDispXboxSDigitale(ottieniDisponibilita(codSeriale, PIATTAFORMA_XBOX_SERIE_S, FORMATO_DIGITALE));
					bean.setDispPcDigitale(ottieniDisponibilita(codSeriale, PIATTAFORMA_PC, FORMATO_DIGITALE));					
				}
				bean.setDispPs5Fisico(ottieniDisponibilita(codSeriale, PIATTAFORMA_PS5, FORMATO_FISICO));
				bean.setDispPs4Fisico(ottieniDisponibilita(codSeriale, PIATTAFORMA_PS4, FORMATO_FISICO));
				bean.setDispXboxXFisico(ottieniDisponibilita(codSeriale, PIATTAFORMA_XBOX_SERIE_X, FORMATO_FISICO));
				bean.setDispXboxSFisico(ottieniDisponibilita(codSeriale, PIATTAFORMA_XBOX_SERIE_S, FORMATO_FISICO));
				bean.setDispPcFisico(ottieniDisponibilita(codSeriale, PIATTAFORMA_PC, FORMATO_FISICO));
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
		
		
		if(!(bean.getCodSeriale().equals("")))
		{
			return bean;
		}
		else
		{
			return null;
		}
	}
	
	
	public synchronized ProductBean dettagliOrdineInclude(ProductBean bean) throws SQLException
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_CARATTERISTICHE + " WHERE CodSeriale = ?";
		
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
	
	
	public synchronized int ottieniDisponibilita(String codSeriale, String piattaforma, String formato) throws SQLException
	{
		
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + TABLE_NAME_DISPONIBILITA + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";
		int quantita=0;
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);
			ps.setString(2, piattaforma);
			ps.setString(3, formato);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
			{
				quantita = rs.getInt("QuantitaDisponibile");
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
		return quantita;
	}
	
	
	public synchronized boolean elimina(String codSeriale) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET FlagVisibita = 0 " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);
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
	
	
	public synchronized Collection<GenereBean> getGenereElements() throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<GenereBean> genere = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_GENERE;
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				GenereBean bean = new GenereBean();
				bean.setNomeGenere(rs.getString("NomeGenere"));
				genere.add(bean);
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
		return genere;
	}
	
	
	public synchronized Collection<PegiBean> getPegiElements() throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<PegiBean> pegi = new LinkedList<>();
		
		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_PEGI;
		
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				PegiBean bean = new PegiBean();
				bean.setCodPEGI(rs.getInt("CodPEGI"));
				pegi.add(bean);
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
		return pegi;
	}
	
	
	public synchronized boolean inserisci(ProductBean product) throws SQLException 
	{

		
		PreparedStatement ps = null;
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		int result4 = 0;
		int result5 = 0;
		int result6 = 0;
		int result7 = 0;
		int result8 = 0;
		int result9 = 0;
		int result10 = 0;
		int result11 = 0;		
		String sql = "INSERT INTO " + ProductModel.TABLE_NAME_PRODOTTO + " (CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";

		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, product.getCodSeriale());
			ps.setString(2, product.getNome());
			ps.setFloat(3, product.getPrezzo());
			ps.setString(4, product.getDataUscita()); 
			ps.setString(5, product.getDescrizioneRidotta());
			ps.setString(6, product.getDescrizioneCompleta());
			ps.setString(7, product.getImmagine());
			ps.setBoolean(8, product.getTipologia());
			result1 = ps.executeUpdate();		
			if(!(product.getTipologia()))
			{
				result2 = inserisciCaratteristiche(product);
				result3 = inserisciDisponibilita(product.getDispPs5Digitale(), product.getCodSeriale(), PIATTAFORMA_PS5, FORMATO_DIGITALE);	
				result4 = inserisciDisponibilita(product.getDispPs4Digitale(), product.getCodSeriale(), PIATTAFORMA_PS4, FORMATO_DIGITALE);
				result5 = inserisciDisponibilita(product.getDispXboxXDigitale(), product.getCodSeriale(),  PIATTAFORMA_XBOX_SERIE_X, FORMATO_DIGITALE);
				result6 = inserisciDisponibilita(product.getDispXboxSDigitale(), product.getCodSeriale(), PIATTAFORMA_XBOX_SERIE_S, FORMATO_DIGITALE);
				result7 = inserisciDisponibilita(product.getDispPcDigitale(), product.getCodSeriale(),  PIATTAFORMA_PC, FORMATO_DIGITALE);					
			}
			result8 = inserisciDisponibilita(product.getDispPs5Fisico(), product.getCodSeriale(), PIATTAFORMA_PS5, FORMATO_FISICO);
			result9 = inserisciDisponibilita(product.getDispPs4Fisico(), product.getCodSeriale(), PIATTAFORMA_PS4, FORMATO_FISICO);
			result10 = inserisciDisponibilita(product.getDispXboxXFisico(), product.getCodSeriale(),  PIATTAFORMA_XBOX_SERIE_X, FORMATO_FISICO);
			result11 = inserisciDisponibilita(product.dispPcFisico, product.getCodSeriale(),  PIATTAFORMA_PC, FORMATO_FISICO);
	
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
	
	public synchronized int inserisciCaratteristiche(ProductBean product) throws SQLException
	{
		
		PreparedStatement ps = null;
		int result=0;
		String sql = "INSERT INTO " + ProductModel.TABLE_NAME_CARATTERISTICHE + " (CodSeriale, NomeGenere, CodPEGI) VALUES (?, ?, ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, product.getCodSeriale());
			ps.setString(2, product.getGenere());
			ps.setInt(3, product.getPegi());
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
	
	public synchronized int inserisciDisponibilita(int quantita, String codSeriale, String piattaforma, String formato) throws SQLException
	{
		
		PreparedStatement ps = null;
		int result=0;
		String sql = "INSERT INTO " + ProductModel.TABLE_NAME_DISPONIBILITA + " (QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato) VALUES (?, ?, ?, ?)";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantita);
			ps.setString(2, codSeriale);
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
		return result;
	}
	
	
	public synchronized boolean modNome(String codSerialeMod, String nome) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Nome = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modPrezzo(String codSerialeMod, Float prezzo) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Prezzo = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modDataUscita(String codSerialeMod, String dataUscita) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DataUscita = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modDescrizioneRidotta(String codSerialeMod, String descrizioneRidotta) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneRidotta = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	
	public synchronized boolean modDescrizioneCompleta(String codSerialeMod, String descrizioneCompleta) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET DescrizioneCompleta = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	public synchronized boolean modPEGI(String codSerialeMod, int pegi) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET CodPEGI = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	public synchronized boolean modGenere(String codSerialeMod, String genere) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_CARATTERISTICHE + " SET NomeGenere = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	public synchronized boolean modImmagine(String codSerialeMod, String immagine) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET Immagine = ? " + " WHERE codSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	public synchronized boolean modDisponibilita(String codSerialeMod, int  disponibilita, String piattaforma, String formato) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_DISPONIBILITA + " SET QuantitaDisponibile = ? " + " WHERE CodSeriale = ? AND NomePiattaforma = ? AND TipoFormato = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	public synchronized boolean modCodSeriale(String codSerialeMod, String codSeriale) throws SQLException 
	{
		
		PreparedStatement ps = null;

		int result = 0;

		String sql = "UPDATE " + ProductModel.TABLE_NAME_PRODOTTO + " SET CodSeriale = ? " + " WHERE CodSeriale = ?";

		try(Connection con = ds.getConnection())
		{
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
		}
		return (result != 0);
	}
	
	
	public synchronized Collection<RecensioneBean> getRecensione(String codSeriale) throws SQLException
	{
		
		PreparedStatement ps = null;
		
		Collection<RecensioneBean> recensioni = new LinkedList<>();

		String sql = "SELECT * FROM " + ProductModel.TABLE_NAME_RECENSIONE + " WHERE CodSeriale = ?";
		try(Connection con = ds.getConnection())
		{
			ps = con.prepareStatement(sql);
			ps.setString(1, codSeriale);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				RecensioneBean bean = new RecensioneBean();
				bean.setValutazione(rs.getInt("Valutazione"));
				bean.setDescrizione(rs.getString("Descrizione"));
				recensioni.add(bean);
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
		
		return recensioni;
	}
	
	
	
	
	

}
