import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * checkbox
 *
 * @version 1.0.0 2020-03-09 11:46
 * @author bruce
 */
public class CheckBoxDemo {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new CheckBoxFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CheckBox Frame v1.0.0");
            frame.setVisible(true);
        });

    }

}

/**
 * A frame with a sample text label and check boxes
 * for selecting font attributes
 */
class CheckBoxFrame extends JFrame {

    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;
    private static final int FONTSIZE = 24;

    public CheckBoxFrame() {

        // add the sample text label
        // 中译为“敏捷的棕毛狐狸从懒狗身上跃过”是一个著名的英语全字母句，常被用于测试字体的显示效果和键盘有没有故障。
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        add(label, BorderLayout.CENTER);

        // this listener sets the font attributes of
        // the label to the check box state
        ActionListener listener = event -> {
            int mode = 0;
            if (bold.isSelected()) mode += Font.BOLD;
            if (italic.isSelected()) mode += Font.ITALIC;
            label.setFont(new Font("Serif", mode, FONTSIZE));
        };

        // add the check boxes
        JPanel buttonPanel = new JPanel();

        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        bold.setSelected(true);
        buttonPanel.add(bold);

        italic = new JCheckBox("Italic");
        italic.addActionListener(listener);
        buttonPanel.add(italic);

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

}
