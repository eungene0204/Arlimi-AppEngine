package siva.arlimi.login.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.database.DatabaseConnection;
import siva.arlimi.login.util.LoginUtil;
import siva.arlimi.util.IOHelper;

public class EmailUserLoginServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		System.out.println("email login");
			
		JSONObject json = IOHelper.readRequest(req);
		
		boolean result = readDB(json);
	
		if(result)
			IOHelper.sendResponse(LoginUtil.VALID_USER, resp);
		else
			IOHelper.sendResponse(LoginUtil.INVALID_USER, resp);
		
	}
	
	private boolean readDB(final JSONObject json)
	{
		Connection conn = null;
		boolean result = false;
		
		try
		{
			String email = json.getString(LoginUtil.EMAIL);
			String password = json.getString(LoginUtil.PASSWORD);
			
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from email_user where email = " +
			"'" + email +"'" + "AND "
					+ "password = "  + "'" + password + "'";
			
			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			result = rs.next();
			
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != conn)
			{
				try
				{
					conn.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}


}
