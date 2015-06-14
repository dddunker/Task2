package com.test.tests;

import com.test.pages.LoginPage;
import com.test.pages.MainPage;
import com.test.users.User;
import com.test.utils.ConfigProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class TestCase {

    protected static WebDriver driver;

    public User user = new User(ConfigProperties.getProperty("user.email"),
            ConfigProperties.getProperty("user.password"));

    protected MainPage mainPage;
    protected LoginPage loginPage;


    protected WebDriver getDriver(String browser) {
        if (driver == null) {
            if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "/opt/google/chrome/chromedriver");
                driver = new ChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Long.parseLong(ConfigProperties.getProperty("implicitlyWait")),
                    TimeUnit.SECONDS);
        }
        return driver;
    }

    @Parameters("browser")
    @BeforeTest
    public void logIn(String browser) {
        mainPage = PageFactory.initElements(getDriver(browser), MainPage.class);
        loginPage = PageFactory.initElements(getDriver(browser), LoginPage.class);

        loginPage.open();
        loginPage.loginAs(user);
    }

    @AfterTest
    public void tearDown() throws Exception {
        mainPage.deleteAllThingsFromActions();
        mainPage.waitUntilSync();
        driver.quit();
    }
}
