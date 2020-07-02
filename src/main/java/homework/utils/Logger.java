package homework.utils;

import org.apache.logging.log4j.LogManager;


public class Logger {
    private final static org.apache.logging.log4j.Logger log = LogManager.getLogger("java3");
    private Class clazz;

    public Logger(Class clazz) {
        this.clazz = clazz;
    }

    public void appInfo(String method, String msg) {
        log.info(clazz + " | " + method + " | " + msg);
    }

    public void appError(String method, String msg) {
        log.error(clazz + " | " + method + " | " + msg);
    }

    public void appWarning(String method, String msg) {
        log.warn(clazz + " | " + method + " | " + msg);
    }
}
