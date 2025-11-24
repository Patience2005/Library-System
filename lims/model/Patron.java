package lims.model;

/**
 * EDUCATIONAL DEMONSTRATION: This class showcases Lecture 2 concepts
 * - Data Types & Variables: Multiple primitive and reference types
 * - Encapsulation: Private fields with public getters/setters
 * - Business logic: Patron borrowing rules and limits
 * 
 * @author Student Name
 * @version 1.0
 * @course Java Programming Lectures 1-6
 */
public class Patron {

    // L2: Instance variables with different data types
    private String patronID;          // L2: Reference type - unique patron identifier
    private String name;              // L2: Reference type - patron full name
    private String type;              // L2: Reference type - patron category
    private int booksBorrowed;        // L2: Primitive type - current book count
    private int overdueCount;         // L2: Primitive type - overdue books count
    private boolean hasOverdues;      // L2: Primitive type - overdue status flag

    /**
     * L2: Constructor with parameter initialization
     */
    public Patron(String patronID, String name, String type) {
        this.patronID = patronID;
        this.name = name;
        this.type = type;
        this.booksBorrowed = 0;       // L2: Initialize to zero
        this.overdueCount = 0;        // L2: Initialize to zero
        this.hasOverdues = false;     // L2: Initialize to false
    }

    // L6: Getter methods - demonstrate encapsulation
    public String getPatronID() {
        return patronID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public boolean hasOverdueBooks() {
        return hasOverdues;
    }

    /**
     * L5: Decision making for patron privileges
     * Returns maximum books allowed based on patron type
     */
    public int getMaxBooksAllowed() {
        // L5: Switch statement for patron type privileges
        switch (type.toLowerCase()) {
            case "faculty":
                return 10;
            case "staff":
                return 8;
            case "graduate":
                return 6;
            case "undergraduate":
                return 4;
            case "community":
                return 2;
            default:
                return 3;  // Default limit
        }
    }

    /**
     * L5: Decision making for borrowing period
     * Returns maximum borrowing days based on patron type
     */
    public int getMaxBorrowingDays() {
        // L5: Switch statement for borrowing periods
        switch (type.toLowerCase()) {
            case "faculty":
                return 60;
            case "staff":
                return 45;
            case "graduate":
                return 30;
            case "undergraduate":
                return 21;
            case "community":
                return 14;
            default:
                return 7;  // Default period
        }
    }

    /**
     * L3: Arithmetic operations for book management
     */
    public void incrementBooksBorrowed() {
        booksBorrowed++;  // L3: Increment operator
    }

    public void decrementBooksBorrowed() {
        if (booksBorrowed > 0) {
            booksBorrowed--;  // L3: Decrement operator
        }
    }

    public void addOverdueBook() {
        overdueCount++;
        hasOverdues = true;  // L3: Assignment operator
    }

    public void removeOverdueBook() {
        if (overdueCount > 0) {
            overdueCount--;
            if (overdueCount == 0) {
                hasOverdues = false;
            }
        }
    }

    /**
     * L6: Overridden toString method
     */
    @Override
    public String toString() {
        // L3: String concatenation
        return name + " (" + type + ") - ID: " + patronID + 
               " [" + booksBorrowed + " books borrowed, " + 
               overdueCount + " overdue]";
    }
}
