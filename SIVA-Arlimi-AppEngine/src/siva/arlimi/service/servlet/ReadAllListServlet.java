package siva.arlimi.service.servlet;

import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.database.DatabaseConnection;
import siva.arlimi.service.util.ServiceUtil;

public class ReadAllListServlet extends HttpServlet
{
	private static final long serialVersionUID = 4676467477869433577L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
	
		JSONArray eventArray = readDB();
		
		System.out.println("eventArray"  + eventArray.toString());
		
		OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(),"utf8");
		writer.write(eventArray.toString());
		writer.close();
	
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
			
		System.out.println("service get");
		
		PrintWriter out = resp.getWriter();
		
		out.println("Hello World");
	}
	
	private JSONArray readDB()
	{
		Connection conn = null;
		JSONArray serviceArray= new JSONArray();
		
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = "select * from shop_list;"; 
			ResultSet rs = stmt.executeQuery(query); 
			
			
			while(rs.next())
			{
				JSONObject service = new JSONObject();
				
				try
				{
					service.put(ServiceUtil.SHOP_NAME, rs.getString("name"));
					service.put(ServiceUtil.SHOP_ADDRESS, rs.getString("address"));
					service.put(ServiceUtil.SHOP_ADDRESS_DETAIL, rs.getString("detail_address"));
					service.put(ServiceUtil.SHOP_ZIP, rs.getString("zip"));
					service.put(ServiceUtil.SHOP_NUM, rs.getString("phone_number"));
					service.put(ServiceUtil.SHOP_LATITUDE,rs.getDouble("latitude"));
					service.put(ServiceUtil.SHOP_LONGITUDE, rs.getDouble("longitude"));
				
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				
				serviceArray.put(service);
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
		
		return serviceArray;
	}


}
