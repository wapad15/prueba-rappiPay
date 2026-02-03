package framework.screens;

import framework.logging.LoggerFactoryProvider;
import framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class ProductHomeScreen extends BaseScreen {

    private static final Logger log = LoggerFactoryProvider.getLogger(ProductHomeScreen.class);

    // Elemento clave que indica que ya estamos en el Home de productos
    private final By productsTitle = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV");

    public boolean isLoaded() {
        log.info("Products: validando pantalla Products");
        boolean loaded = WaitUtils.waitVisibleBoolean(productsTitle, 15);
        log.info("Products: loaded = {}", loaded);
        return loaded;
    }
}
