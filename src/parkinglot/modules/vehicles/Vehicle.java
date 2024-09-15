package parkinglot.modules.vehicles;

public abstract class Vehicle {
    private final String numberPlate;
    private final VehicleType type;

    public Vehicle(String numberPlate, VehicleType vehicleType) {
        this.numberPlate = numberPlate;
        this.type = vehicleType;
    }

    public  String getNumberPlate() {
        return numberPlate;
    }

    public  VehicleType getType() {
        return type;
    }
}
