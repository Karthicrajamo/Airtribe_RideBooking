import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RiderService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final RiderService riderService = new RiderService();
    private static final DriverService driverService = new DriverService();

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        Rider rider = riderService.RegisterRiders("Karthic",23.4);
        riderService.RegisterRiders("Karthic2",23.4);
        Driver driver = driverService.RegisterDriver("Jhon",32.3, VehicleType.BIKE);
        driverService.RegisterDriver("Jhon1",32.3, VehicleType.BIKE);
        driverService.setAvailablity(driver.getId(),false);
        System.out.println("Available Drivers:"+driverService.getAvailableDrivers().size());

        System.out.println("__________________________________________");
        System.out.println("Driver Details:");
        System.out.println("All Drivers:"+driverService.getAllDrivers());



    }
}