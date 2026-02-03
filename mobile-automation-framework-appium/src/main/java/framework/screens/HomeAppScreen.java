package framework.screens;

import framework.logging.LoggerFactoryProvider;
import framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class HomeAppScreen extends BaseScreen {

    private static final Logger log = LoggerFactoryProvider.getLogger(HomeAppScreen.class);

    // Elementos principales del Home de la app
    private final By menuButton    = AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV");
    private final By productsTitle = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV");

    public void openMenu() {
        log.info("Home: abriendo menú lateral");
        WaitUtils.waitClickable(menuButton, 15).click();
    }

    public boolean isProductsLoaded() {
        log.info("Home: validando que Products esté visible");
        boolean loaded = WaitUtils.waitVisibleBoolean(productsTitle, 15);
        log.info("Home: Products visible = {}", loaded);
        return loaded;
    }
}
