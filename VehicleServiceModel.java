import java.util.List;

public class VehicleServiceModel {
    public static final int GENERAL_SERVICE_COST = 1000;
    public static final int OIL_CHANGE_COST = 800;
    public static final int BRAKE_SERVICE_COST = 1200;
    public static final int BATTERY_CHECK_COST = 500;

    private String registrationNumber;
    private String vehicleType;
    private int totalCost;

    public void calculateCost(
            String registrationNumber,
            String vehicleType,
            List<String> selectedServices) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle registration number cannot be empty.");
        }
        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            throw new IllegalArgumentException("Select a vehicle type.");
        }
        if (selectedServices == null || selectedServices.isEmpty()) {
            throw new IllegalArgumentException("Select at least one service.");
        }

        this.registrationNumber = registrationNumber.trim();
        this.vehicleType = vehicleType;
        totalCost = 0;

        for (String service : selectedServices) {
            totalCost += getServiceCost(service);
        }
    }

    private int getServiceCost(String service) {
        switch (service) {
            case "General Service":
                return GENERAL_SERVICE_COST;
            case "Oil Change":
                return OIL_CHANGE_COST;
            case "Brake Service":
                return BRAKE_SERVICE_COST;
            case "Battery Check":
                return BATTERY_CHECK_COST;
            default:
                throw new IllegalArgumentException("Unknown service selected: " + service);
        }
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
