package com.gsd.sreenidhi.cheetah.reporting.html;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.forms.Constants;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class HTMLReportingEngine implements ReportingEngine {

    public HTMLReportingEngine() {
        super();
    }

    public void generateReport() throws CheetahException {
        try {
            File reportOutputDirectory = new File("target/cucumber-reports");
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add("target/cucumber.json"); // specify the path of your cucumber.json file

            String buildNumber = "1";
            String projectName = "Cheetah Project";
            boolean runWithJenkins = false;
            boolean parallelTesting = false;

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setBuildNumber(buildNumber);
         
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
        } catch (Exception e) {
            CheetahEngine.logger.logMessage(e, "HTMLReportingEngine",
                    "Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
                    Constants.LOG_ERROR, true);
            throw new CheetahException(e);
        }
    }

    @Override
    public void recordTransaction() throws CheetahException {
        // Implementation
    }

    @Override
    public void recordSuiteResults() throws CheetahException {
        // Implementation
    }

    @Override
    public void consolidateReport() throws CheetahException { try {
        File reportOutputDirectory = new File("target/consolidated-cucumber-reports");
        Collection<File> jsonFiles = FileUtils.listFiles(new File("target/cucumber-json-reports/"),
                                                          new String[]{"json"}, true);

        List<String> jsonPaths = new ArrayList<>();
        for (File file : jsonFiles) {
            jsonPaths.add(file.getAbsolutePath());
        }

        Configuration configuration = new Configuration(reportOutputDirectory, "Cheetah Project");
        // Additional configuration settings...

        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, configuration);
        reportBuilder.generateReports();
    } catch (Exception e) {
        // Handle exceptions
        throw new CheetahException(e);
    }}

    @Override
    public void recordHistory() throws CheetahException {
        // Implementation
    }
}
