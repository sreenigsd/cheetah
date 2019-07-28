package com.gsd.sreenidhi.cheetah.reporting;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ReportingForm {
	
	ArrayList<ReportingBean> reportingBean;
	String ReportType;
	String transaction;
	Long dbScenarioID;
	Date timeStamp;
	int statusPass;
	int statusFail;
	
	
	/**
	 * @return the reportingBean
	 */
	public ArrayList<ReportingBean> getReportingBean() {
		return reportingBean;
	}
	/**
	 * @param reportingBean the reportingBean to set
	 */
	public void setReportingBean(ArrayList<ReportingBean> reportingBean) {
		this.reportingBean = reportingBean;
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return ReportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		ReportType = reportType;
	}
	/**
	 * @return the transaction
	 */
	public String getTransaction() {
		return transaction;
	}
	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the statusPass
	 */
	public int getStatusPass() {
		return statusPass;
	}
	/**
	 * @param statusPass the statusPass to set
	 */
	public void setStatusPass(int statusPass) {
		this.statusPass = statusPass;
	}
	/**
	 * @return the statusFail
	 */
	public int getStatusFail() {
		return statusFail;
	}
	/**
	 * @param statusFail the statusFail to set
	 */
	public void setStatusFail(int statusFail) {
		this.statusFail = statusFail;
	}
	/**
	 * @return the dbScenarioID
	 */
	public Long getDbScenarioID() {
		return dbScenarioID;
	}
	/**
	 * @param dbScenarioID the dbScenarioID to set
	 */
	public void setDbScenarioID(Long dbScenarioID) {
		this.dbScenarioID = dbScenarioID;
	}
	
	

}
