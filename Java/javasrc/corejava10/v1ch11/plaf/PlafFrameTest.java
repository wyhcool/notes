import javax.swing.*;
import java.awt.*;

/**
 * 观感
 *
 * @version 1.0.0 2020-02-28 22:06
 * @author bruce
 */
public class PlafFrameTest {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new PlafFrame();
            frame.setTitle("PlafFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }
}

/**
 * A frame with a button panel for changing look-and-feel
 */
class PlafFrame extends JFrame {

    private JPanel buttonPanel;

    public PlafFrame() {

        buttonPanel = new JPanel();

        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos) {
            makeButton(info.getName(), info.getClassName());
        }

        add(buttonPanel);
        pack();
    }

    /**
     * Makes a button to change the pluggable look-and-feel
     * @param name the button name
     * @param className the name of the look-and-feel class
     */
    private void makeButton(String name, String className) {
        // add button to panel
        JButton button = new JButton(name);
        buttonPanel.add(button);

        // set button action
        button.addActionListener(event -> {
            // button action: switch to the new look-and-feel
            try {
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
                pack();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
