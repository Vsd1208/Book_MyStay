import java.util.ArrayList;
import java.util.List;

// Model class to represent a Booking
class Reservation {
    private String bookingId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String bookingId, String guestName, String roomType, double price) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %-10s | Room: %-10s | Price: $%.2f",
                bookingId, guestName, roomType, price);
    }

    public double getPrice() { return price; }
}

// Service to handle historical data storage
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addRecord(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllRecords() {
        // Returning a copy to ensure reporting does not modify stored data
        return new ArrayList<>(history);
    }
}

// Service to generate reports from history
class BookingReportService {
    public void generateSummary(List<Reservation> records) {
        System.out.println("\n--- Operational Booking Report ---");
        if (records.isEmpty()) {
            System.out.println("No bookings found in history.");
            return;
        }

        double totalRevenue = 0;
        for (Reservation res : records) {
            System.out.println(res);
            totalRevenue += res.getPrice();
        }

        System.out.println("----------------------------------");
        System.out.println("Total Bookings: " + records.size());
        System.out.printf("Total Revenue:  $%.2f%n", totalRevenue);
        System.out.println("----------------------------------\n");
    }
}

// Main Application Class
class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        // Initialize Services
        BookingHistory historyStore = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("System: Processing new confirmed bookings...");

        // Simulate successful confirmations adding to history
        historyStore.addRecord(new Reservation("BK001", "Alice", "Deluxe", 150.0));
        historyStore.addRecord(new Reservation("BK002", "Bob", "Standard", 100.0));
        historyStore.addRecord(new Reservation("BK003", "Charlie", "Suite", 300.0));

        // Admin requests reports
        System.out.println("Admin: Requesting historical report...");
        List<Reservation> currentRecords = historyStore.getAllRecords();
        reportService.generateSummary(currentRecords);
    }
}