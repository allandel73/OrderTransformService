package esh.bluemix.order.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private String masterOrderNumber = "";
	
	public void parse(InputStream inputStream) {
		
		System.out.println("..Parsing..");
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = builder.parse(inputStream);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("..XPathFactory..");
		
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
	    XPathExpression expr = null;
	    Object result = null;
		try {
			expr = xpath.compile("//PurchaseOrder/ProprietaryInformation[proprietaryInformationType/FreeFormText='MasterOrderNumber']");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	Node node = nodes.item(i);
		    	XPath xpath2 = xfactory.newXPath();
		    	XPathExpression expr2 = null;
		    	expr2 = xpath2.compile("./proprietaryInformationValue/FreeFormText/text()");
				masterOrderNumber = (String)expr2.evaluate(node, XPathConstants.STRING);
				//System.out.println("MasterOrderNumber:" + masterOrderNumber);
		    }
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMasterOrderNumber() {
		return masterOrderNumber;
	}

	public void setMasterOrderNumber(String masterOrderNumber) {
		this.masterOrderNumber = masterOrderNumber;
	}
}
