package emss;

/**
 * Represents one rental transaction.
 * Lecture 2: Variables and Data Types (String, int, double, boolean)
 */
public class RentalTransaction {

    private String transactionID;
    private Customer customer;
    private EquipmentItem item;
    private int rentalHours;
    private double totalCost;
    private String safetyStatus;   // DENIED, CAUTION, URGENT WARNING, HINT, APPROVED
    private String safetyMessage;

    public RentalTransaction(String transactionID, Customer customer, EquipmentItem item,
                             int rentalHours, String safetyStatus, String safetyMessage) {
        this.transactionID = transactionID;
        this.customer = customer;
        this.item = item;
        this.rentalHours = rentalHours;
        this.safetyStatus = safetyStatus;
        this.safetyMessage = safetyMessage;
        this.totalCost = 0.0;
    }

    public void calculateTotalCost() {
        // Lecture 4: Arithmetic operators (+)
        // Lecture 6: Polymorphic method call
        this.totalCost = item.calculateRentalFee(rentalHours);
    }

    public String getTransactionID() {
        return transactionID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getSafetyStatus() {
        return safetyStatus;
    }

    public String getSafetyMessage() {
        return safetyMessage;
    }

    @Override
    public String toString() {
        return "Transaction " + transactionID + "\n" +
                "Customer: " + customer + "\n" +
                "Equipment: " + item + "\n" +
                "Hours: " + rentalHours + "\n" +
                "Safety: " + safetyStatus + " - " + safetyMessage + "\n" +
                "Total cost: " + totalCost;
    }
}
