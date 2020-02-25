import javax.swing.*;
import java.awt.*;

/**
 * 显示一行文本
 *
 * @version 1.0.0 2020-02-25
 * @author bruce
 */
public class NotHelloWorld {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new NotHelloWordFrame();
            frame.setTitle("NotHelloWorld");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * A frame that contains a message panel
 */
class NotHelloWordFrame extends JFrame {

    public NotHelloWordFrame() {
        add(new NotHelloWorldComponent());
        pack();
    }

}

/**
 * A component that displays a message
 */
class NotHelloWorldComponent extends JComponent {

    public static final int MESSAGE_X = 75;
    public static final int MESSAGE_Y = 100;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Not a hello world program", MESSAGE_X, MESSAGE_Y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}