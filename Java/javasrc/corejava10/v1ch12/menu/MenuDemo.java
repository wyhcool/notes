import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.chrono.JapaneseChronology;

/**
 * menu
 *
 * @version 1.0.0 2020-03-09 22:50
 * @author bruce
 */
public class MenuDemo {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new MenuFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Menu Test v1.0.0");
            frame.setVisible(true);
        });

    }

}

/**
 * A frame with a sample menu bar
 */
class MenuFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private Action saveAction;
    private Action saveAsAction;
    private JCheckBoxMenuItem readonlyItem;
    private JPopupMenu popup;
    private JPanel infoPanel;
    private JLabel infoLabel;

    /**
     * A sample action that prints the action name to display
     */
    class TestAction extends AbstractAction {

        public TestAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent event) {
            infoLabel.setText(getValue(Action.NAME) + " selected.");
        }
    }

    public MenuFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // menu file
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new TestAction("New"));

        // demonstrate accelerators
        JMenuItem openItem = fileMenu.add(new TestAction("Open"));
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

        fileMenu.addSeparator();

        saveAction = new TestAction("Save");
        JMenuItem saveItem = fileMenu.add(saveAction);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        saveAsAction = new TestAction("Save As");
        fileMenu.add(saveAsAction);

        fileMenu.addSeparator();

        fileMenu.add(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // demonstrate checkbox and radio button menus
        readonlyItem = new JCheckBoxMenuItem("Read-only");
        readonlyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean saveOk = !readonlyItem.isSelected();
                saveAction.setEnabled(saveOk);
                saveAsAction.setEnabled(saveOk);
            }
        });

        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem insertItem = new JRadioButtonMenuItem("Insert");
        insertItem.setSelected(true);
        JRadioButtonMenuItem overtypeItem = new JRadioButtonMenuItem("Overtype");

        group.add(insertItem);
        group.add(overtypeItem);

        // demonstrate icons
        Action cutAction = new TestAction("Cut");
        Action copyAction = new TestAction("Copy");
        Action pasteAction = new TestAction("Paste");

        // menu edit
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);


        // demonstrate nested menus
        JMenu optionMenu = new JMenu("Options");

        optionMenu.add(readonlyItem);
        optionMenu.addSeparator();
        optionMenu.add(insertItem);
        optionMenu.add(overtypeItem);

        editMenu.addSeparator();
        editMenu.add(optionMenu);

        // menu help
        // demonstrate mnemonics
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        JMenuItem indexItem = new JMenuItem("Index");
        indexItem.setMnemonic('I');
        helpMenu.add(indexItem);

        // also add mnemonic key to an action
        Action aboutAction = new TestAction("About");
        aboutAction.putValue(Action.MNEMONIC_KEY, new Integer('A'));
        helpMenu.add(aboutAction);


        // demonstrate pop-ups
        popup = new JPopupMenu();
        popup.add(cutAction);
        popup.add(copyAction);
        popup.add(pasteAction);

        JPanel panel = new JPanel();
        panel.setComponentPopupMenu(popup);
        add(panel, BorderLayout.CENTER);

        // add all top-level menus to menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        infoPanel = new JPanel();
        add(infoPanel, BorderLayout.SOUTH);
        infoLabel = new JLabel();
        infoPanel.add(infoLabel);


    }

}
