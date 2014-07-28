package siva.arlimi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;

//import org.apache.commons.dbcp2.DriverManagerConnectionFactory;

public class DatabaseConnection extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/siva_arlimi_test";
	private static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
	private static final String DB_POOL_DRIVER = "org.apache.commons.dbcp.PoolingDriver";
	private static final String ID = "root";
	private static final String PASS = "siva0708";
	
	public DatabaseConnection() throws Exception
	{
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection conn = null;
			/*
			Class.forName(DB_POOL_DRIVER);
			conn = DriverManager.getConnection(
				"jdbc:apache:commons:dbcp:/db_pool"); */
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, ID, PASS);
		
		return conn;
	}
}
