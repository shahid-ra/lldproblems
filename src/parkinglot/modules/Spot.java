package parkinglot.modules;

import parkinglot.modules.vehicles.Vehicle;
import parkinglot.modules.vehicles.VehicleType;

public class Spot {
    private final int id;
    private final VehicleType vehicleType;
    private boolean occupied;
    private Vehicle parkedVehicle;

    public Spot(int id, VehicleType vehicleType) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.occupied = false;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void setParkedVehicle(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getId() {
        return id;
    }
}
