package com.lims.dto;

/**
 * EDUCATIONAL DEMONSTRATION: Data Transfer Object (DTO)
 * 
 * This class showcases modern web development concepts:
 * - DTO pattern for API communication
 * - Data validation and encapsulation
 * - JSON serialization/deserialization
 * - Educational concept mapping to web data structures
 * 
 * @author Student Name
 * @version 2.0
 * @course Java Programming Lectures 1-6 - Enhanced with Web Technologies
 */
public class BorrowingRequest {

    // L2: Data types for web API request
    private String patronId;          // L2: Reference type - patron identifier
    private String patronName;        // L2: Reference type - patron name
    private String patronType;        // L2: Reference type - patron category
    private String bookIsbn;          // L2: Reference type - book identifier
    private int borrowDays;           // L2: Primitive type - borrowing period

    /**
     * L6: Default constructor for JSON deserialization
     */
    public BorrowingRequest() {}

    /**
     * L6: Constructor with parameters
     */
    public BorrowingRequest(String patronId, String patronName, String patronType, 
                           String bookIsbn, int borrowDays) {
        this.patronId = patronId;
        this.patronName = patronName;
        this.patronType = patronType;
        this.bookIsbn = bookIsbn;
        this.borrowDays = borrowDays;
    }

    // L6: Getter and setter methods - demonstrate encapsulation
    public String getPatronId() {
        return patronId;
    }

    public void setPatronId(String patronId) {
        this.patronId = patronId;
    }

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public String getPatronType() {
        return patronType;
    }

    public void setPatronType(String patronType) {
        this.patronType = patronType;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(int borrowDays) {
        // L5: Input validation with decision making
        if (borrowDays <= 0) {
            throw new IllegalArgumentException("Borrow days must be positive");
        }
        this.borrowDays = borrowDays;
    }

    /**
     * L6: Overridden toString method for debugging
     */
    @Override
    public String toString() {
        return String.format("BorrowingRequest{patronId='%s', patronName='%s', patronType='%s', bookIsbn='%s', borrowDays=%d}",
            patronId, patronName, patronType, bookIsbn, borrowDays);
    }
}
