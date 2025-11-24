# 📚 Library Management System - Detailed Examples

## 🎯 Java Programming Concepts Demonstrated

This document provides detailed examples of how each Java lecture concept is implemented in the Library Management System.

---

## **Lecture 1: Java Basics - Program Structure & I/O**

### **Example 1: Main Method & Program Entry**
```java
// Location: lims/Main.java
public class Main {
    public static void main(String[] args) {
        // L1: Program entry point
        Main librarySystem = new Main();
        librarySystem.run();
    }
}
```

### **Example 2: Console Input/Output**
```java
// L1: Reading user input
Scanner scanner = new Scanner(System.in);
System.out.print("Enter username: ");
String username = scanner.nextLine();

// L1: Displaying output
System.out.println("Welcome, " + username + "!");
```

### **Example 3: Object Creation**
```java
// L1: Creating objects
AuthService authService = new AuthService();
LibraryRepository libraryRepo = new LibraryRepository();
```

---

## **Lecture 2: Data Types & Variables**

### **Example 1: String Data Types**
```java
// Location: lims/model/LibraryItem.java
protected String isbn;        // Book identifier
protected String title;       // Book title  
protected String author;      // Book author
```

### **Example 2: Numeric Data Types**
```java
// Location: lims/model/Patron.java
private int booksBorrowed;        // Count of borrowed books
private int overdueCount;         // Count of overdue books
private int maxBorrowingDays;     // Maximum allowed days

// Location: lims/model/LibraryItem.java
private double lateFee;           // Late fee amount
```

### **Example 3: Boolean Data Types**
```java
// Location: lims/model/LibraryItem.java
private boolean available;        // Book availability status
private boolean hasOverdues;      // Patron overdue status
```

---

## **Lecture 3: Operators & Expressions**

### **Example 1: Arithmetic Operators**
```java
// Location: lims/model/FictionBook.java
@Override
public double calculateLateFee(int daysOverdue) {
    // L3: Arithmetic operators
    double baseFee = daysOverdue * 0.25;  // Multiplication
    baseFee = baseFee * 0.90;             // Compound assignment
    
    return baseFee;
}
```

### **Example 2: Relational Operators**
```java
// Location: lims/service/CirculationService.java
if (borrowDays > patron.getMaxBorrowingDays()) {
    return new EligibilityResult("DENIED", "Period exceeds maximum allowed");
}

if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed()) {
    return new EligibilityResult("DENIED", "Maximum limit reached");
}
```

### **Example 3: Assignment Operators**
```java
// Location: lims/model/Patron.java
public void incrementBooksBorrowed() {
    booksBorrowed++;        // L3: Increment operator
}

public void setAvailable(boolean available) {
    this.available = available;  // L3: Assignment operator
}
```

---

## **Lecture 4: Logical Operators**

### **Example 1: AND Operator (&&)**
```java
// Location: lims/service/CirculationService.java
// L4: AND operator - both conditions must be true
if (patron.hasOverdueBooks() && patron.getOverdueCount() > 0) {
    return new EligibilityResult("DENIED", 
        "Return overdue books first");
}

// Complex AND with multiple conditions
if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed() 
        && patron.getBooksBorrowed() > 0 
        && patron.getMaxBooksAllowed() > 0) {
    return new EligibilityResult("DENIED", "Maximum limit reached");
}
```

### **Example 2: OR Operator (||)**
```java
// Location: lims/service/CirculationService.java
// L4: OR operator - either condition can be true
boolean isFaculty = patron.getType().equalsIgnoreCase("Faculty");
boolean isStaff = patron.getType().equalsIgnoreCase("Staff");

if ((isFaculty || isStaff) && requestedDays > 60) {
    return new EligibilityResult("CAUTION", "Extended borrowing requires approval");
}
```

### **Example 3: NOT Operator (!)**
```java
// Location: lims/model/ReferenceBook.java
@Override
public boolean canBeBorrowed() {
    return false;  // L4: NOT concept - cannot be borrowed
}

// Usage in service
if (!book.canBeBorrowed()) {
    return new EligibilityResult("DENIED", "Reference books cannot be borrowed");
}
```

---

## **Lecture 5: Decision Making**

### **Example 1: if-else if-else Structure**
```java
// Location: lims/service/CirculationService.java
public EligibilityResult evaluateBorrowingEligibility(Patron patron, LibraryItem book, int requestedDays) {
    // L5: Multi-level decision making
    if (!book.canBeBorrowed()) {
        return new EligibilityResult("DENIED", "Reference books cannot be borrowed");
    } else if (!book.isAvailable()) {
        return new EligibilityResult("DENIED", "Book is currently borrowed");
    } else if (patron.hasOverdueBooks() && patron.getOverdueCount() > 0) {
        return new EligibilityResult("DENIED", "Return overdue books first");
    } else if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed()) {
        return new EligibilityResult("DENIED", "Maximum borrowing limit reached");
    } else if (requestedDays > patron.getMaxBorrowingDays()) {
        return new EligibilityResult("DENIED", "Requested period exceeds maximum allowed");
    } else {
        return new EligibilityResult("APPROVED", "Borrowing request approved");
    }
}
```

### **Example 2: Switch Statement**
```java
// Location: lims/model/Patron.java
public int getMaxBorrowingDays() {
    // L5: Switch statement for discrete values
    switch (patronType.toLowerCase()) {
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
            return 7;  // L5: Default case
    }
}
```

### **Example 3: Nested Decision Making**
```java
// Location: lims/service/CirculationService.java
public BorrowingTransaction processBorrowing(Patron patron, LibraryItem book, int days) {
    EligibilityResult eligibility = evaluateBorrowingEligibility(patron, book, days);
    
    // L5: Nested decision making
    if ("APPROVED".equals(eligibility.getStatus()) || "CAUTION".equals(eligibility.getStatus())) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            patron.incrementBooksBorrowed();
            // Create transaction...
        }
    }
    
    return transaction;
}
```

---

## **Lecture 6: OOP Concepts**

### **Example 1: Inheritance**
```java
// Location: lims/model/LibraryItem.java (Abstract Base Class)
public abstract class LibraryItem {
    protected String isbn;
    protected String title;
    protected String author;
    protected boolean available;
    
    public abstract double calculateLateFee(int daysOverdue);
    public abstract boolean canBeBorrowed();
}

// Location: lims/model/FictionBook.java (Subclass)
public class FictionBook extends LibraryItem {
    private String genre;
    
    @Override
    public double calculateLateFee(int daysOverdue) {
        // L6: Inherited and overridden method
        return daysOverdue * 0.25;
    }
}
```

### **Example 2: Polymorphism**
```java
// Location: lims/service/CirculationService.java
public double calculateTotalLateFees(List<LibraryItem> books) {
    double total = 0.0;
    
    // L6: Polymorphism - same method call, different behavior
    for (LibraryItem book : books) {
        total += book.calculateLateFee(daysOverdue);  // Polymorphic call
    }
    
    return total;
}
```

### **Example 3: Encapsulation**
```java
// Location: lims/model/Patron.java
public class Patron {
    // L6: Private fields - encapsulation
    private String patronID;
    private String name;
    private int booksBorrowed;
    
    // L6: Public getters/setters - controlled access
    public String getPatronID() {
        return patronID;
    }
    
    public void setPatronID(String patronID) {
        this.patronID = patronID;
    }
    
    // L6: Public methods for business logic
    public void incrementBooksBorrowed() {
        if (booksBorrowed < getMaxBooksAllowed()) {
            booksBorrowed++;
        }
    }
}
```

### **Example 4: Abstraction**
```java
// Location: lims/model/LibraryItem.java
public abstract class LibraryItem {
    // L6: Abstract class - cannot be instantiated
    // L6: Abstract methods - must be implemented by subclasses
    
    public abstract double calculateLateFee(int daysOverdue);
    public abstract boolean canBeBorrowed();
    
    // L6: Concrete methods - shared implementation
    public String getBasicInfo() {
        return title + " by " + author;
    }
}
```

---

## **🎮 Interactive Examples for Presentation**

### **Demo Script for Lectures 1-6**

#### **Lecture 1 Demo:**
1. Show program structure in `Main.java`
2. Demonstrate console I/O with login
3. Show object creation of services

#### **Lecture 2 Demo:**
1. Show different data types in `LibraryItem.java`
2. Demonstrate variable declarations
3. Show data type usage in forms

#### **Lecture 3 Demo:**
1. Borrow a book and show arithmetic calculations
2. Return a book and see fee computations
3. Show relational operators in limits

#### **Lecture 4 Demo:**
1. Try borrowing with different conditions
2. Show logical operators in eligibility rules
3. Demonstrate complex condition evaluation

#### **Lecture 5 Demo:**
1. Show multi-level decision making
2. Try different patron types with switch
3. Demonstrate nested if-else structures

#### **Lecture 6 Demo:**
1. Show inheritance hierarchy of books
2. Demonstrate polymorphic fee calculations
3. Show encapsulation with private fields

---

## **📱 Frontend Examples**

### **Web Interface Demonstrations:**
- **View Books Tab**: Shows data structures and collections
- **Borrow Book Tab**: Shows logical operators and decision making
- **Return Book Tab**: Shows arithmetic calculations
- **Search Books Tab**: Shows string operations and inheritance

### **Interactive Features:**
- Form validation and user input
- Dynamic content loading
- Simulated business logic
- Professional UI/UX design

---

**This comprehensive example guide provides everything needed for an impressive educational presentation!** 🎓
