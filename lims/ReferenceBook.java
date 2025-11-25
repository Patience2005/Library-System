public class ReferenceBook extends LibraryItem {

    private String category;

    public ReferenceBook(String isbn, String title, String author, boolean available, String category) {
        super(isbn, title, author, available);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public double calculateLateFee(int daysOverdue) {
        return 0.0;
    }

    @Override
    public boolean canBeBorrowed() {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " [Reference - " + category + " - LIBRARY USE ONLY]";
    }
}
