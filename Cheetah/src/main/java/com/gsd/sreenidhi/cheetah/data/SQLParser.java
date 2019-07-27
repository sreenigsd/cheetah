package com.gsd.sreenidhi.cheetah.data;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class SQLParser {

	/**
	 * @param rs ResultSet
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void processResultSetToXML(ResultSet rs) throws CheetahException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element results = doc.createElement("Results");
			doc.appendChild(results);

			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

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
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);

			System.out.println(sw.toString());

			generateXML(sw.toString());
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @param string
	 * 
	 */
	private void generateXML(String string) {
		// TODO Convert String to XML

	}

	/**
	 * @param string
	 * 
	 */
	private void processSQL() {
		// TODO Read SQL file location from properties
		// TODO Read DB connection data from properties
		// TODO Invoke processResultSetToXML(ResultSet)

	}

}
