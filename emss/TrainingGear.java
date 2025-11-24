package emss;

/**
 * Concrete subclass of EquipmentItem representing training gear.
 * Lecture 6: Inheritance and Polymorphism demonstration.
 */
public class TrainingGear extends EquipmentItem {

    private String difficulty;
    private String ageGroup;

    public TrainingGear(String itemID, String itemName, double rentalRatePerHour, boolean available,
                        String difficulty, String ageGroup) {
        super(itemID, itemName, rentalRatePerHour, available);
        this.difficulty = difficulty;
        this.ageGroup = ageGroup;
    }

    @Override
    public double calculateRentalFee(int hours) {
        // Lecture 4: Arithmetic operators (*, +)
        // Lecture 3: Using double for monetary calculations
        double baseFee = rentalRatePerHour * hours;
        
        // Lecture 5: Decision making with if statement
        // Add 5% discount if difficulty is Beginner
        if ("Beginner".equalsIgnoreCase(difficulty)) {
            baseFee = baseFee * 0.95;  // 5% discount for beginner equipment
        }
        
        return baseFee;
    }

    @Override
    public String toString() {
        return "TrainingGear " + itemID + " [name=" + itemName + ", difficulty=" + difficulty + 
                ", ageGroup=" + ageGroup + ", rate=" + rentalRatePerHour + ", available=" + available + "]";
    }
}
