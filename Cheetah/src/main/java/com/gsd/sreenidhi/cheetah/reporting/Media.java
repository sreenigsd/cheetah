package com.gsd.sreenidhi.cheetah.reporting;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.openqa.selenium.remote.Augmentable;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.CSVUtil;
import com.gsd.sreenidhi.utils.SpecializedScreenRecorder;

/**
 * The media class implements the media controls for the Test execution
 * including Screen Capture and Video recording
 * 
 * @author Sreenidhi, Gundlupet
 *
 */

@Augmentable
public class Media {

	/**
	 * @param transaction
	 *            Transaction ID
	 * @param videoFileName
	 *            Name of the video File
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void initiateVideo(String transaction, String videoFileName) throws CheetahException {

		String filePath;
		if (transaction != null && transaction.length() > 1) {
			filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO" + File.separator
					+ CheetahEngine.app_name + File.separator + transaction;
		} else {
			filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO" + File.separator
					+ CheetahEngine.app_name;
		}

		startRecording(filePath, videoFileName);
	}

	
	/**
	 * @param filePath The path of the video file
	* @param videoFileName
	 *            The file name of the video file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void startRecording(String filePath, String videoFileName) throws CheetahException {

		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Attempting to start Video Recording.",
				Constants.LOG_INFO, false);

		File file = new File(filePath);
		if (!file.exists()) {
			CheetahEngine.logger.logMessage(null, CSVUtil.class.getName(),
					"Creating Report (VIDEO) Directories: " + file.toString(), Constants.LOG_INFO, false);
			file.mkdirs();
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		Rectangle captureSize = new Rectangle(0, 0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		try {
			CheetahEngine.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, file, videoFileName);

			if (Constants.recordVideo) {
				CheetahEngine.screenRecorder.start();
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Video Recording started.",
						Constants.LOG_INFO, false);
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Video Recording disabled.",
						Constants.LOG_INFO, false);
			}

		} catch (IOException | AWTException e) {
			throw new CheetahException();
		}

	}

	/**
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void stopRecording() throws CheetahException {
		if (CheetahEngine.screenRecorder != null) {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Attempting to stop Video Recording.",
					Constants.LOG_INFO, false);
			try {

				if (Constants.recordVideo) {
					CheetahEngine.screenRecorder.stop();
					CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Video Recording Stopped.",
							Constants.LOG_INFO, false);
				} else {
					CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Video Recording disabled.",
							Constants.LOG_INFO, false);
				}

			} catch (IOException e) {
				throw new CheetahException();
			}
		}
	}

}
