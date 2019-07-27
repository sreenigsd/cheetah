package com.gsd.sreenidhi.cheetah.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author sm This class is used to handle parsing of Data from XML
 */
public class XMLParser {
	protected static String key = "";
	protected static String value = "";
	protected static LinkedHashMap<String, String> lHashMap = new LinkedHashMap<String, String>();
	protected static Log logger;

	/**
	 * @param filepath
	 *            The relative filepath from which the Data should be retrieved.
	 * @param dataKey
	 *            The key (identifier) corresponding to the data to be retrieved.
	 * @return LinkedHashMap
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static LinkedHashMap<String, String> getData(String filepath, String dataKey) throws CheetahException {

		try {
			int ctr = 0;

			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			inputFactory.setProperty("javax.xml.stream.isCoalescing", true);
			// read the XML document
			InputStream in = new FileInputStream(filepath);

			XMLStreamReader parser = inputFactory.createXMLStreamReader(in);

			for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next()) {
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:

					if (parser.getLocalName().equalsIgnoreCase(dataKey)) {
						ctr = 1;
					}

					if (ctr == 1 && !parser.getLocalName().isEmpty()) {
						key = parser.getLocalName();
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					if (parser.isEndElement()) {
						if (parser.getLocalName().equalsIgnoreCase(dataKey))
							ctr = 2;
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					if (ctr == 1 && !key.isEmpty()) {
						value = parser.getText();
						if (!value.isEmpty() && !key.equals(dataKey)) {
							lHashMap.put(key, value);
							key = "";
						}
					}
					break;

				default:
					break;

				}// end switch

				if (ctr == 2)
					break;
			} // end for

			// Close the objects
			parser.close();
			in.close();

		} // end try

		catch (FileNotFoundException e) {
			logger.logMessage(null, "XMLParser", "XML File Not Found", Constants.LOG_ERROR, false);
		} catch (XMLStreamException e) {
			logger.logMessage(null, "XMLParser", "Stream error with XML File", Constants.LOG_ERROR, false);
		} catch (IOException e) {
			logger.logMessage(null, "XMLParser", "I/O error with XML File", Constants.LOG_ERROR, false);
		}
		return lHashMap;
	}

	/**
	 * @param xmlFile
	 *            Type File - Input Excel file that needs to be converted
	 * @return JSON String
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String xmlToJson(File xmlFile) throws CheetahException {

		File f = xmlFile;
		BufferedReader br = null;
		FileReader fr = null;
		StringBuffer sb = new StringBuffer();

		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}

		} catch (IOException e) {
			throw new CheetahException(e);
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				throw new CheetahException(ex);
			}
		}

		String xml = sb.toString();
		int prettyPrintIndentFactor = 4;
		String json = null;
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			String jsonPrettyPrintString = xmlJSONObj.toString(prettyPrintIndentFactor);
			json = jsonPrettyPrintString;
		} catch (JSONException je) {
			throw new CheetahException(je);
		}

		return json;
	}

}
