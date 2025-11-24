package lims.service;

import lims.model.LibraryItem;
import lims.model.Patron;
import lims.util.BorrowingTransaction;
import lims.util.EligibilityResult;
import lims.repository.LibraryRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * EDUCATIONAL DEMONSTRATION: This class showcases Lectures 4 & 5 concepts
 * - Lecture 4: Logical Operators (&&, ||, !) in borrowing eligibility
 * - Lecture 5: Decision Making (if-else if-else structures) for library rules
 * - Complex conditional logic with multiple borrowing criteria
 * - Business rule implementation for library circulation
 * 
 * @author Student Name
 * @version 1.0
 * @course Java Programming Lectures 1-6
 */
public class CirculationService {

    private LibraryRepository libraryRepo;
    private List<BorrowingTransaction> transactions;
    private int nextTransactionId;

    /**
     * Constructor with dependency injection
     */
    public CirculationService(LibraryRepository libraryRepo) {
        this.libraryRepo = libraryRepo;
        this.transactions = new ArrayList<>();
        this.nextTransactionId = 1;
    }

    /**
     * L4 & L5: Core borrowing eligibility evaluation
     * Demonstrates complex logical expressions and decision structures
     * 
     * @param patron Library patron attempting to borrow
     * @param book Book being requested
     * @param requestedDays Requested borrowing period
     * @return EligibilityResult with status and detailed message
     */
    public EligibilityResult evaluateBorrowingEligibility(Patron patron, LibraryItem book, int requestedDays) {
        
        // L5: Multi-level if-else if-else structure for borrowing rules
        // L4: Complex logical operators combining multiple conditions
        
        // Rule 1: Reference books cannot be borrowed
        if (!book.canBeBorrowed()) {
            return new EligibilityResult("DENIED", 
                "Reference books cannot be borrowed - library use only");
        }
        
        // Rule 2: Book must be available
        if (!book.isAvailable()) {
            return new EligibilityResult("DENIED", 
                "Book is currently borrowed by another patron");
        }
        
        // Rule 3: Check for overdue books using AND operator
        // L4: && operator requires BOTH conditions to be true
        if (patron.hasOverdueBooks() && patron.getOverdueCount() > 0) {
            return new EligibilityResult("DENIED", 
                "Cannot borrow: Return " + patron.getOverdueCount() + " overdue book(s) first");
        }
        
        // Rule 4: Check borrowing limits with multiple AND conditions
        // L4: Three conditions combined with && operators
        if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed() 
                && patron.getBooksBorrowed() > 0 
                && patron.getMaxBooksAllowed() > 0) {
            return new EligibilityResult("DENIED", 
                "Maximum borrowing limit reached (" + patron.getMaxBooksAllowed() + " books)");
        }
        
        // Rule 5: Check requested borrowing period
        if (requestedDays > patron.getMaxBorrowingDays()) {
            return new EligibilityResult("DENIED", 
                "Requested period exceeds maximum allowed (" + patron.getMaxBorrowingDays() + " days)");
        }
        
        // Rule 6: Special rules for different patron types
        // L4: OR operator for flexible eligibility
        boolean isFaculty = patron.getType().equalsIgnoreCase("Faculty");
        boolean isStaff = patron.getType().equalsIgnoreCase("Staff");
        boolean isGraduate = patron.getType().equalsIgnoreCase("Graduate");
        
        if ((isFaculty || isStaff) && requestedDays > 60) {
            return new EligibilityResult("CAUTION", 
                "Extended borrowing period for faculty/staff requires approval");
        }
        
        if (isGraduate && requestedDays > 30) {
            return new EligibilityResult("CAUTION", 
                "Graduate students may need approval for extended borrowing");
        }
        
        // Default Rule: APPROVED - All acceptable combinations
        return new EligibilityResult("APPROVED", 
            "Borrowing request approved for " + requestedDays + " days");
    }

    /**
     * L3, L4, L5: Process complete borrowing transaction
     * Combines eligibility check with business logic
     * 
     * @param patron Library patron
     * @param book Book to borrow
     * @param days Borrowing period in days
     * @return Complete borrowing transaction
     */
    public BorrowingTransaction processBorrowing(Patron patron, LibraryItem book, int days) {
        
        // L5: Evaluate eligibility first
        EligibilityResult eligibility = evaluateBorrowingEligibility(patron, book, days);
        
        // Generate transaction ID
        String transactionId = "TXN" + String.format("%04d", nextTransactionId);
        nextTransactionId++;
        
        // Create transaction record
        BorrowingTransaction transaction = new BorrowingTransaction(
            transactionId, patron, book, days, eligibility.getStatus(), eligibility.getMessage());
        
        // L4: Process borrowing only if approved or with caution
        // L4: OR operator allows both APPROVED and CAUTION statuses
        if ("APPROVED".equals(eligibility.getStatus()) || "CAUTION".equals(eligibility.getStatus())) {
            
            // Update book availability
            book.setAvailable(false);
            libraryRepo.updateBook(book);
            
            // Update patron borrowing count
            patron.incrementBooksBorrowed();
            
            // Calculate due date (simplified for demo)
            transaction.calculateDueDate(days);
            
            // L3: Calculate potential late fee for display
            double potentialFee = book.calculateLateFee(days);
            transaction.setPotentialLateFee(potentialFee);
        }
        
        // Add to transaction history
        transactions.add(transaction);
        
        return transaction;
    }

    /**
     * L3: Process book return with fee calculations
     * Demonstrates arithmetic operators and date calculations
     * 
     * @param bookISBN ISBN of book being returned
     * @return Calculated late fee (0.0 if returned on time)
     */
    public double processReturn(String bookISBN) {
        
        // Find the book
        LibraryItem book = libraryRepo.findBookByISBN(bookISBN);
        if (book == null) {
            System.out.println("Book not found in catalog.");
            return 0.0;
        }
        
        // Mark book as available
        book.setAvailable(true);
        libraryRepo.updateBook(book);
        
        // Find the borrowing transaction
        BorrowingTransaction transaction = findActiveTransaction(bookISBN);
        if (transaction == null) {
            System.out.println("No active borrowing transaction found for this book.");
            return 0.0;
        }
        
        // Calculate days overdue (simplified calculation)
        int daysOverdue = transaction.calculateDaysOverdue();
        
        // L3: Calculate late fee using polymorphic method
        double lateFee = 0.0;
        if (daysOverdue > 0) {
            lateFee = book.calculateLateFee(daysOverdue);
            
            // L3: Apply additional charges for excessive overdue periods
            if (daysOverdue > 30) {
                lateFee = lateFee * 1.5;  // 50% surcharge for very late returns
                System.out.println("Excessive overdue surcharge (50%) applied.");
            }
        }
        
        // Update transaction
        transaction.markAsReturned(lateFee);
        
        // Update patron record
        transaction.getPatron().decrementBooksBorrowed();
        
        System.out.println("Book returned successfully. Days overdue: " + daysOverdue);
        return lateFee;
    }

    /**
     * L4: Demonstration of complex logical conditions
     * Alternative eligibility checking method
     * 
     * @param patron Library patron
     * @return boolean indicating if patron is in good standing
     */
    public boolean isPatronInGoodStanding(Patron patron) {
        // L4: Complex logical expression with multiple conditions
        boolean hasNoOverdues = !patron.hasOverdueBooks();
        boolean withinBorrowingLimit = patron.getBooksBorrowed() < patron.getMaxBooksAllowed();
        boolean validPatronType = patron.getType() != null && !patron.getType().isEmpty();
        
        // L4: AND operator requires ALL conditions to be true
        return hasNoOverdues && withinBorrowingLimit && validPatronType;
    }

    /**
     * L5: Switch statement for patron type processing
     * 
     * @param patronType Type of patron
     * @return Maximum borrowing days allowed
     */
    public int getMaxBorrowingDays(String patronType) {
        // L5: Switch statement for efficient discrete value handling
        switch (patronType.toLowerCase()) {
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
                return 7;  // L5: Default case for unknown patron types
        }
    }

    /**
     * Helper method to find active transaction for a book
     */
    private BorrowingTransaction findActiveTransaction(String bookISBN) {
        for (BorrowingTransaction tx : transactions) {
            if (tx.getBookIsbn().equals(bookISBN) && !tx.isReturned()) {
                return tx;
            }
        }
        return null;
    }

    /**
     * Get all transactions
     */
    public List<BorrowingTransaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
