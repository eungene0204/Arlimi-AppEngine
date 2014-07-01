package siva.arlimi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
		PrintWriter out = resp.getWriter();
		
		out.println("goood result");
		
		writeDB(null);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		System.out.println("event registration get");
	
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
	}
	
	private void writeDB(String contents)
	{
		/*
		try
		{
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO events values('네일샵','네일아트 2만원');";
			int rs = stmt.executeUpdate(query);
			
			if(rs ==1)
				System.out.println("Update Success");
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		*/
		
	}
}
