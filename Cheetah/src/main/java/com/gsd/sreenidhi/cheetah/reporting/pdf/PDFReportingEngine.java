package com.gsd.sreenidhi.cheetah.reporting.pdf;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingBean;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.cheetah.reports.Chart;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.CalendarUtils;
import com.gsd.sreenidhi.utils.FileUtils;

import io.cucumber.java.Scenario;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class PDFReportingEngine implements ReportingEngine {

	protected static ReportingForm reportingForm = null;
	private static StyleBuilder boldCenteredStyle;
	private static StyleBuilder boldStyle;
	protected static ReportingBean bean = null;
	protected static ArrayList<ReportingBean> reportingBeanList = null;

	/**
	 * PDFReportingEngine constructor
	 */
	public PDFReportingEngine() {
		super();
	}

	@Override
	public void generateReport() throws CheetahException {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddYYYY_hhmmss");
		Date d = new Date();
		String dateString = sdf.format(d);

		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Initializing PDF Reporting Enging",
				Constants.LOG_INFO, false);

		try {
			reportingForm = CheetahEngine.reportingForm;
			String filePath = FileUtils.getReportsPath() + File.separator + "PDF" + File.separator
					+ CheetahEngine.app_name;

			String fileName = CheetahEngine.app_name + "_" + dateString + ".pdf";
			String fileaddress = filePath + File.separator + fileName;
			CheetahEngine.logger.logMessage(null, this.getClass().toString(), "Writing Report to pdf File: " + fileName,
					Constants.LOG_INFO, false);
			boldCenteredStyle = stl.style().bold().setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(14);

			FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

			FileUtils fileUtil = new FileUtils();
			fileUtil.checkpdfFileExists(filePath, fileName, true);
			JasperReportBuilder report = DynamicReports.report();// a new report

			Map<String, Color> seriesColors = new HashMap<String, Color>();
			seriesColors.put("passed", Color.GREEN);
			seriesColors.put("failed", Color.RED);

			// Title
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Generating Title", Constants.LOG_INFO, false);
			SubreportBuilder titleSubReport = cmp.subreport(buildTitleSubReport());

			// Summary
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Generating Summary", Constants.LOG_INFO, false);
			SubreportBuilder summarySubReport = cmp.subreport(buildSummarySubReport());

			// Chart
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Generating Data", Constants.LOG_INFO, false);
			SubreportBuilder chartSubReport = cmp.subreport(buildChartSubReport());

			
			// Details
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Generating Data", Constants.LOG_INFO, false);
			SubreportBuilder dataSubReport = cmp.subreport(buildTestDetailsSubReport());

			final VerticalListBuilder summary = cmp.verticalList();

			summary.add(titleSubReport);
			summary.add(cmp.verticalGap(15));
			summary.add(summarySubReport);
			summary.add(cmp.verticalGap(15));
			summary.add(chartSubReport);
			summary.add(cmp.pageBreak());
			summary.add(dataSubReport);
			summary.add(cmp.verticalGap(15));
			report.summary(summary);

			

			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Finalizing Report", Constants.LOG_INFO, false);
			report.build();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Writing Content to PDF File", Constants.LOG_INFO,
					false);

			report.toPdf(new FileOutputStream(fileaddress));
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "PDF Report Generated Successfully",
					Constants.LOG_INFO, false);
			
			DBExecutor.recordReport("PDF","Test Execution Report", fileaddress);
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, this.getClass().getName(),
					"Exception:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR, true);
		}

	}



	private static TextFieldBuilder<String> createTextField(String label) {

		return cmp.text(label).setStyle(boldCenteredStyle);

	}

	
	@Override
	public void recordTransaction() throws CheetahException {
		//Not implemented in PDF reporting

	}

	@Override
	public void recordSuiteResults() throws CheetahException {
		//Not implemented in PDF reporting

	}

	@Override
	public void consolidateReport() throws CheetahException {
		//Not implemented in PDF reporting

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordHistory()
	 */
	@Override
	public void recordHistory() throws CheetahException {
			//History recording not implemented in PDF reporting
	}

	private class ExpressionColumn extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public String evaluate(ReportParameters reportParameters) {
			return "3";

		}
	}

	private class ExpressionColumn1 extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public String evaluate(ReportParameters reportParameters) {
			return "5";

		}
	}

	/**
	 * @return
	 */
	private JasperReportBuilder buildTitleSubReport() {
		JasperReportBuilder report = DynamicReports.report();
		report.title(cmp.text("Test Execution Report").setStyle(boldCenteredStyle));
		return report;
	}

	/**
	 * @return
	 */
	private JasperReportBuilder buildSummarySubReport() {
		JasperReportBuilder report = DynamicReports.report();
		
		HorizontalListBuilder row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Application: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.props.getProperty("app.name")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Execution Date: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CalendarUtils.getCurrentTimeStamp()).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		report.addSummary(cmp.line());
		report.addSummary(cmp.text("").setText("Execution Parameters").setStyle(boldCenteredStyle));
	
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Test Type: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.props.getProperty("test.type")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Execution Browser: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.props.getProperty("browser.name")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Close Browser: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.props.getProperty("close.browser")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Execution Tag: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.props.getProperty("execution.tag")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Execution Environment: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(CheetahEngine.configurator.executionConfigurator.getExecutionEnv()).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		row = null;
		row = cmp.horizontalList();
		row.add(cmp.text("").setText("Record Video: ").setHorizontalAlignment(HorizontalAlignment.LEFT));
		row.add(cmp.text("").setText(((CheetahEngine.props.getProperty("record.video")) != null ? CheetahEngine.props.getProperty("record.video") : "FALSE (Default)")).setHorizontalAlignment(HorizontalAlignment.RIGHT));
		report.addSummary(row);
		
		report.addSummary(cmp.line());

		report.addSummary(cmp.text("").setText("Summary").setStyle(boldCenteredStyle));
		report.addSummary(cmp.text(""));
		report.addSummary(cmp.text("Total number of test scenarios: " + CheetahEngine.reportingBeanList.size()));
		report.addSummary(
				cmp.text("").setText("Test Start Time: " + CalendarUtils.dateToString(CheetahEngine.cheetahForm.getStartTime())));
		report.addSummary(
				cmp.text("").setText("Test End Time: " + CalendarUtils.dateToString(CheetahEngine.cheetahForm.getEndTime())));

		long difftime = CheetahEngine.cheetahForm.getEndTime().getTime() - CheetahEngine.cheetahForm.getStartTime().getTime();
		long executionTime = TimeUnit.MILLISECONDS.toMillis(difftime);
		report.addSummary(cmp.text("")
				.setText("Total Time for Test Execution: " + CalendarUtils.getDurationBreakdown(executionTime)));

		report.addSummary(cmp.line());
		return report;
	}

	/**
	 * @return
	 * @throws CheetahException 
	 */
	private JasperReportBuilder buildTestDetailsSubReport() throws CheetahException {
		JasperReportBuilder report = DynamicReports.report();

		report.addSummary(cmp.text("").setText(""));
		report.addSummary(cmp.text("").setText("Execution Details").setStyle(boldCenteredStyle));
		report.addSummary(cmp.text("").setText(""));

		final VerticalListBuilder details = cmp.verticalList();

		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Finalizing Report", Constants.LOG_INFO, false);
		report.build();

		Iterator it = CheetahEngine.reportingBeanList.iterator();
		SubreportBuilder detailSubReport;
		ReportingBean localBean = null;
		while (it.hasNext()) {
			detailSubReport = null;
			localBean = null;
			localBean = (ReportingBean) it.next();

			detailSubReport = cmp.subreport(buildBeanDetailsSubReport(localBean));

			details.add(detailSubReport);
		}

		report.summary(details);
		report.addSummary(cmp.text("").setText(""));
		report.addSummary(cmp.text("").setText(""));

		report.build();
		return report;
	}

	/**
	 * @param bean
	 * @return
	 */
	private JasperReportBuilder buildBeanDetailsSubReport(ReportingBean bean) {

		JasperReportBuilder report = null;
		report = DynamicReports.report();

		StyleBuilder backgroundStylePass = stl.style().setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(12)
				.setBackgroundColor(new Color(158, 253, 212));
		StyleBuilder backgroundStyleFail = stl.style().setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(12)
				.setBackgroundColor(new Color(249, 118, 118));

		Scenario scenario = null;
		scenario = bean.getScenarioImpl();

		
		StyleBuilder statusStyle = ("passed".equalsIgnoreCase(scenario.getStatus().toString()) ? backgroundStylePass
				: backgroundStyleFail);
		report.addSummary(cmp.text("").setText("Scenario: " + bean.getScenario()).setStyle(statusStyle));
		report.addSummary(cmp.text("").setText("Applicable tags: " + scenario.getSourceTagNames()));
		report.addSummary(cmp.text("").setText("Execution Status: " + scenario.getStatus()));

		long difftime = bean.getTestEndTime().getTime() - bean.getTestStartTime().getTime();
		long executionTime = TimeUnit.MILLISECONDS.toMillis(difftime);
		report.addSummary(cmp.text("").setText("Execution Time: " + CalendarUtils.getDurationBreakdown(executionTime)));
		report.addSummary(cmp.text("").setText("Test Page URL: " + bean.getTest_Page_URL()));

		if (bean.getScreenshot() != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(bean.getScreenshot());
			report.addSummary(
					cmp.image(bis).setHorizontalAlignment(HorizontalAlignment.CENTER).setMinDimension(500, 330));
		}

		report.addSummary(cmp.text("").setText(""));
		report.addSummary(cmp.text("").setText(""));

		return report;
	}

	private JasperReportBuilder buildChartSubReport() throws CheetahException {
		JasperReportBuilder report = null;
		report = DynamicReports.report();
		
	
		Chart c = new Chart();
		byte[] b = c.generatePieChart(400, 400, CheetahEngine.reportingForm);
		
		if (b != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			report.addSummary(
					cmp.image(bis).setHorizontalAlignment(HorizontalAlignment.CENTER).setMinDimension(400,400));
		}
		
		return report;
	}
}
