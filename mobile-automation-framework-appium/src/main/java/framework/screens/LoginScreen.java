package framework.screens;

import framework.logging.LoggerFactoryProvider;
import framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class LoginScreen extends BaseScreen {

    private static final Logger log = LoggerFactoryProvider.getLogger(LoginScreen.class);

    // Elementos del formulario de login
    private final By usernameInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    private final By passwordInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private final By loginButton   = AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn");

    public void login(String username, String password) {
        log.info("Login: ingresando username");
        WaitUtils.waitVisible(usernameInput, 15).sendKeys(username);

        log.info("Login: ingresando password");
        WaitUtils.waitVisible(passwordInput, 15).sendKeys(password);

        log.info("Login: click en botón Login");
        WaitUtils.waitClickable(loginButton, 15).click();
    }
}
