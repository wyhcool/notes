import com.sun.codemodel.internal.JOp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 匿名内部类
 *
 * @version 1.0.0 2020-01-27
 * @author bruce
 */
public class AnnoymousInnerClassTest {

    public static void main(String[] args) {

        TalkingClock talkingClock = new TalkingClock();
        talkingClock.start(1000, true);

        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}

class TalkingClock {

    public void start(int interval, boolean beep) {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone, the time is " + new Date());
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        };

        Timer t = new Timer(interval, listener);
        t.start();
    }
}