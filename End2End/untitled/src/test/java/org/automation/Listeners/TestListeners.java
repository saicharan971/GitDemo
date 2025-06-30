package org.automation.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.automation.reports.TestExtentReports;
import org.automation.utilites.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import java.io.File;
import java.lang.reflect.Field;

import static com.aventstack.extentreports.ExtentReports.*;

public class TestListeners extends WebDriverUtils implements ITestListener {
    ExtentTest test;
    ExtentReports extent = TestExtentReports.getReportObject();

// No-argument constructor for TestNG
    public TestListeners() {
        super(null); // Will initialize driver in setup method
    }

    public TestListeners(WebDriver driver){
        super(driver);
    }




    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
        attachScreenshot(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed");
        test.fail(result.getThrowable());
        attachScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    private void attachScreenshot(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            Field driverField = null;
            Class<?> clazz = testInstance.getClass(); // Use getClass() to get the runtime class of the test instance
            // Search for 'driver' field up the class hierarchy
            while (clazz != null) {
                try {
                    driverField = clazz.getDeclaredField("driver");
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (driverField == null) {
                test.log(Status.WARNING, "Could not find 'driver' field in test class hierarchy.");
                return;
            }
            driverField.setAccessible(true);
            WebDriver driver = (WebDriver) driverField.get(testInstance);
            if (driver != null) {
                String filePath = takeScreenShot(driver, result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
            } else {
                test.log(Status.WARNING, "WebDriver is null. Screenshot not taken.");
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
        }
    }
}

//         org.apache.commons.io.FileUtils.copyFile(source, destFile);
//         return destPath;
