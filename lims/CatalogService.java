import java.util.ArrayList;
import java.util.List;

public class CatalogService {

    private LibraryRepository libraryRepo;

    public CatalogService(LibraryRepository libraryRepo) {
        this.libraryRepo = libraryRepo;
    }

    public List<LibraryItem> searchBooks(String searchTerm) {
        List<LibraryItem> results = new ArrayList<>();
        List<LibraryItem> allBooks = libraryRepo.getAllBooks();
        
        for (LibraryItem book : allBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
            }
        }
        
        return results;
    }

    public void displayBooks(List<LibraryItem> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Found " + books.size() + " book(s):");
            for (LibraryItem book : books) {
                System.out.println("  " + book);
            }
        }
    }
}
