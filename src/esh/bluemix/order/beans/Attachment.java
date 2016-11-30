package esh.bluemix.order.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Attachment {

	private String requestXML = null;
	private InputStream requestStream = null;

	public Attachment(String requestXML) {
		super();
		this.requestXML = requestXML;
		try {
			setRequestStream(new ByteArrayInputStream(this.requestXML.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InputStream getRequestStream() {
		return requestStream;
	}

	public void setRequestStream(InputStream requestStream) {
		this.requestStream = requestStream;
	}
	
	
	
}
