package framework.driver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {

    // Maneja el driver por hilo para evitar conflictos si el framework escala a paralelismo
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(AppiumDriver d) {
        driver.set(d);
    }

    public static void unload() {
        driver.remove();
    }
}
