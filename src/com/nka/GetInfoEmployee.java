package com.nka;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/get_info_employee")
public class GetInfoEmployee {
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response getInfoEmployee(String info) {
    JSONObject allInfo = null;
    String errorMsg = null;
    
    try {
      allInfo = new JSONObject(info);
    } catch (JSONException jsone) {
      errorMsg = jsone.getMessage();
    }
    
    if(allInfo == null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
            .status(Response.Status.OK)
            .entity(error.toString())
            .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
            .build();
    }
    
    if(allInfo.has("employeeInfo") == false) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
            .status(Response.Status.OK)
            .entity(error.toString())
            .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
            .build();
    }
    
    if(allInfo.isNull("employeeInfo")) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
            .status(Response.Status.OK)
            .entity(error.toString())
            .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
            .build();
    }
    
    JSONObject employeeInfo = allInfo.getJSONObject("employeeInfo");
    
    errorMsg = checkEmployeeInfo(employeeInfo);
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
            .status(Response.Status.OK)
            .entity(error.toString())
            .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
            .build();
    }
    
    EmployeeInfo ei = new EmployeeInfo(
        employeeInfo.getInt("headOrganization"),
        employeeInfo.getInt("headSubdivision"),
        employeeInfo.getInt("employee")
    );
    
    return Response
           .status(Response.Status.OK)
           .entity(ei.getInfo().toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  private String checkEmployeeInfo(JSONObject employeeInfo) {
    if((employeeInfo.has("headOrganization") &&
       employeeInfo.has("headSubdivision") &&
       employeeInfo.has("employee")) == false) {
      return "Сотрудник организации не был выбран";
    }
    if(employeeInfo.isNull("headOrganization") ||
       employeeInfo.isNull("headSubdivision") ||
       employeeInfo.isNull("employee")) {
      return "Сотрудник организации не был выбран";
    }
    
    if((employeeInfo.getInt("headOrganization") == -1) &&
       (employeeInfo.getInt("headSubdivision") == -1) &&
       (employeeInfo.getInt("employee") == -1)) {
      return "Сотрудник организации не был выбран";
    }
    
    return null;
  }
}