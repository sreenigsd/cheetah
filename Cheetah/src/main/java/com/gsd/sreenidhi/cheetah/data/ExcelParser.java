package com.gsd.sreenidhi.cheetah.data;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.FileUtils;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Font;
import jxl.read.biff.BiffException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ExcelParser extends CheetahEngine {

	/**
	 * @param excelFile
	 *            Type File - Input Excel file that needs to be converted
	 * @return String value of the path of the generated XML file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String ExceltoXmlParser(File excelFile) throws CheetahException {

		String xmlLine = "";
		String rowText = "";
		String colText = "";
		String isBold = "";
		Font font = null;
		String cellCol = "";
		String cellAddress = "";
		Cell cell = null;
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(excelFile);
			xmlLine += "<?xml version=\"1.0\"?>" + "\n";
			xmlLine += "<Data>" + "\n";
			for (int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++) {
				Sheet s = workbook.getSheet(sheet);
				// xmlLine += " <sheets>" + "\n";
				Cell[] row = null;
				String headers;
				String[] headerList = null;
				for (int i = 0; i < s.getRows(); i++) {
					row = s.getRow(i);

					if (i == 0) {
						for (int j = 0; j < row.length; j++) {
							cell = row[j];
							cellCol = columnName(cell.getColumn());
							if (row[j].getType() != CellType.EMPTY) {
								colText += cell.getContents() + ",";
							}
						}
						headers = colText.trim();
						headers = headers.substring(0, headers.length() - 1);
						headerList = headers.split(",");

					} else {
						for (int j = 0; j < row.length; j++) {
							if (row[j].getType() != CellType.EMPTY) {
								cell = row[j];
								cellCol = columnName(cell.getColumn());
								cellCol = " colLetter=\"" + cellCol + "\"";
								cellAddress = " address=\"" + cellAddress(cell.getRow() + 1, cell.getColumn()) + "\"";
								isBold = cell.getCellFormat().getFont().getBoldWeight() == 700 ? "true" : "false";
								isBold = ("false".equalsIgnoreCase(isBold) ? "" : " isBold=\"true\"");
								// colText += " <col" + (j + 1) + " " + cellAddress + ">";
								if (headerList != null && headerList.length >= (j + 1)) {
									colText += "      <" + headerList[j] + ">";
								} else {
									colText += "      <col" + (j+1) + " " + cellAddress + ">";
								}

								colText += cell.getContents().trim();
								
								if (headerList != null && headerList.length >= (j + 1)) {
									colText += "</" + headerList[j] + ">" + "\n";
								} else {
									colText += "</col" + (j+1) + ">" + "\n";
								}
								
						//		colText += "</col>" + "\n";
								rowText += cell.getContents();
							}
						}
					}

					if (!"".equalsIgnoreCase(rowText)) {
						xmlLine += "    <row" + i + ">" + "\n";
						xmlLine += colText;
						xmlLine += "    </row"+ i + ">" + "\n";
					}
					colText = "";
					rowText = "";
				}
			}
			xmlLine += "</Data>";

		} catch (UnsupportedEncodingException e) {
			logger.logMessage(null, "ExcelParser",
					"UnsupportedEncodingException: " + e.getMessage() + "\n" + getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (BiffException | IOException e) {
			throw new CheetahException(e);
		}
		return xmlLine;
	}

	/**
	 * @param excelFile
	 *            Type File - Input Excel file that needs to be converted
	 * @param xmlFileName
	 *            Name of Excel File to be created
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void ExceltoXmlFileParser(File excelFile, String xmlFileName) throws CheetahException {
		String xml = ExceltoXmlParser(excelFile);
		FileUtils fu = new FileUtils();
		fu.createFile(xmlFileName, "input", xml);

	}

	/**
	 * @param excelFile
	 *            File that needs to be converted to XML
	 * @return Filename of the generated xml File
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String ExceltoXmlParserindividual(File excelFile) throws CheetahException {

		String xmlLine = "";
		String rowText = "";
		String colName = "";
		String colAdd = "";
		Font font = null;
		Cell cell = null;
		try {
			Workbook workbook = Workbook.getWorkbook(excelFile);
			xmlLine += "<Data>" + "\n";
			int sheet = 0;

			Sheet s = workbook.getSheet(sheet);

			Cell[] row = null;
			for (int i = 1; i < s.getRows(); i++) {
				row = s.getRow(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j].getType() != CellType.EMPTY) {
						cell = row[j];

						if (j == 0) {
							colName += "<name" + i + ">";
							colName += "<first>";
							colName += cell.getContents();
							colName += "</first>";
						}
						if (j == 2) {
							colName += "<last>";
							colName += cell.getContents();
							colName += "</last>";

						}
						if (j == 7) {
							colName += "<ssn>";
							colName += cell.getContents();
							colName += "</ssn>";
							colName += "</name" + i + ">";
						}

						if (j == 3) {
							colAdd += "<street>";
							colAdd += cell.getContents();
							colAdd += "</street>";
						}
						if (j == 4) {
							colAdd += "<city>";
							colAdd += cell.getContents();
							colAdd += "</city>";
						}
						if (j == 5) {
							colAdd += "<state>";
							colAdd += cell.getContents();
							colAdd += "</state>";
						}
						if (j == 6) {
							colAdd += "<zip>";
							colAdd += cell.getContents();
							colAdd += "</zip>";
						}

						if (j == 7) {

							xmlLine += colName;

							xmlLine += "<Details" + i + ">";
							xmlLine += "<Third_Party_Type>LLC</Third_Party_Type>";
							xmlLine += colAdd;
							xmlLine += "<Primary_Phone>2488256507</Primary_Phone>";
							xmlLine += "<Residence>Owns / Buying Residence</Residence>";
							xmlLine += "<Industry_Segment>AG02</Industry_Segment>";
							xmlLine += "</Details" + i + ">";
							colName = "";
							colAdd = "";

						}

					}
				}

			}
			xmlLine += "<Business_Partner>";
			xmlLine += "<Business_Partner_Type>Retail Vocational Business Customer</Business_Partner_Type>";
			xmlLine += "<Business_Partner_Sub-Type>Small Fleet</Business_Partner_Sub-Type>";
			xmlLine += "<Review_Type>Not Required</Review_Type>";
			xmlLine += "<Customer_YE_Month>December</Customer_YE_Month>";
			xmlLine += "</Business_Partner>";
			xmlLine += "</Data>";

		} catch (UnsupportedEncodingException e) {
			logger.logMessage(null, "ExcelParser",
					"UnsupportedEncodingException: " + e.getMessage() + "\n" + getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (BiffException | IOException e) {
			throw new CheetahException(e);
		}
		return xmlLine;
	}

	/**
	 * @param excelFile
	 *            File that needs to be converted to XML
	 * @return Filename of the generated xml File
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String ExceltoXmlParserBusiness(File excelFile) throws CheetahException {
		String xmlLine = "";
		String colName = "";
		String colAdd = "";
		Cell cell = null;
		try {
			Workbook workbook = Workbook.getWorkbook(excelFile);
			xmlLine += "<Data>" + "\n";
			int sheet = 0;
			Sheet s = workbook.getSheet(sheet);

			Cell[] row = null;
			for (int i = 1; i < s.getRows(); i++) {
				row = s.getRow(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j].getType() != CellType.EMPTY) {

						cell = row[j];

						if (j == 3) {
							colName += "<my_data" + i + ">";
							colName += "<legalname>";
							colName += cell.getContents();
							colName += "</legalname>";
							colName += "</my_data" + i + ">";
						}

						if (j == 4) {
							colAdd += "<street>";
							colAdd += cell.getContents();
							colAdd += "</street>";
						}
						if (j == 6) {
							colAdd += "<city>";
							colAdd += cell.getContents();
							colAdd += "</city>";
						}
						if (j == 7) {
							colAdd += "<state>";
							colAdd += cell.getContents();
							colAdd += "</state>";
						}
						if (j == 8) {
							colAdd += "<zip>";
							colAdd += cell.getContents();
							colAdd += "</zip>";
							xmlLine += colName;
							long timeSeed = System.nanoTime(); // to get the current date time value

							double randSeed = Math.random() * 1000; // random number generation

							long midSeed = (long) (timeSeed * randSeed); // mixing up the time and
																			// rand number.

							// variable timeSeed
							// will be unique

							// variable rand will
							// ensure no relation
							// between the numbers

							String s1 = midSeed + "";
							String subStr = s1.substring(0, 9);

							int finalSeed = Integer.parseInt(subStr); // integer value
							xmlLine += "<Details" + i + ">";
							xmlLine += "<FedID>" + finalSeed + "</FedID>";
							xmlLine += "<Third_Party_Type>LLC</Third_Party_Type>";
							xmlLine += colAdd;
							xmlLine += "<Primary_Phone>2488256507</Primary_Phone>";
							xmlLine += "<No_Of_Trucks>1</No_Of_Trucks>";
							xmlLine += "<Industry_Segment>AG02</Industry_Segment>";
							xmlLine += "</Details" + i + ">";
							colName = "";
							colAdd = "";
						}

						// colText += "</col>" + "\n";
						// rowText += cell.getContents();
					}
				}
				// if (rowText != "") {
				xmlLine += "<Business_Partner" + i + ">";
				if (i <= 15) {
					xmlLine += "<Business_Partner_Type>Retail Business Customer</Business_Partner_Type>";
					xmlLine += "<Business_Partner_Sub-Type>Owner Operator</Business_Partner_Sub-Type>";
				}
				if (i > 15 && i <= 30) {
					xmlLine += "<Business_Partner_Type>Retail Vocational Business Customer</Business_Partner_Type>";
					xmlLine += "<Business_Partner_Sub-Type>Small Fleet</Business_Partner_Sub-Type>";
				}
				if (i > 30 && i <= 45) {
					xmlLine += "<Business_Partner_Type>Retail Vocational Business Customer</Business_Partner_Type>";
					xmlLine += "<Business_Partner_Sub-Type>Owner Operator</Business_Partner_Sub-Type>";
				}
				if (i > 45 && i <= 60) {
					xmlLine += "<Business_Partner_Type>Retail Business Customer</Business_Partner_Type>";
					xmlLine += "<Business_Partner_Sub-Type>Small Fleet</Business_Partner_Sub-Type>";
				}

				xmlLine += "<Review_Type>Not Required</Review_Type>";
				xmlLine += "<Customer_YE_Month>December</Customer_YE_Month>";
				xmlLine += "</Business_Partner" + i + ">";

				/// }
				// colText = "";
				// rowText = "";
			}
			xmlLine += "</Data>";
			return xmlLine;
		} catch (UnsupportedEncodingException e) {
			logger.logMessage(null, "ExcelParser",
					"UnsupportedEncodingException: " + e.getMessage() + "\n" + getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (BiffException | IOException e) {
			throw new CheetahException(e);
		}
		return null;
	}

	/**
	 * @param rowNumber
	 *            Integer value of the row number
	 * @param colNumber
	 *            Integer value of the column number
	 * @return Address of the cell corresponding to the row and column
	 */
	private String cellAddress(Integer rowNumber, Integer colNumber) {
		// return "$"+columnName(colNumber)+"$"+rowNumber;
		return columnName(colNumber) + rowNumber;
	}

	/**
	 * @param colNumber
	 *            Integer value of the column number
	 * @return Name of the column
	 */
	private String columnName(Integer colNumber) {
		Base columns = new Base(colNumber, 26);
		columns.transform();
		return columns.getResult();
	}

	/**
	 * @author Gundlupet Sreenidhi - sreenidhi.gsd@gmail.com
	 *
	 */
	class Base {
		String[] colNames = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
		String equalTo;
		int position;
		int number;
		int base;
		int[] digits;
		int[] auxiliar;

		/**
		 * @param n
		 *            Number
		 * @param b
		 *            Base
		 */
		public Base(int n, int b) {
			position = 0;
			equalTo = "";
			base = b;
			number = n;
			digits = new int[1];
		}

		/**
		 * 
		 */
		public void transform() {
			if (number < base) {
				digits[position] = number;
				size();
			} else {
				digits[position] = number % base;
				size();
				position++;
				number = number / base;
				transform();
			}
		}

		/**
		 * @return Results
		 */
		public String getResult() {
			for (int j = digits.length - 2; j >= 0; j--) {
				equalTo += colNames[j > 0 ? digits[j] - 1 : digits[j]];
			}
			return equalTo;
		}

		/**
		 * 
		 */
		private void size() {
			auxiliar = digits;
			digits = new int[auxiliar.length + 1];
			System.arraycopy(auxiliar, 0, digits, 0, auxiliar.length);
		}
	}

	/**
	 * @param excelFile  Type File - Input Excel file that needs to be converted
	 * @return JSON String
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public String excelToJson(File excelFile) throws CheetahException {
		String xml = ExceltoXmlParser(excelFile);
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String json = null;
		 try {
	            JSONObject xmlJSONObj = XML.toJSONObject(xml);
	            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
	            json = jsonPrettyPrintString;
	        } catch (JSONException je) {
	            throw new CheetahException(je);
	        }
		 
		 return json;
	}
}