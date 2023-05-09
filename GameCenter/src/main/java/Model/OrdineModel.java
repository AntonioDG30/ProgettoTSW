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

}
