package com.gsd.sreenidhi.cheetah.reporting.csv;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingBean;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.CSVUtil;
import com.gsd.sreenidhi.utils.CalendarUtils;
import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CSVReportingEngine implements ReportingEngine {
	protected static ReportingForm reportingForm = null;

	public CSVReportingEngine() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#generateReport()
	 */
	public void generateReport() throws CheetahException {

		SimpleDateFormat sdf = new SimpleDateFormat("MMddYYYY_hhmmss");
		Date d = new Date();
		String dateString = sdf.format(d);

		reportingForm = CheetahEngine.reportingForm;
		String filePath = FileUtils.getReportsPath() + File.separator + "CSV" + File.separator
				+ CheetahEngine.app_name + File.separator + "Transactions";
		
		String fileName = CheetahEngine.app_name + "_" + dateString + ".csv";
		CheetahEngine.logger.logMessage(null, this.getClass().toString(), "Writing Report to CSV File: " + fileName,
				Constants.LOG_INFO, false);
		reportingForm = CheetahEngine.reportingForm;
		String header = "Scenario, Status, Page URL, Screenshot URL";
		CSVUtil csvUtil = new CSVUtil();
		csvUtil.checkCSVExists(filePath, fileName, true, header);

		String fileaddress = filePath + File.separator + fileName;

		ArrayList<String> lString = null;
		ArrayList<ReportingBean> reportingBeanList = CheetahEngine.reportingBeanList;
		ReportingBean reportingBean;
		if (reportingBeanList != null) {
			CheetahEngine.logger.logMessage(null, this.getClass().toString(),
					"Reporting Bean Count: " + reportingBeanList.size(), Constants.LOG_INFO, false);
			Iterator it = reportingBeanList.iterator();

			while (it.hasNext()) {
				lString = new ArrayList<String>();
				reportingBean = (ReportingBean) it.next();

				lString.add(validateString(reportingBean.getScenario()));
				lString.add(validateString(reportingBean.getTest_Status()));
				lString.add(validateString(reportingBean.getTest_Page_URL()));
				lString.add(validateString(reportingBean.getScreenshot_URL()));
				CheetahEngine.logger.logMessage(null, this.getClass().toString(),
						"Writing Scenario result: " + validateString(reportingBean.getScenario()),
						Constants.LOG_INFO, false);

				csvUtil.writeLine(fileaddress, lString, ',', '\"');

			}
		} else {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Reporting Bean not captured",
					Constants.LOG_ERROR, false);
		}

	}



	/**
	 * @param text
	 * @return
	 */
	private String validateString(String text) {
		if (text != null)
			return text;
		else
			return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordTransaction()
	 */
	public void recordTransaction() throws CheetahException {
		String filePath = FileUtils.getReportsPath() + File.separator + "CSV"
				+ File.separator + CheetahEngine.app_name;
		String fileName = "Transactions.csv";

		reportingForm = CheetahEngine.reportingForm;
		CSVUtil csvUtil = new CSVUtil();
		String header = "Transaction ID";
		csvUtil.checkCSVExists(filePath, fileName, false, header);

		String fileaddress = filePath + File.separator + fileName;
		ArrayList<String> lString = new ArrayList<String>();
		lString.add(reportingForm.getTransaction());
		CheetahEngine.logger.logMessage(null, this.getClass().toString(),
				"Writing Transation to CSV File: " + fileaddress, Constants.LOG_INFO, false);

		csvUtil.writeLine(fileaddress, lString, ',', ' ');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordSuiteResults()
	 */
	public void recordSuiteResults() throws CheetahException {
		String filePath = FileUtils.getReportsPath() + File.separator + "CSV" + File.separator + CheetahEngine.app_name;
		String fileName = CheetahEngine.app_name + "_suite.csv";

		reportingForm = CheetahEngine.reportingForm;
		CSVUtil csvUtil = new CSVUtil();
		String header = "Transaction ID, Timestamp, Total Tests, Pass, Fail";
		csvUtil.checkCSVExists(filePath, fileName, false, header);

		String fileaddress = filePath + File.separator + fileName;
		ArrayList<String> lString = new ArrayList<String>();
		lString.add(reportingForm.getTransaction());
		lString.add((reportingForm.getTimeStamp()!=null)?reportingForm.getTimeStamp().toString():CalendarUtils.getCurrentTimeStamp());
		lString.add(String.valueOf(reportingForm.getStatusPass()+reportingForm.getStatusFail()));
		lString.add(String.valueOf(reportingForm.getStatusPass()));
		lString.add(String.valueOf(reportingForm.getStatusFail()));
		CheetahEngine.logger.logMessage(null, this.getClass().toString(),
				"Writing Suite Results to CSV File: " + fileaddress, Constants.LOG_INFO, false);

		csvUtil.writeLine(fileaddress, lString, ',', ' ');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#
	 * consolidateReport()
	 */
	@Override
	public void consolidateReport() throws CheetahException {
		String filePath = FileUtils.getReportsPath();
		String fileName = "central";

		reportingForm = CheetahEngine.reportingForm;
		CSVUtil csvUtil = new CSVUtil();
		String header = "Application, Transaction ID, Timestamp, Total Tests, Pass, Fail";

		csvUtil.checkCSVExists(filePath, fileName + ".csv", false, header);
		csvUtil.checkCSVExists(filePath, fileName + "_new.csv", true, "");

		String fileaddress = filePath + File.separator + fileName;
		ArrayList<String> lString = new ArrayList<String>();
		lString.add(CheetahEngine.app_name);
		lString.add(reportingForm.getTransaction());
		lString.add(reportingForm.getTimeStamp().toString());
		lString.add(String.valueOf(reportingForm.getStatusPass()+reportingForm.getStatusFail()));
		lString.add(String.valueOf(reportingForm.getStatusPass()));
		lString.add(String.valueOf(reportingForm.getStatusFail()));
		CheetahEngine.logger.logMessage(null, this.getClass().toString(),
				"Writing Suite Results to CSV File: " + fileaddress + ".csv", Constants.LOG_INFO, false);
		csvUtil.updateOrWriteLine(fileaddress, lString, ',', ' ');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordHistory()
	 */
	@Override
	public void recordHistory() throws CheetahException {

		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);

		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		String mondayDate = "";
		System.out.println("Day of Week:" + Calendar.DAY_OF_WEEK);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			mondayDate = sdf.format(today);
		} else {
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
				cal.add(Calendar.DATE, -1);
				mondayDate = sdf.format(cal.getTime());
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				cal.add(Calendar.DATE, -2);
				mondayDate = sdf.format(cal.getTime());
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
				cal.add(Calendar.DATE, -3);
				mondayDate = sdf.format(cal.getTime());
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				cal.add(Calendar.DATE, -4);
				mondayDate = sdf.format(cal.getTime());
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				cal.add(Calendar.DATE, -5);
				mondayDate = sdf.format(cal.getTime());
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				cal.add(Calendar.DATE, -6);
				mondayDate = sdf.format(cal.getTime());
			}
		}

		int year = cal.get(Calendar.YEAR);

		String filePath = FileUtils.getReportsPath();
		
		String fileName = "testSuite_" + mondayDate + ".csv";
		filePath = filePath + File.separator + "TransactionHistory" + File.separator + year;

		reportingForm = CheetahEngine.reportingForm;
		CSVUtil csvUtil = new CSVUtil();
		String header = "Application, Transaction ID, Timestamp, Total Tests, Pass, Fail";
		csvUtil.checkCSVExists(filePath, fileName, false, header);

		String fileaddress = filePath + File.separator + fileName;
		ArrayList<String> lString = new ArrayList<String>();
		lString.add(CheetahEngine.app_name);
		lString.add(reportingForm.getTransaction());
		lString.add(reportingForm.getTimeStamp().toString());
		lString.add(String.valueOf(reportingForm.getStatusPass()+reportingForm.getStatusFail()));
		lString.add(String.valueOf(reportingForm.getStatusPass()));
		lString.add(String.valueOf(reportingForm.getStatusFail()));
		CheetahEngine.logger.logMessage(null, this.getClass().toString(),
				"Writing Suite Results to CSV File: " + fileaddress, Constants.LOG_INFO, false);

		csvUtil.writeLine(fileaddress, lString, ',', ' ');
	}

	static public Date convertLocalDateTimeToDate(LocalDateTime ldt) {
		ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
		GregorianCalendar cal = GregorianCalendar.from(zdt);
		return cal.getTime();
	}
	
}
