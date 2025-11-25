public class Patron {

    private String patronID;
    private String name;
    private String type;
    private int booksBorrowed;
    private int overdueCount;
    private boolean hasOverdues;

    public Patron(String patronID, String name, String type) {
        this.patronID = patronID;
        this.name = name;
        this.type = type;
        this.booksBorrowed = 0;
        this.overdueCount = 0;
        this.hasOverdues = false;
    }

    public String getPatronID() {
        return patronID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public boolean hasOverdueBooks() {
        return hasOverdues;
    }

    public int getMaxBooksAllowed() {
        switch (type.toLowerCase()) {
            case "faculty":
                return 10;
            case "staff":
                return 8;
            case "graduate":
                return 6;
            case "undergraduate":
                return 4;
            case "community":
                return 2;
            default:
                return 3;  // Default limit
        }
    }

    public int getMaxBorrowingDays() {
        switch (type.toLowerCase()) {
            case "faculty":
                return 60;
            case "staff":
                return 45;
            case "graduate":
                return 30;
            case "undergraduate":
                return 21;
            case "community":
                return 14;
            default:
                return 7;  // Default period
        }
    }

    public void incrementBooksBorrowed() {
        booksBorrowed++;
    }

    public void decrementBooksBorrowed() {
        if (booksBorrowed > 0) {
            booksBorrowed--;
        }
    }

    public void addOverdueBook() {
        overdueCount++;
        hasOverdues = true;
    }

    public void removeOverdueBook() {
        if (overdueCount > 0) {
            overdueCount--;
            if (overdueCount == 0) {
                hasOverdues = false;
            }
        }
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - ID: " + patronID + 
               " [" + booksBorrowed + " books borrowed, " + 
               overdueCount + " overdue]";
    }
}
