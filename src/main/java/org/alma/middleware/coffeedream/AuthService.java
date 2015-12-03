package org.alma.middleware.coffeedream;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
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
	public Response auth(String imei){
		System.out.println("coucou");
		ResponseAuth resp = new ResponseAuth("auth", "imei"+ imei);

        DB db = DBMaker.fileDB(new File("file.db"))
                .closeOnJvmShutdown()
                .transactionDisable()
                .make();

        ConcurrentNavigableMap<Integer,String> map = db.treeMap("collectionName");

        if(true){
			return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
		}
		else {
			return Response.ok(resp, MediaType.APPLICATION_JSON).build();
		}
	}
}
