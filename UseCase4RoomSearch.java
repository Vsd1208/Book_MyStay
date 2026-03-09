import java.util.Map;
import java.util.HashMap;
class RoomSearchService {
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search");

        /* Check and display Single Room availability */
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds: " + singleRoom.numberOfBeds);
            System.out.println("Size: " + singleRoom.squareFeet + " sqft");
            System.out.println("Price per night: " + singleRoom.pricePerNight);
            System.out.println("Available: " + availability.get("Single"));
        }

        /* Check and display Double Room availability */
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds: " + doubleRoom.numberOfBeds);
            System.out.println("Size: " + doubleRoom.squareFeet + " sqft");
            System.out.println("Price per night: " + doubleRoom.pricePerNight);
            System.out.println("Available: " + availability.get("Double"));
        }

        /* Check and display Suite Room availability */
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds: " + suiteRoom.numberOfBeds);
            System.out.println("Size: " + suiteRoom.squareFeet + " sqft");
            System.out.println("Price per night: " + suiteRoom.pricePerNight);
            System.out.println("Available: " + availability.get("Suite"));
        }
    }
}
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(
                inventory,
                single,
                doubleRoom,
                suite
        );
    }
}