import java.util.*;

// Represents an individual optional offering
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

// Manages the association between reservations and selected services
class AddOnServiceManager {
    // Map of Reservation ID to a List of AddOnServices
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
    }

    // Adds a service to a specific reservation
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getServiceName() + " to Reservation: " + reservationId);
    }

    // Calculates the total cost of all add-ons for a specific reservation
    public double calculateTotalAddOnCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services == null) return 0.0;

        return services.stream()
                .mapToDouble(AddOnService::getPrice)
                .sum();
    }

    // Displays services for a reservation
    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            services.forEach(System.out::println);
            System.out.println("Total Add-On Cost: $" + calculateTotalAddOnCost(reservationId));
        }
    }
}

class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        // Define available services
        AddOnService breakfast = new AddOnService("Breakfast", 15.0);
        AddOnService spa = new AddOnService("Spa Treatment", 50.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 30.0);

        // Scenario: Guest with Reservation ID "RES1001" selects services
        String resId = "RES1001";

        manager.addServiceToReservation(resId, breakfast);
        manager.addServiceToReservation(resId, airportPickup);

        // Display summary
        manager.displayServices(resId);

        // Scenario: Another Guest with Reservation ID "RES1002"
        String resId2 = "RES1002";
        manager.addServiceToReservation(resId2, spa);
        manager.displayServices(resId2);
    }
}