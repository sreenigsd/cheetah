package com.gsd.sreenidhi.cheetah.reporting;

import java.util.Date;

import io.cucumber.java.Scenario;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ReportingBean {
	
	public String Test_Status;
	public String Test_Page_URL;
	public String screenshot_URL;
	byte[] screenshot = null;
	public String scenario;
	public Date testStartTime;
	public Date testEndTime;
	public long executionTime;
	public Scenario scenarioImpl;
	public gherkin.formatter.model.Scenario gherkingScenario;
	public String scenarioExecutionId;
	/**
	 * @return the scenarioImpl
	 */
	public io.cucumber.java.Scenario getScenarioImpl() {
		return scenarioImpl;
	}
	
	/**
	 * @param scenarioImpl2 the scenarioImpl to set
	 */
	public void setScenarioImpl(Scenario scenarioImpl2) {
		this.scenarioImpl = scenarioImpl2;
	}
	
	/**
	 * @return the testStartTime
	 */
	public Date getTestStartTime() {
		return testStartTime;
	}

	/**
	 * @param testStartTime the testStartTime to set
	 */
	public void setTestStartTime(Date testStartTime) {
		this.testStartTime = testStartTime;
	}

	/**
	 * @return the testEndTime
	 */
	public Date getTestEndTime() {
		return testEndTime;
	}

	/**
	 * @param testEndTime the testEndTime to set
	 */
	public void setTestEndTime(Date testEndTime) {
		this.testEndTime = testEndTime;
	}

	/**
	 * @return the gherkingScenario
	 */
	public gherkin.formatter.model.Scenario getGherkingScenario() {
		return gherkingScenario;
	}
	
	/**
	 * @param gherkingScenario the gherkingScenario to set
	 */
	public void setGherkingScenario(gherkin.formatter.model.Scenario gherkingScenario) {
		this.gherkingScenario = gherkingScenario;
	}
	
	/**
	 * @return the test_Status
	 */
	public String getTest_Status() {
		return Test_Status;
	}
	/**
	 * @param test_Status the test_Status to set
	 */
	public void setTest_Status(String test_Status) {
		Test_Status = test_Status;
	}
	
	/**
	 * @return the screenshot_URL
	 */
	public String getScreenshot_URL() {
		return screenshot_URL;
	}
	/**
	 * @param screenshot_URL the screenshot_URL to set
	 */
	public void setScreenshot_URL(String screenshot_URL) {
		this.screenshot_URL = screenshot_URL;
	}
	/**
	 * @return the test_Page_URL
	 */
	public String getTest_Page_URL() {
		return Test_Page_URL;
	}
	/**
	 * @param test_Page_URL the test_Page_URL to set
	 */
	public void setTest_Page_URL(String test_Page_URL) {
		Test_Page_URL = test_Page_URL;
	}
	/**
	 * @return the screenshot
	 */
	public byte[] getScreenshot() {
		return screenshot;
	}
	/**
	 * @param screenshot the screenshot to set
	 */
	public void setScreenshot(byte[] screenshot) {
		this.screenshot = screenshot;
	}
	/**
	 * @return the scenario
	 */
	public String getScenario() {
		return scenario;
	}
	/**
	 * @param scenario the scenario to set
	 */
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	/**
	 * @return the executionTime
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the scenarioExecutionId
	 */
	public String getScenarioExecutionId() {
		return scenarioExecutionId;
	}

	/**
	 * @param scenarioExecutionId the scenarioExecutionId to set
	 */
	public void setScenarioExecutionId(String scenarioExecutionId) {
		this.scenarioExecutionId = scenarioExecutionId;
	}
	

}
