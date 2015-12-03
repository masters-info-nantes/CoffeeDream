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
    @Path("/alimentation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void alimentation() {

        MapDBStorage mapdb = new MapDBStorage();

        //alimentation de la table d'identities

        UserBean u1 = new UserBean("0600000001","John", "Doe");
        UserBean u2 = new UserBean("0600000002","jacky", "chan");
        UserBean u3 = new UserBean("0600000003","TITI", "TITI");
        UserBean u4 = new UserBean("0600000004","TYTY", "TYTY");
        UserBean u5 = new UserBean("0600000005","TOTOR", "TOTOR");

        String imei1 = "123456789012345";
        String imei2 = "345678901234567";
        String imei3 = "234567890123456";
        String imei4 = "678901234566789";
        String imei5 = "098765432112345";

        mapdb.putUser(imei1,u1);
        mapdb.putUser(imei2,u2);
        mapdb.putUser(imei3,u3);
        mapdb.putUser(imei4,u4);
        mapdb.putUser(imei5,u5);

        mapdb.closeDB();

    }

	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response auth(HashMap<String, Object> request){

		// 1 - No IMEI in request
		if(!request.containsKey("imei")){
			return makeError("auth", "Please provide an imei number to get a token");
		}

		// 2 - Bad IMEI formatting
		String imeiNumber = (String) request.get("imei");

		if(imeiNumber.length() != 15){
			return makeError("auth", "Imei (" + imeiNumber + ") malformed. It contains " + imeiNumber.length() + " characters instead of 15.");
		}

		// 3 - IMEI found in user database
		MapDBStorage mapdb = new MapDBStorage();

		if(mapdb.containsImei(imeiNumber)){
			String token = UUID.randomUUID().toString();
			mapdb.putToken(token, imeiNumber);
			mapdb.closeDB();

			HashMap<String, Object> data = new HashMap<>();
			data.put("token", token);
			data.put("callback", "http://localhost:8080/coffeedream/token");

			ResponseAuth resp = new ResponseAuth("auth", data);
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}

		// 4 - IMEI not found in user database
		else {
			mapdb.closeDB();
			return makeError("auth", "Imei (" + imeiNumber + ") not found in user database");
		}
	}

	@POST
	@Path("/token")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response token(HashMap<String, Object> request){

		// 1 - No Token in request
		if(!request.containsKey("token")){
			return makeError("token", "Please provide a token to get a user profile.");
		}

		// 2 - Bad token formatting
		String tokenNumber = (String) request.get("token");

		if(tokenNumber.length() != UUID.randomUUID().toString().length()){
			return makeError("token", "Token (" + tokenNumber + ") malformed.");
		}

		// 3 - Token found in database
		MapDBStorage mapdb = new MapDBStorage();

		if(mapdb.containsToken(tokenNumber)){
			UserBean user = mapdb.getUserByToken(tokenNumber);
			mapdb.closeDB();

			HashMap<String, Object> data = new HashMap<>();
			data.put("user", user);

			ResponseAuth resp = new ResponseAuth("token", data);
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}

		// 4 - Token not found in database
		else {
			mapdb.closeDB();
			return makeError("token", "Token (" + tokenNumber + ") not from this server.");
		}
	}

	private Response makeError(String caller, String message){
		HashMap<String, Object> data = new HashMap<>();
		data.put("error", message);

		ResponseAuth resp = new ResponseAuth(caller, data);
		return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
	}
}
