package siva.arlimi.shop.servlet;

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
import siva.arlimi.database.util.DatabaseUtil;
import siva.arlimi.login.util.LoginUtil;
import siva.arlimi.shop.util.ShopUtils;
import siva.arlimi.util.IOHelper;

public class ShopRegistrationServlet extends HttpServlet
{
	private static final long serialVersionUID = -9029041810745289351L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		System.out.println("hahahahahahah");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		JSONObject json = IOHelper.readRequest(req);
		int result = writeDB(json);

		if (result == DatabaseUtil.UPDATE_SUCCESS)
			IOHelper.sendResponse(ShopUtils.RESULT_OK, resp);
		else
			IOHelper.sendResponse(ShopUtils.RESULT_FAIL, resp);

	}

	private int writeDB(JSONObject data)
	{
		
		int result = 0;
		
		try
		{
			String email = data.getString(LoginUtil.EMAIL);
			String name = data.getString(ShopUtils.KEY_NAME);
			String address = data.getString(ShopUtils.KEY_ADDRESS);
			String detail = data.getString(ShopUtils.KEY_DETAIL_ADDRESS);
			String phone =  data.getString(ShopUtils.KEY_PHONE);
			String zip = data.getString(ShopUtils.KEY_ZIP);
			double latitude = data.getDouble(ShopUtils.KEY_LATITUDE);
			double longitude = data.getDouble(ShopUtils.KEY_LONGITUDE);
			
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String query = String.format("insert into shop_list" +
					" values ('%s','%s', '&s','%s','%s', '%s','%f', '%f' );",
					email, name, address, detail, zip, phone, latitude, longitude );
			
			System.out.println(query);
			
			int rs = stmt.executeUpdate(query);
			
			if(1 == rs)
				 result = DatabaseUtil.UPDATE_SUCCESS;
			else
				result = DatabaseUtil.UPDATE_FAIL;
			
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
		
		return result;
		
	}
	

}
