package siva.arlimi.servlet;

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
import siva.arlimi.util.IOHelper;

public class FacebookUserRegistrationServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		JSONObject json = IOHelper.readRequest(req);
		writeDB(json);
		
	}

	private void writeDB(JSONObject data)
	{
		// TODO Auto-generated method stub
			try
		{
			String email = data.getString("EMAIL");
			String name = data.getString("NAME");
			
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into facebook_user" +
					" values ('%s','%s');", email, name );
			
			System.out.println(query);
			
			int rs = stmt.executeUpdate(query);
			
			if(1 == rs)
				System.out.println("facebook user updated");
			else
				System.out.println("Fail to update facebook user");
			
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
