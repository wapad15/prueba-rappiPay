package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    // Maneja la carga de archivos .properties según la plataforma seleccionada
    private static Properties props;

    private ConfigManager() {}

    public static void load(Platform platform) {
        String fileName = switch (platform) {
            case ANDROID -> "android.properties";
            case IOS -> "ios.properties";
        };

        props = new Properties();
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo de configuración: " + fileName);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración: " + fileName, e);
        }
    }

    public static String get(String key) {
        if (props == null) {
            throw new IllegalStateException("ConfigManager no ha cargado properties. Llama ConfigManager.load(...) primero.");
        }
        return props.getProperty(key);
    }
}
