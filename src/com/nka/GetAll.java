package com.nka;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/get_all")
public class GetAll {
  @GET
  @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
  public Response home() {
    GetAllPeople gap = new GetAllPeople();
    
    return Response
      .status(Response.Status.OK)
      .entity(gap.getPeople().toString())
      .type(MediaType.APPLICATION_JSON + ";charset=utf-8")
      .build();
  }
}