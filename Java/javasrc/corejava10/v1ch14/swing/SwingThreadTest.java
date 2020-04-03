import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @version 1.0.0 2020-04-03 23:43
 * @author bruce
 */
public class SwingThreadTest {
    public static void main(String[] args) {
        JFrame frame = new SwingThreadFrame();
        frame.setTitle("SwingThreadTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
 * This frame has two buttons to fill a combo box
 * from a separate thread.
 * The `Good` button uses the event queue,
 * the `Bad` button modifies the combo box directly.
 */
class SwingThreadFrame extends JFrame {
    public SwingThreadFrame() {
        final JComboBox<Integer> combo = new JComboBox<>();
        combo.insertItemAt(Integer.MAX_VALUE, 0);
        combo.setPrototypeDisplayValue(combo.getItemAt(0));
        combo.setSelectedIndex(0);

        JPanel panel = new JPanel();

        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(event ->
            new Thread(new GoodWorkerRunnable(combo)).start());
        panel.add(goodButton);

        JButton badButton = new JButton("Bad");
        badButton.addActionListener(event ->
            new Thread(new BadWorkerRunnable(combo)).start());
        panel.add(badButton);

        panel.add(combo);
        add(panel);
        pack();
    }
}


class BadWorkerRunnable implements Runnable {

    private JComboBox<Integer> combo;
    private Random generator;

    public BadWorkerRunnable(JComboBox<Integer> aCombo) {
        combo = aCombo;
        generator = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int i = Math.abs(generator.nextInt());
                if (i % 2 == 0) {
                    combo.insertItemAt(i, 0);
                } else if (combo.getItemCount() > 0) {
                    combo.removeItemAt(i % combo.getItemCount());
                } else {

                }
                Thread.sleep(1);
            }
        } catch (InterruptedException ex) {

        }
    }
}


class GoodWorkerRunnable implements Runnable {

    private JComboBox<Integer> combo;
    private Random generator;

    public GoodWorkerRunnable(JComboBox<Integer> aCombo) {
        combo = aCombo;
        generator = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                EventQueue.invokeLater(() -> {
                    int i = Math.abs(generator.nextInt());
                    if (i % 2 == 0) {
                        combo.insertItemAt(i, 0);
                    } else if (combo.getItemCount() > 0) {
                        combo.removeItemAt(i % combo.getItemCount());
                    } else {

                    }
                });
                Thread.sleep(1);
            }
        } catch (InterruptedException ex) {

        }
    }
}