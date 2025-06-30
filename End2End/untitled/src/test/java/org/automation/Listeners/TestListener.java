package org.automation.Listeners;

import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public interface TestListener extends ITestNGListener {
    void onTestStart(ITestResult result);

    void onTestSuccess(ITestResult result);

    void onTestFailure(ITestResult result);

    void onTestSkipped(ITestResult result);

    void onFinish(ITestContext context);
}
