package com.nka;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

// Класс для создания поручения, редактирования, удаления поручений
@Path("/assignment")
public class AssignmentWork {
  
  // создание
  @Path("/create")
  @POST
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response createAssignment(String data) {
    JSONObject jobj = null;
    String errorMsg = null;
    try {
      jobj = new JSONObject(data);
    } catch (JSONException e) {
      errorMsg = e.getMessage();
    }
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
             .status(Response.Status.OK)
             .entity(error.toString())
             .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
             .build();
    }
    
    AssignmentCreater ac = new AssignmentCreater();
    
    return Response
           .status(Response.Status.OK)
           .entity(ac.create(jobj).toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  // создание
  @Path("/get")
  @POST
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response getAssignment(String data) {
    JSONObject jobj = null;
    String errorMsg = null;
    try {
      jobj = new JSONObject(data);
    } catch (JSONException e) {
      errorMsg = e.getMessage();
    }
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
             .status(Response.Status.OK)
             .entity(error.toString())
             .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
             .build();
    }
    
    AssignmentGet ag = new AssignmentGet();
    
    return Response
           .status(Response.Status.OK)
           .entity(ag.get(jobj).toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  // создание
  @Path("/update")
  @POST
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response updateAssignment(String data) {
    JSONObject jobj = null;
    String errorMsg = null;
    try {
      jobj = new JSONObject(data);
    } catch (JSONException e) {
      errorMsg = e.getMessage();
    }
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
             .status(Response.Status.OK)
             .entity(error.toString())
             .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
             .build();
    }
    
    AssignmentCreater ac = new AssignmentCreater();
    ac.create(jobj);
    
    return Response
           .status(Response.Status.OK)
           .entity("{\"status\": true}")
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  // создание
  @Path("/delete")
  @POST
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response deleteAssignment(String data) {
    JSONObject jobj = null;
    String errorMsg = null;
    try {
      jobj = new JSONObject(data);
    } catch (JSONException e) {
      errorMsg = e.getMessage();
    }
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
             .status(Response.Status.OK)
             .entity(error.toString())
             .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
             .build();
    }
    
    AssignmentDeleter ad = new AssignmentDeleter();
    ad.delete(jobj);
    
    return Response
           .status(Response.Status.OK)
           .entity("{\"status\": true}")
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  // исполнение
  @Path("/execute")
  @POST
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response executeAssignment(String data) {
    JSONObject jobj = null;
    String errorMsg = null;
    try {
      jobj = new JSONObject(data);
    } catch (JSONException e) {
      errorMsg = e.getMessage();
    }
    
    if(errorMsg != null) {
      JSONObject error = new JSONObject("{\"status\": false}");
      error.put("error", errorMsg);
      
      return Response
             .status(Response.Status.OK)
             .entity(error.toString())
             .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
             .build();
    }
    
    AssignmentExecute ae = new AssignmentExecute();
    
    return Response
           .status(Response.Status.OK)
           .entity(ae.execute(jobj).toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
}