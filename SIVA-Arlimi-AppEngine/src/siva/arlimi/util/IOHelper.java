package siva.arlimi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class IOHelper 
{
	
	public static JSONObject readRequest(HttpServletRequest req)
	{
		
		JSONObject json = null;
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

			if (!sb.toString().isEmpty())
			{
				try
				{
					json = new JSONObject(sb.toString());

				} catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
		}

		return json;

	}
	
	public static void sendResponse(Object result, HttpServletResponse resp )
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

}
