import java.util.ArrayList;
import java.util.List;

public class VehicleServiceController {
    private final VehicleServiceModel model;
    private final VehicleServiceView view;

    public VehicleServiceController(VehicleServiceModel model, VehicleServiceView view) {
        this.model = model;
        this.view = view;
        view.getCalculateButton().addActionListener(event -> calculateCost());
    }

    private void calculateCost() {
        List<String> selectedServices = new ArrayList<>();
        if (view.getGeneralServiceBox().isSelected()) {
            selectedServices.add("General Service");
        }
        if (view.getOilChangeBox().isSelected()) {
            selectedServices.add("Oil Change");
        }
        if (view.getBrakeServiceBox().isSelected()) {
            selectedServices.add("Brake Service");
        }
        if (view.getBatteryCheckBox().isSelected()) {
            selectedServices.add("Battery Check");
        }

        try {
            model.calculateCost(
                    view.getRegistrationNumber(),
                    view.getVehicleType(),
                    selectedServices);
            view.displayResult(model);
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }
}
