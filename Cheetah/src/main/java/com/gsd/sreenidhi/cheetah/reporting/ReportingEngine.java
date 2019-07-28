package com.gsd.sreenidhi.cheetah.reporting;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public interface ReportingEngine {

	/**
	 * @throws CheetahException  Generic Exception Object that handles all exceptions
	 */
	public void generateReport() throws CheetahException;

	/**
	 * @throws CheetahException  Generic Exception Object that handles all exceptions
	 */
	public void recordTransaction() throws CheetahException;

	/**
	 * @throws CheetahException  Generic Exception Object that handles all exceptions
	 */
	public void recordSuiteResults() throws CheetahException;

	/**
	 * @throws CheetahException  Generic Exception Object that handles all exceptions
	 */
	public void consolidateReport() throws CheetahException;

	public void recordHistory() throws CheetahException;
}
