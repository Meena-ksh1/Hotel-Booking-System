import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String type;
    boolean isBooked;
    double price;

    Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }
}

class Booking {
    String customerName;
    String phoneNumber;
    Room room;
    int nights;

    Booking(String name, String phone, Room room, int nights) {
        this.customerName = name;
        this.phoneNumber = phone;
        this.room = room;
        this.nights = nights;
        room.isBooked = true;
    }

    double getTotal() {
        return room.price * nights;
    }

    void generateReceipt() {
        System.out.println("\n----- Hotel Booking Receipt -----");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Room Number: " + room.roomNumber);
        System.out.println("Room Type: " + room.type);
        System.out.println("Nights: " + nights);
        System.out.println("Total Bill: ₹" + getTotal());
        System.out.println("----------------------------------");

        saveToFile();
    }

    void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            writer.write(customerName + "," + phoneNumber + "," + room.roomNumber + "," + room.type + "," + nights + "," + getTotal());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }
}

public class HotelBookingSystem {
    static Room[] rooms = new Room[10];
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initRooms();
        int choice;

        do {
            System.out.println("\n--- Hotel Booking Menu ---");
            System.out.println("1. Show Room Availability");
            System.out.println("2. Book a Room");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showAvailability();
                case 2 -> bookRoom();
                case 3 -> System.out.println("Thank you for using Hotel Booking System.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    static void initRooms() {
        for (int i = 0; i < rooms.length; i++) {
            if (i < 4)
                rooms[i] = new Room(i + 1, "Single", 1000);
            else if (i < 7)
                rooms[i] = new Room(i + 1, "Double", 2000);
            else
                rooms[i] = new Room(i + 1, "Deluxe", 3000);
        }
    }

    static void showAvailability() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println("Room " + room.roomNumber + " - " + room.type + " - ₹" + room.price);
            }
        }
    }

    static void bookRoom() {
        showAvailability();
        System.out.print("Enter Room Number to Book: ");
        int roomNo = sc.nextInt();

        if (roomNo < 1 || roomNo > rooms.length || rooms[roomNo - 1].isBooked) {
            System.out.println("Invalid or already booked room.");
            return;
        }

        sc.nextLine(); // consume leftover newline
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        System.out.print("Enter number of nights: ");
        int nights = sc.nextInt();

        Booking booking = new Booking(name, phone, rooms[roomNo - 1], nights);
        booking.generateReceipt();
    }
        
}
