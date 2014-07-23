package siva.arlimi.servlet;

import java.io.BufferedReader;
import java.io.IOException;
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

public class ReadEventByIDServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2008857857750225485L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		System.out.println("doGet");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		System.out.println("readEventID post");
		
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader bf;	
		JSONArray jsonArray = null;
	
		try
		{
			bf = req.getReader();
			while( null != (line = bf.readLine() ))
			{
				sb.append(line);
			}
		}
		catch(Exception e) { }
		
		System.out.println("sb: " + sb.toString());
		
		try 
		{
			if(!sb.toString().isEmpty())
			{
				jsonArray = new JSONArray(sb.toString());
			}
			else
			{
				System.out.println("StringBuilder is empty");
			}
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		//Get Ids from JsonArray
		String[] eventIds = readIds(jsonArray);
		//readDB(eventIds);
		
	}
	
	
	private String[] readIds(JSONArray jsonArray)
	{
		int len = jsonArray.length();
		String[] eventIds = new String[len];
		
		for(int i = 0; i < len; i++)
		{
			try
			{
				JSONObject json = (JSONObject) jsonArray.get(i);
				eventIds[i] = (String) json.get(String.valueOf(i));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			
		}
		
		return eventIds;
	}

	private JSONArray readDB(String[] eventId)
	{
		Connection conn = null;
		JSONArray eventArray = new JSONArray();
		
		String query = makeQuery(eventId);
		
		/*
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			//String query = "select * from event;"; 
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
		} */
		
		return eventArray;
	}

	private String makeQuery(String[] eventId)
	{
		StringBuilder sb = new StringBuilder
				("selcet * from event where id in(");
		
		for(int i = 0; i < eventId.length; i++)
		{
			sb.append(eventId[i]);
			if( !((eventId.length -1) == i))
				sb.append(",");
		}
		sb.append(")");
		
		return sb.toString();
	}
	
}
