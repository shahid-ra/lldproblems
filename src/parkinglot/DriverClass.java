package parkinglot;

import parkinglot.modules.Level;
import parkinglot.modules.ParkingLot;
import parkinglot.modules.vehicles.Cars;
import parkinglot.modules.vehicles.Motorcycle;
import parkinglot.modules.vehicles.Truck;
import parkinglot.modules.vehicles.Vehicle;

public class DriverClass {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        parkingLot.addLevel(new Level(1, 10));
        parkingLot.addLevel(new Level(2, 5));

        Vehicle car = new Cars("ABC123");
        Vehicle truck = new Truck("XYZ789");
        Vehicle motorcycle = new Motorcycle("M1234");

        // Park vehicles
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(truck);
        parkingLot.parkVehicle(motorcycle);

        // Display availability
        parkingLot.displayAvailability();

        // Unpark vehicle
        parkingLot.unparkVehicle(motorcycle);

        // Display updated availability
        parkingLot.displayAvailability();
    }
}
