package siva.arlimi.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
import siva.arlimi.util.EventUtil;

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
		
		StringBuilder sb = readRequest(req);
		JSONObject events = conversionToJson(sb);
		String[] eventIds = readIds(events);
		JSONArray result = readDB(eventIds);
		
		sendResult(result, resp);

	}
	
	private void sendResult(JSONArray result,HttpServletResponse resp) throws UnsupportedEncodingException
	{
		OutputStreamWriter writer;
		try
		{
			writer = new OutputStreamWriter(resp.getOutputStream(),"utf8");
			writer.write(result.toString());
			writer.close();
			
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private StringBuilder readRequest(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader bf;

		try
		{
			bf = req.getReader();
			while (null != (line = bf.readLine()))
			{
				sb.append(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return sb;
	}
	
	private JSONObject conversionToJson(StringBuilder sb)
	{
	
		JSONObject json = new JSONObject();

		try
		{
			if (!sb.toString().isEmpty())
			{
				json = new JSONObject(sb.toString());
			} 
			else
			{
				System.out.println("StringBuilder is empty");
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json;
	}

	private String[] readIds(JSONObject json)
	{
		int len = json.length() - 1;
		String[] eventIds = new String[len];

		for (int i = 0; i < len; i++)
		{
			try
			{
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
		JSONArray jsonArray = new JSONArray();

		String query = makeQuery(eventId);
		 	
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query); 
			
			while(rs.next())
			{
				JSONObject event = new JSONObject();
				
				event.put(EventUtil.EVENT_ID, Integer.valueOf(rs.getInt("id")));

				event.put(EventUtil.EMAIL, rs.getString("owner_email"));
				event.put(EventUtil.EVENT_CONTENTS,
						rs.getString("event_contents"));
				event.put(EventUtil.EVENT_LATITUDE,
						rs.getString("event_latitude"));
				event.put(EventUtil.EVENT_LONGITUDE,
						rs.getString("event_longitude"));
				
				jsonArray.put(event);

			}
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return jsonArray;
	}

	private String makeQuery(String[] eventId)
	{
		StringBuilder sb = new StringBuilder("select * from event where id in(");

		for (int i = 0; i < eventId.length; i++)
		{
			if (null != eventId[i])
			{
				sb.append(eventId[i]);
				if (!((eventId.length - 1) == i))
					sb.append(",");
			}
		}
		sb.append(")");

		return sb.toString();
	}

}
