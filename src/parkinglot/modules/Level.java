package parkinglot.modules;

import parkinglot.modules.vehicles.Vehicle;
import parkinglot.modules.vehicles.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Level {
    int id; // this will be represented as level numberPlate
    List<Spot> spots;
    Map<String, Spot> bookedSpots;
    public Level(int id, int numberOfSpots) {
        this.id = id;
        spots = new ArrayList<>();
        bookedSpots = new ConcurrentHashMap<>();
        double bikes = 0.50;
        double cars = 0.40;
        double trucks = 0.10;
        int spotId = 1;
        for (int i = 0; i < Math.floor(bikes * numberOfSpots); i++) {
            spots.add(new Spot(spotId++, VehicleType.MOTORCYCLE));
        }
        for (int i = 0; i < Math.floor(cars * numberOfSpots); i++) {
            spots.add(new Spot(spotId++, VehicleType.CARS));
        }
        for (int i = 0; i < Math.ceil(trucks * numberOfSpots); i++) {
            spots.add(new Spot(spotId++, VehicleType.TRUCKS));
        }
    }

    public synchronized boolean parkVehicle(Vehicle vehicle) {
        for (Spot spot : spots) {
            if (!spot.isOccupied() && spot.getVehicleType() == vehicle.getType()) {
                spot.setParkedVehicle(vehicle);
                spot.setOccupied(true);
                bookedSpots.put(vehicle.getNumberPlate(), spot);
                return true;
            }
        }
        return false;
    }

    public  synchronized boolean unparkVehicle(Vehicle vehicle) {
        Spot bookedSpot = bookedSpots.get(vehicle.getNumberPlate());
        if (bookedSpot != null) {
            bookedSpot.setParkedVehicle(null);
            bookedSpot.setOccupied(false);
            bookedSpots.remove(vehicle.getNumberPlate());
            return true;
        }
        return false;
    }

    public void displayAvailability() {
        System.out.println("Level " + this.id + " Availability:");
        for (Spot spot : spots) {
            System.out.println("Spot " + spot.getId() + ": " + (!spot.isOccupied() ? "Available For"  : "Occupied By ")+" "+spot.getVehicleType());
        }
    }
}
