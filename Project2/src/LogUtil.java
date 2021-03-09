import java.util.logging.Logger;

/**
 * This is a logging class used for testing purposes.
 */
public interface LogUtil {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    void makeLog(int caseNum);
}
