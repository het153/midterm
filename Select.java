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
	    //System.out.println(myObj.deletedepartment("4").toString());
		
	}
	
}
