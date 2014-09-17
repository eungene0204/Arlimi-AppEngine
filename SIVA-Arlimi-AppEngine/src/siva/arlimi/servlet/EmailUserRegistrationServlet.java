package siva.arlimi.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
		OutputStreamWriter writer;
		
		writer = new OutputStreamWriter(resp.getOutputStream(), "utf8");
		writer.write("hello world");
		
		System.out.println("hihihihi");
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
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
				writeDB(json);
				
			}
		}
		catch(Exception e){} 
		
	}

	private void writeDB(JSONObject data)
	{
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
				System.out.println("Email user updated");
			else
				System.out.println("Fail to update email user");
			
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
		
	}

}
