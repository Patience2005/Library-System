package emss;

/**
 * Abstract parent class for all rentable equipment items.
 * Demonstrates inheritance and polymorphism (Lecture 6).
 */
public abstract class EquipmentItem {

    protected String itemID;
    protected String itemName;
    protected double rentalRatePerHour;
    protected boolean available;

    public EquipmentItem(String itemID, String itemName, double rentalRatePerHour, boolean available) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.rentalRatePerHour = rentalRatePerHour;
        this.available = available;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getRentalRatePerHour() {
        return rentalRatePerHour;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Polymorphic method: each subclass calculates rental fee differently.
     * Lecture 6: Method Overriding for Polymorphism
     */
    public abstract double calculateRentalFee(int hours);

    @Override
    public String toString() {
        return itemID + " [name=" + itemName + ", rate=" + rentalRatePerHour + ", available=" + available + "]";
    }
}
