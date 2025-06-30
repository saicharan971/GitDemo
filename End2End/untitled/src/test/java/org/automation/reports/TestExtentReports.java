package org.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestExtentReports {


        public static ExtentReports getReportObject(){
            String reportsDir = System.getProperty("user.dir") + "//reports";

            // Ensure the reports directory exists
            java.io.File dir = new java.io.File(reportsDir);
            if (!dir.exists()) {
                dir.mkdirs(); // Create the reports directory if it doesn't exist
            }
            // Define the path for the report file
            String path = reportsDir + "//index.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
            sparkReporter.config().setReportName("Web Automation Results");
            sparkReporter.config().setDocumentTitle("Test Results");

            // Create an instance of ExtentReports and attach the reporter
            ExtentReports extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Tester", "Saicharan");
            return extent;


        }

    private void setSystemInfo(String tester, String saicharan) {
    }

    private void attachReporter(ExtentSparkReporter sparkReporter) {
    }



}
// Compare this snippet from src/test/java/org/automation/utilites/WebDriverUtils.java:
