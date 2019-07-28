package com.gsd.sreenidhi.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class jsonUtils {

	/**
	 * Returns JSONObject for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return JSON Object
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public JSONObject getJsonObject(String jSon, String key) throws CheetahException {

		JSONObject obj = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			obj = jsonObj.getJSONObject(key);

		}
		return obj;
	}

	/**
	 * Returns a String for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return String
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String getJsonString(String jSon, String key) throws CheetahException {

		String value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getString(key);

		}
		return value;
	}

	/**
	 * Returns an int for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return int
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public int getJsonInt(String jSon, String key) throws CheetahException {

		int value = 0;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getInt(key);

		}
		return value;
	}

	/**
	 * Returns a BigDecimal for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return BigDecimal
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public BigDecimal getJsonBigDecimal(String jSon, String key) throws CheetahException {

		BigDecimal value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getBigDecimal(key);

		}
		return value;
	}

	/**
	 * Returns a BigInteger for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return BigInteger
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public BigInteger getJsonBigInteger(String jSon, String key) throws CheetahException {

		BigInteger value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getBigInteger(key);

		}
		return value;
	}

	/**
	 * Returns a Double for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return Double
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public Double getJsonDouble(String jSon, String key) throws CheetahException {

		Double value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getDouble(key);

		}
		return value;
	}

	/**
	 * Returns a Long for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return Long
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public Long getJsonLong(String jSon, String key) throws CheetahException {

		Long value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getLong(key);
		}
		return value;
	}

	/**
	 * Returns a JSONArray for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return JSONArray
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public JSONArray getJsonArray(String jSon, String key) throws CheetahException {

		JSONArray value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getJSONArray(key);
		}
		return value;
	}

	/**
	 * Returns a JSONArray for the given key
	 * 
	 * @param obj
	 *            JSONObject
	 * @param key Key
	 * @return JSONArray
	 * @throws CheetahException
	 *             Exception Generic Exception Object that handles all
	 *             exceptions
	 */
	public JSONArray getJsonArray(JSONObject obj, String key) throws CheetahException {

		JSONArray value = null;
		String json = convertJSONObjecttoString(obj);
		if (json != null && !"".equalsIgnoreCase(json)) {
			String jsonStr = json;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getJSONArray(key);
		}
		return value;
	}

	/**
	 * Returns a Boolean for the given key
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return Boolean
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public Boolean getJsonBoolean(String jSon, String key) throws CheetahException {

		Boolean value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getBoolean(key);
		}
		return value;
	}

	/**
	 * Returns a boolean value after checking is the value of the given key is null
	 * 
	 * @param jSon
	 *            JSON String
	 * @param key
	 *            Key
	 * @return boolean
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public boolean isNullValue(String jSon, String key) throws CheetahException {

		boolean value = true;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.isNull(key);
		}
		return value;
	}

	/**
	 * Retuns a String Iterator for Keys
	 * 
	 * @param jSon
	 *            JSON String
	 * @return Iterator
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public Iterator<String> getKeys(String jSon) throws CheetahException {

		Iterator<String> value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.keys();
		}
		return value;
	}

	/**
	 * Returns a JSONArray of names
	 * 
	 * @param jSon
	 *            JSON String
	 * @return JSONArray
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public JSONArray getNames(String jSon) throws CheetahException {

		JSONArray value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.names();
		}
		return value;
	}

	/**
	 * Returns a String array of Names
	 * 
	 * @param jSon
	 *            JSON String
	 * @return String Array
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String[] getNamesArray(String jSon) throws CheetahException {

		String[] value = null;
		if (jSon != null && !"".equalsIgnoreCase(jSon)) {
			String jsonStr = jSon;
			JSONObject jsonObj = new JSONObject(jsonStr);
			value = jsonObj.getNames(jsonObj);
		}
		return value;
	}

	/**
	 * @param obj
	 *            JSONObject
	 * @return String
	 */
	public static String convertJSONObjecttoString(JSONObject obj) {
		return JSONObject.valueToString(obj);
	}
}
