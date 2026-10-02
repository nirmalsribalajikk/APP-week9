import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class EmployeeLoginView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JButton loginButton = new JButton("Login");

    public EmployeeLoginView() {
        setTitle("Employee Management Portal - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        add(createForm());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        addRow(panel, 0, "Username:", usernameField);
        addRow(panel, 1, "Password:", passwordField);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 2;
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonConstraints.insets = new Insets(10, 5, 5, 5);
        panel.add(loginButton, buttonConstraints);
        return panel;
    }

    private void addRow(JPanel panel, int row, String labelText, JTextField field) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.LINE_END;
        labelConstraints.insets = new Insets(5, 5, 5, 10);
        panel.add(new JLabel(labelText), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(field, fieldConstraints);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void clearPassword() {
        passwordField.setText("");
    }

    public void showMessage(String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, "Employee Portal", messageType);
    }
}
