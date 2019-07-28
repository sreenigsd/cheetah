package com.gsd.sreenidhi.cheetah.reporting.excel;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ExcelReportingEngine implements ReportingEngine {
	protected static ReportingForm reportingForm = null;

	public ExcelReportingEngine() {
		super();
	}

	public void generateReport() {
		reportingForm = CheetahEngine.reportingForm;
	}

	@Override
	public void recordTransaction() throws CheetahException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordSuiteResults() throws CheetahException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consolidateReport() throws CheetahException {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.gsd.sreenidhi.cheetah.reporting.ReportingEngine#recordHistory()
	 */
	@Override
	public void recordHistory() throws CheetahException {
		
	}
}
