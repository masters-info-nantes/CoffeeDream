package org.alma.middleware.coffeedream;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;


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
	public Response auth(@PathParam("imei") String imei, @PathParam("test") int test){


        DB db = DBMaker.fileDB(new File("file.db"))
                .closeOnJvmShutdown()
                .transactionDisable()
                .make();

        ConcurrentNavigableMap<Integer,String> map = db.treeMap("collectionName");


		HashMap<String, Object> data = new HashMap<>();
		ResponseAuth resp = null;

		System.out.println("test: "+test);
		switch (test){
			default:
				data.put("token", "KUGFYTDLUHKJFIIOM");
				data.put("callback", "http://localhost:8080/coffeedream/token");

				resp = new ResponseAuth("auth", data);
				return Response.ok(resp, MediaType.APPLICATION_JSON).build();

			case 0:
				data.put("error", "Token not found for this IMEI");
				resp = new ResponseAuth("auth", data);
				return Response.status(Response.Status.NOT_FOUND).entity(resp).build();

			case 1:
				data.put("error", "IMEI MAL FORMED");
				resp = new ResponseAuth("auth", data);
				return Response.status(Response.Status.NOT_FOUND).entity(resp).build();

		}
	}

	@POST
	@Path("/token")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response token(@PathParam("token") String token, @PathParam("test") int test){

		HashMap<String, Object> data = new HashMap<>();
		ResponseAuth resp = null;

		switch (test){
			default:
				data.put("user", new UserBean(689876874 , "John", "Doe"));
				resp = new ResponseAuth("token", data);
				return Response.ok(resp, MediaType.APPLICATION_JSON).build();

			case 0:
				data.put("error", "Token not found");
				resp = new ResponseAuth("token", data);
				return Response.status(Response.Status.NOT_FOUND).entity(resp).build();

			case 1:
				data.put("error", "Token not found for this IMEI");
				resp = new ResponseAuth("token", data);
				return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}
	}
}
