import java.util.*;

class UseCase10BookingCancellation {

    // Simple Inventory State
    private static int availableRooms = 5;
    private static Map<String, String> activeBookings = new HashMap<>();

    // Stack to track recently released room IDs (LIFO Rollback)
    private static Stack<String> releasedRoomsStack = new Stack<>();

    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Use Case 10 ---");

        // 1. Setup initial state with some bookings
        simulateBooking("B001", "RM101");
        simulateBooking("B002", "RM102");
        displayStatus();

        // 2. Perform a valid cancellation
        cancelBooking("B002");

        // 3. Attempt to cancel a non-existent booking
        cancelBooking("B003");

        // 4. Perform another cancellation to see Stack behavior
        simulateBooking("B004", "RM104");
        cancelBooking("B004");
        cancelBooking("B001");

        displayStatus();
        showRollbackHistory();
    }

    /**
     * Goal: Enable safe cancellation and inventory rollback
     */
    public static void cancelBooking(String bookingId) {
        System.out.println("\n[Action] Initiating cancellation for: " + bookingId);

        // Validation: Ensure reservation exists
        if (!activeBookings.containsKey(bookingId)) {
            System.out.println("Error: Cancellation failed. Booking ID " + bookingId + " not found.");
            return;
        }

        // State Reversal Logic
        String roomId = activeBookings.remove(bookingId);

        // Push to Stack (LIFO Rollback Logic)
        releasedRoomsStack.push(roomId);

        // Inventory Restoration
        availableRooms++;

        System.out.println("Success: Booking " + bookingId + " cancelled. Room " + roomId + " returned to pool.");
    }

    private static void simulateBooking(String id, String room) {
        activeBookings.put(id, room);
        availableRooms--;
    }

    private static void displayStatus() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Available Inventory: " + availableRooms);
        System.out.println("Active Bookings: " + activeBookings);
    }

    private static void showRollbackHistory() {
        System.out.println("\n--- Rollback History (Stack - Most Recent First) ---");
        if (releasedRoomsStack.isEmpty()) {
            System.out.println("No rooms released yet.");
        } else {
            while (!releasedRoomsStack.isEmpty()) {
                System.out.println("Released Room ID: " + releasedRoomsStack.pop());
            }
        }
    }
}