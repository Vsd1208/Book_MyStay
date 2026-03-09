import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        int availableRooms = inventory.getRoomAvailability().get(roomType);

        if (availableRooms <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.getRoomAvailability().put(roomType, availableRooms - 1);

        System.out.println(
                reservation.getGuestName()
                        + " confirmed for "
                        + roomType
                        + " Room | Room ID: "
                        + roomId
        );
    }
    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 1).toUpperCase();

        int number = allocatedRoomIds.size() + 1;

        return prefix + "00" + number;
    }
}
public class UseCase6RoomAllocation {

    public static void main(String[] args) {

        System.out.println("Room Allocation");

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        RoomAllocationService allocationService = new RoomAllocationService();
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));
        while (bookingQueue.hasPendingRequests()) {

            Reservation reservation = bookingQueue.getNextRequest();

            allocationService.allocateRoom(reservation, inventory);
        }
    }
}