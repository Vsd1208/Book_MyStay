import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

class BookingProcessor {
    private int availableRooms;
    private final List<String> successfulBookings = new ArrayList<>();

    public BookingProcessor(int totalRooms) {
        this.availableRooms = totalRooms;
    }

    // Critical Section: Synchronized to prevent race conditions
    public synchronized void processBooking(String guestName) {
        System.out.println(guestName + " is attempting to book a room...");

        if (availableRooms > 0) {
            // Simulate processing time
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

            availableRooms--;
            successfulBookings.add(guestName);
            System.out.println("SUCCESS: Room allocated to " + guestName + ". Rooms left: " + availableRooms);
        } else {
            System.out.println("FAILURE: No rooms available for " + guestName);
        }
    }

    public void displayFinalState() {
        System.out.println("\n--- Final Booking Summary ---");
        System.out.println("Total Successful Bookings: " + successfulBookings.size());
        System.out.println("Remaining Rooms: " + availableRooms);
    }
}
class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        int totalRooms = 3;
        int totalGuests = 6;

        BookingProcessor processor = new BookingProcessor(totalRooms);
        ExecutorService executor = Executors.newFixedThreadPool(totalGuests);

        System.out.println("Starting Concurrent Booking Simulation...\n");

        for (int i = 1; i <= totalGuests; i++) {
            String guestName = "Guest-" + i;
            executor.execute(() -> processor.processBooking(guestName));
        }

        executor.shutdown();
        try {
            // Wait for all threads to finish
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        processor.displayFinalState();
    }
}