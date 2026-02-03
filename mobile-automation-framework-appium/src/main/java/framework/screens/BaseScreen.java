package framework.screens;

import framework.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BaseScreen {

    // Clase base para que todas las pantallas compartan acceso al driver y búsquedas básicas
    protected AppiumDriver driver() {
        return DriverManager.getDriver();
    }

    protected WebElement find(By locator) {
        return driver().findElement(locator);
    }
}
