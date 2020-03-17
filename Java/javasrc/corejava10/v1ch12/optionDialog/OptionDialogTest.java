import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Date;

/**
 * 对话框
 *
 * @version 1.0.0 2020-03-17 20:12
 * @author bruce
 */
public class OptionDialogTest {

    public static void main(String[] args) {

        EventQueue.invokeLater(()-> {
            JFrame frame = new OptionDialogFrame();
            frame.setTitle("CircleLayoutTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * A frame that contains settings for selecting various option dialogs.
 */
class OptionDialogFrame extends JFrame {

    private ButtonPanel typePanel;
    private ButtonPanel messagePanel;
    private ButtonPanel messageTypePanel;
    private ButtonPanel optionTypePanel;
    private ButtonPanel optionsPanel;
    private ButtonPanel inputPanel;
    private String messageString = "Message";
    private Icon messageIcon = new ImageIcon(ClassLoader.getSystemResource("ice-cream.png"));
    private Object messageObject = new Date();
    private Component messageComponent = new SampleComponent();


    public OptionDialogFrame() {

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 3));

        typePanel = new ButtonPanel("Type", "Message", "Confirm", "Option", "Input");
        messageTypePanel = new ButtonPanel("Message Type", "ERROR_MESSAGE", "INFORMATION_MESSAGE", "WARNING_MESSAGE", "QUESTION_MESSAGE", "PLAIN_MESSAGE");
        messagePanel = new ButtonPanel("Message", "String", "Icon", "Component", "Other", "Object[]");
        optionTypePanel = new ButtonPanel("Confirm", "DEFAULT_OPTION", "YES_NO_OPTION", "YES_NO_CANCEL_OPTION", "OK_CANCEL_OPTION");
        optionsPanel = new ButtonPanel("Option", "String[]", "Icon[]", "Object[]");
        inputPanel = new ButtonPanel("Input", "Text field", "Combo box");

        gridPanel.add(typePanel);
        gridPanel.add(messageTypePanel);
        gridPanel.add(messagePanel);
        gridPanel.add(optionTypePanel);
        gridPanel.add(optionsPanel);
        gridPanel.add(inputPanel);

        // add a panel with a show button
        JPanel showPanel = new JPanel();
        JButton showButton = new JButton("Show");
        showButton.addActionListener(new ShowAction());
        showPanel.add(showButton);

        add(gridPanel, BorderLayout.CENTER);
        add(showPanel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Gets the currently selected message
     * @return a string, icon, component, or object array, depending on the Message panel selection
     */
    public Object getMessage() {
        String s = messagePanel.getSelection();
        if (s.equals("String")) {
            return messageString;
        } else if (s.equals("Icon")) {
            return messageIcon;
        } else if (s.equals("Component")) {
            return messageComponent;
        } else if (s.equals("Object[]")) {
            return new Object[] { messageString, messageIcon, messageComponent, messageObject};
        } else if (s.equals("Other")) {
            return messageObject;
        } else {
            return null;
        }
    }


    public Object[] getOptions() {
        String s = optionsPanel.getSelection();
        if (s.equals("String[]")) {
            return new String[] { "Yellow", "Blue", "Red" };
        } else if (s.equals("Icon[]")) {
            return new Icon[] { new ImageIcon(ClassLoader.getSystemResource("bread.png")), new ImageIcon(ClassLoader.getSystemResource("cake.png")), new ImageIcon(ClassLoader.getSystemResource("pizza.png")) };
        } else if (s.equals("Object[]")) {
            return new Object[] { messageString, messageIcon, messageComponent, messageObject };
        } else {
            return null;
        }
    }

    /**
     * Gets the selected message or option type
     * @param panel the Message Type or Confirm panel
     * @return the selected XXX_MESSAGE or XXX_OPTION constant from JOptionPane class
     */
    public int getType(ButtonPanel panel) {
        String s = panel.getSelection();
        try {
            return JOptionPane.class.getField(s).getInt(null);
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * The action listener for the Show button shows a
     * Confirm, Input, Message, or Option Dialog
     * depending on the Type panel selection.
     */
    private class ShowAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (typePanel.getSelection().equals("Confirm")) {
                JOptionPane.showConfirmDialog(OptionDialogFrame.this, getMessage(), "Title", getType(optionsPanel), getType(messagePanel));
            } else if (typePanel.getSelection().equals("Input")) {
                if (inputPanel.getSelection().equals("Text field")) {
                    JOptionPane.showInputDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messagePanel));
                } else {
                    JOptionPane.showInputDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messagePanel), null, new String[] { "Yellow", "Blue", "Red" }, "Blue");
                }
            } else if (typePanel.getSelection().equals("Message")) {
                JOptionPane.showMessageDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messagePanel));
            } else if (typePanel.getSelection().equals("Option")) {
                JOptionPane.showOptionDialog(OptionDialogFrame.this, getMessage(), "Title", getType(optionTypePanel), getType(messagePanel), null, getOptions(), getOptions()[0]);
            }
        }
    }
}

/**
 * A component with a painted surface
 */
class SampleComponent extends JComponent {

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1);
        g2.setPaint(Color.YELLOW);
        g2.fill(rect);
        g2.setPaint(Color.BLUE);
        g2.draw(rect);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }
}

/**
 * A panel with radio buttons inside a titled border.
 */
class ButtonPanel extends JPanel {

    private ButtonGroup group;

    /**
     * Constructs a button panel
     * @param title the title shown in the border
     * @param options an array of radio button labels
     */
    public ButtonPanel(String title, String ...options) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        group = new ButtonGroup();

        // make one radio button for each option
        for (String option : options) {
            JRadioButton b = new JRadioButton(option);
            b.setActionCommand(option);
            add(b);
            group.add(b);
            b.setSelected(option == options[0]);
        }
    }

    /**
     * Gets the currently selected option.
     *
     * @return the label of the currently selected radio button
     */
    public String getSelection() {
        return group.getSelection().getActionCommand();
    }

}