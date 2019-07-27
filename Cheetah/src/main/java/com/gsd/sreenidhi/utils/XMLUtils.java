package com.gsd.sreenidhi.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class XMLUtils {

	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

	/**
	 * @param xml XML FIle
	 * @return String
	 */
	public String getrootElement(File xml) {
		Element rootElement = null;
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document document = builder.parse(new File(xml.getAbsolutePath()));

			rootElement = document.getDocumentElement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement.getNodeName();
	}

	/**
	 * @param tagName Tag Name
	 * @param element Element
	 * @return String
	 */
	protected String getValue(String tagName, Element element) {
		NodeList list = element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();

			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}

		return null;
	}
	
	/**
	 * @param xml XML String
	 * @param tagName tagname
	 * @param iterator integer
	 * @return String
	 */
	public static String getTagValue(String xml, String tagName, int iterator){
	    return xml.split("<"+tagName+">")[iterator].split("</"+tagName+">")[0];
	}
	
	/**
	 * @param xml XML String
	 * @param tagName tagname
	 * @return String array
	 */
	public String[] getElementsList(String xml, String tagName) {
		
		StringBuilder values = new StringBuilder();
		org.jsoup.nodes.Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		for (org.jsoup.nodes.Element e : doc.select("Request")) {
			values.append(e+",");
		}
		
		String val = values.toString().trim();
		val = val.substring(0, val.length());
		
		
		return val.split(",");
	}
	
	
}
