import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 *
 * @version 1.0.0 2020-03-02 21:02
 * @author bruce
 */
public class MouseEventTest {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new MouseFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Mouse Test Demo");
            frame.setVisible(true);
        });

    }
}

/**
 * A frame containing a panel for testing mouse operations
 */
class MouseFrame extends JFrame {

    public MouseFrame() {
        add(new MouseComponent());
        pack();
    }

}

/**
 * A component with mouse operations for adding and moving squares
 */
class MouseComponent extends JComponent {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private static final int SIDE_LENGTH = 10;
    private ArrayList<Rectangle2D> squares;
    private Rectangle2D current; // the square containing the mouse cursor

    public MouseComponent() {
        squares = new ArrayList<>();
        current = null;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // draw all squares
        for (Rectangle2D r : squares) {
            g2.draw(r);
        }
    }

    /**
     * Finds the first square containing a point
     *
     * @param p a point
     * @return the first square that contains p
     */
    public Rectangle2D find(Point2D p) {
        for (Rectangle2D r : squares) {
            if (r.contains(p)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Adds a square to the collection
     *
     * @param p the center of the square
     */
    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        current = new Rectangle2D.Double(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
        squares.add(current);
        repaint();
    }

    /**
     * Removes a square from the collection
     *
     * @param s the square to remove
     */
    public void remove(Rectangle2D s) {
        if (s == null) return;
        if (s == current) current = null;
        squares.remove(s);
        repaint();
    }

    /**
     * 点击事件
     */
    private class MouseHandler extends MouseAdapter {

        /**
         * 鼠标按下
         * @param event
         */
        @Override
        public void mousePressed(MouseEvent event) {
            // add a new square if the cusor isn't inside a square
            current = find(event.getPoint());
            if (current == null) add(event.getPoint());
        }

        /**
         * 鼠标点击
         * @param event
         */
        public void mouseClicked(MouseEvent event) {
            // remove the current square if double clicked
            current = find(event.getPoint());
            if (current != null && event.getClickCount() >= 2) remove(current);
        }
    }

    /**
     * 移动事件
     */
    private class MouseMotionHandler implements MouseMotionListener {

        /**
         * 鼠标移动
         * @param event
         */
        @Override
        public void mouseMoved(MouseEvent event) {
            // set the mouse cursor to cross if it is inside a rectangle
            if (find(event.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
            else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        /**
         * 鼠标拖动
         * @param event
         */
        @Override
        public void mouseDragged(MouseEvent event) {
            if (current != null) {
                int x = event.getX();
                int y = event.getY();

                // drag the current rectangle to center it at (x, y)
                current.setFrame(x - SIDE_LENGTH / 2, y - SIDE_LENGTH - 2, SIDE_LENGTH, SIDE_LENGTH);
                repaint();
            }
        }
    }

}
