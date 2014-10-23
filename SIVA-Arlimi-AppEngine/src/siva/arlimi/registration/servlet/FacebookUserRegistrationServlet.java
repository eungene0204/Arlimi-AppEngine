package siva.arlimi.registration.servlet;

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

import siva.arlimi.database.DatabaseConnection;
import siva.arlimi.database.util.DatabaseUtil;
import siva.arlimi.login.util.LoginUtil;
import siva.arlimi.util.IOHelper;

public class FacebookUserRegistrationServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976072385302228547L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		JSONObject json = IOHelper.readRequest(req);
		int result = writeDB(json);
		
		if(result == DatabaseUtil.UPDATE_SUCCESS)
			IOHelper.sendResponse(LoginUtil.VALID_USER, resp);
		else if(result == DatabaseUtil.DUPLICATE_KEY)
			IOHelper.sendResponse(LoginUtil.DUPLICATE_USER, resp);
		else
			IOHelper.sendResponse(LoginUtil.INVALID_USER, resp);
		
	}

	private int writeDB(JSONObject data)
	{
		int result = 0;
		
		try
		{
			String email = data.getString("EMAIL");
			String name = data.getString("NAME");
			
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into facebook_user" +
					" values ('%s','%s')", email, name );
			
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
			System.out.println("state :" + e.getSQLState());
			
			//sqlState 23000 duplicate key
			if(e.getSQLState().equals("23000"))
				return result = DatabaseUtil.DUPLICATE_KEY;
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Result :"  + result);
		
		return result;
		
	}

}
