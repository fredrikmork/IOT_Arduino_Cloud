package no.hvl.dat110.ac.rest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {
	
	private AtomicInteger cid;
	protected ConcurrentHashMap<Integer, AccessEntry> log;
	
	public AccessLog () {
		this.log = new ConcurrentHashMap<Integer,AccessEntry>();
		cid = new AtomicInteger(0);
	}

	// add an access entry for the message and return assigned id
	public int add(String message) {
		int id = cid.getAndIncrement();
		AccessEntry accessEntry = new AccessEntry(id, message);
		log.put(id, accessEntry);
		return id;

	}
		
	// retrieve a specific access entry
	public AccessEntry get(int id) {
		return log.get(id);

	}
	
	// clear the access entry log
	public void clear() {
		log.clear();
	}
	
	//  JSON representation of the access log
	public String toJson () {
//		String json = "[";
//    	for(int i = 0; i < cid.get(); i++){
//			json += "\n\t{\n\t\t\"id\": " + cid + ",\n\t\t\"message\": " + get(cid.get())  + "\n\t}";
//			if(cid.get() != i-1 ){
//				json += ",";
//			}
//		}
//		json += "]";
//
//    	return json;

    	Gson gson = new Gson();

    	return gson.toJson(log.values().toArray());
    }
}
