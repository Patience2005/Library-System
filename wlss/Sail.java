package wlss;

/**
 * Concrete subclass of WaterSportItem representing a sail.
 * Lecture 6: Inheritance and Polymorphism demonstration.
 */
public class Sail extends WaterSportItem {

    private float areaSqMeters;
    private String material;

    public Sail(String itemID, String itemName, double rentalRatePerHour, boolean available,
                float areaSqMeters, String material) {
        super(itemID, itemName, rentalRatePerHour, available);
        this.areaSqMeters = areaSqMeters;
        this.material = material;
    }

    @Override
    public double calculateRentalFee(int hours) {
        // Lecture 4: Arithmetic operators (*, +)
        // Lecture 3: Using double for monetary calculations
        double baseFee = rentalRatePerHour * hours;
        
        // Lecture 5: Decision making with if statement
        // Add 5% discount if material is Standard
        if ("Standard".equalsIgnoreCase(material)) {
            baseFee = baseFee * 0.95;  // 5% discount
        }
        
        return baseFee;
    }

    @Override
    public String toString() {
        return "Sail " + itemID + " [name=" + itemName + ", area=" + areaSqMeters + "m2, material=" + material +
                ", rate=" + rentalRatePerHour + ", available=" + available + "]";
    }
}
