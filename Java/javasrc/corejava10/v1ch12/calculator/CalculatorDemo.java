import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * calculator
 *
 * @version 1.0.0 2020-03-05 23:00
 * @author bruce
 */
public class CalculatorDemo {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new CalculatorFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Calculator v1.0.0");
            frame.setVisible(true);
        });

    }
}

/**
 * A frame with a calculator panel display
 */
class CalculatorFrame extends JFrame {

    public CalculatorFrame() {
        add(new CalculatorPanel());
        pack();
    }
}

/**
 * A panel with calculator buttons and a result display
 */
class CalculatorPanel extends JPanel {

    private JButton display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;

    public CalculatorPanel() {

        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start = true;

        // add the display
        display = new JButton("0");
        display.setEnabled(false);
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        // add the buttons in a 4 x 4 grid
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        addButton("7" ,insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4" ,insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1" ,insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0" ,insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Adds a button to the center panel.
     *
     * @param label the button label
     * @param listener the button listener
     */
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * The action inserts the button action string to the end of the display text
     */
    private class InsertAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }

    }

    /**
     * The action executes the command that the button action string denotes
     */
    private class CommandAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                }
                else lastCommand = command;
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }

    }

    public void calculate(double x) {
        if (lastCommand.equals("+")) result += x;
        else if (lastCommand.equals("-")) result -= x;
        else if (lastCommand.equals("*")) result *= x;
        else if (lastCommand.equals("/")) result /= x;
        else if (lastCommand.equals("=")) result = x;
        display.setText("" + result);
    }
}

