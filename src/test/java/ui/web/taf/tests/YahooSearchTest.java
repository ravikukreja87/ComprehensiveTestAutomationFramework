package ui.web.taf.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.web.taf.pages.YahooSearchPage;
import ui.web.taf.utils.ui.WaitUtils;

import java.time.Duration;

@Epic("Web Search")
@Feature("Yahoo Search")
public class YahooSearchTest {
    private WebDriver driver;
    private YahooSearchPage yahooSearchPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Implicitly wait for elements to be found
        WaitUtils.setImplicitWait(driver, 10);
        yahooSearchPage = new YahooSearchPage(driver);
    }

    @Test(description = "Verify Yahoo Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User searches for a term on Yahoo")
    @Description("Test checks if searching for 'Selenium WebDriver' returns a page with the correct title.")
    public void testYahooSearch() {
        yahooSearchPage.navigateTo();
        String originalWindow = driver.getWindowHandle();
        
        yahooSearchPage.searchFor("Selenium WebDriver");

        // Wait for the new tab to open
        WaitUtils.waitForNumberOfWindowsToBe(driver, 2, 10);

        // Switch to the new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Explicitly wait for the title to contain the search term in the new tab
        WaitUtils.waitForTitleToContain(driver, "Selenium WebDriver", 10);
        
        String title = yahooSearchPage.getTitle();
        Assert.assertTrue(title.contains("Selenium WebDriver"), "Title should contain the search term");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
