package org.automation.Tests;

import org.automation.Base.BaseTest;
import org.automation.FilesUtilities.FileUtility;
import org.testng.annotations.Test;


import java.util.Map;

public class TestFile extends BaseTest {

    @Test(priority = 1) // Example test method
    public void eLogin() throws InterruptedException {
        // Do NOT call driverInitialize() here. The driver is already initialized in BaseTest's setUp().
        // Call your login or test logic directly:
        LoginPage("10699251", "123456");
        redirectToRegisterPage();
    }

    }


