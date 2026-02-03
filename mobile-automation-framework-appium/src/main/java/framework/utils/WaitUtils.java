package framework.utils;

import framework.driver.DriverManager;
import framework.logging.LoggerFactoryProvider;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

public class WaitUtils {

    // Utilidad central para esperas explícitas y evitar repetir lógica en pantallas
    private static final Logger log = LoggerFactoryProvider.getLogger(WaitUtils.class);

    private WaitUtils() {}

    private static AppiumDriver driver() {
        return DriverManager.getDriver();
    }

    public static WebElement waitVisible(By locator, int seconds) {
        try {
            log.debug("WAIT visible ({}s): {}", seconds, locator);
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.warn("TIMEOUT visible ({}s): {}", seconds, locator);
            throw e;
        }
    }

    public static WebElement waitClickable(By locator, int seconds) {
        try {
            log.debug("WAIT clickable ({}s): {}", seconds, locator);
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            log.warn("TIMEOUT clickable ({}s): {}", seconds, locator);
            throw e;
        }
    }

    public static boolean waitVisibleBoolean(By locator, int seconds) {
        try {
            waitVisible(locator, seconds);
            return true;
        } catch (Exception e) {
            log.info("Visible=false ({}s): {}", seconds, locator);
            return false;
        }
    }
}
