import java.io.*;
import java.util.*;

// Essential to implement Serializable to allow objects to be saved to a file
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> inventory;
    List<String> bookings;

    public SystemState(Map<String, Integer> inventory, List<String> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}
class UseCase12DataPersistenceRecovery {
    private static final String STORAGE_FILE = "system_state.ser";
    private Map<String, Integer> inventory = new HashMap<>();
    private List<String> bookings = new ArrayList<>();

    public static void main(String[] args) {
        UseCase12DataPersistenceRecovery app = new UseCase12DataPersistenceRecovery();

        // 1. System Restarts & Attempts Recovery
        app.loadState();

        // 2. Simulate some operations if the system is empty
        if (app.bookings.isEmpty()) {
            System.out.println("No previous state found. Initializing new data...");
            app.inventory.put("Deluxe Room", 10);
            app.bookings.add("Booking #101: John Doe - Deluxe Room");
            app.inventory.put("Deluxe Room", 9); // Decrement inventory
        }

        app.displayCurrentStatus();

        // 3. System prepares for shutdown (Persistence)
        app.saveState();
        System.out.println("System state persisted. You can now restart the app to verify recovery.");
    }

    // Save state to a file (Serialization)
    public void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            SystemState state = new SystemState(inventory, bookings);
            oos.writeObject(state);
            System.out.println("Successfully saved inventory and booking history to " + STORAGE_FILE);
        } catch (IOException e) {
            System.err.println("Error during persistence: " + e.getMessage());
        }
    }

    // Load state from a file (Deserialization)
    @SuppressWarnings("unchecked")
    public void loadState() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with a fresh state.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            SystemState state = (SystemState) ois.readObject();
            this.inventory = state.inventory;
            this.bookings = state.bookings;
            System.out.println("System Recovery Complete: Restored " + bookings.size() + " bookings.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed or file corrupted. Starting fresh. Error: " + e.getMessage());
        }
    }

    public void displayCurrentStatus() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Booking History: " + bookings);
        System.out.println("---------------------------\n");
    }
}