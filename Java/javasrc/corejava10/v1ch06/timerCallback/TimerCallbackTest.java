import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


/**
 * 回调
 *
 * @version 1.0.0 2020-01-20
 * @author bruce
 */
public class TimerCallbackTest {

    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        Timer t = new Timer(5000, listener);
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program");
        System.exit(0);
    }
}


class TimePrinter implements ActionListener {

    public void actionPerformed(ActionEvent ev) {
        System.out.println("At the tone, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}


