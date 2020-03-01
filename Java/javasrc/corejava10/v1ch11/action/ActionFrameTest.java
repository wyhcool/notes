import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.KeyStore;

/**
 * 按钮与按键映射到动作对象上
 *
 * @version 1.0.0 2020-03-01 22:20
 * @author bruce
 */
public class ActionFrameTest {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new ActionFrame();
            frame.setTitle("Action Frame Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }

}

/**
 * A frame with a panel that demonstrates color change actions
 */
class ActionFrame extends JFrame {

    private JPanel buttonPanel;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ActionFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        buttonPanel = new JPanel();

        // define actions
        Action greenAction = new ColorAction("green", "green.png", Color.GREEN);
        Action orangeAction = new ColorAction("orange", "orange.png", Color.ORANGE);
        Action purpleAction = new ColorAction("purple", "purple.png", new Color(255,0,255));

        // add buttons for these actions
        buttonPanel.add(new JButton(greenAction));
        buttonPanel.add(new JButton(orangeAction));
        buttonPanel.add(new JButton(purpleAction));

        // add panel to frame
        add(buttonPanel);

        // associate the G O P keys with names
        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl G"), "panel.green");
        imap.put(KeyStroke.getKeyStroke("ctrl O"), "panel.orange");
        imap.put(KeyStroke.getKeyStroke("ctrl P"), "panel.purple");

        // associate the names with actions
        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.green", greenAction);
        amap.put("panel.orange", orangeAction);
        amap.put("panel.purple", purpleAction);

    }

    public class ColorAction extends AbstractAction {
        /**
         * Constructs a color action.
         * @param name the name to show on the button
         * @param iconPath the icon path to display on the button
         * @param c the background color
         */
        public ColorAction(String name, String iconPath, Color c) {
            putValue(Action.NAME, name);
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
            icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
            putValue("color", c);
        }

        public void actionPerformed(ActionEvent event) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }

    }
}
