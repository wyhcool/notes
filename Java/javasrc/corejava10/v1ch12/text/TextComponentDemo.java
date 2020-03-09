import com.sun.org.apache.bcel.internal.generic.SWITCH;

import javax.swing.*;
import java.awt.*;

/**
 * text
 *
 * @version 1.0.0 2020-03-09 10:55
 * @author bruce
 */
public class TextComponentDemo {
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new TextComponentFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Text Component Frame v1.0.0");
            frame.setVisible(true);
        });

    }
}

/**
 * A frame with sample text components
 */
class TextComponentFrame extends JFrame {

    private static final int TEXTAREA_ROWS = 8;
    private static final int TEXTAREA_COLUMNS = 20;

    public TextComponentFrame() {

        JTextField textField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(new JLabel("User name: ", SwingConstants.RIGHT));
        northPanel.add(textField);
        northPanel.add(new JLabel("Password: ", SwingConstants.RIGHT));
        northPanel.add(passwordField);

        add(northPanel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        // add button to append text into the text area
        JPanel southPanel = new JPanel();

        JButton insertButton = new JButton("Insert");

        southPanel.add(insertButton);

        insertButton.addActionListener(event -> {
            textArea.append("User name: " + textField.getText() + " Password: " + new String(passwordField.getPassword()) + "\n");
        });

        add(southPanel, BorderLayout.SOUTH);
        pack();
    }
}

