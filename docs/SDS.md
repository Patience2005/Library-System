# Software Design Specification (SDS)
**System:** Equipment Management and Safety System (EMSS)
**Type:** Java Console Application

---

## 1. Introduction

### 1.1 Purpose of This Document
This Software Design Specification (SDS) describes the internal design of EMSS, including:
- Overall architecture and layers.
- Detailed class design (attributes, methods, relationships).
- Safety logic design.
- Data structures and main control flow.

It translates the SRS into a concrete design to guide Java implementation.

### 1.2 Scope
EMSS is a console-based application that:
- Manages an in-memory inventory of equipment items.
- Manages customers and rental transactions.
- Applies safety rules based on environmental conditions and skill level.
- Uses OOP features: classes, inheritance, polymorphism, and methods.

---

## 2. System Architecture

### 2.1 Architectural Overview
EMSS uses a simple layered architecture:

- **Presentation Layer (UI Layer)**
  - `EMSSApp` (main class)
  - Handles login prompts, main menu, input/output, console display.

- **Application Logic Layer**
  - `RentalService`
  - `SafetyChecker`
  - `InventoryManager`
  - `AuthenticationService`
  - Implements safety rules, rental creation, inventory queries, user validation.

- **Domain Model Layer (Entities)**
  - `EquipmentItem` (abstract class)
  - `SportsEquipment`, `TrainingGear` (subclasses)
  - `Customer`
  - `RentalTransaction`
  - Represents core business data and behavior.

All data is stored in memory (e.g., `ArrayList`).

---

## 3. Detailed Design

### 3.1 Class Diagram (Textual Description)

You can convert this to a UML class diagram.

- **EquipmentItem** (abstract class)
  - **Attributes**:
    - `itemID : String`
    - `itemName : String`
    - `rentalRatePerHour : double`
    - `available : boolean`
  - **Methods**:
    - `isAvailable() : boolean`
    - `setAvailable(available : boolean) : void`
    - `getItemID() : String`
    - `getItemName() : String`
    - `getRentalRatePerHour() : double`
    - `calculateRentalFee(hours : int) : double` (abstract)

- **SportsEquipment** (extends `EquipmentItem`)
  - **Attributes**:
    - `size : float`
    - `type : String`
  - **Methods**:
    - Constructor with all fields.
    - Override `calculateRentalFee(hours : int) : double`
      - Adds 15% surcharge if size > 10.0

- **TrainingGear** (extends `EquipmentItem`)
  - **Attributes**:
    - `difficulty : String`
    - `ageGroup : String`
  - **Methods**:
    - Constructor.
    - Override `calculateRentalFee(hours : int) : double`
      - Adds 5% discount if difficulty is "Beginner"

- **Customer**
  - **Attributes**:
    - `customerID : String`
    - `name : String`
    - `skillLevel : String` ("Beginner", "Intermediate", "Advanced")
  - **Methods**:
    - Getters.
    - Constructor.

- **RentalTransaction**
  - **Attributes**:
    - `transactionID : String`
    - `customer : Customer`
    - `item : WaterSportItem`
    - `rentalHours : int`
    - `totalCost : double`
    - `safetyStatus : String`
    - `safetyMessage : String`
  - **Methods**:
    - Constructor.
    - `calculateTotalCost() : void`
    - `toString() : String` for summary display.

- **SafetyChecker**
  - **Methods**:
    - `evaluateSafety(skillLevel : String, windSpeed : int) : SafetyResult`
  - **Inner Class**:
    - `SafetyResult`
      - `status : String`
      - `message : String`
  - Contains the `if-else if-else` logic from the SRS.

- **InventoryManager**
  - **Attributes**:
    - `items : List<WaterSportItem>`
  - **Methods**:
    - `addItem(item : WaterSportItem) : void`
    - `getAvailableItems() : List<WaterSportItem>`
    - `findItemById(itemID : String) : WaterSportItem`
    - `markItemAsRented(itemID : String) : void`
    - `printAvailableItems() : void`

- **AuthenticationService**
  - **Attributes**:
    - `users : Map<String, String>` (username → password)
  - **Methods**:
    - `isValidUser(username : String, password : String) : boolean`

- **RentalService**
  - **Attributes**:
    - `inventoryManager : InventoryManager`
    - `safetyChecker : SafetyChecker`
    - `transactions : List<RentalTransaction>`
  - **Methods**:
    - `createRental(customer : Customer, item : WaterSportItem, rentalHours : int, windSpeed : int) : RentalTransaction`
    - `getAllTransactions() : List<RentalTransaction>`

- **WLSSApp** (main class)
  - **Attributes**:
    - `scanner : Scanner`
    - Instances of service classes.
  - **Methods**:
    - `main(String[] args)` – entry point.
    - `run() : void` – main loop.
    - `login() : boolean`
    - `showMainMenu() : void`
    - `handleCreateRental() : void`
    - `handleListInventory() : void`
    - `handleViewTransactions() : void`

### 3.2 Class Relationships (UML-style)

- `WaterSportItem`
  - Generalization:
    - `WindsurfBoard` → `WaterSportItem`
    - `Sail` → `WaterSportItem`

- `InventoryManager`
  - Aggregation of `WaterSportItem` (has many items).

- `Customer` and `RentalTransaction`
  - `RentalTransaction` has a reference to exactly one `Customer`.
  - `RentalTransaction` aggregates one `WaterSportItem`.

- `RentalService`
  - Uses `InventoryManager` and `SafetyChecker`.
  - Creates `RentalTransaction` objects.

- `WLSSApp`
  - Uses `AuthenticationService`, `RentalService`, `InventoryManager`.

---

## 4. Component Design

### 4.1 WaterSportItem and Subclasses

**Design Rationale:**
- Use inheritance to show shared attributes and behavior.
- Use polymorphism for `calculateRentalFee()` to demonstrate OOP concepts.

**Fee Formulas:**
- `WindsurfBoard.calculateRentalFee(hours)`:
  ```java
  double baseFee = rentalRatePerHour * hours;
  if (volumeLitres < 100.0f) {
      baseFee = baseFee * 1.15; // 15% surcharge
  }
  return baseFee;
  ```

- `Sail.calculateRentalFee(hours)`:
  ```java
  double baseFee = rentalRatePerHour * hours;
  if ("Standard".equalsIgnoreCase(material)) {
      baseFee = baseFee * 0.95; // 5% discount
  }
  return baseFee;
  ```

### 4.2 SafetyChecker Logic

`evaluateSafety(skillLevel, windSpeed)` uses chained `if-else if-else`:

```java
if (windSpeed > 30) {
    return new SafetyResult("URGENT WARNING", "Dangerous conditions for all skill levels");
} else if (skillLevel.equals("Beginner") && windSpeed > 15) {
    return new SafetyResult("DENIED", "Wind too strong for Beginner");
} else if (skillLevel.equals("Intermediate") && windSpeed >= 20 && windSpeed <= 30) {
    return new SafetyResult("CAUTION", "Challenging conditions for Intermediate");
} else if (windSpeed < 5) {
    return new SafetyResult("HINT", "Low wind - recommend waiting");
} else {
    return new SafetyResult("APPROVED", "Conditions acceptable");
}
```

### 4.3 RentalService

`createRental(...)` will:
1. Call `SafetyChecker.evaluateSafety()`.
2. If status is "DENIED", create transaction with `totalCost = 0` and do not mark item as rented.
3. If "APPROVED" or "CAUTION", mark item as rented, calculate total cost.
4. Add transaction to internal list and return it.

---

## 5. Data Design

### 5.1 In-Memory Data Structures

- **InventoryManager.items**
  - Type: `List<WaterSportItem>` (e.g., `ArrayList<WaterSportItem>`)
  - Initialized at startup with sample boards and sails.

- **RentalService.transactions**
  - Type: `List<RentalTransaction>`
  - Holds all transactions during one run.

- **AuthenticationService.users**
  - Type: `Map<String, String>`
  - Stores demo staff accounts.

### 5.2 ID Generation

- Item IDs: hardcoded (e.g., "B001", "S001").
- Transaction IDs: generated using counter ("T1", "T2", etc.).

---

## 6. User Interface Design (Console)

### 6.1 Login Screen
- Prompts for username and password.
- Success: welcome message and main menu.
- Failure: error message, retry up to 3 times.

### 6.2 Main Menu
```
=== Main Menu ===
1. Create New Rental
2. List Available Equipment
3. View All Transactions
4. Exit
```

### 6.3 Create New Rental Flow
1. Prompt for customer name and skill level.
2. Show available equipment.
3. Prompt for item ID selection.
4. Prompt for rental hours.
5. Prompt for wind speed.
6. Evaluate safety and display summary.

---

## 7. Error Handling and Validation

- Validate menu options with error messages and re-display.
- Validate numeric input (`rentalHours`, `windSpeed`) with try-catch.
- Validate item selection with error messages for invalid IDs.
- Reject negative hours or wind speed.

---

## 8. Traceability to SRS

- SRS **FR-EQUIP-1..3** → Implemented by `WaterSportItem` and subclasses with polymorphic `calculateRentalFee`.
- SRS **FR-SAFE-1..3** → Implemented by `SafetyChecker.evaluateSafety()` using `if-else if-else`.
- SRS **FR-RENT-1..4** → Implemented by `RentalTransaction` + `RentalService.createRental()`.
- SRS **FR-UI-1..3** → Implemented in `WLSSApp.run()` and handlers.

---

## 9. Theoretical and Human Resource Management (THRM) Design

### 9.1 Role-Based Access Control (RBAC) Design
- **User Class**: Represents any system user with `userID`, `username`, `role` (enum: `STAFF`, `MANAGER`, `SAFETY_OFFICER`).
- **AuthenticationService** can be extended to validate roles and enforce UI restrictions.

### 9.2 Safety Rule Engine Design
- **SafetyChecker** encapsulates the risk matrix.
- Rules stored in extensible structure for future configuration by Safety Officer.

### 9.3 Audit and Logging Design
- **RentalTransaction** includes `safetyStatus` and `safetyMessage`.
- Future **TransactionLog Service** could persist logs with timestamps and user context.

### 9.4 Human Factors Considerations
- Explicit prompts reduce memory errors.
- Safety messages are uppercase and clear.
- Input validation prevents accidents.

---

## 10. Appendix: UML Class Diagram Description

```
+---------------------+       +-------------------+       +-------------------+
|    WaterSportItem   |<>----|   WindsurfBoard   |       |       Sail        |
+---------------------+       +-------------------+       +-------------------+
| -itemID: String     |       | -volumeLitres: float      | -areaSqMeters: float |
| -itemName: String   |       | -finsType: String          | -material: String    |
| -rentalRate: double |       +-------------------+       +-------------------+
| -available: boolean |       | +calculateRentalFee()      | +calculateRentalFee()|
+---------------------+       +-------------------+       +-------------------+
| +isAvailable()      |
| +setAvailable()     |
| +calculateRentalFee()|
+---------------------+

+-------------------+       +---------------------+       +-------------------+
|      Customer     |1---->*| RentalTransaction  |<>-----|   SafetyChecker   |
+-------------------+       +---------------------+       +-------------------+
| -customerID: String|       | -transactionID: String      | +evaluateSafety()  |
| -name: String      |       | -customer: Customer          +-------------------+
| -skillLevel: String|       | -item: WaterSportItem
+-------------------+       | -rentalHours: int
                            | -totalCost: double
                            | -safetyStatus: String
                            +---------------------+
                            | +calculateTotalCost()
                            | +toString()
                            +---------------------+
```

---
