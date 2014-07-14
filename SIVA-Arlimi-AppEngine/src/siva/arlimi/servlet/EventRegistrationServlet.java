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
		String line = null;
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
		
		try 
		{
			JSONObject jsonObject = new JSONObject(sb.toString());
			System.out.println(jsonObject.toString());
			
			String user = (String) jsonObject.get("USER");
			System.out.println(user.toString());
			
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
	
	private void writeDB(String id)
	{
		try
		{
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into gcm_registration_id" +
			"(reg_id) values ('%s');", id); 
			
			int rs = stmt.executeUpdate(query);
			
			if(rs ==1)
				System.out.println("GCM Id Update Success");
			else
				System.out.println("Gcm registration fail");
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	
	}

}
