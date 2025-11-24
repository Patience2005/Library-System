# User Manual
**System:** Equipment Management and Safety System (EMSS)
**Version:** 1.0
**Audience:** Equipment Rental Staff

---

## 1. Introduction

### 1.1 Purpose
This manual explains how to use the **EMSS** console application to:
- Log in as staff.
- View available equipment.
- Create a new rental, including safety checks based on environmental conditions and customer skill level.
- View a log of all transactions during the session.

### 1.2 Target Users
- **Staff** who handle equipment rentals.
- No programming knowledge is required; users only need basic keyboard skills.

---

## 2. System Requirements

- Java Runtime Environment (JRE) installed on the workstation.
- Command line / terminal access.
- The compiled EMSS program (e.g., `EMSSApp.class` or a runnable JAR).

---

## 3. Getting Started

### 3.1 Launching EMSS

1. Open a terminal or command prompt.
2. Navigate to the directory containing the compiled EMSS classes.
3. Run the application:

```bash
java emss.EMSSApp
```

4. The welcome screen will appear, prompting for login.

---

## 4. Logging In

### 4.1 Login Screen

- You will be asked for a **username** and **password**.
- Demo accounts:
  - Username: `staff1` / Password: `pass123`
  - Username: `manager` / Password: `admin`

**Example:**

```
Welcome to Windsurf Logistics & Safety System (WLSS)
Username: staff1
Password: pass123
Login successful. Welcome, staff1!
```

- If credentials are incorrect, you may retry up to three times before the program exits.

---

## 5. Main Menu

After a successful login, the **Main Menu** appears:

```
=== Main Menu ===
1. Create New Rental
2. List Available Equipment
3. View All Transactions
4. Exit
Enter choice:
```

- Enter `1`, `2`, `3`, or `4` to select an option.
- Invalid entries will show an error; you will be prompted again.

---

## 6. Creating a New Rental

### 6.1 Step-by-Step Flow

1. **Select option 1** from the Main Menu.
2. **Enter customer details**:
   - Customer name.
   - Skill level: `Beginner`, `Intermediate`, or `Advanced`.
3. **Select equipment**:
   - The system displays all available items with IDs.
   - Enter one item ID (e.g., `E001` or `T001`).
4. **Enter rental duration** in hours (integer).
5. **Enter current condition level** (1-100 scale).
6. The system evaluates safety rules and shows a summary.

### 6.2 Example Session

#### 6.2.1 Choose "Create New Rental"

```
=== Create New Rental ===
Enter customer name: Alex Lee
Enter skill level (Beginner / Intermediate / Advanced): Beginner
```

#### 6.2.2 View Available Equipment

```
=== Available Equipment ===
SportsEquipment E001 [name=Professional Tennis Racket, size=12.5, type=Racket, rate=15.0, available=true]
SportsEquipment E002 [name=Mountain Bike, size=18.0, type=Bicycle, rate=25.0, available=true]
TrainingGear T001 [name=Balance Board, difficulty=Beginner, ageGroup=All Ages, rate=8.0, available=true]
TrainingGear T002 [name=Agility Ladder, difficulty=Intermediate, ageGroup=Youth, rate=6.0, available=true]
Enter item ID to rent: E001
```

#### 6.2.3 Provide Rental Hours and Condition Level

```
Enter rental duration in hours: 3
Enter current condition level (1-100): 18
```

#### 6.2.4 Safety Evaluation and Summary

Because the customer is a **Beginner** and condition level is **18** (> 15), the system applies **Rule 1** and **denies** the rental:

```
=== Rental Summary ===
Transaction T1
Customer: Alex Lee (Beginner)
Equipment: SportsEquipment E001 [name=Professional Tennis Racket]
Hours: 3
Safety: DENIED - Conditions too challenging for Beginner
Total cost: 0.0
```

If the rental were allowed, the total cost would be calculated and the equipment marked as unavailable for the session.

---

## 7. Listing Available Equipment

### 7.1 How to View Inventory

1. From the Main Menu, select **option 2**.
2. The system prints all equipment with their current availability.

**Example:**

```
=== Available Equipment ===
Board B001 [name=Performance Board, rate=15.0, available=true]
Board B002 [name=Beginner Board, rate=12.0, available=true]
Sail S001 [name=Race Sail, rate=10.0, available=true]
Sail S002 [name=Training Sail, rate=8.0, available=true]
```

- Items that have been rented will show `available=false` until the program ends.

---

## 8. Viewing All Transactions

### 8.1 How to View the Transaction Log

1. From the Main Menu, select **option 3**.
2. All transactions created during this session are displayed in order.

**Example:**

```
=== Transaction Log ===
Transaction T1
Customer: Alex Lee (Beginner)
Equipment: Board B001 [name=Performance Board]
Hours: 3
Safety: DENIED - Wind too strong for Beginner
Total cost: 0.0
----------------------
Transaction T2
Customer: Jamie Chen (Intermediate)
Equipment: Sail S001 [name=Race Sail]
Hours: 2
Safety: CAUTION - Challenging conditions for Intermediate
Total cost: 23.0
----------------------
```

- The log includes both denied and approved rentals, with safety messages and calculated costs.

---

## 9. Safety Rules Reference

| Rule | Condition | Result | Message |
|------|-----------|--------|---------|
| 1 | Skill = Beginner AND Wind > 15 knots | **DENIED** | "Wind too strong for Beginner" |
| 2 | Wind > 30 knots (any skill) | **URGENT WARNING** | "Dangerous conditions for all skill levels" |
| 3 | Skill = Intermediate AND 20 ≤ Wind ≤ 30 knots | **CAUTION** | "Challenging conditions for Intermediate" |
| 4 | Wind < 5 knots (any skill) | **HINT** | "Low wind - recommend waiting" |
| — | All other cases | **APPROVED** | "Conditions acceptable" |

- These rules are enforced automatically when you create a new rental.
- The safety decision is included in the rental summary.

---

## 10. Query Procedures (Safety Compliance Examples)

### 10.1 Example 1: DENIED Status

**Input:**
- Skill Level: `Beginner`
- Wind Speed: `18`

**Expected Output:**
```
Safety: DENIED - Wind too strong for Beginner
Total cost: 0.0
```

### 10.2 Example 2: CAUTION Status

**Input:**
- Skill Level: `Intermediate`
- Wind Speed: `25`

**Expected Output:**
```
Safety: CAUTION - Challenging conditions for Intermediate
Total cost: [calculated fee]
```

### 10.3 Example 3: APPROVED Status

**Input:**
- Skill Level: `Advanced`
- Wind Speed: `12`

**Expected Output:**
```
Safety: APPROVED - Conditions acceptable
Total cost: [calculated fee]
```

---

## 11. Exiting the System

1. From the Main Menu, select **option 4**.
2. The program displays a goodbye message and terminates.

```
Goodbye.
```

---

## 12. Troubleshooting

| Problem | Possible Cause | Solution |
|---------|----------------|----------|
| "Invalid credentials" | Wrong username/password | Verify demo accounts; ensure correct spelling and case. |
| "Please enter a valid integer." | Non-numeric input for hours or wind speed | Enter a whole number (e.g., `3` or `18`). |
| "Item X not found or not available." | Typo or item already rented | Check the inventory list; use exact IDs shown. |
| Program does not start | Java not installed or classpath issue | Install Java; ensure you are in the correct directory. |

---

## 13. Notes and Limitations

- Data is stored only in memory for the current session; closing the program clears all transaction logs and rental states.
- Equipment return functionality is not included in this version; items remain marked as unavailable until the program ends.
- Wind speed must be entered manually; there is no external sensor integration.

---

## 14. Contact and Support

For technical support or questions about WLSS, contact:
- **System Administrator**: [Your Name/Email]
- **Documentation Version**: 1.0

---

**End of User Manual**
