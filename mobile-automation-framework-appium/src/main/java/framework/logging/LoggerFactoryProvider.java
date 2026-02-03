package framework.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerFactoryProvider {

    // Punto central para obtener loggers del framework
    private LoggerFactoryProvider() {}

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
