package com.gsd.sreenidhi.cheetah.reports;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.Styler.ChartTheme;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class Chart extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8425877869194148803L;
	private String chartFileLocation = "."+File.separator+"target"+File.separator+"summary";

	/**
	 * Constructor
	 */
	public Chart() {
		//Class Constructor
	}

	/**
	 * @param width
	 *            width of the pie chart
	 * @param height
	 *            height height of the pie chart
	 * @param reportingForm
	 *            Form containing the data for the pie chart
	 * @return byte array of image
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public byte[] generatePieChart(int width, int height, ReportingForm reportingForm) throws CheetahException {
		createPieChart(width, height, reportingForm);
		byte[] b = getimageByte();
		return b;
	}

	/**
	 * @param width
	 *            width height of the pie chart
	 * @param height
	 *            height height of the pie chart
	 * @param reportingForm
	 *            Form containing the data for the pie chart
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void createPieChart(int width, int height, ReportingForm reportingForm) throws CheetahException {

		// Create Chart
		org.knowm.xchart.PieChart chart = new PieChartBuilder().width(width).height(height).title("Execution Results")
				.theme(ChartTheme.GGPlot2).build();

		// Customize Chart
		chart.getStyler().setLegendVisible(false);
		chart.getStyler().setAnnotationType(AnnotationType.LabelAndPercentage);
		chart.getStyler().setAnnotationDistance(1.15);
		chart.getStyler().setPlotContentSize(.7);
		chart.getStyler().setStartAngleInDegrees(90);

		// Series

		chart.addSeries("FAIL", reportingForm.getStatusFail()).setFillColor(Color.RED);
		chart.addSeries("PASS", reportingForm.getStatusPass()).setFillColor(Color.GREEN);

		// Save it
		CheetahEngine.logger.logMessage(null, "Chart", "Image saved to location: " + chartFileLocation , Constants.LOG_INFO, false);
		try {
			BitmapEncoder.saveBitmap(chart, chartFileLocation, BitmapFormat.PNG);
		} catch (IOException e) {
			throw new CheetahException(e);
		}

		// or save it in high-res
		try {
			BitmapEncoder.saveBitmapWithDPI(chart, chartFileLocation+"200", BitmapFormat.PNG, 200);
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @return byte array of image
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private byte[] getimageByte() throws CheetahException {

		CheetahEngine.logger.logMessage(null, "Chart", "Converting Image to Byte"  , Constants.LOG_INFO, false);
		
		byte[] imageInByte = null;
		try{

			BufferedImage originalImage =
		                              ImageIO.read(new File(chartFileLocation+".png"));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( originalImage, "png", baos );
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

			}catch(IOException e){
				throw new CheetahException(e);
			}
		return imageInByte;

	}

}
