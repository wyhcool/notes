import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * toolbar
 *
 * @version 1.0.0 2020-03-11 22:23
 * @author bruce
 */
public class ToolBarDemo {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new ToolBarFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("ToolBarTest");
            frame.setVisible(true);
        });

    }

}

/**
 * A frame with a toolbar and menu for color changes.
 */
class ToolBarFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JPanel panel;

    public ToolBarFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add a panel for color change
        panel = new JPanel();
        add(panel, BorderLayout.CENTER);

        // set up actions
        Action blueAction = new ColorAction("Blue", new ImageIcon(ClassLoader.getSystemResource("3d.gif")), Color.BLUE);
        Action greenAction = new ColorAction("Green", new ImageIcon(ClassLoader.getSystemResource("changelog.png")), Color.GREEN);
        Action redAction = new ColorAction("Red", new ImageIcon(ClassLoader.getSystemResource("folder-audio.png")), Color.RED);

        Action exitAction = new AbstractAction("Exit", new ImageIcon(ClassLoader.getSystemResource("car.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");

        // populate 填充 toolbar
        JToolBar bar = new JToolBar();
        bar.add(blueAction);
        bar.add(greenAction);
        bar.add(redAction);
        bar.addSeparator();
        bar.add(exitAction);
        add(bar, BorderLayout.NORTH);

        // populate menu
        JMenu menu = new JMenu("Color");
        menu.add(blueAction);
        menu.add(greenAction);
        menu.add(redAction);
        menu.add(exitAction);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

    }

    /**
     * The color action sets the background of the frame
     * to a given color.
     */
    class ColorAction extends AbstractAction {

        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, name + " background");
            putValue("Color", c);
        }

        public void actionPerformed(ActionEvent event) {
            Color c = (Color) getValue("Color");
            panel.setBackground(c);
        }
    }
}
