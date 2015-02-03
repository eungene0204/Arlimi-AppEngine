package siva.arlimi.event.servlet;

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
import siva.arlimi.event.util.EventUtils;
import siva.arlimi.util.IOHelper;

public class EventRegistrationServlet extends HttpServlet 
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
		JSONObject json = IOHelper.readRequest(req);
		int result = writeDB(json);
		
	}
	
	private int writeDB(JSONObject data)
	{
		int result = 0;
		
		try
		{
			String cotents = data.getString(EventUtils.EVENT_CONTENTS);
			String email = data.getString(EventUtils.KEY_EMAIL);
			String name = data.getString(EventUtils.KEY_NAME);
			String start_date = data.getString(EventUtils.EVENT_START_DATE);
			String end_date = data.getString(EventUtils.EVENT_END_DATE);
			String start_time = data.getString(EventUtils.EVENT_START_TIME);
			String end_time = data.getString(EventUtils.EVENT_END_TIME);
			
			
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
