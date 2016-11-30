package esh.bluemix.order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.ibm.json.java.JSONObject;

import esh.bluemix.order.beans.Attachment;
import esh.bluemix.order.beans.OrderData;
import esh.bluemix.order.db.CloudantConnection;
import esh.bluemix.order.db.OrderDBHandler;
import esh.bluemix.order.util.XMLParser;

@Path("/ordertransform")
public class OrderTransform {

	@POST
	@Produces("application/xml")
	@Consumes("application/xml")
	public String proccess(String requestFile) throws Exception, IOException {
		
		System.out.println("OrderTransformService Started!");
		
		//System.out.println("Data: requestFile[" + requestFile + "]");
		
		CloudantConnection.createClient();
		Database dbConnect = CloudantConnection.connectToDatabase("esh_db");
		
		Attachment attach = new Attachment(requestFile);
		XMLParser xParser = new XMLParser();
		xParser.parse(attach.getRequestStream());
		System.out.println("MasterOrderNumber:" + xParser.getMasterOrderNumber());
		
		OrderData data = new OrderData(xParser.getMasterOrderNumber());
		data.set_id(xParser.getMasterOrderNumber());
		
		OrderDBHandler orderDBHandler = new OrderDBHandler(data, attach, dbConnect);
		orderDBHandler.process();
	
        return requestFile;
        
	}

}
