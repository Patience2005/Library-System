package wlss;

/**
 * Concrete subclass of WaterSportItem representing a windsurf board.
 * Lecture 6: Inheritance and Polymorphism demonstration.
 */
public class WindsurfBoard extends WaterSportItem {

    private float volumeLitres;
    private String finsType;

    public WindsurfBoard(String itemID, String itemName, double rentalRatePerHour, boolean available,
                         float volumeLitres, String finsType) {
        super(itemID, itemName, rentalRatePerHour, available);
        this.volumeLitres = volumeLitres;
        this.finsType = finsType;
    }

    @Override
    public double calculateRentalFee(int hours) {
        // Lecture 4: Arithmetic operators (*, +)
        // Lecture 3: Using double for monetary calculations
        double baseFee = rentalRatePerHour * hours;
        
        // Lecture 5: Decision making with if statement
        // Add 15% surcharge if volume is below performance threshold
        if (volumeLitres < 100.0f) {
            baseFee = baseFee * 1.15;  // 15% surcharge
        }
        
        return baseFee;
    }

    @Override
    public String toString() {
        return "Board " + itemID + " [name=" + itemName + ", vol=" + volumeLitres + "L, fins=" + finsType +
                ", rate=" + rentalRatePerHour + ", available=" + available + "]";
    }
}
