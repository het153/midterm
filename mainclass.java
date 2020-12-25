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

import com.midterm.mainclass;
import com.midterm.Mysqlconnection;
import com.midterm.account;
import com.midterm.employee;

@Path("/aws")
public class mainclass {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	PreparedStatement preparedStatement = null;

	JSONObject mainObj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
public static void main (String a[]) {
		
		mainclass myObj = new mainclass();
	    //System.out.println(myObj.deletedepartment("4").toString());
		
	}

	@GET
	@Path("/getEmp")
	@Produces(MediaType.APPLICATION_JSON)
	/*public String getEmployee() {
		return "MMMM";
	}*/
	public Response getEmployee() {
		
		
		
		Mysqlconnection connection = new Mysqlconnection();

		con = connection.getConnection();
		
		try {
			
			stmt = con.createStatement();

			rs = stmt.executeQuery("Select * from employee");
			

			while (rs.next()) {
				childObj = new JSONObject();

				childObj.accumulate("Employee Number", rs.getString("EMP_ID"));
				childObj.accumulate("Last Name", rs.getString("LAST_NAME"));
				childObj.accumulate("First Name", rs.getString("FIRST_NAME"));
				childObj.accumulate("Start Date", rs.getString("START_DATE"));
				childObj.accumulate("Title", rs.getString("TITLE"));

				jsonArray.put(childObj);
			}

			mainObj.put("Employee", jsonArray);
		} catch (SQLException e) {
			//return "SQL Exception : " + e.getMessage();
			System.out.println("SQL Exception : " + e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				//return "Finally Block SQL Exception : " + e.getMessage();
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		
	

		return Response.status(200).entity(mainObj.toString()).build();
}
	@POST
	@Path("/createEmp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEmployee(employee emp)
	{
		Mysqlconnection connection = new Mysqlconnection();
		
		con = connection.getConnection();
		
	try
	{
		
		
		
		
		String query = "INSERT INTO `midterm`.`employee`(`EMP_ID`,`END_DATE`,`FIRST_NAME`,`LAST_NAME`,`START_DATE`,`ASSIGNED_BRANCH_ID`,`DEPT_ID`,`SUPERIOR_EMP_ID`)"
				+ "VALUES(?,?,?,?,?,?,?,?)";
		
		
		preparedStatement = con.prepareStatement(query);

		preparedStatement.setInt(1, emp.getEMP_ID());
		preparedStatement.setDate(2, emp.getEND_DATE());
		preparedStatement.setString(3,emp.getFIRST_NAME());
		preparedStatement.setString(4,emp.getLAST_NAME());
		preparedStatement.setDate(5,emp.getSTART_DATE());
		preparedStatement.setInt(6, emp.getASSIGNED_BRANCH_ID());
		preparedStatement.setInt(7, emp.getDEPT_ID());
		preparedStatement.setInt(8, emp.getSUPERIOR_EMP_ID());
		
		int rowCount = preparedStatement.executeUpdate();
		
		if(rowCount>0)
		{
			System.out.println("Record inserted Successfully! : "+rowCount);
			
			mainObj.accumulate("Status", 201);
			mainObj.accumulate("Message", "Record Successfully added!");
		}else
		{
			mainObj.accumulate("Status", 500);
			mainObj.accumulate("Message", "Something went wrong!");
		}
		
		
	}catch (SQLException e) {

		mainObj.accumulate("Status", 500);
		mainObj.accumulate("Message", e.getMessage());
	}finally {
		try
		{
			con.close();
			preparedStatement.close();
		}catch (SQLException e) {
			System.out.println("Finally SQL Exception : "+e.getMessage());
		}
	}
	
	
	return Response.status(201).entity(mainObj.toString()).build();
	
		
		
	}
	@GET
	@Path("/account/{ACCOUNT_ID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccount(@PathParam("ACCOUNT_ID")String ACCOUNT_ID)
	{

		Mysqlconnection connection = new Mysqlconnection();
		
		con = connection.getConnection();
		
	try
	{
		stmt=con.createStatement();
		String query =("select * from account where account_id="+ACCOUNT_ID);
		rs=stmt.executeQuery(query);
		while(rs.next())
		{
			mainObj.accumulate("ACCOUNT_ID",rs.getString("ACCOUNT_ID"));
			mainObj.accumulate("AVAIL_BALANCE",rs.getString("AVAIL_BALANCE"));
			mainObj.accumulate("CLOSE_DATE",rs.getString("CLOSE_DATE"));
			mainObj.accumulate("LAST_ACTIVITY_DATE",rs.getString("LAST_ACTIVITY_DATE"));
			mainObj.accumulate("OPEN_DATE",rs.getString("OPEN_DATE"));
			mainObj.accumulate("PENDING_BALANCE",rs.getString("PENDING_BALANCE"));
			mainObj.accumulate("STATUS",rs.getString("STATUS"));
			mainObj.accumulate("CUST_ID",rs.getString("CUST_ID"));
			mainObj.accumulate("OPEN_BRANCH_ID",rs.getString("OPEN_BRANCH_ID"));
			mainObj.accumulate("OPEN_EMP_ID",rs.getString("OPEN_EMP_ID"));
			mainObj.accumulate("PRODUCT_CD",rs.getString("PRODUCT_CD"));
			
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
	@Path("/deldpt/{DEPT_ID}")
	public Response deletedepartment(@PathParam("DEPT_ID")String DEPT_ID) {
		Mysqlconnection connection= new Mysqlconnection();
		
		con = connection.getConnection();
		Status status =Status.OK;
		
		try {
			
			String query ="DELETE FROM department WHERE DEPT_ID="+DEPT_ID;
			
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
	
	@PUT
	@Path("/updateAcc/{ACCOUNT_ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateaccount(@PathParam("ACCOUNT_ID") int ACCOUNT_ID,account acc)
	{

		Mysqlconnection connection = new Mysqlconnection();
		
		con = connection.getConnection();
		
		try
		{
			String query = "UPDATE midterm.account SET AVAIL_BALANCE = ? , CLOSE_DATE = ? , LAST_ACTIVITY_DATE = ? , OPEN_DATE = ? , PENDING_BALANCE = ? , STATUS = ? WHERE ACCOUNT_ID ="+ACCOUNT_ID;
		
			preparedStatement = con.prepareStatement(query);

			
				preparedStatement.setFloat(1, acc.getAVAIL_BALANCE());
				preparedStatement.setDate(2,acc.getCLOSE_DATE());
				preparedStatement.setDate(3,acc.getLAST_ACTIVITY_DATE());
				preparedStatement.setDate(4,acc.getOPEN_DATE());
				preparedStatement.setFloat(5, acc.getPENDING_BALANCE());
				preparedStatement.setString(6, acc.getSTATUS());
				
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
