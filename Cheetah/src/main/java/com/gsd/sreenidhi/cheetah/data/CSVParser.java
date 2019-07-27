package com.gsd.sreenidhi.cheetah.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CSVParser {
	// Protected Properties
	protected DocumentBuilderFactory domFactory = null;
	protected DocumentBuilder domBuilder = null;

	/**
	 * Constructor
	 */
	public CSVParser() {
		try {
			domFactory = DocumentBuilderFactory.newInstance();
			domBuilder = domFactory.newDocumentBuilder();
		} catch (FactoryConfigurationError | ParserConfigurationException exp) {
			System.err.println(exp.toString());
		}  catch (Exception exp) {
			System.err.println(exp.toString());
		}

	}

	/**
	 * @param csvFileName
	 *            Name of the CSV file in the input location
	 * @param xmlFileName
	 *            Name of the converted XML file
	 * @param delimiter
	 *            CSV file delimiter
	 * @return Row count for converted rows
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */

	public int convertFile(String csvFileName, String xmlFileName, String delimiter) throws CheetahException {

		com.gsd.sreenidhi.utils.FileUtils fu = new com.gsd.sreenidhi.utils.FileUtils();
		int rowsCount = -1;
		BufferedReader csvReader = null;
		CSVReader reader = null;
		try {
			CheetahEngine.logger.logMessage(null, "CSVParser", "Processing new DOM - CSVParser", Constants.LOG_INFO,
					false);

			domFactory = DocumentBuilderFactory.newInstance();
			domBuilder = domFactory.newDocumentBuilder();

			CheetahEngine.logger.logMessage(null, "CSVParser", "Creating new Document", Constants.LOG_INFO, false);
			Document newDoc = domBuilder.newDocument();
			// Root element
			Element rootElement = newDoc.createElement("CSVParser");
			newDoc.appendChild(rootElement);
			// Read csv file
			CheetahEngine.logger.logMessage(null, "CSVParser", "Read csv File: " + csvFileName, Constants.LOG_INFO,
					false);
			csvReader = new BufferedReader(new FileReader(fu.getFile("INPUT", csvFileName).getAbsolutePath()),
					delimiter.charAt(0));

			CheetahEngine.logger.logMessage(null, "CSVParser", "Read csv File: " + csvFileName, Constants.LOG_INFO,
					false);
			// ** Now using the OpenCSV **//

			reader = new CSVReader(new FileReader(fu.getFile("INPUT", csvFileName).getAbsolutePath()),
					delimiter.charAt(0));

			String[] nextLine;
			int line = 0;
			List<String> headers = new ArrayList<String>(5);

			CheetahEngine.logger.logMessage(null, "CSVParser", "Reading lines from CSV", Constants.LOG_INFO, false);
			while ((nextLine = reader.readNext()) != null) {

				if (line == 0) { // Header row
					CheetahEngine.logger.logMessage(null, "CSVParser", "Reading Headers from line: " + (line + 1),
							Constants.LOG_INFO, false);
					for (String col : nextLine) {
						headers.add(col.trim());
					}
					CheetahEngine.logger.logMessage(null, "CSVParser", "Header Size: " + headers.size(),
							Constants.LOG_INFO, false);
				} else { // Data row
					CheetahEngine.logger.logMessage(null, "CSVParser", "Reading Data Row: " + (line + 1),
							Constants.LOG_INFO, false);
					Element rowElement = newDoc.createElement("row" + line);
					rootElement.appendChild(rowElement);

						int col = 0;
					for (String value : nextLine) {

						String header = headers.get(col);
						header = header.trim();
						header = header.replaceAll(" ", "_");
						header = header.replaceAll("-", "_");
						//header = header.replaceAll("[-,.[\\s]]*", "_");
						
						Element curElement = newDoc.createElement(header);
						curElement.appendChild(newDoc.createTextNode(value.trim()));
						rowElement.appendChild(curElement);
				
						col++;
					}
				}
				line++;
			}
			CheetahEngine.logger.logMessage(null, "CSVParser", "End of CSV Parsing", Constants.LOG_INFO, false);
			// ** End of CSV parsing**//

			FileWriter writer = null;

			xmlFileName = xmlFileName.trim();
			if (!xmlFileName.endsWith(".xml")) {
				xmlFileName = xmlFileName + ".csv";
			}

			try {
				CheetahEngine.logger.logMessage(null, "CSVParser", "Creating new XML file Object", Constants.LOG_INFO,
						false);
				writer = new FileWriter(new File(
						Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "src" + File.separator
								+ "test" +  File.separator + "data" + File.separator + "input" + File.separator + xmlFileName));

				TransformerFactory tranFactory = TransformerFactory.newInstance();
				Transformer aTransformer = tranFactory.newTransformer();
				aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
				aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
				aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				Source src = new DOMSource(newDoc);
				Result result = new StreamResult(writer);
				aTransformer.transform(src, result);

				writer.flush();
				CheetahEngine.logger.logMessage(null, "CSVParser", "XML file has been created: " + xmlFileName,
						Constants.LOG_INFO, false);

			} catch (Exception exp) {
				throw new CheetahException(exp);
			} finally {
				if(writer!=null) {
					writer.close();
				}
					
			}

			// Output to console for testing


		} catch (IOException exp) {
			throw new CheetahException(exp);
		} catch (Exception exp) {
			throw new CheetahException(exp);
		}finally {
			try {
				if(csvReader!=null)
				csvReader.close();
				
				if(reader!=null)
				reader.close();
			}catch(IOException e) {
				throw new CheetahException(e);
			}
			
		}
		return rowsCount;

	}
}
