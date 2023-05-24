package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPool 
{
	
	private DBConnectionPool() 
	{
	    throw new IllegalStateException("Utility class");
	}
	
	static Logger logger = Logger.getLogger(DBConnectionPool.class.getName());
	private static List<Connection> freeDbConnections;
	static 
    {
		freeDbConnections = new LinkedList<>();
		try 
        {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} 
        catch (ClassNotFoundException e) 
        {
        	logger.log(Level.WARNING, e.getMessage());
		} 
        
	}

	private static synchronized Connection createDBConnection() throws SQLException 
    {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "GameCenter";
		String username = "root";
		String pass = "Digiorgio";
		String params = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        
		try
		{
			newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + params, username, pass);
			newConnection.setAutoCommit(false);
		}
		catch(SQLException e)
		{
			if(newConnection != null)
			{
				newConnection.close();
			}
		}
		
		return newConnection;
	}


	public static synchronized Connection getConnection() throws SQLException 
    {
		Connection connection;

		if (!freeDbConnections.isEmpty()) 
        {
			connection = freeDbConnections.get(0);
			freeDbConnections.remove(0);
			try 
            {
				if (connection.isClosed())
                {
                    connection = getConnection();
                }
			} 
            catch (SQLException e) 
            {                
                connection.close();
       			connection = getConnection();
			}
		} 
        else 
        {
			connection = createDBConnection();
		}
		return connection;
	}

	public static synchronized void releaseConnection(Connection connection)
    {
		if(connection != null)
		{
			freeDbConnections.add(connection);
		}
	}
}