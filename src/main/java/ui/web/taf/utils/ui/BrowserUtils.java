package ui.web.taf.utils.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import ui.web.taf.utils.core.LoggingUtils;

import java.util.Set;

public class BrowserUtils {

    /**
     * Scrolls the view to the specified web element.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to scroll to.
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        LoggingUtils.debug("Scrolling to element: " + element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Clicks a web element using JavaScript, bypassing potential visibility issues.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to click.
     */
    public static void clickByJS(WebDriver driver, WebElement element) {
        LoggingUtils.debug("Clicking element with JavaScript: " + element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Switches the driver's focus to the newest window or tab.
     *
     * @param driver The WebDriver instance.
     * @return The handle of the original window.
     */
    public static String switchToNewWindow(WebDriver driver) {
        LoggingUtils.debug("Switching to new window.");
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String windowHandle : allWindows) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                LoggingUtils.info("Switched to window: " + windowHandle);
                break;
            }
        }
        return originalWindow;
    }

    /**
     * Handles a JavaScript alert by either accepting or dismissing it.
     *
     * @param driver The WebDriver instance.
     * @param accept True to accept the alert, false to dismiss it.
     */
    public static void handleAlert(WebDriver driver, boolean accept) {
        try {
            Alert alert = driver.switchTo().alert();
            if (accept) {
                LoggingUtils.info("Accepting alert with text: " + alert.getText());
                alert.accept();
            } else {
                LoggingUtils.info("Dismissing alert with text: " + alert.getText());
                alert.dismiss();
            }
        } catch (Exception e) {
            LoggingUtils.error("Failed to handle alert: " + e.getMessage());
        }
    }

    /**
     * Highlights a web element by adding a red border, useful for debugging.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to highlight.
     */
    public static void highlightElement(WebDriver driver, WebElement element) {
        LoggingUtils.debug("Highlighting element: " + element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;')", element);
        try {
            // Keep the highlight for a short period
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Revert to the original style
        js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
    }
}
