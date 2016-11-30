package esh.bluemix.order.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XMLTester {

	public static void main(String[] args) {
		
		System.out.println("XML Parser Tester");
		
		XMLParser xparser = new XMLParser();
		
		File file = new File("/Users/allandel/OneDrive/BLUEMIX/BMS3Y4Request.xml");
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		xparser.parse(is);

	}

}
