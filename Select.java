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
@GET
	@Path("/Employee/{EMP_ID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("EMP_ID")String EMP_ID)
	{

		MysqlConnection connection = new MysqlConnection();
		
		con = connection.getConnection();
		
	try
	{
		stmt=con.createStatement();
		String query =("select * from employee where EMP_ID="+EMP_ID);
		rs=stmt.executeQuery(query);
		while(rs.next())
		{
			//mainObj.accumulate("EMP_ID",rs.getInt("EMP_ID"));
			mainObj.accumulate("END_DATE",rs.getDate("END_DATE"));
			mainObj.accumulate("FIRST_NAME",rs.getString("FIRST_NAME"));
			mainObj.accumulate("LAST_NAME",rs.getString("LAST_NAME"));
			mainObj.accumulate("START_DATE",rs.getDate("START_DATE"));
			mainObj.accumulate("TITLE",rs.getString("TITLE"));
			mainObj.accumulate("ASSIGNED_BRANCH_ID",rs.getInt("ASSIGNED_BRANCH_ID"));
			mainObj.accumulate("DEPT_ID",rs.getInt("DEPT_ID"));
			mainObj.accumulate("SUPERIOR_EMP_ID",rs.getInt("SUPERIOR_EMP_ID"));
			
			
		}
		if(!mainObj.isEmpty())
		{
			return Response.ok().entity(mainObj.toString()).build();
		}else
		{
			mainObj.accumulate("Status", 500);
			mainObj.accumulate("Message", "Something went wrong!");
		}
		}
		catch (SQLException e) {

			mainObj.accumulate("Status", 204);
			mainObj.accumulate("Message", e.getMessage());
		
			
	}
	return Response.noContent().entity(mainObj.toString()).build();

}
@DELETE
	@Path("/Acc/{ACCOUNT_ID}")
	public Response deleteaccount(@PathParam("ACCOUNT_ID")String ACCOUNT_ID) {
		MysqlConnection connection= new MysqlConnection();
		
		con = connection.getConnection();
		Status status =Status.OK;
		
		try {
			
			String query ="DELETE FROM account WHERE ACCOUNT_ID="+ACCOUNT_ID;
			
			stmt=con.createStatement();
			
			int rowCount = stmt.executeUpdate(query);
			
			if(rowCount>0) {
				status=Status.OK;
				mainObj.accumulate("Status", status);
				mainObj.accumulate("Message", " data deleted!");
		}else {
			status=Status.NOT_MODIFIED;
			mainObj.accumulate("Status", status);
			mainObj.accumulate("Message", " oops wrong!");
		}
	}catch(SQLException e) {
		e.printStackTrace();
		status=Status.NOT_MODIFIED;
		mainObj.accumulate("Status", status);
		mainObj.accumulate("Message", "something wrong");
		
	}
		return Response.status(status).entity(mainObj.toString()).build();
	}	
}
