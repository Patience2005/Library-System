package lims.model;

/**
 * EDUCATIONAL DEMONSTRATION: This class showcases Lecture 6 concepts
 * - Object-Oriented Programming: Abstract base class for all library items
 * - Encapsulation: Protected fields with getter/setter methods
 * - Abstraction: Abstract method for polymorphic behavior
 * - Inheritance: Base class for all book types (Fiction, NonFiction, Reference)
 * 
 * @author Student Name
 * @version 1.0
 * @course Java Programming Lectures 1-6
 */
public abstract class LibraryItem {

    // L2: Instance variables with different data types for library items
    protected String isbn;               // L2: Reference type - unique book identifier
    protected String title;              // L2: Reference type - book title
    protected String author;             // L2: Reference type - book author
    protected boolean available;         // L2: Primitive type - borrowing availability

    /**
     * L6: Constructor - demonstrates parameter initialization
     * @param isbn Unique book identifier (ISBN-13 format)
     * @param title Full book title
     * @param author Book author name
     * @param availability Current borrowing status
     */
    public LibraryItem(String isbn, String title, String author, boolean availability) {
        // L2: Variable assignments using 'this' keyword
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = availability;
    }

    // L6: Getter methods - demonstrate encapsulation
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    // L6: Setter method - demonstrates controlled access to internal state
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * L6: Abstract method - demonstrates abstraction and polymorphism
     * Each book type must implement this method with its own fee calculation logic
     * 
     * @param daysOverdue Number of days the book is overdue
     * @return Total late fee for the specified overdue period
     */
    public abstract double calculateLateFee(int daysOverdue);

    /**
     * L6: Abstract method for borrowing eligibility
     * Different book types have different borrowing rules
     * 
     * @return true if the book can be borrowed, false otherwise
     */
    public abstract boolean canBeBorrowed();

    /**
     * L6: Overridden toString method - demonstrates method overriding
     * Provides a string representation of the library item
     * 
     * @return Formatted string with book details
     */
    @Override
    public String toString() {
        // L3: String concatenation with + operator
        String status = available ? "Available" : "Borrowed";
        return isbn + " | " + title + " by " + author + " [" + status + "]";
    }
}
