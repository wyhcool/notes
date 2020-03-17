import javax.swing.*;
import java.awt.*;

/**
 * dialog
 *
 * @version 1.0.0 2020-03-18 07:46
 * @author bruce
 */
public class DialogTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new DialogFrame();
            frame.setTitle("DialogTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * A frame with a menu whose File->About action shows a dialog.
 */
class DialogFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private AboutDialog dialog;

    public DialogFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // constructs a file menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // add about and exit menu items

        // the about menu shows the about dialog
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(event -> {
            if (dialog == null) { // first time
                dialog = new AboutDialog(DialogFrame.this);
            }
            dialog.setVisible(true);
        });
        fileMenu.add(aboutItem);

        // the exit menu exits the program
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> {
            System.exit(0);
        });
        fileMenu.add(exitItem);
    }
}

/**
 * A sample modal dialog that displays a message and waits for the user to click the OK button.
 */
class AboutDialog extends JDialog {

    public AboutDialog(JFrame owner) {
        super(owner, "About DialogTest", true);

        // add HTML label to center
        add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann</hr></html>"), BorderLayout.CENTER);

        // OK button closes the dialog
        JButton ok = new JButton("OK");
        ok.addActionListener(event -> setVisible(false));

        // add OK button to southern border
        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        pack();
    }
}