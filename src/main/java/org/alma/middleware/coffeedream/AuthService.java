package org.alma.middleware.coffeedream;

import org.alma.middleware.coffeedream.Bean.UserBean;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;


@Path("")
public class AuthService {

	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response auth(HashMap<String, Object> request){


		HashMap<String, Object> data = new HashMap<>();
		ResponseAuth resp = null;

		// 1 - No IMEI in request
		if(!request.containsKey("imei")){
			data.put("error", "Please provide an imei number to get a token");
			resp = new ResponseAuth("auth", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}

		// 2 - Bad IMEI formatting
		String imeiNumber = (String) request.get("imei");

		if(imeiNumber.length() != 15){
			data.put("error", "Imei (" + imeiNumber + ") malformed. It contains " + imeiNumber.length() + " characters instead of 15.");
			resp = new ResponseAuth("auth", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}

		// 3 - IMEI found in user database
		if(mapdb.contains(imeiNumber)){
			String token = UUID.randomUUID().toString();
			mapdb.add(token, imeiNumber);

			data.put("token", token);
			data.put("callback", "http://localhost:8080/coffeedream/token");

			resp = new ResponseAuth("auth", data);
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}

		// 4 - IMEI not found in user database
		else {
			data.put("error", "Imei (" + imeiNumber + ") not found in user database");
			resp = new ResponseAuth("auth", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}
	}

	@POST
	@Path("/token")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response token(HashMap<String, Object> request){

		HashMap<String, Object> data = new HashMap<>();
		ResponseAuth resp = null;

		// 1 - No Token in request
		if(!request.containsKey("token")){
			data.put("error", "Please provide a token to get a user profile");

			resp = new ResponseAuth("token", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}

		// 2 - Bad token formatting
		String tokenNumber = (String) request.get("token");

		if(tokenNumber.length() != UUID.randomUUID().toString().length()){
			data.put("error", "Token (" + tokenNumber + ") malformed.");

			resp = new ResponseAuth("token", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}

		// 3 - Token found in database
		if(mapdb.contains(tokenNumber)){
			String imeiNumber = mapdb.get(tokenNumber);
			UserBean user = mapdb.get(imeiNumber);

			data.put("user", user);

			resp = new ResponseAuth("token", data);
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}

		// 4 - Token not found in database
		else {
			data.put("error", "Token (" + tokenNumber + ") not from this server");

			resp = new ResponseAuth("token", data);
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}
	}
}
