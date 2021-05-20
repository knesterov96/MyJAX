package com.nka;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/home")
public class Home {
  @GET
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response home() {
    JSONObject res = new JSONObject("{\"status\": true}");
    res.put("msg", "Hello, world!");
    
    return Response
          .status(Response.Status.OK)
          .entity(res.toString(1))
          .build();
  }
  
  /*
  @Path("/home/{msg}")
  @GET
  @Produces("text/html")
  public String home(@PathParam("msg") String msg) {
    return "Your msg is: \"" + msg + "\"";
  }
  */
}