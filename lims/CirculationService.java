import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing library circulation operations including
 * borrowing eligibility, transaction processing, and returns.
 */
public class CirculationService {

    private LibraryRepository libraryRepo;
    private List<BorrowingTransaction> transactions;
    private int nextTransactionId;

    public CirculationService(LibraryRepository libraryRepo) {
        this.libraryRepo = libraryRepo;
        this.transactions = new ArrayList<>();
        this.nextTransactionId = 1;
    }

    /**
     * Evaluates whether a patron is eligible to borrow a specific book.
     * Checks availability, overdue status, borrowing limits, and patron type restrictions.
     */
    public EligibilityResult evaluateBorrowingEligibility(Patron patron, LibraryItem book, int requestedDays) {
        
        // Reference books cannot be borrowed
        if (!book.canBeBorrowed()) {
            return new EligibilityResult("DENIED", 
                "Reference books cannot be borrowed - library use only");
        }
        
        // Book must be available
        if (!book.isAvailable()) {
            return new EligibilityResult("DENIED", 
                "Book is currently borrowed by another patron");
        }
        
        // Check for overdue books
        if (patron.hasOverdueBooks() && patron.getOverdueCount() > 0) {
            return new EligibilityResult("DENIED", 
                "Cannot borrow: Return " + patron.getOverdueCount() + " overdue book(s) first");
        }
        
        // Check borrowing limits
        if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed() 
                && patron.getBooksBorrowed() > 0 
                && patron.getMaxBooksAllowed() > 0) {
            return new EligibilityResult("DENIED", 
                "Maximum borrowing limit reached (" + patron.getMaxBooksAllowed() + " books)");
        }
        
        // Check requested borrowing period
        if (requestedDays > patron.getMaxBorrowingDays()) {
            return new EligibilityResult("DENIED", 
                "Requested period exceeds maximum allowed (" + patron.getMaxBorrowingDays() + " days)");
        }
        
        // Special rules for different patron types
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
        
        // Default approval for all acceptable combinations
        return new EligibilityResult("APPROVED", 
            "Borrowing request approved for " + requestedDays + " days");
    }

    /**
     * Processes a complete borrowing transaction including eligibility check,
     * book status updates, and transaction recording.
     */
    public BorrowingTransaction processBorrowing(Patron patron, LibraryItem book, int days) {
        
        // Evaluate eligibility first
        EligibilityResult eligibility = evaluateBorrowingEligibility(patron, book, days);
        
        // Generate transaction ID
        String transactionId = "TXN" + String.format("%04d", nextTransactionId);
        nextTransactionId++;
        
        // Create transaction record
        BorrowingTransaction transaction = new BorrowingTransaction(
            transactionId, patron, book, days, eligibility.getStatus(), eligibility.getMessage());
        
        // Process borrowing only if approved or with caution
        if ("APPROVED".equals(eligibility.getStatus()) || "CAUTION".equals(eligibility.getStatus())) {
            
            // Update book availability
            book.setAvailable(false);
            libraryRepo.updateBook(book);
            
            // Update patron borrowing count
            patron.incrementBooksBorrowed();
            
            // Calculate due date
            transaction.calculateDueDate(days);
            
            // Calculate potential late fee for display
            double potentialFee = book.calculateLateFee(days);
            transaction.setPotentialLateFee(potentialFee);
        }
        
        // Add to transaction history
        transactions.add(transaction);
        
        return transaction;
    }

    /**
     * Processes book return and calculates any applicable late fees.
     * Updates book availability and patron records.
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
        
        // Calculate days overdue
        int daysOverdue = transaction.calculateDaysOverdue();
        
        // Calculate late fee using polymorphic method
        double lateFee = 0.0;
        if (daysOverdue > 0) {
            lateFee = book.calculateLateFee(daysOverdue);
            
            // Apply additional charges for excessive overdue periods
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
     * Checks if a patron is in good standing for borrowing privileges.
     */
    public boolean isPatronInGoodStanding(Patron patron) {
        boolean hasNoOverdues = !patron.hasOverdueBooks();
        boolean withinBorrowingLimit = patron.getBooksBorrowed() < patron.getMaxBooksAllowed();
        boolean validPatronType = patron.getType() != null && !patron.getType().isEmpty();
        
        return hasNoOverdues && withinBorrowingLimit && validPatronType;
    }

    /**
     * Returns maximum borrowing days allowed for different patron types.
     */
    public int getMaxBorrowingDays(String patronType) {
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
                return 7;  // Default case for unknown patron types
        }
    }

    private BorrowingTransaction findActiveTransaction(String bookISBN) {
        for (BorrowingTransaction tx : transactions) {
            if (tx.getBookIsbn().equals(bookISBN) && !tx.isReturned()) {
                return tx;
            }
        }
        return null;
    }

    public List<BorrowingTransaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
