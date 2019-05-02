package no.hvl.dat110.ac.rest;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// implement the routes required for the access control service


		post("/accessdevice/log", (request, response) -> {
			Gson gson= new Gson();
			AccessMessage am = gson.fromJson(request.body(),AccessMessage.class );
			int id = accesslog.add(am.getMessage());
			return gson.toJson(accesslog.get(id));
		});

		get("/accessdevice/log", (req, res) -> {
			return accesslog.toJson();
		});

		get("/accessdevice/log/:id", (req, res) -> {
			int id = -1;
			try {
				id = Integer.parseInt(req.params(":id"));

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			Gson gson = new Gson();
			return gson.toJson(accesslog.get(id));
		});

		put("accessdevice/code", (req,res) -> {
			Gson gson = new Gson();
			AccessCode ac = gson.fromJson(req.body(), AccessCode.class);
			ac.setAccesscode(accesscode.getAccesscode());
			return req.body();
		});

		get("/accessdevice/code", (request, response) -> {
			return accesscode.getAccesscode();
		});

		delete("/accessdevice/log", (request, response) -> {
			accesslog.clear();
			return accesslog.toJson();
		});
    }
    
}
