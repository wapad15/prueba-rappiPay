package framework.driver;

import framework.config.ConfigManager;
import framework.config.Platform;
import framework.driver.capabilities.AndroidCapsBuilder;
import framework.logging.LoggerFactoryProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.slf4j.Logger;

import java.net.URL;

public class DriverFactory {

    // Se encarga de construir el driver según la plataforma configurada
    private static final Logger log = LoggerFactoryProvider.getLogger(DriverFactory.class);

    public AppiumDriver createDriver(Platform platform) {
        try {
            URL serverUrl = new URL(ConfigManager.get("appiumServerUrl"));
            log.info("Creando driver para platform={} | appiumServerUrl={}", platform, serverUrl);

            return switch (platform) {
                case ANDROID -> {
                    Capabilities caps = new AndroidCapsBuilder().build();
                    log.info("Android caps -> {}", caps);
                    yield new AndroidDriver(serverUrl, caps);
                }
                case IOS -> throw new UnsupportedOperationException("iOS aún no implementado en este reto (solo dejamos estructura).");
            };

        } catch (Exception e) {
            log.error("Error creando driver para platform={}: {}", platform, e.getMessage(), e);
            throw new RuntimeException("No se pudo crear el driver para: " + platform, e);
        }
    }
}
