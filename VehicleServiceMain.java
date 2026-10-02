import javax.swing.SwingUtilities;

public class VehicleServiceMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleServiceModel model = new VehicleServiceModel();
            VehicleServiceView view = new VehicleServiceView();
            new VehicleServiceController(model, view);
            view.setVisible(true);
        });
    }
}
