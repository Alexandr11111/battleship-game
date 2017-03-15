package battleship;

import org.apache.log4j.*;

/**
 * Logging entity.
 */
public class Log {
    private static final Logger log = LogManager.getLogger("SERVICE");

    public static void info(String msg) {
        log.info(msg);
    }

    public static void info(Object msg, Throwable e) {
        log.info(msg, e);
    }

    public static void debug(String msg) {
        log.debug(msg);
    }

    public static void debug(Object msg, Throwable e) {
        log.debug(msg, e);
    }

    public static void warn(String msg) {
        log.warn(msg);
    }

    public static void warn(Object msg, Throwable e) {
        log.warn(msg, e);
    }

    public static void error(String msg) {
        log.error(msg);
    }

    public static void error(Object msg, Throwable e) {
        log.error(msg, e);
    }

    public static Logger fromName(String name) {
        return LogManager.getLogger(name);
    }

    public static void setLoggerConfig() {

        // Console.
        ConsoleAppender console = new ConsoleAppender(); //create appender
        String PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.DEBUG);
        console.activateOptions();

        Logger.getRootLogger().addAppender(console);

        // File.
        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile("C:\\\\temp\\\\log4j-batlleship.log");
        fa.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n"));
        fa.setThreshold(Level.DEBUG);
        fa.setAppend(true);
        fa.activateOptions();

        Logger.getRootLogger().addAppender(fa);
    }
}
