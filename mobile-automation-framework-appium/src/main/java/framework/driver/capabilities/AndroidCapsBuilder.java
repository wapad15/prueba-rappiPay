package framework.driver.capabilities;

import framework.config.ConfigManager;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.time.Duration;

public class AndroidCapsBuilder {

    // Construye las capabilities de Android a partir de la configuración externa
    public UiAutomator2Options build() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName(ConfigManager.get("deviceName"));
        options.setUdid(ConfigManager.get("udid"));

        String appPath = ConfigManager.get("app");
        String absoluteAppPath = new java.io.File(appPath).getAbsolutePath();
        options.setApp(absoluteAppPath);

        options.setAppPackage(ConfigManager.get("appPackage"));
        options.setAppActivity(ConfigManager.get("appActivity"));

        options.setAppWaitActivity(ConfigManager.get("appWaitActivity"));
        options.setAppWaitDuration(Duration.ofMillis(Long.parseLong(ConfigManager.get("appWaitDuration"))));

        options.setNoReset(Boolean.parseBoolean(ConfigManager.get("noReset")));
        options.setFullReset(Boolean.parseBoolean(ConfigManager.get("fullReset")));

        options.setNewCommandTimeout(Duration.ofSeconds(Long.parseLong(ConfigManager.get("newCommandTimeout"))));

        options.autoGrantPermissions();
        options.setDisableWindowAnimation(true);

        return options;
    }
}
