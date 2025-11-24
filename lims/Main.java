package lims;

import java.util.Scanner;
import java.util.List;

// Import all required classes
import lims.service.AuthService;
import lims.service.CirculationService;
import lims.service.CatalogService;
import lims.repository.LibraryRepository;
import lims.model.LibraryItem;
import lims.model.FictionBook;
import lims.model.NonFictionBook;
import lims.model.ReferenceBook;
import lims.model.Patron;
import lims.util.BorrowingTransaction;

/**
 * LIMS - Library Inventory Management System
 * 
 * EDUCATIONAL DEMONSTRATION: This class showcases Lecture 1 concepts
 * - Java Basics: main method, program structure, basic I/O
 * - Object creation and method invocation
 * - Console interaction with Scanner class
 * 
 * @author Student Name
 * @version 1.0
 * @course Java Programming Lectures 1-6
 */
public class Main {

    // L1: Instance variables for the library system
    private Scanner scanner;                    // L1: Scanner for console input
    private AuthService authService;            // L2: Service for staff authentication
    private LibraryRepository libraryRepo;      // L6: Repository for book management
    private CirculationService circulationService; // L4, L5: Service for borrowing rules
    private CatalogService catalogService;      // L3, L5: Service for catalog operations

    /**
     * L1: Constructor - initializes all library system components
     * Demonstrates object creation and dependency injection
     */
    public Main() {
        this.scanner = new Scanner(System.in);                    // L1: Create Scanner object
        this.authService = new AuthService();                     // L2: Create authentication service
        this.libraryRepo = new LibraryRepository();               // L6: Create library repository
        this.circulationService = new CirculationService(libraryRepo); // L4, L5: Create circulation service
        this.catalogService = new CatalogService(libraryRepo);    // L3, L5: Create catalog service
    }

    /**
     * L1: Main method - program entry point
     * This is where the Java Virtual Machine starts execution
     */
    public static void main(String[] args) {
        // L1: Create main library system object
        Main librarySystem = new Main();
        
        // L1: Initialize system with sample books
        librarySystem.seedSampleData();
        
        // L1: Start the main application loop
        librarySystem.run();
    }

    /**
     * L1: Main application loop
     * Demonstrates while loop, switch statement, and method calls
     */
    public void run() {
        // L1: Console output - welcome message
        System.out.println("=========================================");
        System.out.println("   Library Inventory Management System");
        System.out.println("         Educational Java Project");
        System.out.println("=========================================");
        System.out.println("Demonstrating Lectures 1-6 Concepts");
        System.out.println();

        // L5: Decision making - login process
        if (!performLogin()) {
            System.out.println("Authentication failed. Exiting system.");
            return; // L1: Early return from method
        }

        // L5: Main menu loop using do-while
        int choice;
        do {
            displayMainMenu();                    // L1: Method call
            choice = readIntegerInput("Enter your choice (1-5): ");
            
            // L5: Switch statement for menu navigation
            switch (choice) {
                case 1:
                    handleBorrowBook();           // L1: Method call
                    break;
                case 2:
                    handleReturnBook();           // L1: Method call
                    break;
                case 3:
                    handleSearchCatalog();        // L1: Method call
                    break;
                case 4:
                    handleViewTransactions();     // L1: Method call
                    break;
                case 5:
                    System.out.println("Thank you for using LIMS!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
                    break;
            }
        } while (choice != 5);                    // L5: Loop condition

        // L1: Close scanner to prevent resource leaks
        scanner.close();
        System.out.println("System shutdown complete.");
    }

    /**
     * L5: User authentication with retry logic
     * Demonstrates if-else statements and for loops
     */
    private boolean performLogin() {
        System.out.println("=== Library Staff Authentication ===");
        
        // L5: For loop with fixed attempts
        for (int attempts = 1; attempts <= 3; attempts++) {
            System.out.print("Username: ");
            String username = scanner.nextLine();     // L1: Read user input
            
            System.out.print("Password: ");
            String password = scanner.nextLine();     // L1: Read user input

            // L4: Logical expression for authentication
            if (authService.validateUser(username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
                return true;                          // L5: Return true on success
            } else {
                // L5: Conditional message based on remaining attempts
                if (attempts < 3) {
                    System.out.println("Invalid credentials. " + (3 - attempts) + " attempts remaining.");
                } else {
                    System.out.println("Maximum attempts reached.");
                }
            }
        }
        return false;                                 // L5: Return false on failure
    }

    /**
     * L1: Display main menu options
     * Simple console output demonstration
     */
    private void displayMainMenu() {
        System.out.println();
        System.out.println("=== Library Main Menu ===");
        System.out.println("1. Borrow Book           (L5: Decision Making)");
        System.out.println("2. Return Book           (L3: Date Calculations)");
        System.out.println("3. Search Catalog        (L6: Inheritance Display)");
        System.out.println("4. View Transactions     (L2: Data Handling)");
        System.out.println("5. Exit System           (L1: Program Termination)");
    }

    /**
     * L5: Handle book borrowing workflow
     * Demonstrates method calls, user input, and business logic
     */
    private void handleBorrowBook() {
        System.out.println("\n=== Borrow Book ===");
        System.out.println("This demonstrates L4 (Logical Operators) and L5 (Decision Making)");

        // L2: Get patron information
        System.out.print("Enter patron ID: ");
        String patronID = scanner.nextLine();

        System.out.print("Enter patron name: ");
        String patronName = scanner.nextLine();

        System.out.print("Enter patron type (Student/Faculty/Staff): ");
        String patronType = scanner.nextLine();

        // L6: Create patron object
        Patron patron = new Patron(patronID, patronName, patronType);

        // L6: Display available books
        libraryRepo.displayAvailableBooks();

        // L2: Get book selection
        System.out.print("Enter book ISBN to borrow: ");
        String bookISBN = scanner.nextLine();

        LibraryItem selectedBook = libraryRepo.findBookByISBN(bookISBN);
        if (selectedBook == null || !selectedBook.isAvailable()) {
            System.out.println("Book not found or not available for borrowing.");
            return;
        }

        // L2: Get borrowing parameters
        int borrowDays = readIntegerInput("Enter borrowing period (days): ");

        // L3, L4, L5: Process borrowing with business logic
        BorrowingTransaction transaction = circulationService.processBorrowing(
            patron, selectedBook, borrowDays);

        // L1: Display results
        System.out.println("\n=== Borrowing Summary ===");
        System.out.println(transaction);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * L3: Handle book return workflow
     * Demonstrates date calculations and fee computations
     */
    private void handleReturnBook() {
        System.out.println("\n=== Return Book ===");
        System.out.println("This demonstrates L3 (Operators & Expressions)");

        System.out.print("Enter book ISBN to return: ");
        String bookISBN = scanner.nextLine();

        // L3: Process return with calculations
        double lateFee = circulationService.processReturn(bookISBN);
        
        if (lateFee > 0) {
            System.out.println("Late fee: $" + String.format("%.2f", lateFee));
        } else {
            System.out.println("Book returned successfully. No late fee.");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * L6: Display book catalog
     * Shows inheritance and polymorphism
     */
    private void handleSearchCatalog() {
        System.out.println("\n=== Book Catalog Search ===");
        System.out.println("This demonstrates L6 (Inheritance & Polymorphism)");
        
        System.out.print("Enter search term (title/author): ");
        String searchTerm = scanner.nextLine();
        
        List<LibraryItem> results = catalogService.searchBooks(searchTerm);
        
        if (results.isEmpty()) {
            System.out.println("No books found matching: " + searchTerm);
        } else {
            System.out.println("Found " + results.size() + " book(s):");
            for (LibraryItem book : results) {
                System.out.println("  " + book);
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * L2: Display transaction history
     * Shows data handling and list processing
     */
    private void handleViewTransactions() {
        System.out.println("\n=== Transaction History ===");
        System.out.println("This demonstrates L2 (Data Types & Variables)");
        
        List<BorrowingTransaction> transactions = circulationService.getAllTransactions();
        
        // L5: Decision making for empty list
        if (transactions.isEmpty()) {
            System.out.println("No transactions found. Process a borrowing first!");
        } else {
            System.out.println("Total transactions: " + transactions.size());
            for (BorrowingTransaction tx : transactions) {
                System.out.println("----------------------------------------");
                System.out.println(tx);
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * L2: Helper method for reading integer input with validation
     * Demonstrates exception handling and loop constructs
     */
    private int readIntegerInput(String prompt) {
        while (true) {                                    // L5: Infinite loop with break
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();         // L1: Read input
                return Integer.parseInt(input);            // L2: Parse to integer
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    /**
     * L6: Initialize system with sample library data
     * Demonstrates object creation and collection usage
     */
    private void seedSampleData() {
        System.out.println("Initializing library with sample books...");
        
        // L6: Create fiction book objects
        libraryRepo.addBook(new FictionBook(
            "978-0-13-468599-1", "Effective Java", "Joshua Bloch", true, "Programming"));
        libraryRepo.addBook(new FictionBook(
            "978-0-321-35668-0", "Clean Code", "Robert C. Martin", true, "Software Engineering"));
        
        // L6: Create non-fiction book objects
        libraryRepo.addBook(new NonFictionBook(
            "978-1-4919-5076-6", "Introduction to Algorithms", "Thomas H. Cormen", true, "Computer Science"));
        libraryRepo.addBook(new NonFictionBook(
            "978-0-262-03293-3", "Structure and Interpretation", "Harold Abelson", true, "Programming"));
        
        // L6: Create reference book objects
        libraryRepo.addBook(new ReferenceBook(
            "978-0-13-235088-4", "Java Language Specification", "James Gosling", true, "Reference"));
        
        System.out.println("Library initialization complete.");
    }
}
