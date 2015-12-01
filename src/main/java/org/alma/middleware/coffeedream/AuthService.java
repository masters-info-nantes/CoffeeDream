package org.alma.middleware.coffeedream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class AuthService {


	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		return "coucou";
	}

	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response auth(String imei){
		System.out.println("coucou");
		ResponseAuth resp = new ResponseAuth("auth", "imei"+ imei);

		if(true){
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}
		else {
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}
	}
}
