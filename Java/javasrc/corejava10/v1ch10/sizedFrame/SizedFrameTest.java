import javax.swing.*;
import java.awt.*;

/**
 * sized frame
 *
 * @version 1.0.0 2020-02-25
 * @author bruce
 */
public class SizedFrameTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SizedFrame frame = new SizedFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("SizedFrame");
            frame.setVisible(true);
        });
    }

}

class SizedFrame extends JFrame {

    public SizedFrame() {

        // get screen dimensions
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // set frame width, height and let platform pick screen location
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationByPlatform(true);

        // set frame icon
        Image img = new ImageIcon("icon.gif").getImage();
        setIconImage(img);
    }
}
