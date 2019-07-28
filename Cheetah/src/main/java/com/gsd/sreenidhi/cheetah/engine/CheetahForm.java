package com.gsd.sreenidhi.cheetah.engine;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gsd.sreenidhi.cheetah.data.XMLParser;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.cheetah.reporting.Media;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CheetahForm {
	
	public static WebDriver driver = null;
	public static String transactionID = null;
	public Properties properties = null;
	public Media media = null;
	public Log logger = null;
	public static XMLParser xmlParser = null;
	public DesiredCapabilities capabilities = null;
	public static String parentWindow = null;
	public Long dbId = null;
	
	public static String executionTimestamp = null;
	
	public static String getExecutionTimestamp() {
		return executionTimestamp;
	}

	public static void setExecutionTimestamp(String executionTimestamp) {
		CheetahForm.executionTimestamp = executionTimestamp;
	}

	public static String remoteServerLink = null;
	
	
	public static String getRemoteServerLink() {
		return remoteServerLink;
	}

	public static void setRemoteServerLink(String remoteServerLink) {
		CheetahForm.remoteServerLink = remoteServerLink;
	}

	public static HashMap<String, String> dbDriversClassName;
	
	public static Date startTime=null;
	public static Date endTime=null;
	
	public static String parentHandle;
	public static String childHandle;

	public static boolean highlights;
	public static boolean splitVideo;
	
	public static String proxy_url;
	public static String proxy_port;
	public static String proxy_pac;
	
	public static String getProxy_pac() {
		return proxy_pac;
	}

	public static void setProxy_pac(String proxy_pac) {
		CheetahForm.proxy_pac = proxy_pac;
	}

	public static String getProxy_url() {
		return proxy_url;
	}

	public static void setProxy_url(String proxy_url) {
		CheetahForm.proxy_url = proxy_url;
	}

	public static String getProxy_port() {
		return proxy_port;
	}

	public static void setProxy_port(String proxy_port) {
		CheetahForm.proxy_port = proxy_port;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		CheetahForm.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		CheetahForm.endTime = endTime;
	}

	/**
	 * @return hashmap
	 */
	public HashMap getDbDriversClassName() {
		return dbDriversClassName;
	}

	/**
	 * @param dbDriversClassName hashmap
	 */
	public void setDbDriversClassName(HashMap<String, String> dbDriversClassName) {
		CheetahForm.dbDriversClassName = dbDriversClassName;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		CheetahForm.driver = driver;
	}

	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		CheetahForm.transactionID = transactionID;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the media
	 */
	public Media getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(Media media) {
		this.media = media;
	}

	/**
	 * @return the logger
	 */
	public Log getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Log logger) {
		this.logger = logger;
	}

	/**
	 * @return the xmlParser
	 */
	public static XMLParser getXmlParser() {
		return xmlParser;
	}

	/**
	 * @param xmlParser the xmlParser to set
	 */
	public void setXmlParser(XMLParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	/**
	 * @return the capabilities
	 */
	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(DesiredCapabilities capabilities) {
		this.capabilities = capabilities;
	}

	public String getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(String parentWindow) {
		CheetahForm.parentWindow = parentWindow;
	}

	/**
	 * @return the dbId
	 */
	public Long getDbId() {
		return dbId;
	}

	/**
	 * @param dbId the dbId to set
	 */
	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	/**
	 * @return the parentHandle
	 */
	public String getParentHandle() {
		return parentHandle;
	}

	/**
	 * @param parentHandle the parentHandle to set
	 */
	public void setParentHandle(String parentHandle) {
		CheetahForm.parentHandle = parentHandle;
	}

	/**
	 * @return the childHandle
	 */
	public String getChildHandle() {
		return childHandle;
	}

	/**
	 * @param childHandle the childHandle to set
	 */
	public void setChildHandle(String childHandle) {
		CheetahForm.childHandle = childHandle;
	}

	/**
	 * @return the highlights
	 */
	public boolean isHighlights() {
		return highlights;
	}

	/**
	 * @param highlights the highlights to set
	 */
	public void setHighlights(boolean highlights) {
		CheetahForm.highlights = highlights;
	}

	/**
	 * @return the splitVideo
	 */
	public  boolean isSplitVideo() {
		return splitVideo;
	}

	/**
	 * @param splitVideo the splitVideo to set
	 */
	public  void setSplitVideo(boolean splitVideo) {
		CheetahForm.splitVideo = splitVideo;
	}

	



}
