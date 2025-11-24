# EMSS Project Structure and Presentation Guide

## 📁 Project Organization

```
EMSS/
├── 📄 README.md                    # Project overview and quick start
├── 📄 PROJECT_STRUCTURE.md         # This file - structure and presentation guide
├── 📁 docs/                        # Documentation folder
│   ├── 📄 SRS.md                   # Software Requirements Specification
│   ├── 📄 SDS.md                   # Software Design Specification  
│   └── 📄 UserManual.md            # User manual with examples
├── 📁 src/                         # Source code (professional structure)
│   └── 📁 emss/                    # Main package
│       ├── 📄 Main.java            # Entry point (renamed from EMSSApp)
│       ├── 📄 model/               # Domain model classes
│       │   ├── 📄 EquipmentItem.java
│       │   ├── 📄 SportsEquipment.java
│       │   ├── 📄 TrainingGear.java
│       │   └── 📄 Customer.java
│       ├── 📄 service/             # Business logic classes
│       │   ├── 📄 RentalService.java
│       │   ├── 📄 SafetyService.java
│       │   └── 📄 AuthService.java
│       ├── 📄 repository/          # Data management classes
│       │   └── 📄 InventoryRepository.java
│       └── 📄 util/                # Utility classes
│           ├── 📄 SafetyResult.java
│           └── 📄 RentalTransaction.java
├── 📁 lib/                         # External libraries (if any)
├── 📁 test/                        # Test cases (future enhancement)
└── 📁 build/                       # Compiled classes
```

## 🎯 Educational Concept Mapping

### Lecture 1: Java Basics
- **File**: `src/emss/Main.java`
- **Concepts**: `main()` method, program entry point, basic console I/O

### Lecture 2: Data Types & Variables  
- **Files**: All model classes
- **Concepts**: `String`, `int`, `double`, `float`, `boolean`, variable declarations

### Lecture 3: Operators & Expressions
- **Files**: `SportsEquipment.java`, `TrainingGear.java`, `RentalTransaction.java`
- **Concepts**: Arithmetic operators (`+`, `*`), comparison operators

### Lecture 4: Logical Operators
- **File**: `src/emss/service/SafetyService.java`
- **Concepts**: `&&`, `||` operators in safety rule evaluation

### Lecture 5: Decision Making
- **File**: `src/emss/service/SafetyService.java`
- **Concepts**: `if-else if-else` chains, conditional logic

### Lecture 6: OOP Concepts
- **Files**: All model classes
- **Concepts**: Classes, objects, inheritance, polymorphism, abstraction

## 📋 Presentation Structure

### 1. Introduction (2 minutes)
- Project overview: Equipment Management and Safety System
- Educational objectives and Java concepts demonstrated
- System architecture overview

### 2. System Architecture (3 minutes)
- Layered architecture explanation
- Package structure and responsibilities
- Class relationships and design patterns

### 3. Code Walkthrough - Core Concepts (10 minutes)

#### 3.1 Inheritance & Polymorphism
- **Show**: `EquipmentItem.java` → `SportsEquipment.java` & `TrainingGear.java`
- **Explain**: Abstract class, method overriding, polymorphic behavior

#### 3.2 Decision Making & Logic
- **Show**: `SafetyService.java` safety rule evaluation
- **Explain**: `if-else if-else` chains, logical operators

#### 3.3 Data Types & Operations
- **Show**: Fee calculation methods
- **Explain**: Primitive types, arithmetic operators, type casting

### 4. Live Demo (5 minutes)
- Compile and run the application
- Demonstrate all menu options
- Show safety rule evaluation in action

### 5. Conclusion (2 minutes)
- Summary of implemented concepts
- Potential enhancements
- Q&A

## 🔧 Build & Run Instructions

```bash
# Compile the project
javac src/emss/*.java src/emss/*/*.java -d build

# Run the application  
java -cp build emss.Main
```

## 📚 Documentation References

- **SRS**: Functional requirements and system specifications
- **SDS**: Technical design and architecture details
- **User Manual**: Step-by-step usage guide

## 🎨 Code Quality Standards

- Clear comments explaining educational concepts
- Consistent naming conventions
- Proper package organization
- Comprehensive error handling
- Clean, readable code structure

## 🚀 Key Features to Highlight

1. **Object-Oriented Design**: Clear inheritance hierarchy
2. **Safety Logic**: Complex decision-making with multiple conditions
3. **User Interface**: Clean console interaction
4. **Data Management**: In-memory storage with proper encapsulation
5. **Educational Value**: Each lecture concept clearly demonstrated
