package com.lims.controller;

import com.lims.model.LibraryItem;
import com.lims.model.FictionBook;
import com.lims.model.Patron;
import com.lims.service.LibraryService;
import com.lims.dto.BorrowingRequest;
import com.lims.dto.BorrowingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EDUCATIONAL DEMONSTRATION: Spring Boot REST Controller
 * 
 * This class showcases modern Java web development:
 * - REST API design with Spring Boot
 * - HTTP methods and request handling
 * - JSON serialization/deserialization
 * - Swagger/OpenAPI documentation
 * - Educational concept mapping to web operations
 * 
 * @author Student Name
 * @version 2.0
 * @course Java Programming Lectures 1-6 - Enhanced with Web Technologies
 */
@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "*")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    /**
     * L1: Get all available books - HTTP GET operation
     * Demonstrates REST API design and data retrieval
     */
    @Operation(summary = "Get all available books", description = "Retrieve list of all available library items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/books")
    public ResponseEntity<List<LibraryItem>> getAllBooks() {
        // L2: Working with collections and reference types
        List<LibraryItem> books = libraryService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * L4: Search books by title or author - demonstrates logical operators
     */
    @Operation(summary = "Search books", description = "Search books by title or author using logical operators")
    @GetMapping("/books/search")
    public ResponseEntity<List<LibraryItem>> searchBooks(@RequestParam String term) {
        // L4: Logical operators in search implementation
        List<LibraryItem> results = libraryService.searchBooks(term);
        return ResponseEntity.ok(results);
    }

    /**
     * L5: Borrow a book - demonstrates decision making and business logic
     */
    @Operation(summary = "Borrow a book", description = "Process book borrowing with eligibility checking")
    @PostMapping("/borrow")
    public ResponseEntity<BorrowingResponse> borrowBook(@RequestBody BorrowingRequest request) {
        // L5: Decision making in borrowing process
        BorrowingResponse response = libraryService.processBorrowing(request);
        return ResponseEntity.ok(response);
    }

    /**
     * L3: Return a book - demonstrates arithmetic operations for fee calculation
     */
    @Operation(summary = "Return a book", description = "Process book return and calculate late fees")
    @PostMapping("/return/{isbn}")
    public ResponseEntity<String> returnBook(@PathVariable String isbn) {
        // L3: Arithmetic operations in fee calculation
        double lateFee = libraryService.processReturn(isbn);
        String message = lateFee > 0 ? 
            String.format("Book returned. Late fee: $%.2f", lateFee) : 
            "Book returned successfully. No late fee.";
        return ResponseEntity.ok(message);
    }

    /**
     * L2: Get borrowing transactions - data handling and collections
     */
    @Operation(summary = "Get transaction history", description = "Retrieve all borrowing transactions")
    @GetMapping("/transactions")
    public ResponseEntity<List<Object>> getTransactions() {
        // L2: Working with collections and data types
        List<Object> transactions = libraryService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * L6: Add a new book - demonstrates OOP and object creation
     */
    @Operation(summary = "Add a new book", description = "Add a new book to the library catalog")
    @PostMapping("/books")
    public ResponseEntity<LibraryItem> addBook(@RequestBody LibraryItem book) {
        // L6: Object creation and polymorphism
        LibraryItem savedBook = libraryService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    /**
     * L4: Check borrowing eligibility - complex logical operators
     */
    @Operation(summary = "Check borrowing eligibility", description = "Evaluate if a patron can borrow a book")
    @GetMapping("/eligibility/{patronId}/{bookIsbn}")
    public ResponseEntity<String> checkEligibility(@PathVariable String patronId, @PathVariable String bookIsbn) {
        // L4: Complex logical operators in eligibility checking
        boolean eligible = libraryService.checkBorrowingEligibility(patronId, bookIsbn);
        String message = eligible ? "Eligible to borrow" : "Not eligible to borrow";
        return ResponseEntity.ok(message);
    }

    /**
     * L5: Calculate late fee - decision making and arithmetic
     */
    @Operation(summary = "Calculate late fee", description = "Calculate potential late fee for overdue book")
    @GetMapping("/fee/{bookIsbn}/{daysOverdue}")
    public ResponseEntity<String> calculateFee(@PathVariable String bookIsbn, @PathVariable int daysOverdue) {
        // L5: Decision making and arithmetic operations
        double fee = libraryService.calculateLateFee(bookIsbn, daysOverdue);
        String message = String.format("Late fee for %d days: $%.2f", daysOverdue, fee);
        return ResponseEntity.ok(message);
    }
}
