import javax.swing.*;
import java.awt.*;

/**
 * combo box
 *
 * @version 1.0.0 2020-03-09 14:08
 * @author bruce
 */
public class ComboBoxDemo {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new ComboBoxFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CheckBox Frame v1.0.0");
            frame.setVisible(true);
        });

    }

}

class ComboBoxFrame extends JFrame {

    private JComboBox<String> faceCombo;
    private JLabel label;
    private static final int DEFAULT_SIZE = 24;

    public ComboBoxFrame() {

        // add the sample text label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        add(label, BorderLayout.CENTER);

        // make a combo box and add face names
        faceCombo = new JComboBox<>();
        faceCombo.addItem("Serif");
        faceCombo.addItem("SansSerif");
        faceCombo.addItem("Monospaced");
        faceCombo.addItem("Dialog");
        faceCombo.addItem("DialogInput");

        // the combo box listener changes the label font to
        // the selected face name
        faceCombo.addActionListener(event -> {
            label.setFont(
                    new Font(faceCombo.getItemAt(faceCombo.getSelectedIndex()),
                            Font.PLAIN, DEFAULT_SIZE));
        });

        // add combo box to a panel at the frame's southern border
        JPanel panel = new JPanel();
        panel.add(faceCombo);
        add(panel, BorderLayout.SOUTH);
        pack();
    }
}
