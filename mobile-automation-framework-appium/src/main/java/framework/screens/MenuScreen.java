package framework.screens;

import framework.logging.LoggerFactoryProvider;
import framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class MenuScreen extends BaseScreen {

    private static final Logger log = LoggerFactoryProvider.getLogger(MenuScreen.class);

    // Opciones principales del menú lateral
    private final By loginItem  = AppiumBy.accessibilityId("Login Menu Item");
    private final By logoutItem = AppiumBy.accessibilityId("Logout Menu Item");

    public void goToLogin() {
        log.info("Menú: yendo a Login");
        WaitUtils.waitClickable(loginItem, 15).click();
    }

    public void logout() {
        log.info("Menú: seleccionando Logout");
        WaitUtils.waitClickable(logoutItem, 15).click();
    }
}
