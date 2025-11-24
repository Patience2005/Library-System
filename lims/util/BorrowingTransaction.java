package lims.util;

import lims.model.Patron;
import lims.model.LibraryItem;

/**
 * EDUCATIONAL DEMONSTRATION: Lecture 2 - Data Types and Variables
 */
public class BorrowingTransaction {

    private String transactionId;
    private Patron patron;
    private LibraryItem book;
    private String bookIsbn;
    private int borrowDays;
    private String dueDate;
    private double potentialLateFee;
    private String status;
    private String message;
    private boolean returned;
    private double actualLateFee;

    public BorrowingTransaction(String transactionId, Patron patron, LibraryItem book, 
                               int borrowDays, String status, String message) {
        this.transactionId = transactionId;
        this.patron = patron;
        this.book = book;
        this.bookIsbn = book.getIsbn();
        this.borrowDays = borrowDays;
        this.status = status;
        this.message = message;
        this.returned = false;
        this.actualLateFee = 0.0;
    }

    public void calculateDueDate(int days) {
        // Simplified due date calculation
        this.dueDate = "2024-12-" + (15 + days);  // Demo calculation
    }

    public int calculateDaysOverdue() {
        // Simplified overdue calculation
        return borrowDays > 14 ? borrowDays - 14 : 0;
    }

    public void setPotentialLateFee(double fee) {
        this.potentialLateFee = fee;
    }

    public void markAsReturned(double lateFee) {
        this.returned = true;
        this.actualLateFee = lateFee;
    }

    public boolean isReturned() {
        return returned;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public Patron getPatron() {
        return patron;
    }

    @Override
    public String toString() {
        return "Transaction " + transactionId + "\n" +
               "Patron: " + patron.getName() + " (" + patron.getType() + ")\n" +
               "Book: " + book.getTitle() + " (ISBN: " + bookIsbn + ")\n" +
               "Borrow Period: " + borrowDays + " days\n" +
               "Due Date: " + dueDate + "\n" +
               "Status: " + status + " - " + message + "\n" +
               "Potential Late Fee: $" + String.format("%.2f", potentialLateFee);
    }
}
