package siva.arlimi.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siva.arlimi.database.DatabaseConnection;
import siva.arlimi.util.EventUtil;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class ReadEventListServlet extends HttpServlet
{

	private static final long serialVersionUID = -2879876496503959149L;

	private static final String EventList = null;
	
	
	private String resName;
	private String contents;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
			
		JSONArray eventArray = readDB();
		
		OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(),"utf8");
		writer.write(eventArray.toString());
		writer.close();
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		JSONArray eventArray = readDB();
		
		OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(),"utf8");
		writer.write(eventArray.toString());
		writer.close();
		
	}
	

	
	private JSONArray readDB()
	{
		Connection conn = null;
		JSONArray eventArray = new JSONArray();
		
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = "select * from event;"; 
			ResultSet rs = stmt.executeQuery(query); 
			
			
			while(rs.next())
			{
				
				JSONObject event = new JSONObject();
				try
				{
					event.put(EventUtil.EVENT_ID, Integer.valueOf(rs.getInt("id")));
					
					event.put(EventUtil.EMAIL, rs.getString("owner_email"));
					event.put(EventUtil.EVENT_CONTENTS, rs.getString("event_contents"));
					event.put(EventUtil.EVENT_LATITUDE, rs.getString("event_latitude"));
					event.put(EventUtil.EVENT_LONGITUDE, rs.getString("event_longitude"));
					
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				
				//eventArray.add(event); 
				eventArray.put(event);
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch(NullPointerException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			if( null != conn)
				try
				{
					conn.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return eventArray;
	}

}
