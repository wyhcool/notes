import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * data exchange
 *
 * @version 1.0.0 2020-03-18 07:36
 * @author bruce
 */
public class DataExchangeTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new DataExchangeFrame();
            frame.setTitle("DataExchangeTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * A frame with a menu whose File->Connect action shows a password dialog.
 */
class DataExchangeFrame extends JFrame {

    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 40;
    private PasswordChooser dialog = null;
    private JTextArea textArea;

    public DataExchangeFrame() {

        // construct a File menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // add Connect and Exit items
        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.addActionListener(new ConnectAction());
        fileMenu.add(connectItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
    }

    /**
     * The Connect action pops up the password dialog
     */
    private class ConnectAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // if first time construct
            if (dialog == null) dialog = new PasswordChooser();

            // set default values
            dialog.setUser(new User("yourname", null));

            // pop up dialog
            if (dialog.showDialog(DataExchangeFrame.this, "Connect")) {
                // if accepted, retrieve user input
                User u = dialog.getUser();
                textArea.append("user name = " + u.getName() + ", password = " + (new String(u.getPassword())) + "\n");
            }

        }
    }

}

class User {

    private String name;

    private char[] password;

    public User(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}

/**
 * A password chooser that is shown extends JPanel
 */
class PasswordChooser extends JPanel {

    private JTextField username;
    private JPasswordField password;
    private JButton okButton;
    private boolean ok;
    private JDialog dialog;

    public PasswordChooser() {

        setLayout(new BorderLayout());

        // construct a panel with user name and password fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("User name:"));
        panel.add(username = new JTextField(""));
        panel.add(new JLabel("Password:"));
        panel.add(password = new JPasswordField(""));
        add(panel, BorderLayout.CENTER);

        // create Ok and Cancel buttons that teminate the dialog
        okButton = new JButton("Ok");
        okButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        // add buttons to the southern border
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setUser(User u) {
        username.setText(u.getName());
    }

    public User getUser() {
        return new User(username.getText(), password.getPassword());
    }

    public boolean showDialog(Component parent, String title) {
        ok = false;

        // locate the owner frame
        Frame owner = null;
        if (parent instanceof Frame) {
            owner = (Frame) parent;
        } else {
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
        }

        // if first time, or if owner has changed, make new dialog
        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
        }

        // set title and show dialog
        dialog.setTitle(title);
        // 注意：为 true 时会一直阻塞
        dialog.setVisible(true);
        return ok;
    }
}