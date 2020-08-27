package com.gsd.sreenidhi.cheetah.actions.webService;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.utils.jsonUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class WebServiceActions {

	protected static Response response;
	protected static ValidatableResponse json;
	protected static RequestSpecification request;
	protected static Map<String, String> paramsmap;
	protected static StringBuffer resp;
	protected static URLConnection conn = null;

	/**
	 * Returns a HTTP URL Connection
	 * @param url URL
	 * @return HttpURLConnection
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static HttpURLConnection getHttpConnection(String url) throws CheetahException {
		URL endpointUrl = null;
		HttpURLConnection connection = null;

		String updatedURL = "";
		if (CheetahEngine.hashMap != null && CheetahEngine.hashMap.get("access_key") != null) {
			updatedURL = url + "?access_token=" + CheetahEngine.hashMap.get("access_key");
		} else {
			updatedURL = url;
		}
		CheetahEngine.logger.logMessage(null, "WebServiceActions", "WebService (HTTP) URL:" + updatedURL,
				Constants.LOG_INFO, false);

		try {
			endpointUrl = new URL(url);
			connection = (HttpURLConnection) endpointUrl.openConnection();

			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);

		} catch (MalformedURLException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"MalformedURLException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return connection;
	}

	/**
	 * @param url URL
	 * @return HttpsURLConnection
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static HttpsURLConnection getHttpsConnection(String url) throws CheetahException {
		URL endpointUrl = null;
		HttpsURLConnection connection = null;

		String updatedURL = "";
		if (CheetahEngine.hashMap != null && CheetahEngine.hashMap.get("access_key") != null) {
			updatedURL = url + "?access_token=" + CheetahEngine.hashMap.get("access_key");
		} else {
			updatedURL = url;
		}
		CheetahEngine.logger.logMessage(null, "WebServiceActions", "WebService (HTTPS) URL:" + updatedURL,
				Constants.LOG_INFO, false);

		try {
			endpointUrl = new URL(url);
			connection = (HttpsURLConnection) endpointUrl.openConnection();

			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);

		} catch (MalformedURLException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"MalformedURLException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return connection;
	}

	/**
	 * @param url
	 *            End Point URL for WebService Connection
	 * @return HttpURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 * @deprecated Use the
	 *             {@link com.gsd.sreenidhi.cheetah.actions.webService.WebServiceActions#getHttpConnection(String)}
	 *             getHttpConnection(url) method - or -
	 *             {@link com.gsd.sreenidhi.cheetah.actions.webService.WebServiceActions#getHttpsConnection(String)}
	 *             getHttpsConnection(url) method in
	 *             {@link com.gsd.sreenidhi.cheetah.actions.webService.WebServiceActions}
	 *             class
	 * 
	 * 
	 */
	@Deprecated
	public static HttpURLConnection processRequest(String url) throws CheetahException {

		URL endpointUrl;

		String updatedURL = "";
		if (CheetahEngine.hashMap != null && CheetahEngine.hashMap.get("access_key") != null) {
			updatedURL = url + "?access_token=" + CheetahEngine.hashMap.get("access_key");
		} else {
			updatedURL = url;
		}
		CheetahEngine.logger.logMessage(null, "WebServiceActions", "WebService URL:" + updatedURL, Constants.LOG_INFO,
				false);
		try {
			endpointUrl = new URL(updatedURL);
			conn = (HttpURLConnection) endpointUrl.openConnection();
			((HttpURLConnection) conn).setRequestProperty("Accept", "*/*");
			((HttpURLConnection) conn).setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Authorization", "Basic " +
			// encoded);
			((HttpURLConnection) conn).setUseCaches(false);
			((HttpURLConnection) conn).setDoOutput(true);
			((HttpURLConnection) conn).setDoInput(true);

		} catch (MalformedURLException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"MalformedURLException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return (HttpURLConnection) conn;

	}

	/**
	 * @param connection
	 *            HttpURLConnection
	 * @param method
	 *            HTTP Request method
	 * @return HttpURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpURLConnection setRequestMethod(HttpURLConnection connection, String method)
			throws CheetahException {
		try {
			connection.setRequestMethod(method);
		} catch (ProtocolException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"ProtocolException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
		return connection;
	}
	
	/**
	 * @param connection
	 *            HttpsURLConnection
	 * @param method
	 *            HTTP Request method
	 * @return HttpsURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpsURLConnection setRequestMethod(HttpsURLConnection connection, String method)
			throws CheetahException {
		try {
			connection.setRequestMethod(method);
		} catch (ProtocolException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"ProtocolException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
		return connection;
	}

	/**
	 * @param table
	 *            Data Table
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void build_request(DataTable table) throws CheetahException {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			paramsmap = table.asMap(String.class, String.class);
			request = given();
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
			throw new CheetahException(e);

		}

	}

	/**
	 * @param connection
	 *            HttpURLConnection
	 * @param inputData
	 *            Connection Body / payload
	 * @return HttpURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpURLConnection processWebServiceData(HttpURLConnection connection, String inputData)
			throws CheetahException {
		String data = inputData;

		CheetahEngine.logger.logMessage(null, "WebServiceActions", "WebService Data: " + data, Constants.LOG_INFO, false);

		byte[] dataBytes;
		try {
			dataBytes = data.getBytes(Constants.UTF_8_ENCODING);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(dataBytes);
			wr.flush();
			wr.close();
			CheetahEngine.logger.logMessage(null, "WebServiceActions", "Data Sent.", Constants.LOG_INFO, false);
		} catch (UnsupportedEncodingException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"UnsupportedEncodingException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return connection;
	}
	
	
	/**
	 * @param connection
	 *            HttpsURLConnection
	 * @param inputData
	 *            Connection Body / payload
	 * @return HttpsURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpsURLConnection processWebServiceData(HttpsURLConnection connection, String inputData)
			throws CheetahException {
		String data = inputData;

		CheetahEngine.logger.logMessage(null, "WebServiceActions", "WebService Data: " + data, Constants.LOG_INFO, false);

		byte[] dataBytes;
		try {
			dataBytes = data.getBytes(Constants.UTF_8_ENCODING);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(dataBytes);
			wr.flush();
			wr.close();
			CheetahEngine.logger.logMessage(null, "WebServiceActions", "Data Sent.", Constants.LOG_INFO, false);
		} catch (UnsupportedEncodingException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"UnsupportedEncodingException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return connection;
	}

	/**
	 * @param connection
	 *            HttpURLConnection
	 * @return connection response string
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static String process_response(HttpURLConnection connection) throws CheetahException {
		String jsonString = "";
		if (connection != null) {
			CheetahEngine.logger.logMessage(null, "WebServiceActions", "Received Connection Response", Constants.LOG_INFO,
					false);
			try {

				if (connection.getResponseCode() == 200 || connection.getResponseCode() == 204) {
					Reader in = new BufferedReader(
							new InputStreamReader(connection.getInputStream(), Constants.UTF_8_ENCODING));
					for (int c; (c = in.read()) >= 0; jsonString = jsonString + (char) c)
						;
					CheetahEngine.logger.logMessage(null, "WebServiceActions", "JSON Output: " + jsonString,
							Constants.LOG_INFO, false);
					String accsKey = getAccessToken(jsonString);
					CheetahEngine.logger.logMessage(null, "WebServiceActions", "Access Key: " + accsKey,
							Constants.LOG_INFO, false);
					CheetahEngine.hashMap.put("access_key", accsKey);
				} else {
					connection.getHeaderFields()
							.forEach((key, value) -> System.err.println("key:" + key + " value" + value));
				}
			} catch (IOException e) {
				CheetahEngine.logger.logMessage(e, "WebServiceActions",
						"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
						true);
			}
		} else {
			Exception e = new NullPointerException();
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"NullPointerException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
			throw new NullPointerException();
		}
		return jsonString;
	}

	/**
	 * @param connection
	 *            HttpsURLConnection
	 * @return connection response string
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static String process_response(HttpsURLConnection connection) throws CheetahException {
		String jsonString = "";
		if (connection != null) {
			CheetahEngine.logger.logMessage(null, "WebServiceActions", "Received Connection Response", Constants.LOG_INFO,
					false);
			try {

				if (connection.getResponseCode() == 200 || connection.getResponseCode() == 204) {
					Reader in = new BufferedReader(
							new InputStreamReader(connection.getInputStream(), Constants.UTF_8_ENCODING));
					for (int c; (c = in.read()) >= 0; jsonString = jsonString + (char) c)
						;
					CheetahEngine.logger.logMessage(null, "WebServiceActions", "JSON Output: " + jsonString,
							Constants.LOG_INFO, false);
					String accsKey = getAccessToken(jsonString);
					CheetahEngine.logger.logMessage(null, "WebServiceActions", "Access Key: " + accsKey,
							Constants.LOG_INFO, false);
					CheetahEngine.hashMap.put("access_key", accsKey);
				} else {
					connection.getHeaderFields()
							.forEach((key, value) -> System.err.println("key:" + key + " value" + value));
				}
			} catch (IOException e) {
				CheetahEngine.logger.logMessage(e, "WebServiceActions",
						"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
						true);
			}
		} else {
			Exception e = new NullPointerException();
			CheetahEngine.logger.logMessage(e, "WebServiceActions",
					"NullPointerException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
			throw new NullPointerException();
		}
		return jsonString;
	}
	
	/**
	 * @param jsonString
	 *            JSON String
	 * @return access_token
	 */
	private static String getAccessToken(String jsonString) {
		String accessToken = "";
		String[] arr = null;
		arr = jsonString.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains("access_token")) {
				accessToken = arr[i].substring(arr[i].indexOf(":"));
				accessToken = accessToken.substring(accessToken.indexOf("\"") + 1, accessToken.length() - 1);
			}
		}
		return accessToken;
	}

	/**
	 * @param connection
	 *            HttpURLConnection
	 */
	public void setConnection(HttpURLConnection connection) {
		conn = connection;
	}
	
	/**
	 * @param connection
	 *            HttpURLConnection
	 */
	public void setConnection(HttpsURLConnection connection) {
		conn = connection;
	}
	
	/**
	 * @return URLConnection
	 */
	public URLConnection getConnection() {
		return  conn;
	}
	

	/**
	 * @return HttpURLConnection
	 */
	public HttpURLConnection getHTTPConnection() {
		return (HttpURLConnection) conn;
	}
	
	
	/**
	 * @return HttpsURLConnection
	 */
	public HttpsURLConnection getHTTPsConnection() {
		return (HttpsURLConnection) conn;
	}

	/**
	 * @param connection
	 *            HttpURLConnection
	 * @param propertyName
	 *            Name of the HTTP Request property
	 * @param propertyValue
	 *            Value of the HTTP Request property
	 * @return HttpURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpURLConnection setConnectionParameters(HttpURLConnection connection, String propertyName,
			String propertyValue) throws CheetahException {
		connection.setRequestProperty(propertyName, propertyValue);
		return connection;
	}

	
	/**
	 * @param connection
	 *            HttpsURLConnection
	 * @param propertyName
	 *            Name of the HTTP Request property
	 * @param propertyValue
	 *            Value of the HTTP Request property
	 * @return HttpsURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpsURLConnection setConnectionParameters(HttpsURLConnection connection, String propertyName,
			String propertyValue) throws CheetahException {
		connection.setRequestProperty(propertyName, propertyValue);
		return connection;
	}
	
	/**
	 * @param endpointURL
	 *            End Point URL
	 * @param headers
	 *            HashMap of headers
	 * @param json
	 *            JSON PayLoad body in JSONObject format.
	 * @param http_method
	 *            HTTP Request Methods (GET, PUT, POST, HEAD...)
	 * @return HTTP URL Connection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("static-access")
	public static HttpURLConnection evaluateHTTPConnection(String endpointURL, HashMap<String, String> headers, String http_method,
			JSONObject json) throws CheetahException {
		HttpURLConnection conn = null;
		jsonUtils ju = new jsonUtils();
		String jsonString;
		if (json != null) {
			jsonString = ju.convertJSONObjecttoString(json);
		} else {
			jsonString = null;
		}

		conn = evaluateHTTPConnection(endpointURL, headers, http_method, jsonString);
		return conn;
	}

	/**
	 * @param endpointURL
	 *            End Point URL
	 * @param headers
	 *            HashMap of headers
	 * @param json
	 *            JSON PayLoad body in JSONObject format.
	 * @param http_method
	 *            HTTP Request Methods (GET, PUT, POST, HEAD...)
	 * @return HTTP URL Connection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("static-access")
	public static HttpsURLConnection evaluateHTTPSConnection(String endpointURL, HashMap<String, String> headers, String http_method,
			JSONObject json) throws CheetahException {
		HttpsURLConnection conn = null;
		jsonUtils ju = new jsonUtils();
		String jsonString;
		if (json != null) {
			jsonString = ju.convertJSONObjecttoString(json);
		} else {
			jsonString = null;
		}

		conn = evaluateHTTPSConnection(endpointURL, headers, http_method, jsonString);
		return conn;
	}

	
	/**
	 * @param endpointURL
	 *            End Point URL
	 * @param headers
	 *            HashMap of headers
	 * @param json
	 *            JSON PayLoad body in String format
	 * @param http_method
	 *            HTTP Request Methods (GET, PUT, POST, HEAD...)
	 * @return HTTP URL Connection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpURLConnection evaluateHTTPConnection(String endpointURL, HashMap<String, String> headers, String http_method,
			String json) throws CheetahException {
		HttpURLConnection conn = getHttpConnection(endpointURL);
		conn = setRequestMethod(conn, http_method);

		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				conn = setConnectionParameters(conn, key, headers.get(key));
			}
		}

		if (json != null && !json.isEmpty()) {
			conn = processWebServiceData(conn, json);
		}
		return conn;
	}
	
	/**
	 * @param endpointURL
	 *            End Point URL
	 * @param headers
	 *            HashMap of headers
	 * @param json
	 *            JSON PayLoad body in String format
	 * @param http_method
	 *            HTTP Request Methods (GET, PUT, POST, HEAD...)
	 * @return HttpsURLConnection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static HttpsURLConnection evaluateHTTPSConnection(String endpointURL, HashMap<String, String> headers, String http_method,
			String json) throws CheetahException {
		HttpsURLConnection conn = getHttpsConnection(endpointURL);
		conn = setRequestMethod(conn, http_method);

		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				conn = setConnectionParameters(conn, key, headers.get(key));
			}
		}

		if (json != null && !json.isEmpty()) {
			conn = processWebServiceData(conn, json);
		}
		return conn;
	}

}
