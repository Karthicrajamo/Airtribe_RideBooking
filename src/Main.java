import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final RiderService riderService = new RiderService();
    private static final DriverService driverService = new DriverService();

    private static final RideService rideService = new RideService(riderService, driverService, new NearestDriverStrategy(), new DefaultFareStrategy());

    private static final Scanner scanner = new Scanner(System.in);

    private static final String currentMatchingStrategy = "Nearest Driver";
    private static final String currentFareStrategy = "Default Fare";

    public static void main(String[] args) throws NoDriverAvailableException {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       Welcome to RideWise        ║");
        System.out.println("╚══════════════════════════════════╝");

        boolean running = true;
        while(running){
            printMenu();
            System.out.print("-> ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addRider();
                case 2 -> addDriver();
                case 3 -> availableDrivers();
                case 4 -> requestRide();
                case 5 -> completeRide();
                case 6 -> cancelRide();
                case 7 -> viewAllRides();
                case 8 -> changeStrategy();
                case 9 -> {
                    System.out.println("GoodBye...");
                    running = false;
                }
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("───────────────────────────────────────────────────");
        System.out.println("  Strategy: "+ currentMatchingStrategy +" | "+ currentFareStrategy);
        System.out.println("───────────────────────────────────────────────────");
        System.out.println("  1. Add Rider");
        System.out.println("  2. Add Driver");
        System.out.println("  3. View Available Drivers");
        System.out.println("  4. Request Ride");
        System.out.println("  5. Complete Ride");
        System.out.println("  6. Cancel Ride");
        System.out.println("  7. View All Rides");
        System.out.println("  8. Change Strategies");
        System.out.println("  9. Exit");
        System.out.println("─────────────────────────────────────");
    }

    private static void addRider(){
        scanner.nextLine();
        System.out.print("Enter Rider Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Location: ");
        Double location = scanner.nextDouble();
        scanner.nextLine();
        riderService.registerRiders(name, location);
        System.out.print("=========ENTER to Continue=======");
        scanner.nextLine();
    }

    private static void addDriver(){
        scanner.nextLine();
        System.out.print("Enter Driver Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Current Location: ");
        Double location = scanner.nextDouble();

        System.out.println("Enter Vehicle Type(Default BIKE): ");
        System.out.println("1 -> BIKE");
        System.out.println("2 -> CAR");
        System.out.println("3 -> AUTO");
        int type = scanner.nextInt();
        VehicleType vehicleType = switch (type){
            case 1 -> VehicleType.BIKE;
            case 2 -> VehicleType.CAR;
            case 3 -> VehicleType.AUTO;
            default -> {
                System.out.println("Invalid Vehicle Type. Defaulting to Bike");
                yield VehicleType.BIKE;
            }
        };
        driverService.RegisterDriver(name, location, vehicleType);
        System.out.print("=========ENTER to Continue=======");
        scanner.nextLine();
    }

    private static void availableDrivers(){
        System.out.println("-------Available Drivers--------");
        List<Driver> drivers = driverService.getAvailableDrivers();

        if(drivers.isEmpty()){
            System.out.println("No drivers available right now.");
            return;
        }
        for(Driver driver : drivers){
            System.out.println("* "+driver);
        }
        System.out.print("=========ENTER to Continue=======");
        scanner.nextLine();
    }

    private static void requestRide() throws NoDriverAvailableException { //4
        scanner.nextLine();
        System.out.println("-------Request Ride-------");
        List<Rider> riders = riderService.getAllRiders();
        System.out.println("*** Registered Riders ***");
        riderService.getAllRiders().forEach(rider -> System.out.println("* "+rider.getId()));

        System.out.print("Rider Id: ");
        String riderId = scanner.nextLine();
        if(!riderService.isExist(riderId)){
            System.out.println("Rider not found: "+riderId);
            return;
        }
        System.out.print("Distance (km): ");
        Double distance = scanner.nextDouble();
        if(distance < 0 ){
            System.out.println("Distance must be in Positive.");
            return;
        }
        try {
            Ride ride = rideService.requestRide(riderId, distance);
            System.out.println("Ride Details: "+ride);
        }catch (NoDriverAvailableException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.print("=========ENTER to Continue=======");
        scanner.nextLine();
    }

    private static void completeRide(){
        scanner.nextLine();
        viewAllRides();
        System.out.println("-------Complete Ride-------");

        System.out.print("Ride Id: ");
        String rideId = scanner.nextLine();
        rideService.completeRide(rideId);
        scanner.nextLine();
    }

    private static void cancelRide(){
        scanner.nextLine();
        viewAllRides();
        System.out.println("-------Cancel Ride-------");
        System.out.print("Ride Id: ");
        String rideId = scanner.nextLine();
        rideService.cancelRide(rideId);
        System.out.print("=========ENTER to Continue=======");
        scanner.nextLine();
    }

    private static void viewAllRides(){
//        scanner.nextLine();
        Collection<Ride> rides = rideService.getAllRides();
        System.out.println("*** Assigned Rides ***");
        rides.forEach(ride -> {
            if (ride.getRideStatus() == RideStatus.ASSIGNED) {
                System.out.println(ride);
            }
        });
//        scanner.nextLine();
    }

    private static void changeStrategy(){
        scanner.nextLine();
        System.out.println("-------Change Matching Strategy-------");
        System.out.println("*** Matching Strategy ***");
        System.out.println(" 1 -> Nearest Driver Strategy");
        System.out.println(" 2 -> LeastActive Driver strategy");
        System.out.println("Choice -> ");

        int choiceM = scanner.nextInt();
        RideMatchingStrategy matchingStrategy = null;
        switch (choiceM){
            case 1 -> matchingStrategy = new NearestDriverStrategy();
            case 2 -> matchingStrategy = new LeastActiveDriverStrategy();
            default -> {
                System.out.println("Strategy not match: Default Strategy - Nearest Driver Strategy");
                matchingStrategy = new NearestDriverStrategy();
            }
        }

        System.out.println("-------Change Fare Strategy-------");
        System.out.println("*** Fare Strategy ***");
        System.out.println(" 1 -> Default Fare Strategy");
        System.out.println(" 2 -> Peak Hour Fare strategy");
        System.out.println("Choice -> ");

        int choiceF = scanner.nextInt();
        FareStrategy fareStrategy = null;
        switch (choiceF){
            case 1 -> fareStrategy = new DefaultFareStrategy();
            case 2 -> fareStrategy = new PeakHourFareStrategy();
            default -> {
                System.out.println("Strategy not match: Default Strategy - Default Fare Strategy");
                fareStrategy = new DefaultFareStrategy();
            }
        }

        rideService.withMatchingStrategy(matchingStrategy);
        rideService.withFareStrategy(fareStrategy);
        System.out.println("Strategies updated successfully. \n Matching Strategy"+matchingStrategy+"\n Fare Strategy: "+fareStrategy);

    }
}