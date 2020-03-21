import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;

/**
 * file chooser
 *
 * @version 1.0.0 2020-03-21 10:14
 * @author bruce
 */
public class FileChooserTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ImageViewerFrame();
            frame.setTitle("FileChooserTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * A frame that has a menu for loading an image and display area for
 * loaded image
 */
class ImageViewerFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
    private JLabel label;
    private JFileChooser chooser;

    public ImageViewerFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // set up menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event -> {
            chooser.setCurrentDirectory(new File("."));
            // show file chooser dialog
            int result = chooser.showOpenDialog(ImageViewerFrame.this);

            // if image file accepted, set it as icon of the label
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(name));
                pack();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));

        // use a label to display the images
        label = new JLabel();
        add(label);

        // set up file chooser
        chooser = new JFileChooser();

        // accept all image files ending with .jpg, .jpeg, .gif, .png
        FileFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "gif", "png");
        chooser.setFileFilter(filter);

        chooser.setAccessory(new ImagePreviewer(chooser));

        chooser.setFileView(new FileIconView(filter, new ImageIcon(ClassLoader.getSystemResource("valentine.png"))));
    }

}

/**
 * A file chooser accessory that previews images
 */
class ImagePreviewer extends JLabel {

    public ImagePreviewer(JFileChooser chooser) {

        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());

        chooser.addPropertyChangeListener(event -> {
            if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(event.getPropertyName())) {
                // the use has selected a new file
                File f = (File) event.getNewValue();
                if (f == null) {
                    setIcon(null);
                    return;
                }

                // read the image into an icon
                ImageIcon icon = new ImageIcon(f.getPath());

                // if the icon is too large to fit, scale it
                if (icon.getIconWidth() > getWidth()) {
                    icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
                }
                setIcon(icon);
            }
        });
    }
}

/**
 * A file view that displays an icon for all files that match a file filter.
 */
class FileIconView extends FileView {

    private FileFilter filter;
    private Icon icon;

    public FileIconView(FileFilter aFilter, Icon anIcon) {
        filter = aFilter;
        icon = anIcon;
    }

    public Icon getIcon(File f) {
        if (!f.isDirectory() && filter.accept(f)) {
            return icon;
        } else {
            return null;
        }
    }

}