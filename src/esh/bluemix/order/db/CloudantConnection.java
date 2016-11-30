package esh.bluemix.order.db;

import java.net.MalformedURLException;
import java.util.Map.Entry;
import java.util.Set;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


public class CloudantConnection {
	
	private static String user;
	private static String password;
	private static CloudantClient client = null;

	
	public CloudantConnection() {
		
			
	}
	
	public static CloudantClient createClient() throws MalformedURLException {
        String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String serviceName = null;
        client = null;

        if (VCAP_SERVICES != null) {
            System.out.println("VCAP_SERVICE");
            JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
            Entry<String, JsonElement> dbEntry = null;
            Set<Entry<String, JsonElement>> entries = obj.entrySet();
            for (Entry<String, JsonElement> eachEntry : entries) {
                if (eachEntry.getKey().toLowerCase().contains("cloudant")) {
                    dbEntry = eachEntry;
                    break;
                }
            }
            if (dbEntry == null) {
                throw new RuntimeException("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable");
            }

            obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
            serviceName = (String) dbEntry.getKey();
            System.out.println("Service Name - " + serviceName);

            obj = (JsonObject) obj.get("credentials");

            user = obj.get("username").getAsString();
            password = obj.get("password").getAsString();

            client = ClientBuilder.account(user)
                    .username(user)
                    .password(password)
                    .build();

        } 
        
        if (client!=null) {
        	System.out.println("SUCCESSFULLY CONNECTED TO CLOUDANT!");
        }

        return client;
	}
	
	public static Database connectToDatabase(String dbName) {
		
		if (client!=null) {
			Database dbConnection = client.database(dbName, false);
			
			if (dbConnection!=null) {
				System.out.println("Success: Connected to " + dbName);
			} else {
				System.out.println("Error: No " + dbName + " found.");
			}
			
			return dbConnection;
		} else {
			return null;
		}
		
	}
	

}
