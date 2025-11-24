package emss;

/**
 * Concrete subclass of EquipmentItem representing sports equipment.
 * Lecture 6: Inheritance and Polymorphism demonstration.
 */
public class SportsEquipment extends EquipmentItem {

    private float size;
    private String type;

    public SportsEquipment(String itemID, String itemName, double rentalRatePerHour, boolean available,
                           float size, String type) {
        super(itemID, itemName, rentalRatePerHour, available);
        this.size = size;
        this.type = type;
    }

    @Override
    public double calculateRentalFee(int hours) {
        // Lecture 4: Arithmetic operators (*, +)
        // Lecture 3: Using double for monetary calculations
        double baseFee = rentalRatePerHour * hours;
        
        // Lecture 5: Decision making with if statement
        // Add 15% surcharge if size is Large
        if (size > 10.0f) {
            baseFee = baseFee * 1.15;  // 15% surcharge for large equipment
        }
        
        return baseFee;
    }

    @Override
    public String toString() {
        return "SportsEquipment " + itemID + " [name=" + itemName + ", size=" + size + ", type=" + type +
                ", rate=" + rentalRatePerHour + ", available=" + available + "]";
    }
}
