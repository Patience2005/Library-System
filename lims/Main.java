import java.util.Scanner;
import java.util.List;

/**
 * Library Inventory Management System (LIMS)
 * Main application class with console interface for library operations.
 */
public class Main {

    private Scanner scanner;
    private AuthService authService;
    private LibraryRepository libraryRepo;
    private CirculationService circulationService;
    private CatalogService catalogService;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.libraryRepo = new LibraryRepository();
        this.circulationService = new CirculationService(libraryRepo);
        this.catalogService = new CatalogService(libraryRepo);
    }

    public static void main(String[] args) {
        Main librarySystem = new Main();
        librarySystem.seedSampleData();
        librarySystem.run();
    }

    public void run() {
        System.out.println("=========================================");
        System.out.println("   Library Inventory Management System");
        System.out.println("=========================================");
        System.out.println();

        if (!performLogin()) {
            System.out.println("Authentication failed. Exiting system.");
            return;
        }

        int choice;
        do {
            displayMainMenu();
            choice = readIntegerInput("Enter your choice (1-6): ");
            
            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleBorrowBook();
                    break;
                case 3:
                    handleReturnBook();
                    break;
                case 4:
                    handleSearchCatalog();
                    break;
                case 5:
                    handleViewTransactions();
                    break;
                case 6:
                    System.out.println("Thank you for using LIMS!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
                    break;
            }
        } while (choice != 6);

        scanner.close();
        System.out.println("System shutdown complete.");
    }

    private boolean performLogin() {
        System.out.println("=== Library Staff Authentication ===");
        
        for (int attempts = 1; attempts <= 3; attempts++) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (authService.validateUser(username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
                return true;
            } else {
                if (attempts < 3) {
                    System.out.println("Invalid credentials. " + (3 - attempts) + " attempts remaining.");
                } else {
                    System.out.println("Maximum attempts reached.");
                }
            }
        }
        return false;
    }

    private void displayMainMenu() {
        System.out.println();
        System.out.println("=== Library Main Menu ===");
        System.out.println("1. Add Book");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. Search Catalog");
        System.out.println("5. View Transactions");
        System.out.println("6. Exit System");
    }

    private void handleAddBook() {
        System.out.println("\n=== Add New Book ===");
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        
        System.out.println("Book Types:");
        System.out.println("1. Fiction");
        System.out.println("2. Non-Fiction");
        System.out.println("3. Reference");
        int bookType = readIntegerInput("Select book type (1-3): ");
        
        System.out.print("Enter Category/Genre: ");
        String category = scanner.nextLine();
        
        LibraryItem newBook;
        switch (bookType) {
            case 1:
                newBook = new FictionBook(isbn, title, author, true, category);
                break;
            case 2:
                newBook = new NonFictionBook(isbn, title, author, true, category);
                break;
            case 3:
                newBook = new ReferenceBook(isbn, title, author, true, category);
                break;
            default:
                System.out.println("Invalid book type. Using Fiction as default.");
                newBook = new FictionBook(isbn, title, author, true, category);
                break;
        }
        
        libraryRepo.addBook(newBook);
        System.out.println("✅ Book added successfully: " + title);
        System.out.println("💾 Book saved to database file!");
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void handleBorrowBook() {
        System.out.println("\n=== Borrow Book ===");

        System.out.print("Enter patron ID: ");
        String patronID = scanner.nextLine();

        System.out.print("Enter patron name: ");
        String patronName = scanner.nextLine();

        System.out.print("Enter patron type (Student/Faculty/Staff): ");
        String patronType = scanner.nextLine();

        Patron patron = new Patron(patronID, patronName, patronType);

        libraryRepo.displayAvailableBooks();

        System.out.print("Enter book ISBN to borrow: ");
        String bookISBN = scanner.nextLine();

        LibraryItem selectedBook = libraryRepo.findBookByISBN(bookISBN);
        if (selectedBook == null || !selectedBook.isAvailable()) {
            System.out.println("Book not found or not available for borrowing.");
            return;
        }

        int borrowDays = readIntegerInput("Enter borrowing period (days): ");

        BorrowingTransaction transaction = circulationService.processBorrowing(
            patron, selectedBook, borrowDays);

        System.out.println("\n=== Borrowing Summary ===");
        System.out.println(transaction);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void handleReturnBook() {
        System.out.println("\n=== Return Book ===");

        System.out.print("Enter book ISBN to return: ");
        String bookISBN = scanner.nextLine();

        double lateFee = circulationService.processReturn(bookISBN);
        
        if (lateFee > 0) {
            System.out.println("Late fee: $" + String.format("%.2f", lateFee));
        } else {
            System.out.println("Book returned successfully. No late fee.");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void handleSearchCatalog() {
        System.out.println("\n=== Book Catalog Search ===");
        
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

    private void handleViewTransactions() {
        System.out.println("\n=== Transaction History ===");
        
        List<BorrowingTransaction> transactions = circulationService.getAllTransactions();
        
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

    private int readIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private void seedSampleData() {
        System.out.println("Initializing library with sample books...");
        
        libraryRepo.addBook(new FictionBook(
            "978-0-13-468599-1", "Effective Java", "Joshua Bloch", true, "Programming"));
        libraryRepo.addBook(new FictionBook(
            "978-0-321-35668-0", "Clean Code", "Robert C. Martin", true, "Software Engineering"));
        
        libraryRepo.addBook(new NonFictionBook(
            "978-1-4919-5076-6", "Introduction to Algorithms", "Thomas H. Cormen", true, "Computer Science"));
        libraryRepo.addBook(new NonFictionBook(
            "978-0-262-03293-3", "Structure and Interpretation", "Harold Abelson", true, "Programming"));
        
        libraryRepo.addBook(new ReferenceBook(
            "978-0-13-235088-4", "Java Language Specification", "James Gosling", true, "Reference"));
        
        // Additional sample books
        libraryRepo.addBook(new FictionBook(
            "978-0-07-180855-3", "Java: The Complete Reference", "Herbert Schildt", true, "Programming"));
        libraryRepo.addBook(new FictionBook(
            "978-1-4493-3187-0", "Head First Java", "Kathy Sierra", true, "Education"));
        libraryRepo.addBook(new NonFictionBook(
            "978-0-321-76572-3", "The C++ Programming Language", "Bjarne Stroustrup", true, "Computer Science"));
        libraryRepo.addBook(new NonFictionBook(
            "978-0-13-110362-7", "The C Programming Language", "Brian Kernighan", true, "Programming"));
        libraryRepo.addBook(new ReferenceBook(
            "978-0-13-468599-2", "Python Documentation", "Python Software Foundation", true, "Reference"));
        
        System.out.println("Library initialization complete.");
    }
}
