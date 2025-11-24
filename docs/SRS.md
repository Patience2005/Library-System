# Software Requirements Specification (SRS)
**Project:** Equipment Management and Safety System (EMSS)
**Type:** Standalone Java Console Application

---

## 1. Introduction

### 1.1 Purpose
The purpose of this Software Requirements Specification (SRS) is to define the functional and non-functional requirements for the Equipment Management and Safety System (EMSS). EMSS is a Java console application that manages equipment inventory, rental transactions, and safety compliance checks based on current environmental conditions and user skill level.

This document is intended for:
- **Students / Developers** implementing the EMSS in Java.
- **Lecturers / Evaluators** assessing the correctness and completeness of the implementation.
- **Future maintainers** who may extend the system.

### 1.2 Scope
EMSS supports operations for an equipment rental facility. The system will:
- Manage an inventory of equipment items (sports equipment, training gear) using OOP with inheritance and polymorphism.
- Handle the rental lifecycle:
  - Staff login.
  - Selecting equipment for a customer.
  - Calculating rental fees based on rental duration and equipment rates.
- Enforce safety rules using decision-making logic based on environmental conditions and customer skill level.
- Interact with the user through a text-based console interface using keyboard input.

The system is **single-user and offline**, running on a local machine. It does not include graphics, networking, or persistent database storage (in this version).

### 1.3 Definitions, Acronyms, and Abbreviations
- **EMSS**: Equipment Management and Safety System.
- **OOP**: Object-Oriented Programming.
- **EquipmentItem**: Parent/abstract class or interface representing a generic rentable equipment item.
- **Beginner / Intermediate / Advanced**: Customer skill levels used for safety decisions.
- **Rental Transaction**: A single rental instance linking a customer with one or more items, a duration, and total fee.

### 1.4 References
- Java SE documentation (Oracle).
- Course lecture notes:
  - Lecture 2–4: Data types, variables, operators.
  - Lecture 5: Decision making (if, if-else, if-else-if, logical operators).
  - Lecture 6: Classes, objects, inheritance, polymorphism.

---

## 2. Overall Description

### 2.1 Product Perspective
EMSS is a **new, standalone** console application. It does not integrate with external systems. All data (inventory and customers) may be initialized in memory when the application starts (e.g., hardcoded sample data) and kept for the duration of the program execution only.

The system follows a layered, object-oriented design:
- **Presentation Layer (Console UI):** Handles input/output using `Scanner` and standard console printing.
- **Application Logic Layer:** Contains safety checks, rental fee calculations, and inventory operations.
- **Domain Model Layer:** Contains core classes such as `EquipmentItem` and its subclasses, `Customer`, and `RentalTransaction`.

### 2.2 Product Functions (High-Level)
At a high level, EMSS will:
- **User Authentication**
  - Allow staff to log in using simple username/password.
- **Inventory Management**
  - Represent all equipment items as objects inheriting from `EquipmentItem`.
  - Query which items are available/unavailable.
- **Safety Compliance Checking**
  - Accept environmental condition readings and customer skill level.
  - Apply defined safety rules to approve, deny, or warn on rentals.
- **Rental Management**
  - Create new rental transactions.
  - Calculate rental fees using equipment rates and rental duration.
  - Display a summary (customer, items, duration, price, safety message).

### 2.3 User Characteristics
- **Staff User**
  - Basic computer literacy.
  - Familiar with equipment rental concepts and skill levels.
  - Will operate EMSS via a command-line interface (CLI).

No programming knowledge is assumed for staff users; however, developers must be familiar with Java and OOP.

### 2.4 Constraints
- **Technical Constraints**
  - Implemented in standard Java (e.g., Java 8+), console only.
  - No graphical user interface libraries (e.g., JavaFX or Swing) are required.
  - No external database is required; in-memory data structures (arrays, ArrayLists) may be used.

- **Pedagogical Constraints**
  - Must demonstrate:
    - Use of primitive data types (int, float, double, boolean) and `String`.
    - Arithmetic operators `+`, `-`, `*`, `/`.
    - Decision making with `if`, `if-else`, `if-else-if`, logical operators `&&`, `||`.
    - OOP concepts including classes, objects, inheritance, and polymorphism.

### 2.5 Assumptions and Dependencies
- Staff know how to obtain current environmental condition readings (e.g., from monitoring equipment) and input them correctly.
- Skill levels are limited to predefined values: `"Beginner"`, `"Intermediate"`, `"Advanced"`.
- The system runs on a single local machine with a Java runtime installed.
- Inventory sizes and equipment catalog are relatively small (console-friendly).

---

## 3. Specific Requirements

### 3.1 Functional Requirements

#### 3.1.1 User Authentication
- **FR-AUTH-1**: The system shall prompt the user for a **username** and **password** at startup.
- **FR-AUTH-2**: The system shall validate the credentials against a predefined list (e.g., hardcoded array/list for Staff users).
- **FR-AUTH-3**: If credentials are invalid, the system shall display an error message and allow retry or exit.
- **FR-AUTH-4**: On successful login, the system shall display a main menu of available operations.

#### 3.1.2 Equipment OOP Model
- **FR-EQUIP-1**: The system shall define an **abstract parent class `EquipmentItem`** with:
  - `itemID : String`
  - `itemName : String`
  - `rentalRatePerHour : double`
  - `isAvailable : boolean`
  - Abstract method `double calculateRentalFee(int hours)`

- **FR-EQUIP-2**: The system shall implement at least two subclasses inheriting from `EquipmentItem`:
  - `SportsEquipment` with unique attributes `size : float` and `type : String`
  - `TrainingGear` with unique attributes `difficulty : String` and `ageGroup : String`

- **FR-EQUIP-3**: Each subclass shall implement `calculateRentalFee(int hours)` with unique logic:
  - `SportsEquipment`: Adds 15% surcharge if size is "Large"
  - `TrainingGear`: Adds 5% discount if difficulty is "Beginner"

#### 3.1.3 Safety Compliance Module
- **FR-SAFE-1**: The system shall prompt for `ConditionLevel (int)` and `CustomerSkillLevel (String)`.
- **FR-SAFE-2**: The system shall implement a single chained `if-else if-else` structure with the following rules:
  - **Rule 1 (Beginner Denial)**: If `SkillLevel` is "Beginner" AND `ConditionLevel` > 15 → status = "DENIED"
  - **Rule 2 (Urgent Warning)**: If `ConditionLevel` > 30 → status = "URGENT WARNING" (regardless of skill)
  - **Rule 3 (Intermediate Caution)**: If `SkillLevel` is "Intermediate" AND 20 ≤ `ConditionLevel` ≤ 30 → status = "CAUTION"
  - **Rule 4 (Low Condition Hint)**: If `ConditionLevel` < 5 → status = "HINT"
  - **Default**: All other conditions → status = "APPROVED"

- **FR-SAFE-3**: The system shall display the safety status as part of the rental process.

#### 3.1.4 Rental Transaction Logic
- **FR-RENT-1**: The system shall only proceed with fee calculation if safety status is "APPROVED" or "CAUTION".
- **FR-RENT-2**: The system shall prompt for rental duration in hours (`int`).
- **FR-RENT-3**: The system shall calculate the final fee using the polymorphic method: `item.calculateRentalFee(hours)`.
- **FR-RENT-4**: The system shall display a transaction summary including customer details, selected equipment, duration, total cost, and safety status.

#### 3.1.5 User Interaction (Console I/O)
- **FR-UI-1**: The system shall provide a main menu after login with options to:
  - Create new rental
  - List available equipment
  - View transaction log
  - Exit
- **FR-UI-2**: The system shall use `Scanner` for keyboard input with clear prompts.
- **FR-UI-3**: The system shall handle invalid inputs gracefully with error messages and re-prompts.

---

### 3.2 Non-Functional Requirements

#### 3.2.1 Performance Requirements
- **NFR-PERF-1**: The system shall respond to each user input within 1 second on a standard modern PC.
- **NFR-PERF-2**: The system shall support at least 50 items in memory without noticeable delay.

#### 3.2.2 Reliability and Availability
- **NFR-REL-1**: As a console educational application, WLSS must not crash under normal usage.
- **NFR-REL-2**: The system shall handle invalid input types without crashing.

#### 3.2.3 Usability
- **NFR-USAB-1**: All prompts and messages shall be in clear, simple English.
- **NFR-USAB-2**: Safety decisions shall be visually clear (e.g., uppercase labels).

#### 3.2.4 Portability
- **NFR-PORT-1**: The system shall be platform-independent with a Java Runtime Environment.

#### 3.2.5 Maintainability
- **NFR-MAINT-1**: The code shall be modular with clear separation of concerns.
- **NFR-MAINT-2**: The code shall contain extensive comments explaining OOP structure and decision logic.

---

## 4. Theoretical and Human Resource Management (THRM)

### 4.1 Purpose
This section describes the theoretical foundations and human resource considerations for WLSS, including organizational roles, staff training, safety compliance, and theoretical models.

### 4.2 Organizational Roles
| Role | Description | System Access |
|------|-------------|---------------|
| **Staff** | Handles day-to-day rentals and customer interactions | Standard WLSS console access |
| **Manager** | Oversees inventory, pricing, and transaction logs | Extended reporting capabilities |
| **Safety Officer** | Reviews safety violations and updates thresholds | Consults system logs |

### 4.3 Staff Training and Competency
- **Operational Training**: Console navigation, equipment IDs, wind speed entry, safety message interpretation
- **Safety Theory Training**: Wind speed thresholds, skill level definitions, liability considerations
- **Certification**: Staff must pass a quiz on safety rules

### 4.4 Safety Compliance and Liability
- **Legal Framework**: Safety thresholds aligned with watersports guidelines
- **Audit Trail**: All transactions logged with timestamp and safety decision
- **Incident Reporting**: Managers can retrieve transaction logs for review

### 4.5 Theoretical Foundations
- **Risk Management Model**: Rule-based risk matrix combining wind speed and skill level
- **Decision Theory**: Deterministic decision rules to reduce cognitive load
- **Human Factors**: Explicit prompts reduce memory reliance and improve situational awareness

---

## 5. Appendices

### Appendix A: Safety Compliance Rules Summary
| Rule | Condition | Status | Message |
|------|-----------|--------|---------|
| 1 | Beginner AND Wind > 15 | DENIED | "Wind too strong for Beginner" |
| 2 | Wind > 30 | URGENT WARNING | "Dangerous conditions for all skill levels" |
| 3 | Intermediate AND 20 ≤ Wind ≤ 30 | CAUTION | "Challenging conditions for Intermediate" |
| 4 | Wind < 5 | HINT | "Low wind - recommend waiting" |
| Default | All other cases | APPROVED | "Conditions acceptable" |

### Appendix B: Future Enhancements
- Persistent storage (database or file-based)
- Multiple simultaneous users
- Equipment maintenance tracking
- Integration with external wind sensors

---
