package wlss;

import java.util.Scanner;

/**
 * Main class for WLSS console application.
 * Lecture 1: Java Basics - main method as entry point
 * Lecture 2: Variables and Data Types
 * Lecture 5: Decision Making
 * Lecture 6: Classes and Objects - application controller
 */
public class WLSSApp {

    private Scanner scanner = new Scanner(System.in);
    private AuthenticationService authService = new AuthenticationService();
    private InventoryManager inventoryManager = new InventoryManager();
    private SafetyChecker safetyChecker = new SafetyChecker();
    private RentalService rentalService = new RentalService(inventoryManager, safetyChecker);

    // Lecture 1: Standard main method - program entry point
    public static void main(String[] args) {
        WLSSApp app = new WLSSApp();
        app.seedSampleData();
        app.run();
    }

    /**
     * Initialize the system with sample equipment data.
     * Lecture 6: Object creation and initialization
     */
    private void seedSampleData() {
        // Add sample windsurf boards
        inventoryManager.addItem(new WindsurfBoard("B001", "Performance Board", 15.0, true, 95.0f, "Single"));
        inventoryManager.addItem(new WindsurfBoard("B002", "Beginner Board", 12.0, true, 120.0f, "Tri-fin"));
        
        // Add sample sails
        inventoryManager.addItem(new Sail("S001", "Race Sail", 10.0, true, 6.5f, "Performance"));
        inventoryManager.addItem(new Sail("S002", "Training Sail", 8.0, true, 5.0f, "Standard"));
    }

    /**
     * Main application loop.
     * Lecture 5: Decision making with switch statement
     */
    public void run() {
        System.out.println("Welcome to Windsurf Logistics & Safety System (WLSS)");

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

        WaterSportItem selectedItem = inventoryManager.findItemById(itemId);
        if (selectedItem == null || !selectedItem.isAvailable()) {
            System.out.println("Item not found or not available. Canceling rental.");
            return;
        }

        // Get rental duration
        int hours = readInt("Enter rental duration in hours: ");
        
        // Get wind speed for safety check
        int windSpeed = readInt("Enter current wind speed (knots): ");

        // Create rental transaction
        RentalTransaction tx = rentalService.createRental(customer, selectedItem, hours, windSpeed);

        // Display rental summary
        System.out.println("=== Rental Summary ===");
        System.out.println(tx);
    }

    private void handleListInventory() {
        inventoryManager.printAvailableItems();
    }

    private void handleViewTransactions() {
        System.out.println("=== Transaction Log ===");
        for (RentalTransaction tx : rentalService.getAllTransactions()) {
            System.out.println(tx);
            System.out.println("----------------------");
        }
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
