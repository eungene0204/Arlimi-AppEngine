package siva.arlimi.registration.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.server.spi.auth.AuthUtils;

import siva.arlimi.database.DatabaseConnection;
import siva.arlimi.database.util.DatabaseUtil;
import siva.arlimi.login.util.LoginUtil;
import siva.arlimi.util.IOHelper;

public class EmailUserRegistrationServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7031731508614002681L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
	
		int result = 0;
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader bf;
		
		try
		{
			bf = req.getReader();
			while(null != (line = bf.readLine()))
			{
				sb.append(line);
			}
			
			if(!sb.toString().isEmpty())
			{
				JSONObject json = new JSONObject(sb.toString());
				result = writeDB(json);
				
			}
		}
		catch(Exception e){} 
		
		if(DatabaseUtil.UPDATE_SUCCESS == result)
		{
			System.out.println("Update Success");
			IOHelper.sendResponse(LoginUtil.VALID_USER, resp);
		}
		else
		{
			System.out.println("Update Fail");
			IOHelper.sendResponse(LoginUtil.INVALID_USER, resp);
		}
			
		
	}

	private int writeDB(JSONObject data)
	{
		int result = 0;
		
		try
		{
			String email = data.getString("EMAIL");
			String password = data.getString("PASSWORD");
			String name = data.getString("NAME");
			
			System.out.println(email);
			
			Connection conn = new DatabaseConnection().getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into email_user" +
					" values ('%s','%s','%s');", email, name, password );
			
			System.out.println(query);
			
			int rs = stmt.executeUpdate(query);
			
			if(1 == rs)
				result = DatabaseUtil.UPDATE_SUCCESS;
			else
				result = DatabaseUtil.UPDATE_FAIL;
			
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

}
