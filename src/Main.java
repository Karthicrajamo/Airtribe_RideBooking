import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.service.RiderService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final RiderService riderService = new RiderService();

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        Rider rider = riderService.RegisterRiders("Karthic",23.4);
        riderService.RegisterRiders("Karthic2",23.4);
        Rider rider1 = riderService.getRiderById(rider.getId());
        System.out.println(riderService.getAllRiders().iterator().next().getName());


    }
}