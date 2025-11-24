# 📚 Library Inventory Management System (LIMS)

**A comprehensive Java-based library management system with console and web interfaces**

A full-featured library management application that demonstrates modern Java programming practices including object-oriented design, business logic implementation, and user interface development.

---

## 🎯 Project Overview

The Library Inventory Management System (LIMS) is a complete solution for managing library operations including book cataloging, patron management, borrowing/returning transactions, and administrative functions. The system features both a traditional console interface and a modern web-based interface.

---

## 🚀 Quick Start

### **Console Application**
```bash
# Navigate to project directory
cd "C:\Users\ADMIN\Library-System"

# Run the console application
java lims.Main

# Login credentials:
# Username: librarian
# Password: admin123
```

### **Web Application (Optional)**
If you have Maven installed:
```bash
mvn spring-boot:run
# Then open http://localhost:8080
```

---

## 🎮 Features

### **Core Functionality**
- **📚 Book Management**: Add, update, and search library books
- **👤 Patron Management**: Register and manage library patrons
- **📝 Borrowing System**: Process book borrowing with eligibility checks
- **↩️ Return Processing**: Handle book returns with late fee calculations
- **🔍 Advanced Search**: Search books by title, author, or ISBN
- **📊 Transaction History**: View complete borrowing history

### **User Interface Options**
- **Console Interface**: Traditional command-line interface
- **Web Interface**: Modern responsive web application
- **REST API**: Programmatic access to all system functions

---

## 📁 Project Structure

```
Library-System/
├── 📄 README.md                    # This file
├── 📄 EXAMPLES.md                  # Code examples
├── 📄 PROJECT_DEMO.txt             # Demo guide
├── 📁 lims/                        # Console application
│   ├── 📄 Main.java                # Entry point & main menu
│   ├── 📁 model/                   # Domain models
│   │   ├── 📄 LibraryItem.java     # Abstract book class
│   │   ├── 📄 FictionBook.java     # Fiction book implementation
│   │   ├── 📄 NonFictionBook.java  # Non-fiction book implementation
│   │   ├── 📄 ReferenceBook.java   # Reference book implementation
│   │   └── 📄 Patron.java          # Library patron model
│   ├── 📁 service/                 # Business logic
│   │   ├── 📄 CirculationService.java # Borrowing rules & calculations
│   │   ├── 📄 AuthService.java     # User authentication
│   │   └── 📄 CatalogService.java  # Book search functionality
│   ├── 📁 repository/              # Data management
│   │   └── 📄 LibraryRepository.java # Book collection management
│   └── 📁 util/                    # Utility classes
│       ├── 📄 BorrowingTransaction.java # Transaction model
│       └── 📄 EligibilityResult.java     # Eligibility checking
├── 📁 src/main/java/com/lims/      # Web application
│   ├── 📄 LibraryApplication.java  # Spring Boot main class
│   ├── 📁 controller/              # REST controllers
│   ├── 📁 model/                   # JPA entities
│   ├── 📁 service/                 # Business services
│   └── 📁 dto/                     # Data transfer objects
├── 📁 src/main/resources/
│   ├── 📄 application.properties   # Configuration
│   └── 📁 static/                  # Web frontend files
│       ├── 📄 index.html          # Main web interface
│       ├── 📄 simple_demo.html    # Interactive demo
│       └── 📄 browser_test.html   # Browser test
└── 📄 pom.xml                      # Maven configuration
```

---

## 💻 Code Examples

### **Book Borrowing Logic**
```java
public EligibilityResult evaluateBorrowingEligibility(Patron patron, LibraryItem book, int requestedDays) {
    if (!book.canBeBorrowed()) {
        return new EligibilityResult("DENIED", "Reference books cannot be borrowed");
    } else if (!book.isAvailable()) {
        return new EligibilityResult("DENIED", "Book is currently borrowed");
    } else if (patron.hasOverdueBooks()) {
        return new EligibilityResult("DENIED", "Return overdue books first");
    } else if (patron.getBooksBorrowed() >= patron.getMaxBooksAllowed()) {
        return new EligibilityResult("DENIED", "Maximum borrowing limit reached");
    } else {
        return new EligibilityResult("APPROVED", "Borrowing request approved");
    }
}
```

### **Late Fee Calculation**
```java
public class FictionBook extends LibraryItem {
    @Override
    public double calculateLateFee(int daysOverdue) {
        return daysOverdue * 0.25; // $0.25 per day for fiction books
    }
}
```

---

## 🛠️ Technical Stack

### **Backend**
- **Java 8+** - Core programming language
- **Spring Boot** - Web application framework
- **JPA/Hibernate** - Data persistence
- **H2 Database** - In-memory database for demo
- **Maven** - Build and dependency management

### **Frontend**
- **HTML5/CSS3** - Modern web standards
- **JavaScript** - Interactive functionality
- **Bootstrap** - Responsive design framework
- **Swagger UI** - API documentation

---

## 🎮 Interactive Demo

### **Console Interface**
1. **Launch**: `java lims.Main`
2. **Login**: `librarian` / `admin123`
3. **Navigate** through menu options:
   - Borrow books with eligibility checking
   - Return books with automatic fee calculation
   - Search catalog by title or author
   - View transaction history

### **Web Interface**
1. **Start**: `mvn spring-boot:run`
2. **Access**: http://localhost:8080
3. **Features**:
   - Modern responsive design
   - Interactive book catalog
   - Online borrowing/returning
   - Real-time search functionality

---

## 🏆 Key Features

### **Business Logic**
- **Eligibility Checking**: Complex rules for borrowing permissions
- **Fee Calculation**: Automated late fee computation
- **Inventory Management**: Real-time book availability tracking
- **Patron Management**: Different privilege levels for users

### **Data Management**
- **In-Memory Storage**: Fast demo data access
- **Transaction Logging**: Complete audit trail
- **Search Functionality**: Quick book lookup
- **Status Tracking**: Real-time availability updates

### **User Experience**
- **Intuitive Interface**: Easy-to-use menu system
- **Error Handling**: Comprehensive input validation
- **Feedback System**: Clear success/error messages
- **Help Documentation**: Built-in user guidance

---

## 📞 Documentation

- **📄 EXAMPLES.md** - Detailed code examples
- **📄 PROJECT_DEMO.txt** - Step-by-step demo guide
- **📄 GITHUB_SETUP.md** - Git and GitHub setup instructions
- **💬 Inline Comments** - Comprehensive code documentation

---

## 🎯 Perfect For

- **Software Development Portfolios** - Professional Java project
- **Educational Demonstrations** - Programming concepts showcase
- **Library Management** - Real-world application scenario
- **System Design Examples** - Complete application architecture
- **Interview Preparation** - Practical coding examples

---

**🚀 Ready to deploy and demonstrate! This comprehensive library management system showcases professional Java development with both console and web interfaces.**

---

*Last Updated: November 2024*  
*Version: 2.0 - Professional Edition*

## 🌐 Web Interface Access

### **Main Application**
- **URL**: http://localhost:8080
- **Features**: Modern responsive web interface
- **Technology**: HTML5, CSS3, JavaScript, REST API

### **API Documentation (Swagger)**
- **URL**: http://localhost:8080/swagger-ui.html
- **Features**: Interactive API testing
- **Endpoints**: All library operations documented
- **Usage**: Test APIs directly in browser

### **Database Console (H2)**
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Username**: `sa`
- **Password**: `password`
- **Features**: Live database queries and management

## 🎯 Educational Concepts Demonstrated

✅ **Lecture 1**: Java Basics (main method, program structure, I/O)  
✅ **Lecture 2**: Data Types (String, int, double, boolean)  
✅ **Lecture 3**: Operators (arithmetic, relational, assignment)  
✅ **Lecture 4**: Logical Operators (&&, ||, !)  
✅ **Lecture 5**: Decision Making (if-else if-else, switch)  
✅ **Lecture 6**: OOP (inheritance, polymorphism, abstraction)  

## 📊 System Features

- **Patron Management**: Different borrowing limits by type
- **Book Catalog**: Fiction, NonFiction, Reference books
- **Circulation Rules**: Complex eligibility checking
- **Fee Calculation**: Automated late fee computation
- **Transaction Tracking**: Complete borrowing history

## 🔧 Demo Credentials

- **Username**: `librarian` / **Password**: `admin123`
- **Username**: `staff` / **Password**: `library`

---

**Ready to run and demonstrate all Java programming concepts!**
