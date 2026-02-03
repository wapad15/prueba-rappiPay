package tests;

import framework.config.ConfigManager;
import framework.config.Platform;
import framework.driver.DriverFactory;
import framework.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        // Cargamos la configuración base del framework para Android
        ConfigManager.load(Platform.ANDROID);

        // Inicializamos el driver antes de cada prueba
        AppiumDriver driver = new DriverFactory().createDriver(Platform.ANDROID);
        DriverManager.setDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver != null) {
            // Cerramos la sesión al final para no dejar procesos abiertos
            driver.quit();
        }
        DriverManager.unload();
    }
}
