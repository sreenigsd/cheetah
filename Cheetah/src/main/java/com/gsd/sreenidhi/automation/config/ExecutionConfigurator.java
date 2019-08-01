package com.gsd.sreenidhi.automation.config;

public class ExecutionConfigurator {

	public String executionEnv;
	public String hubPort;
	public String terrainLink;
	public String terrainPort;
	public String saucelabsUsername;
	public String saucelabsAccessKey;
	public String appiumHub;
	public String appiumPort;

	public String getExecutionEnv() {
		return executionEnv;
	}

	public void setExecutionEnv(String executionEnv) {
		this.executionEnv = executionEnv;
	}

	
	/**
	 * @return the hubPort
	 */
	public String getHubPort() {
		return hubPort;
	}

	/**
	 * @param hubPort the hubPort to set
	 */
	public void setHubPort(String hubPort) {
		this.hubPort = hubPort;
	}

	/**
	 * @return the saucelabsUsername
	 */
	public String getSaucelabsUsername() {
		return saucelabsUsername;
	}

	public String getTerrainLink() {
		return terrainLink;
	}

	public void setTerrainLink(String terrainLink) {
		this.terrainLink = terrainLink;
	}

	public String getTerrainPort() {
		return terrainPort;
	}

	public void setTerrainPort(String terrainPort) {
		this.terrainPort = terrainPort;
	}

	/**
	 * @param saucelabsUsername the saucelabsUsername to set
	 */
	public void setSaucelabsUsername(String saucelabsUsername) {
		this.saucelabsUsername = saucelabsUsername;
	}

	/**
	 * @return the saucelabsAccessKey
	 */
	public String getSaucelabsAccessKey() {
		return saucelabsAccessKey;
	}

	/**
	 * @param saucelabsAccessKey the saucelabsAccessKey to set
	 */
	public void setSaucelabsAccessKey(String saucelabsAccessKey) {
		this.saucelabsAccessKey = saucelabsAccessKey;
	}

	/**
	 * @return the appiumHub
	 */
	public String getAppiumHub() {
		return appiumHub;
	}

	/**
	 * @param appiumHub the appiumHub to set
	 */
	public void setAppiumHub(String appiumHub) {
		this.appiumHub = appiumHub;
	}

	/**
	 * @return the appiumPort
	 */
	public String getAppiumPort() {
		return appiumPort;
	}

	/**
	 * @param appiumPort the appiumPort to set
	 */
	public void setAppiumPort(String appiumPort) {
		this.appiumPort = appiumPort;
	}
	
}
