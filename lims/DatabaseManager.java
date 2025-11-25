import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple file-based database for storing library books
 * Books are saved to books.txt file in CSV format
 */
public class DatabaseManager {
    
    private static final String BOOKS_FILE = "books.txt";
    
    /**
     * Save all books to file
     * Format: isbn,title,author,available,type,category
     */
    public static void saveBooksToFile(List<LibraryItem> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (LibraryItem book : books) {
                String line = book.getIsbn() + "," + 
                             book.getTitle() + "," + 
                             book.getAuthor() + "," + 
                             book.isAvailable() + "," + 
                             getBookType(book) + "," + 
                             getBookCategory(book);
                writer.println(line);
            }
            System.out.println("Books saved to " + BOOKS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }
    
    /**
     * Load books from file
     */
    public static List<LibraryItem> loadBooksFromFile() {
        List<LibraryItem> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        
        if (!file.exists()) {
            System.out.println("No existing database found. Creating new one.");
            return books; // Return empty list if file doesn't exist
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    LibraryItem book = createBookFromParts(parts);
                    if (book != null) {
                        books.add(book);
                    }
                }
            }
            System.out.println("Loaded " + books.size() + " books from database.");
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        
        return books;
    }
    
    /**
     * Add a new book to the database file
     */
    public static void addBookToFile(LibraryItem book) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE, true))) {
            String line = book.getIsbn() + "," + 
                         book.getTitle() + "," + 
                         book.getAuthor() + "," + 
                         book.isAvailable() + "," + 
                         getBookType(book) + "," + 
                         getBookCategory(book);
            writer.println(line);
            System.out.println("Book added to database: " + book.getTitle());
        } catch (IOException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }
    
    /**
     * Get book type as string
     */
    private static String getBookType(LibraryItem book) {
        if (book instanceof FictionBook) return "Fiction";
        if (book instanceof NonFictionBook) return "NonFiction";
        if (book instanceof ReferenceBook) return "Reference";
        return "Unknown";
    }
    
    /**
     * Get book category/subject
     */
    private static String getBookCategory(LibraryItem book) {
        if (book instanceof FictionBook) return ((FictionBook) book).getGenre();
        if (book instanceof NonFictionBook) return ((NonFictionBook) book).getSubject();
        if (book instanceof ReferenceBook) return ((ReferenceBook) book).getCategory();
        return "General";
    }
    
    /**
     * Create book object from CSV parts
     */
    private static LibraryItem createBookFromParts(String[] parts) {
        try {
            String isbn = parts[0];
            String title = parts[1];
            String author = parts[2];
            boolean available = Boolean.parseBoolean(parts[3]);
            String type = parts[4];
            String category = parts[5];
            
            switch (type) {
                case "Fiction":
                    return new FictionBook(isbn, title, author, available, category);
                case "NonFiction":
                    return new NonFictionBook(isbn, title, author, available, category);
                case "Reference":
                    return new ReferenceBook(isbn, title, author, available, category);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error creating book from data: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if database file exists
     */
    public static boolean databaseExists() {
        return new File(BOOKS_FILE).exists();
    }
    
    /**
     * Delete database file (for testing/reset)
     */
    public static void clearDatabase() {
        File file = new File(BOOKS_FILE);
        if (file.delete()) {
            System.out.println("Database cleared.");
        } else {
            System.out.println("Could not clear database.");
        }
    }
}
