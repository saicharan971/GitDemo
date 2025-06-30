package org.automation.utilites;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


public class WebDriverUtils {



    public WebDriverWait Wait;
    public WebDriver driver;


    public WebDriverUtils(WebDriver driver) {
        this.driver = driver;
    }



    //WebDriverWait method to set up an explicit wait
    public void webDriverWait(int Seconds) {
        Wait=new WebDriverWait(driver, Duration.ofSeconds(Seconds));
    }

    //Method to take a screenshot
    public String takeScreenShot(WebDriver driver, String testCaseName) throws IOException {
        // Use a valid file path for the screenshot
        String filePath = "D:\\RestAssured\\End2End\\untitled\\reports" + File.separator + testCaseName + ".png";
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(filePath));
        return filePath;
    }

    //Method to wait for an element to be invisible
    public void InvisibleWait() {
    Wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("loading"))));
    }

    //Method for implicit wait for 10 seconds
    public void implicitWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    public void elementClick(By by) {
        Wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public void elementVisible(WebElement element) {
        Wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Method to read properties from a file in src/main/resources
    public static Properties readProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        // Adjust the path to point to src/main/resources
        String filePath = System.getProperty("user.dir") + "/src/main/resources/" + fileName;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
        return properties;
    }
}
