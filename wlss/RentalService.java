package wlss;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates rentals: safety check, transaction creation, logging.
 * Lecture 6: Service layer pattern and dependency injection.
 */
public class RentalService {

    private InventoryManager inventoryManager;
    private SafetyChecker safetyChecker;
    private List<RentalTransaction> transactions = new ArrayList<>();
    private int nextTransactionNumber = 1;

    public RentalService(InventoryManager inventoryManager, SafetyChecker safetyChecker) {
        this.inventoryManager = inventoryManager;
        this.safetyChecker = safetyChecker;
    }

    public RentalTransaction createRental(Customer customer, WaterSportItem selectedItem,
                                          int rentalHours, int windSpeed) {

        // Evaluate safety using the SafetyChecker
        SafetyResult result = safetyChecker.evaluateSafety(customer.getSkillLevel(), windSpeed);

        // Generate transaction ID
        String txId = "T" + nextTransactionNumber;
        nextTransactionNumber++;

        // Create transaction with safety result
        RentalTransaction tx = new RentalTransaction(txId, customer, selectedItem,
                rentalHours, result.getStatus(), result.getMessage());

        // Only proceed with rental if APPROVED or CAUTION
        // Lecture 5: Decision making with logical operators
        if (!"DENIED".equalsIgnoreCase(result.getStatus())) {
            inventoryManager.markItemAsRented(selectedItem.getItemID());
            tx.calculateTotalCost();  // Calculate the actual cost
        }

        // Add transaction to log
        transactions.add(tx);
        return tx;
    }

    public List<RentalTransaction> getAllTransactions() {
        return transactions;
    }
}
