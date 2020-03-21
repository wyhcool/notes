import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * color chooser
 *
 * @version 1.0.0 2020-03-21 12:17
 * @author bruce
 */
public class ColorChooserTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ColorChooserFrame();
            frame.setTitle("ColorChooserTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class ColorChooserFrame extends JFrame {

    public ColorChooserFrame() {
        add(new ColorChooserPanel());
        pack();
    }

}

/**
 * A panel with buttons to pop up three types of color choosers.
 */
class ColorChooserPanel extends JPanel {

    public ColorChooserPanel() {

        JButton modalButton = new JButton("Modal");
        modalButton.addActionListener(new ModalListener());
        add(modalButton);

        JButton modalessButton = new JButton("Modaless");
        modalessButton.addActionListener(new ModalessListener());
        add(modalessButton);

        JButton immediateButton = new JButton("Immediate");
        immediateButton.addActionListener(new ImmediateListener());
        add(immediateButton);
    }

    private class ModalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color defaultColor = getBackground();
            Color selected = JColorChooser.showDialog(ColorChooserPanel.this, "Set Background", defaultColor);
            if (selected != null) {
                setBackground(selected);
            }
        }
    }

    private class ModalessListener implements ActionListener {

        private JDialog dialog;
        private JColorChooser chooser;

        public ModalessListener() {
            chooser = new JColorChooser();
            dialog = JColorChooser.createDialog(
                    ColorChooserPanel.this,
                    "Background Color",
                    false,
                    chooser,
                    event -> setBackground(chooser.getColor()),
                    null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }

    private class ImmediateListener implements ActionListener {

        private JDialog dialog;
        private JColorChooser chooser;

        public ImmediateListener() {
            chooser = new JColorChooser();
            chooser.getSelectionModel().addChangeListener(event -> {
                setBackground(chooser.getColor());
            });
            dialog = new JDialog((Frame) null,false);
            dialog.add(chooser);
            dialog.pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }
}
