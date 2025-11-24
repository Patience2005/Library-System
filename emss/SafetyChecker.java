package emss;

/**
 * Encapsulates safety rules using if-else if-else and logical operators.
 * Lecture 5: Decision Making (if-else if-else structures)
 * Lecture 4: Logical Operators (&&, ||)
 */
public class SafetyChecker {

    public SafetyResult evaluateSafety(String skillLevel, int conditionLevel) {
        // Rule 2: URGENT WARNING for any skill level if conditionLevel > 30
        // Lecture 5: Chained if-else if-else structure
        if (conditionLevel > 30) {
            return new SafetyResult("URGENT WARNING",
                    "Dangerous conditions for all skill levels");
        }
        // Rule 1: Beginners denied if conditionLevel > 15
        // Lecture 4: Logical operator AND (&&)
        else if (skillLevel.equalsIgnoreCase("Beginner") && conditionLevel > 15) {
            return new SafetyResult("DENIED",
                    "Conditions too challenging for Beginner");
        }
        // Rule 3: Intermediate caution between 20 and 30
        // Lecture 4: Logical operators AND (&&)
        else if (skillLevel.equalsIgnoreCase("Intermediate")
                && conditionLevel >= 20 && conditionLevel <= 30) {
            return new SafetyResult("CAUTION",
                    "Challenging conditions for Intermediate");
        }
        // Rule 4: Hint for low condition levels
        else if (conditionLevel < 5) {
            return new SafetyResult("HINT",
                    "Low activity level - recommend waiting for better conditions");
        } else {
            // Default: All other conditions are approved
            return new SafetyResult("APPROVED",
                    "Conditions acceptable for this skill level");
        }
    }
}
