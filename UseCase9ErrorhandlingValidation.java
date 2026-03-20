import java.util.*;

// Custom Exception for Booking Failures
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class UseCase9ErrorHandlingValidation {
    // Inventory representing room types and available counts
    private static Map<String, Integer> roomInventory = new HashMap<>();

    static { /*made my VSD*/
        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }

    public static void main(String[] args) {
        System.out.println("--- Use Case 9: Error Handling & Validation ---");

        // Test Scenarios
        processBooking("Guest_01", "Standard", 2);  // Valid booking
        processBooking("Guest_02", "Penthouse", 1); // Invalid Room Type
        processBooking("Guest_03", "Suite", 5);     // Insufficient Inventory
        processBooking("Guest_04", "Deluxe", 0);    // Invalid Quantity

        displayFinalInventory();
    }

    /**
     * Validates and processes a booking request.
     * Uses a Fail-Fast approach to catch errors early.
     */
    public static void processBooking(String guestName, String roomType, int quantity) {
        System.out.println("\nProcessing request: " + guestName + " for " + quantity + " " + roomType + "(s)");

        try {
            // 1. Validate Input: Quantity must be positive
            if (quantity <= 0) {
                throw new InvalidBookingException("Invalid quantity: Booking must be for at least 1 room.");
            }

            // 2. Validate Room Type: Must exist in inventory
            if (!roomInventory.containsKey(roomType)) {
                throw new InvalidBookingException("Error: Room type '" + roomType + "' does not exist.");
            }

            // 3. Validate System State: Check availability
            int currentStock = roomInventory.get(roomType);
            if (quantity > currentStock) {
                throw new InvalidBookingException("Insufficient inventory: Only " + currentStock + " " + roomType + "(s) available.");
            }

            // 4. Update State: Only reached if all validations pass
            roomInventory.put(roomType, currentStock - quantity);
            System.out.println("SUCCESS: Booking confirmed for " + guestName);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.err.println("VALIDATION FAILED: " + e.getMessage());
        }
    }

    public static void displayFinalInventory() {
        System.out.println("\n--- Final Room Inventory ---");
        roomInventory.forEach((type, count) -> System.out.println(type + ": " + count));
    }
}