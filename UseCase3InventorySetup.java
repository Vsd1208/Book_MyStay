import java.util.HashMap;
import java.util.Map;
class RoomInventory {
    private Map<String, Integer> roomAvailability;
    public RoomInventory() {
        this.roomAvailability = new HashMap<>();
        initializeInventory();
    }
    private void initializeInventory() {
        // You can later load this from database, config file, etc.
        roomAvailability.put("Standard Single", 25);
        roomAvailability.put("Standard Double", 40);
        roomAvailability.put("Deluxe Single", 12);
        roomAvailability.put("Deluxe Double", 18);
        roomAvailability.put("Junior Suite", 8);
        roomAvailability.put("Family Suite", 6);
        roomAvailability.put("Presidential Suite", 2);
    }
    public Map<String, Integer> getRoomAvailability() {
        // Returning a copy to prevent external modification
        return new HashMap<>(roomAvailability);
    }
    public void updateAvailability(String roomType, int count) {
        if (count < 0) {
            count = 0; // prevent negative inventory
        }
        roomAvailability.put(roomType, count);
    }
    public boolean isAvailable(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0) > 0;
    }
    public int getAvailableCount(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }
}
/**
 * =============================================
 * MAIN CLASS - UseCase3InventorySetup
 * =============================================
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 *   This class demonstrates how room availability
 *   is managed using a centralized inventory.
 *
 *   Room objects are used to retrieve pricing
 *   and room characteristics.
 *
 *   No booking or search logic is introduced here.
 *
 * @version 3.1
 */
public class UseCase3InventorySetup {
    public static void main(String[] args) {
        // Create the centralized inventory
        RoomInventory inventory = new RoomInventory();
        // Display initial availability
        System.out.println("Initial Room Inventory:");
        System.out.println("------------------------");
        inventory.getRoomAvailability().forEach((type, count) ->
                System.out.printf("%-20s : %d rooms available%n", type, count));

        System.out.println();

        // Example: simulate some usage / maintenance
        System.out.println("Updating availability...");
        inventory.updateAvailability("Standard Single", 18);
        inventory.updateAvailability("Deluxe Double", 5);
        inventory.updateAvailability("Family Suite", 0);   // fully booked

        // Show updated state
        System.out.println("\nUpdated Room Inventory:");
        System.out.println("------------------------");
        inventory.getRoomAvailability().forEach((type, count) ->
                System.out.printf("%-20s : %d rooms available%n", type, count));
    }
}