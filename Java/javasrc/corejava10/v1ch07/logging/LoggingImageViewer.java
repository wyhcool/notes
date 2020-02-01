import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 对图片查看的日志记录
 *
 * @version 1.0.0 2020-01-31
 * @author bruce
 */
public class LoggingImageViewer {

    private final static Logger myLogger = Logger.getLogger("com.mycompany.myapp");

    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null) {
            try {
                myLogger.setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
                myLogger.addHandler(handler);
            } catch (IOException ex) {
                myLogger.log(Level.SEVERE, "Can't create log file handler", ex);
            }
        }

        EventQueue.invokeLater(() -> {
            Handler windowHandler = new WindowHandler();
            windowHandler.setLevel(Level.ALL);
            myLogger.addHandler(windowHandler);

            JFrame frame = new ImageViewerFrame();
            frame.setTitle("LoggingImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            myLogger.fine("Showing frame");
            frame.setVisible(true);
        });
    }
}
