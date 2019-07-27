package com.gsd.sreenidhi.cheetah.reporting.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberOverviewChartsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class HTMLReportingEngine implements ReportingEngine {

	public static ReportingForm reportingForm = null;

	public HTMLReportingEngine() {
		super();
	}

	public void generateReport() throws CheetahException {
		reportingForm = CheetahEngine.reportingForm;
		String content = null;
		Date date = new Date();
		CucumberDetailedResults reportDetail = new CucumberDetailedResults();
		try {
			reportDetail.setOutputDirectory("target/");

			reportDetail.setOutputName("cucumber-results");
			reportDetail.setSourceFile("./target/cucumber.json");
			reportDetail.setScreenShotLocation("./screenshot/");
			reportDetail.execute(true, true);

		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
					"IO Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
					"Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}

		CucumberUsageReporting reportUsage = new CucumberUsageReporting();

		reportUsage.setOutputDirectory("target/");
		reportUsage.setJsonUsageFile("./target/cucumber-usage.json");
		try {
			reportUsage.executeReport();

		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
					"Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		CheetahEngine.logger.logMessage(null, "HTMLReportingEngine", "Usage Report Completed", Constants.LOG_INFO, false);

		ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
		options.setOutputFolder("target/");
		options.setReportPrefix("cucumber-reports");
		options.setJsonReportPaths(new String[] { "./target/cucumber.json" });
		options.setOverviewReport(true);
		options.setCoverageReport(true);
		CucumberOverviewChartsReport reportChart = new CucumberOverviewChartsReport(options);
		try {
			reportChart.execute(true, false);
			// reportChart.exportToPDF(new
			// File("./target/cucumber-reports-charts-report.html"),"testChart");
		} catch (Exception e) {

			e.printStackTrace();
		}
		CucumberResultsOverview reportsoverView = new CucumberResultsOverview();
		reportsoverView.setOutputDirectory("target/");
		reportsoverView.setOutputName("cucumber-results");
		reportsoverView.setSourceFile("./target/cucumber.json");

		try {

			reportsoverView.execute();
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
					"Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}

		CheetahEngine.logger.logMessage(null, "HTMLReportingEngine", "Overview Report Completed", Constants.LOG_INFO,
				false);

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("./target//header.html"));

			String dt = date.toString();
			String app = CheetahEngine.app_name;
			bw.write("<html><head><title>New Page</title></head><body><h1>" + dt + "</h1><h1>" + app
					+ "</h1></body></html>");

			bw.close();
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
					"IOException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
	}

	@Override
	public void recordTransaction() throws CheetahException {

	}

	@Override
	public void recordSuiteResults() throws CheetahException {
		// TODO Auto-generated method stub

	}

	@Override
	public void consolidateReport() throws CheetahException {
		CucumberConsolidatedReport reportConsolidated = new CucumberConsolidatedReport();
		reportConsolidated.setOutputDirectory("target/");
		reportConsolidated.setOutputName("cucumber-results");
		reportConsolidated.setPdfPageSize("A4 landscape");
		reportConsolidated.setSourceFile("./target/cucumber.json");

		try {
			reportConsolidated.execute(new File("./resources/consolidated_batch.json"), false
			);
			// reportConsolidated.exportToPDF(new
			// File("./target/cucumber-results-batch-config1.html"),"Consolidated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		CheetahEngine.logger.logMessage(null, "HTMLReportingEngine", "Consolidated Report Completed", Constants.LOG_INFO,
				false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordHistory()
	 */
	@Override
	public void recordHistory() throws CheetahException {

	}
	/*
	 * public void convertPdf() throws IOException, DocumentException { Document
	 * document = new Document(); // step 2 PdfWriter writer =
	 * PdfWriter.getInstance(document, new FileOutputStream(
	 * "C:\\Users\\abd\\workspace\\ECM\\target\\cucumber-results-feature-overview.pdf"
	 * )); // step 3 document.open(); // step 4
	 * XMLWorkerHelper.getInstance().parseXHtml(writer, document, new
	 * FileInputStream(
	 * "C:\\Users\\abd\\workspace\\ECM\\target\\cucumber-results-feature-overview.html"
	 * )); //step 5 document.close();
	 * 
	 * System.out.println( "PDF Created!" ); }
	 */
}
