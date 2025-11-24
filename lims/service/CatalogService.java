package lims.service;

import lims.model.LibraryItem;
import lims.repository.LibraryRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * EDUCATIONAL DEMONSTRATION: Lecture 3 - Operators and Expressions
 */
public class CatalogService {

    private LibraryRepository libraryRepo;

    public CatalogService(LibraryRepository libraryRepo) {
        this.libraryRepo = libraryRepo;
    }

    public List<LibraryItem> searchBooks(String searchTerm) {
        List<LibraryItem> results = new ArrayList<>();
        List<LibraryItem> allBooks = libraryRepo.getAllBooks();
        
        // L3: String comparison and logical operators
        for (LibraryItem book : allBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
            }
        }
        
        return results;
    }
}
