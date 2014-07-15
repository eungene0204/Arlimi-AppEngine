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

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class ReadEventListServlet extends HttpServlet
{

	private static final long serialVersionUID = -2879876496503959149L;

	private static final String EventList = null;
	
	
	String resName;
	String contents;
	
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
			String query = "select * from events;"; 
			ResultSet rs = stmt.executeQuery(query); 
			
			while(rs.next())
			{
				JSONObject event = new JSONObject();
	/*			
				event.put("business_name", rs.getString("res_name"));
				event.put("contents", rs.getString("contents"));
				
				eventArray.add(event); */
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return eventArray;
	}

}
