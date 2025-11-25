public class FictionBook extends LibraryItem {

    private String genre;

    public FictionBook(String isbn, String title, String author, boolean available, String genre) {
        super(isbn, title, author, available);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public double calculateLateFee(int daysOverdue) {
        double baseFee = daysOverdue * 0.25;
        
        if ("Science Fiction".equalsIgnoreCase(genre) || "Fantasy".equalsIgnoreCase(genre)) {
            baseFee = baseFee * 0.90;
        }
        
        return baseFee;
    }

    @Override
    public boolean canBeBorrowed() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " [Fiction - " + genre + "]";
    }
}
