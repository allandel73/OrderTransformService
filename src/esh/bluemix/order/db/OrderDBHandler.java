package esh.bluemix.order.db;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

import esh.bluemix.order.beans.Attachment;
import esh.bluemix.order.beans.OrderData;

public class OrderDBHandler {

	private OrderData orderData = null;
	private Attachment attach = null;
	private Database dbConnect = null;
	
	public OrderDBHandler(OrderData data, Attachment attach, Database dbConnect) {
		this.orderData = data;
		this.attach = attach;
		this.dbConnect = dbConnect;
	}
	
	public void process() {
		
		String result = "Success";
		
		boolean exists = dbConnect.contains(orderData.get_id());
		
		if (!exists) {
			System.out.println("Order ID " + orderData.get_id() + " doesn't exist");
			dbConnect.save(orderData);
			this.orderData = dbConnect.find(OrderData.class, orderData.get_id());
			result = "Added";
		} else {
			System.out.println("Order ID " + orderData.get_id() + " exists!");
			this.orderData = dbConnect.find(OrderData.class, orderData.get_id());
			Response response = dbConnect.update(orderData);
			this.orderData.set_rev(response.getRev());
			System.out.println(this.orderData);
			result = "Existed";
		}
	
		System.out.println("Result:" + result);
		dbConnect.saveAttachment(attach.getRequestStream(), "Request3Y4XML", "text/xml",orderData.get_id(), orderData.get_rev());
		System.out.println("Attachment is saved as well");
	}

}
