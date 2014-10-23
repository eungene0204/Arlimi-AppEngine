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
	private String name = "";
	
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
	
		JSONObject user = new JSONObject();
		if(result)
		{
			System.out.println("valid user");
			
			try
			{
				user.put(LoginUtil.NAME, name);
				user.put(LoginUtil.KEY_LOGIN_RESULT, LoginUtil.VALID_USER);
				
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			
			IOHelper.sendResponse(user, resp);
		}
		else
		{
			System.out.println("invalid user");
			
			try
			{
				user.put(LoginUtil.KEY_LOGIN_RESULT, LoginUtil.INVALID_USER);
				
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IOHelper.sendResponse(user, resp);
		}
		
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
			
			if(result)
				name = rs.getString("name");
			
			System.out.println(name);
			
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
