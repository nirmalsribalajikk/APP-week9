import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class VehicleServiceView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JTextField registrationField = new JTextField(18);
    private final JRadioButton twoWheelerButton = new JRadioButton("Two Wheeler");
    private final JRadioButton carButton = new JRadioButton("Car");
    private final JCheckBox generalServiceBox = new JCheckBox("General Service (₹1,000)");
    private final JCheckBox oilChangeBox = new JCheckBox("Oil Change (₹800)");
    private final JCheckBox brakeServiceBox = new JCheckBox("Brake Service (₹1,200)");
    private final JCheckBox batteryCheckBox = new JCheckBox("Battery Check (₹500)");
    private final JButton calculateButton = new JButton("Calculate Cost");
    private final JLabel resultLabel = new JLabel("Enter vehicle details and select services.");

    public VehicleServiceView() {
        setTitle("Vehicle Service Cost Estimator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        ButtonGroup vehicleTypeGroup = new ButtonGroup();
        vehicleTypeGroup.add(twoWheelerButton);
        vehicleTypeGroup.add(carButton);

        add(resultLabel, BorderLayout.NORTH);
        add(createInputPanel(), BorderLayout.CENTER);
        add(calculateButton, BorderLayout.SOUTH);

        resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        addRow(panel, 0, "Registration Number:", registrationField);

        JPanel vehicleTypePanel = new JPanel(new GridLayout(1, 2));
        vehicleTypePanel.add(twoWheelerButton);
        vehicleTypePanel.add(carButton);
        addRow(panel, 1, "Vehicle Type:", vehicleTypePanel);

        JPanel servicesPanel = new JPanel(new GridLayout(0, 1));
        servicesPanel.add(generalServiceBox);
        servicesPanel.add(oilChangeBox);
        servicesPanel.add(brakeServiceBox);
        servicesPanel.add(batteryCheckBox);
        addRow(panel, 2, "Required Services:", servicesPanel);
        return panel;
    }

    private void addRow(JPanel panel, int row, String labelText, java.awt.Component component) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        labelConstraints.insets = new Insets(5, 5, 5, 10);
        panel.add(new JLabel(labelText), labelConstraints);

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.gridx = 1;
        componentConstraints.gridy = row;
        componentConstraints.anchor = GridBagConstraints.LINE_START;
        componentConstraints.fill = GridBagConstraints.HORIZONTAL;
        componentConstraints.weightx = 1;
        componentConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(component, componentConstraints);
    }

    public String getRegistrationNumber() {
        return registrationField.getText();
    }

    public String getVehicleType() {
        if (twoWheelerButton.isSelected()) {
            return "Two Wheeler";
        }
        if (carButton.isSelected()) {
            return "Car";
        }
        return "";
    }

    public JCheckBox getGeneralServiceBox() {
        return generalServiceBox;
    }

    public JCheckBox getOilChangeBox() {
        return oilChangeBox;
    }

    public JCheckBox getBrakeServiceBox() {
        return brakeServiceBox;
    }

    public JCheckBox getBatteryCheckBox() {
        return batteryCheckBox;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

    public void displayResult(VehicleServiceModel model) {
        resultLabel.setText(String.format(
                "<html><b>%s</b> (%s) &nbsp; Total Service Cost: <b>₹%,d</b></html>",
                model.getRegistrationNumber(), model.getVehicleType(), model.getTotalCost()));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
