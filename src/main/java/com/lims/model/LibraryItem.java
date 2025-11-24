package com.lims.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * EDUCATIONAL DEMONSTRATION: Enhanced JPA Entity Model
 * 
 * This class showcases Lecture 6 concepts with modern web development:
 * - Object-Oriented Programming: JPA Entity base class
 * - Data persistence with database mapping
 * - Inheritance with JPA strategy
 * - Modern Java annotations
 * 
 * @author Student Name
 * @version 2.0
 * @course Java Programming Lectures 1-6 - Enhanced with Web Technologies
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class LibraryItem {

    // L2: JPA annotations for database mapping
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // L2: Primary key for database
    
    @Column(unique = true, nullable = false)
    private String isbn;               // L2: Reference type - unique book identifier
    
    @Column(nullable = false)
    private String title;              // L2: Reference type - book title
    
    @Column(nullable = false)
    private String author;             // L2: Reference type - book author
    
    @Column(nullable = false)
    private boolean available;         // L2: Primitive type - availability status
    
    @Column(precision = 10, scale = 2)
    private BigDecimal rentalRatePerHour; // L2: BigDecimal for precise financial calculations

    /**
     * L6: Default constructor for JPA
     */
    public LibraryItem() {}

    /**
     * L6: Constructor with parameters
     */
    public LibraryItem(String isbn, String title, String author, boolean available, BigDecimal rentalRatePerHour) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
        this.rentalRatePerHour = rentalRatePerHour;
    }

    // L6: Getter methods - demonstrate encapsulation
    public Long getId() {
        return id;
    }

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

    public BigDecimal getRentalRatePerHour() {
        return rentalRatePerHour;
    }

    // L6: Setter methods - demonstrate controlled access
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setRentalRatePerHour(BigDecimal rentalRatePerHour) {
        this.rentalRatePerHour = rentalRatePerHour;
    }

    /**
     * L6: Abstract method for polymorphic behavior
     * Each book type implements its own late fee calculation
     * 
     * @param daysOverdue Number of days overdue
     * @return Calculated late fee
     */
    public abstract BigDecimal calculateLateFee(int daysOverdue);

    /**
     * L6: Abstract method for borrowing eligibility
     * 
     * @return true if the book can be borrowed
     */
    public abstract boolean canBeBorrowed();

    /**
     * L6: Overridden toString method
     * 
     * @return String representation of the library item
     */
    @Override
    public String toString() {
        // L3: String concatenation with modern formatting
        return String.format("%s | %s by %s [%s]", isbn, title, author, available ? "Available" : "Borrowed");
    }
}
