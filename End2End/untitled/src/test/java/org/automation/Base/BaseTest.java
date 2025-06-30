package org.automation.Base;

import org.automation.FilesUtilities.FileUtility;
import org.automation.utilites.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class BaseTest extends WebDriverUtils {

    WebDriver driver;
    WebDriverUtils utils;


    // No-argument constructor for TestNG
    public BaseTest() {
        super(null); // Will initialize driver in setup method
    }

    // BaseTest constructor to initialize the WebDriver
    public BaseTest(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    //Page Object method to return the WebDriver instance

    //Login Username and Password fields
    @FindBy(css = "input[name='username']")
    WebElement loginUsername;

    @FindBy(css = "input[name='password']")
    WebElement loginPassword;

    @FindBy(css = "input[value='Log In']")
    WebElement loginButton;

    //Fields for User Registration
    @FindBy(css = "input[id*=firstName]")
     WebElement firstName;

    @FindBy(css = "input[id*=lastName]")
     WebElement lastName;

    @FindBy(css = "input[name*=street]")
     WebElement StreetAddress;

    @FindBy(css = "input[name*=city]")
     WebElement City;

    @FindBy(css = "input[name*=state]")
     WebElement State;

    @FindBy(css = "input[name*=zipCode]")
    WebElement ZipCode;

    @FindBy(css = "input[name*=phoneNumber]")
    WebElement PhoneNumber;

    @FindBy(css = "input[name*=ssn]")
    WebElement SSN;

    @FindBy(css = "input[id='customer.username']")
    WebElement Username;

    @FindBy(css = "input[id='customer.password']")
    WebElement Password;

    @FindBy(css = "input[id='repeatedPassword']")
    WebElement ConfirmPassword;

    @FindBy(css = "input[value='Register']")
    WebElement RegisterButton;

    @FindBy(css = "p[class='error']")
    WebElement errorMessage;

    @FindBy(xpath = "//a[contains(@href, 'register') and text()='Register']")
    WebElement registerLink;

    @FindBy(xpath = "//a[contains(@href, 'logout') and text()='Log Out']")
    WebElement logoutLink;

    @BeforeClass
    public void broswerSetUp() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        if (this.driver == null) {
            this.driver = new ChromeDriver();
        }
        this.utils = new WebDriverUtils(driver);
        PageFactory.initElements(driver, this);

        // Read baseUrl from properties file in src/main/resources
        Properties props = WebDriverUtils.readProperties("config.properties");
        String baseUrl = props.getProperty("baseUrl");
        driver.get(baseUrl);
        utils.webDriverWait(20);
    }


    public void LoginPage(String username, String password) {
        loginUsername.sendKeys(username);
        loginPassword.sendKeys(password);
        loginButton.click();
        utils.elementVisible(errorMessage);

        // Check if the error message is displayed
        if (errorMessage.isDisplayed()) {
            System.out.println("Login failed: " + errorMessage.getText());
        } else {
            System.out.println("Login successful");
        }


    }

    public void redirectToRegisterPage() {
        webDriverWait(10);
        registerLink.click();
        utils.elementVisible(firstName);
    }


    // 2. Test method using the data
    @Test(priority = 2, dataProvider = "jsonDataProvider", dataProviderClass = FileUtility.class)
    public void testWithJsonData(Map<String, Object> testData) throws InterruptedException {
        firstName.clear();
        String firstName = (String) testData.get("firstName");
        lastName.clear();
        String lastName = (String) testData.get("lastName");
        StreetAddress.clear();
        String streetAddress = (String) testData.get("streetAddress");
        City.clear();
        String city = (String) testData.get("city");
        State.clear();
        String state = (String) testData.get("state");
        ZipCode.clear();
        String zipCode = (String) testData.get("zipCode");
        PhoneNumber.clear();
        String phoneNumber = (String) testData.get("phoneNumber");
        SSN.clear();
        String ssn = (String) testData.get("ssn");
        Username.clear();
        String username = (String) testData.get("username");
        Password.clear();
        String password = (String) testData.get("password");
        ConfirmPassword.clear();
        registerUser(firstName, lastName, streetAddress, city, state, zipCode, phoneNumber, ssn, username, password);
        Thread.sleep(5000L); // Wait for the registration to complete
    }


    public void registerUser(String firstName, String lastName, String streetAddress, String city, String state, String zipCode, String phoneNumber, String ssn, String username, String password) throws InterruptedException {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.StreetAddress.sendKeys(streetAddress);
        this.City.sendKeys(city);
        this.State.sendKeys(state);
        this.ZipCode.sendKeys(zipCode);
        this.PhoneNumber.sendKeys(phoneNumber);
        this.SSN.sendKeys(ssn);
        this.Username.sendKeys(username);
        this.Password.sendKeys(password);
        this.ConfirmPassword.sendKeys(password);
        RegisterButton.click();
        Thread.sleep(2000L); // Wait for the registration to complete
        try {
            if (logoutLink.isDisplayed()) {
                System.out.println("User logged in, redirecting to register page.");
                logoutLink.click();
                Thread.sleep(2000L);
            }
        } catch (Exception e) {
            System.out.println("user not logged in, redirecting to register page.");
        }
        redirectToRegisterPage();
    }


    @AfterClass(alwaysRun = true)
        public void tearDown () {
            if (driver != null) {
                driver.quit();
            }
        }

    }
