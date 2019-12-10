import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPanel extends JPanel {

    private static JFrame f;

    public MyPanel() {
        JButton btn = new JButton("关闭窗口");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 我想关闭窗口, 应该怎么写? 不要用 System.exit(0); 或 System.exit(1);
                f.dispose();
            }
        });
        this.add(btn);
    }

    public static void main(String[] args) {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new MyPanel());
        f.setSize(400, 300);
        f.setVisible(true);
    }
}
