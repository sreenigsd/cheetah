package com.gsd.sreenidhi.cheetah.actions.webService;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class SpringWebServiceActions extends WebServiceActions{

	protected static JSONObject jsonRequest = new JSONObject();
	protected static JSONObject object = null;
	protected static HttpHeaders headers = new HttpHeaders();
	protected static HttpEntity entity;
	protected static RestTemplate restTemplate;
	protected static String serviceURL;
	protected static MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();

	public static RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/*
	 * Section for Spring Object HTTP json message Converters
	 */

	public static JSONObject retrieveJsonRequestObject() {
		return jsonRequest;
	}

	public static void setJsonRequestObject(JSONObject jsonRequestObject) {
		jsonRequest = jsonRequestObject;
	}

	public static void addJsonRequestParameter(String key, Object value) {
		jsonRequest.put(key, value);
	}

	public static void removeJsonRequestParameter(String key) {
		jsonRequest.remove(key);
	}

	public static HttpHeaders retrieveHTTPHeaders() {
		return headers;
	}

	public static void setHTTPHeaders(HttpHeaders httpHdr) {
		headers = httpHdr;
	}

	public static void setHTTPHeader(String key, String value) {
		headers.set(key, value);
	}

	public static void removeHTTPHeader(String key) {
		headers.remove(key);
	}

	public static void clearHTTPHeader() {
		headers.clear();
	}

	public static void initializeHTTPEntity() {
		entity = new HttpEntity(jsonRequest.toString(), headers);
	}

	public static void initializeHTTPEntity(JSONObject jsonObj, HttpHeaders httpHdr) {
		entity = new HttpEntity(jsonObj.toString(), httpHdr);
	}

	public static MappingJackson2HttpMessageConverter getHttpMessageConverter() {
		return jsonHttpMessageConverter;
	}

	public static void setHttpMessageConverter(MappingJackson2HttpMessageConverter httpConvrtr) {
		jsonHttpMessageConverter = httpConvrtr;
	}

	public static ResponseEntity processHTTPEntity(String completeServiceURL, HttpMethod method,
			ParameterizedTypeReference responseType) {

		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		restTemplate().getMessageConverters().add(jsonHttpMessageConverter);
		ResponseEntity responseEntity = restTemplate().exchange(completeServiceURL, method, entity,	responseType);
		return responseEntity;
	}
	
	public static ResponseEntity processHTTPEntity(String completeServiceURL, HttpMethod method, HttpEntity ntty,
			ParameterizedTypeReference responseType) {

		restTemplate().getMessageConverters().add(jsonHttpMessageConverter);
		ResponseEntity responseEntity = restTemplate().exchange(completeServiceURL, method, ntty,responseType);
		return responseEntity;
	}
	
	public static ResponseEntity processHTTPEntity(String completeServiceURL, HttpMethod method,
			java.lang.Class responseType) {

		// jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
		// false);
		restTemplate().getMessageConverters().add(jsonHttpMessageConverter);
		ResponseEntity responseEntity = restTemplate().exchange(completeServiceURL, method, entity,	responseType);
		return responseEntity;
	}
	
	public static ResponseEntity processHTTPEntity(String completeServiceURL, HttpMethod method, HttpEntity ntty,
			java.lang.Class responseType) {

		restTemplate().getMessageConverters().add(jsonHttpMessageConverter);
		ResponseEntity responseEntity = restTemplate().exchange(completeServiceURL, method, ntty,responseType);
		return responseEntity;
	}
	
	
}
