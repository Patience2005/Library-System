package com.lims.model;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

/**
 * EDUCATIONAL DEMONSTRATION: Fiction Book Entity with JPA
 * 
 * This class showcases Lecture 6 concepts:
 * - Inheritance: Extends LibraryItem with JPA
 * - Polymorphism: Overrides calculateLateFee method
 * - Entity mapping and database persistence
 * - Modern Java with BigDecimal for financial calculations
 * 
 * @author Student Name
 * @version 2.0
 * @course Java Programming Lectures 1-6 - Enhanced with Web Technologies
 */
@Entity
public class FictionBook extends LibraryItem {

    private String genre;     // L2: Reference type - fiction genre

    /**
     * L6: Default constructor for JPA
     */
    public FictionBook() {}

    /**
     * L6: Constructor with parameters
     */
    public FictionBook(String isbn, String title, String author, boolean available, 
                      BigDecimal rentalRatePerHour, String genre) {
        super(isbn, title, author, available, rentalRatePerHour);
        this.genre = genre;
    }

    // L6: Getter and setter methods
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * L6: Polymorphic method implementation
     * Fiction books have standard late fee rates with genre-based discounts
     * 
     * @param daysOverdue Number of days overdue
     * @return Total late fee for fiction books
     */
    @Override
    public BigDecimal calculateLateFee(int daysOverdue) {
        // L3: BigDecimal arithmetic for precise financial calculations
        BigDecimal baseRate = new BigDecimal("0.25"); // $0.25 per day
        BigDecimal baseFee = baseRate.multiply(new BigDecimal(daysOverdue));
        
        // L5: Decision making for genre-based adjustments
        if ("Science Fiction".equalsIgnoreCase(genre) || "Fantasy".equalsIgnoreCase(genre)) {
            // Popular genres get 10% discount
            baseFee = baseFee.multiply(new BigDecimal("0.90"));
        }
        
        return baseFee;
    }

    /**
     * L6: Fiction books can always be borrowed
     * 
     * @return true - fiction books are borrowable
     */
    @Override
    public boolean canBeBorrowed() {
        return true;
    }

    /**
     * L6: Overridden toString method with genre information
     * 
     * @return Detailed string representation
     */
    @Override
    public String toString() {
        return super.toString() + " [Fiction - " + genre + "]";
    }
}
