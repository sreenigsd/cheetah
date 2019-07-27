package com.gsd.sreenidhi.cheetah.data;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DynamicDataParser {

	/**
	 * @param rs
	 *            The ResultSet that needs to be converted to XML file
	 * @param fileName
	 *            The name of the XML file that needs to be generated for the
	 *            ResultSet (rs)
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void processSQLResultSetToXML(ResultSet rs, String fileName) throws CheetahException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();

			Element results = doc.createElement("Results");
			doc.appendChild(results);

			ResultSetMetaData rsmd;

			rsmd = rs.getMetaData();

			int colCount;

			colCount = rsmd.getColumnCount();

			while (rs.next()) {
				Element row = doc.createElement("Row");
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(i);
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode(value.toString()));
					row.appendChild(node);
				}
			}

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);

			FileUtils fileUtils = new FileUtils();
			fileUtils.createFile(fileName, sw.toString());
		} catch (ParserConfigurationException | SQLException | DOMException | TransformerException e) {
			throw new CheetahException(e);
		} 

	}

	/**
	 * @param jsonString
	 *            JSON string that needs to be processed
	 * @param fileName
	 *            File that will be created
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void processJSONToXML(String jsonString, String fileName) throws CheetahException {
		JSONObject json = new JSONObject(jsonString);
		String xml = XML.toString(json);

		FileUtils fileUtils = new FileUtils();
		fileUtils.createFile(fileName, xml);
	}
}
