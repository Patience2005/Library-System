public abstract class LibraryItem {

    protected String isbn;
    protected String title;
    protected String author;
    protected boolean available;

    public LibraryItem(String isbn, String title, String author, boolean availability) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = availability;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateLateFee(int daysOverdue);

    public abstract boolean canBeBorrowed();

    @Override
    public String toString() {
        String status = available ? "Available" : "Borrowed";
        return isbn + " | " + title + " by " + author + " [" + status + "]";
    }
}
