package com.lims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EDUCATIONAL DEMONSTRATION: Spring Boot Application Entry Point
 * 
 * This class demonstrates:
 * - Lecture 1: Java Basics - Spring Boot main method
 * - Modern Java application structure
 * - Web application startup and configuration
 * 
 * @author Student Name
 * @version 2.0
 * @course Java Programming Lectures 1-6 - Enhanced with Web Interface
 */
@SpringBootApplication
public class LibraryApplication {

    /**
     * L1: Main method - Spring Boot application entry point
     * This replaces the console main method with a web-based approach
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // L1: Start the Spring Boot application
        SpringApplication.run(LibraryApplication.class, args);
        
        // L1: Console output for educational purposes
        System.out.println("=========================================");
        System.out.println("  Library Management System - Web Edition");
        System.out.println("         Educational Java Project");
        System.out.println("=========================================");
        System.out.println("🌐 Web Interface: http://localhost:8080");
        System.out.println("📚 Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("=========================================");
        System.out.println("Demonstrating Lectures 1-6 + Web Technologies");
    }
}
