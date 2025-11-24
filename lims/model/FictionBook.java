package lims.model;

/**
 * EDUCATIONAL DEMONSTRATION: This class showcases Lecture 6 concepts
 * - Inheritance: Extends LibraryItem base class
 * - Polymorphism: Overrides calculateLateFee method
 * - Method Overriding: Provides specific implementation for fiction books
 * - Constructor Chaining: Uses super() to call parent constructor
 * 
 * @author Student Name
 * @version 1.0
 * @course Java Programming Lectures 1-6
 */
public class FictionBook extends LibraryItem {

    // L2: Additional property specific to fiction books
    private String genre;     // L2: Reference type - fiction genre

    /**
     * L6: Constructor demonstrating constructor chaining
     * Uses super() to call parent class constructor
     */
    public FictionBook(String isbn, String title, String author, boolean available, String genre) {
        // L6: Call to parent constructor using super()
        super(isbn, title, author, available);
        
        // L2: Initialize subclass-specific property
        this.genre = genre;
    }

    // L6: Getter method for subclass property
    public String getGenre() {
        return genre;
    }

    /**
     * L6: Polymorphic method implementation
     * Overrides abstract method from parent class
     * Fiction books have standard late fee rates
     * 
     * @param daysOverdue Number of days overdue
     * @return Total late fee for fiction books
     */
    @Override
    public double calculateLateFee(int daysOverdue) {
        // L3: Arithmetic operators for fee calculation
        double baseFee = daysOverdue * 0.25;  // Standard rate: $0.25 per day
        
        // L5: Decision making for genre-based adjustments
        if ("Science Fiction".equalsIgnoreCase(genre) || "Fantasy".equalsIgnoreCase(genre)) {
            // Popular genres get 10% discount
            baseFee = baseFee * 0.90;
        }
        
        return baseFee;
    }

    /**
     * L6: Fiction books can always be borrowed
     * Overrides parent abstract method
     * 
     * @return true - fiction books are borrowable
     */
    @Override
    public boolean canBeBorrowed() {
        return true;  // L2: Boolean literal
    }

    /**
     * L6: Method overriding demonstration
     * Extends parent toString with genre information
     * 
     * @return Detailed string representation including genre
     */
    @Override
    public String toString() {
        // L3: String concatenation with parent method call
        return super.toString() + " [Fiction - " + genre + "]";
    }
}
