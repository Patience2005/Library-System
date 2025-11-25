import java.util.ArrayList;
import java.util.List;

public class LibraryRepository {

    private List<LibraryItem> books = new ArrayList<>();

    public LibraryRepository() {
        this.books = DatabaseManager.loadBooksFromFile();
    }

    public void addBook(LibraryItem book) {
        books.add(book);
        DatabaseManager.addBookToFile(book);
    }

    public List<LibraryItem> getAllBooks() {
        return new ArrayList<>(books);
    }

    public LibraryItem findBookByISBN(String isbn) {
        for (LibraryItem book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public void updateBook(LibraryItem book) {
        // In a real system, this would update the database
        // For demo, books are updated by reference
    }

    public void displayAvailableBooks() {
        System.out.println("=== Available Books ===");
        for (LibraryItem book : books) {
            if (book.isAvailable()) {
                System.out.println("  " + book);
            }
        }
    }
}
