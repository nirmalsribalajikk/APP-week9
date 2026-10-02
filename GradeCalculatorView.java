import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class GradeCalculatorView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JTextField nameField = new JTextField(18);
    private final JTextField subject1Field = new JTextField(18);
    private final JTextField subject2Field = new JTextField(18);
    private final JTextField subject3Field = new JTextField(18);
    private final JButton calculateButton = new JButton("Calculate Result");
    private final JLabel resultLabel = new JLabel("Enter student details and click Calculate Result.");

    public GradeCalculatorView() {
        setTitle("Student Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createInputPanel(), BorderLayout.CENTER);
        add(calculateButton, BorderLayout.SOUTH);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(resultLabel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        addRow(panel, 0, "Student Name:", nameField);
        addRow(panel, 1, "Subject 1 Marks:", subject1Field);
        addRow(panel, 2, "Subject 2 Marks:", subject2Field);
        addRow(panel, 3, "Subject 3 Marks:", subject3Field);
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
        fieldConstraints.weightx = 1;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(field, fieldConstraints);
    }

    public String getStudentName() {
        return nameField.getText();
    }

    public String getSubject1Marks() {
        return subject1Field.getText();
    }

    public String getSubject2Marks() {
        return subject2Field.getText();
    }

    public String getSubject3Marks() {
        return subject3Field.getText();
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

    public void displayResult(Student student) {
        resultLabel.setText(String.format(
                "<html><b>%s</b> &nbsp; Total: %.2f &nbsp; Average: %.2f &nbsp; Grade: <b>%c</b></html>",
                student.getName(), student.getTotal(), student.getAverage(), student.getGrade()));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
