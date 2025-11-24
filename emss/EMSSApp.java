package emss;

import java.util.Scanner;
import java.util.List;

/**
 * Main class for EMSS console application.
 * Lecture 1: Java Basics - main method as entry point
 * Lecture 2: Variables and Data Types
 * Lecture 5: Decision Making
 * Lecture 6: Classes and Objects - application controller
 */
public class EMSSApp {

    private Scanner scanner = new Scanner(System.in);
    private AuthenticationService authService = new AuthenticationService();
    private InventoryManager inventoryManager = new InventoryManager();
    private SafetyChecker safetyChecker = new SafetyChecker();
    private RentalService rentalService = new RentalService(inventoryManager, safetyChecker);

    // Lecture 1: Standard main method - program entry point
    public static void main(String[] args) {
        EMSSApp app = new EMSSApp();
        app.seedSampleData();
        app.run();
    }

    /**
     * Initialize the system with sample equipment data.
     * Lecture 6: Object creation and initialization
     */
    private void seedSampleData() {
        // Add sample sports equipment
        inventoryManager.addItem(new SportsEquipment("E001", "Professional Tennis Racket", 15.0, true, 12.5f, "Racket"));
        inventoryManager.addItem(new SportsEquipment("E002", "Mountain Bike", 25.0, true, 18.0f, "Bicycle"));
        
        // Add sample training gear
        inventoryManager.addItem(new TrainingGear("T001", "Balance Board", 8.0, true, "Beginner", "All Ages"));
        inventoryManager.addItem(new TrainingGear("T002", "Agility Ladder", 6.0, true, "Intermediate", "Youth"));
    }

    /**
     * Main application loop.
     * Lecture 5: Decision making with switch statement
     */
    public void run() {
        System.out.println("Welcome to Equipment Management and Safety System (EMSS)");

        if (!login()) {
            System.out.println("Exiting system.");
            return;
        }

        int choice;
        do {
            showMainMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1:
                    handleCreateRental();
                    break;
                case 2:
                    handleListInventory();
                    break;
                case 3:
                    handleViewTransactions();
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    /**
     * Handle user login with retry logic.
     * Lecture 5: Decision making with if-else and loops
     */
    private boolean login() {
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (authService.isValidUser(username, password)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                return true;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }
        return false;
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println("=== Main Menu ===");
        System.out.println("1. Create New Rental");
        System.out.println("2. List Available Equipment");
        System.out.println("3. View All Transactions");
        System.out.println("4. Exit");
    }

    /**
     * Handle the create rental workflow.
     * Lecture 2: Variables and Data Types (String, int)
     * Lecture 5: Decision making
     */
    private void handleCreateRental() {
        System.out.println("=== Create New Rental ===");

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter skill level (Beginner / Intermediate / Advanced): ");
        String skillLevel = scanner.nextLine();

        // Create customer object
        Customer customer = new Customer("C1", name, skillLevel);

        // Show available equipment
        inventoryManager.printAvailableItems();
        
        System.out.print("Enter item ID to rent: ");
        String itemId = scanner.nextLine();

        EquipmentItem selectedItem = inventoryManager.findItemById(itemId);
        if (selectedItem == null || !selectedItem.isAvailable()) {
            System.out.println("Item not found or not available. Canceling rental.");
            return;
        }

        // Get rental duration
        int hours = readInt("Enter rental duration in hours: ");
        
        // Get condition level for safety check
        int conditionLevel = readInt("Enter current condition level (1-100): ");

        // Create rental transaction
        RentalTransaction tx = rentalService.createRental(customer, selectedItem, hours, conditionLevel);

        // Display rental summary
        System.out.println("=== Rental Summary ===");
        System.out.println(tx);
    }

    private void handleListInventory() {
        System.out.println("\n=== Listing Available Equipment ===");
        inventoryManager.printAvailableItems();
        System.out.println("=== End of Equipment List ===\n");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private void handleViewTransactions() {
        System.out.println("\n=== Transaction Log ===");
        List<RentalTransaction> transactions = rentalService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found. Create a rental first!");
        } else {
            for (RentalTransaction tx : transactions) {
                System.out.println(tx);
                System.out.println("----------------------");
            }
        }
        System.out.println("=== End of Transaction Log ===\n");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Helper method to read integer input with validation.
     * Lecture 2: Data types and input validation
     */
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
