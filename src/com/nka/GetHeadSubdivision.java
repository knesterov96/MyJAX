package com.nka;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/getHeadSubdivision")
public class GetHeadSubdivision {
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
    
    StartGetHeadSubdivision sghs = new StartGetHeadSubdivision();
    
    return Response
           .status(Response.Status.OK)
           .entity(sghs.startGet(jobj).toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
}