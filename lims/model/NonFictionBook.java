package lims.model;

/**
 * EDUCATIONAL DEMONSTRATION: Lecture 6 - Inheritance and Polymorphism
 */
public class NonFictionBook extends LibraryItem {

    private String subject;

    public NonFictionBook(String isbn, String title, String author, boolean available, String subject) {
        super(isbn, title, author, available);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public double calculateLateFee(int daysOverdue) {
        double baseFee = daysOverdue * 0.20;  // Lower rate for educational materials
        
        // Academic subjects get discount
        if ("Computer Science".equalsIgnoreCase(subject) || "Mathematics".equalsIgnoreCase(subject)) {
            baseFee = baseFee * 0.85;
        }
        
        return baseFee;
    }

    @Override
    public boolean canBeBorrowed() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " [Non-Fiction - " + subject + "]";
    }
}
