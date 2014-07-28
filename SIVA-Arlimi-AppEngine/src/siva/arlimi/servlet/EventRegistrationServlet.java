package siva.arlimi.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siva.arlimi.database.DatabaseConnection;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class EventRegistrationServlet extends HttpServlet
{
	private static final long serialVersionUID = -9064508857005070633L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		System.out.println("event registration post");
		
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader bf;	
	
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
				JSONObject jsonObject = new JSONObject(sb.toString());
				writeDB(jsonObject);
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
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		System.out.println("event registration get");
		
		PrintWriter out = resp.getWriter();
		
		out.println("Hello World");
		
		//writeDB("TEST");
		
		/*
		try
		{
			JSONObject json = new JSONObject(req.getParameter("USER"));
			Iterator<String> it = json.keys();
			
			while(it.hasNext())
			{
				String key = it.next();
			}
			
			PrintWriter out = resp.getWriter();
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			System.out.println("null!!");
			e.printStackTrace();
		} 
		*/
	}
	
	private void writeDB(JSONObject json)
	{
		String email = "";
		try {
			email = (String)json.get("EMAIL");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		email="Amsa@gmail.gmail";
		String contents = "Amsa";
		String latitude = "37.550424";
		String longitude = "127.127530";
		
		try
		{
			int id = 1;
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into event" +
			"(id, owner_email, event_contents, event_start_date, event_start_time, event_end_date, event_end_time, event_radius, event_latitude, event_longitude) values ('%d','%s','%s','%s','%s','%s','%s','%s','%s','%s');", 
			  		 id++, email, contents,null,null,null,null,null, latitude, longitude); 
			
			int rs = stmt.executeUpdate(query);
			
			if(rs ==1)
				System.out.println("Event Update Success");
			else
				System.out.println("Gcm registration fail");
			
		} catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	
	}

}
