package wlss;

/**
 * Represents a customer renting equipment.
 * Lecture 2: Variables and Data Types (String, int)
 */
public class Customer {

    private String customerID;
    private String name;
    private String skillLevel;  // "Beginner", "Intermediate", "Advanced"

    public Customer(String customerID, String name, String skillLevel) {
        this.customerID = customerID;
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    @Override
    public String toString() {
        return name + " (" + skillLevel + ")";
    }
}
