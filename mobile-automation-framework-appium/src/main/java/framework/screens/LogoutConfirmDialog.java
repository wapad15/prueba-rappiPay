package framework.screens;

import framework.logging.LoggerFactoryProvider;
import framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class LogoutConfirmDialog extends BaseScreen {

    private static final Logger log = LoggerFactoryProvider.getLogger(LogoutConfirmDialog.class);

    // Botón del diálogo que confirma el cierre de sesión
    private final By confirmButton = AppiumBy.id("android:id/button1");

    public void confirm() {
        log.info("Logout dialog: confirmando logout (OK)");
        WaitUtils.waitClickable(confirmButton, 15).click();
    }
}
