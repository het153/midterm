package com.midterm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.hk2.api.Self;
import org.json.JSONArray;
import org.json.JSONObject;

import com.midterm.Select;

@Path("/aws")
public class Select {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	PreparedStatement preparedStatement = null;

	JSONObject mainObj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
public static void main (String a[]) {
		
		Select myObj = new Select();
	    
		
	}
	@PUT
	@Path("/updatedept/{DEPT_ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatedepartment(@PathParam("DEPT_ID") int DEPT_ID,department dept)
	{

		MysqlConnection connection = new MysqlConnection();
		
		con = connection.getConnection();
		
		try
		{
			String query = "UPDATE midterm.department SET NAME= ? , STATUS = ? WHERE DEPT_ID ="+DEPT_ID;
		
			preparedStatement = con.prepareStatement(query);

			
				preparedStatement.setString(1, dept.getNAME());
				
				
			int rowCount = preparedStatement.executeUpdate();
			Status	status=Status.OK;
			if(rowCount>0)
			{
				status=Status.OK;
				
				mainObj.accumulate("Status", status);
				mainObj.accumulate("Message", "Record Successfully updated!");
			}else
			{
				status = Status.NOT_MODIFIED;
				mainObj.accumulate("Status", status);
				mainObj.accumulate("Message", "Something went wrong!");
			}
			
				
				
		
		
	}
		catch (SQLException e) {
		
		e.printStackTrace();
	}
return null;
}
	
}
