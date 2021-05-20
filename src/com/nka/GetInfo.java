package com.nka;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// получение орг. структуры, всех поручений, моих поручений
@Path("/get_info")
public class GetInfo {
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response getAllInfo(String info) {
    JSONObject data = null;
    String errorMsg = null;
    try {
      data = new JSONObject(info);
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
    
    errorMsg = checkData(data);
    if(errorMsg != null) {
        JSONObject error = new JSONObject("{\"status\": false}");
        error.put("error", errorMsg);
        return Response
               .status(Response.Status.OK)
               .entity(error.toString())
               .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
               .build();
      }
    
    GetOrganization go = new GetOrganization();
    GetAssignment ga = new GetAssignment();
    
    JSONObject res = new JSONObject("{\"status\": true}");
    
    JSONArray org = new JSONArray();
    org.put(go.getOrg(data.getInt("id"), data.getString("role")));
    res.put("orgStruckt", org);
    res.put("allAssignment", ga.getAllAssignment(data.getInt("id"), data.getString("role")));
    res.put("myAssignment", ga.getMyAssignment(data.getInt("id"), data.getString("role")));
    
    return Response
           .status(Response.Status.OK)
           .entity(res.toString())
           .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
           .build();
  }
  
  private String checkData(JSONObject data) {
    if(has(data, "id") == false) {
      return "Не был указан идентификатор пользователя";
    }
    if(has(data, "role") == false) {
      return "Не была указана роль пользователя";
    }
    
    return null;
  }
  
  private boolean has(JSONObject jobj, String name) {
    if(jobj.has(name) == false) {
      return false;
    }
    
    return jobj.isNull(name) == false;
  }
}