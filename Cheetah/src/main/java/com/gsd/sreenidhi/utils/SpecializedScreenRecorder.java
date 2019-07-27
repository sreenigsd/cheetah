package com.gsd.sreenidhi.utils;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
 
/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class SpecializedScreenRecorder extends ScreenRecorder {
 
    private String name;
 
    /**
     * @param cfg  GraphicsConfiguration
     * @param captureArea Rectangle
     * @param fileFormat Format
     * @param screenFormat Format
     * @param mouseFormat Format
     * @param audioFormat Format
     * @param movieFolder  File
     * @param name String
     * @throws IOException IOException
     * @throws AWTException AWTException
     */
    public SpecializedScreenRecorder(GraphicsConfiguration cfg,
           Rectangle captureArea, Format fileFormat, Format screenFormat,
           Format mouseFormat, Format audioFormat, File movieFolder,
           String name) throws IOException, AWTException {
         super(cfg, captureArea, fileFormat, screenFormat, mouseFormat,
                  audioFormat, movieFolder);
         this.name = name;
    }
 
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
    	try {
			CheetahEngine.logger.logMessage(null, "Specialized Screen Recorder", "Validating movie file location", Constants.LOG_INFO, false);
		} catch (CheetahException e) {
			e.printStackTrace();
		}
          if (!movieFolder.exists()) {
                movieFolder.mkdirs();
          } else if (!movieFolder.isDirectory()) {
                throw new IOException("\"" + movieFolder + "\" is not a directory.");
          }
                           
          SimpleDateFormat dateFormat = new SimpleDateFormat(
                   "yyyy-MM-dd HH.mm.ss");
                         
          return new File(movieFolder, name +  "."
                  + Registry.getInstance().getExtension(fileFormat));
    }
 }