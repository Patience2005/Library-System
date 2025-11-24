package wlss;

/**
 * Encapsulates safety rules using if-else if-else and logical operators.
 * Lecture 5: Decision Making (if-else if-else structures)
 * Lecture 4: Logical Operators (&&, ||)
 */
public class SafetyChecker {

    public SafetyResult evaluateSafety(String skillLevel, int windSpeed) {
        // Rule 2: URGENT WARNING for any skill level if windSpeed > 30
        // Lecture 5: Chained if-else if-else structure
        if (windSpeed > 30) {
            return new SafetyResult("URGENT WARNING",
                    "Dangerous conditions for all skill levels");
        }
        // Rule 1: Beginners denied if windSpeed > 15
        // Lecture 4: Logical operator AND (&&)
        else if (skillLevel.equalsIgnoreCase("Beginner") && windSpeed > 15) {
            return new SafetyResult("DENIED",
                    "Wind too strong for Beginner");
        }
        // Rule 3: Intermediate caution between 20 and 30 knots
        // Lecture 4: Logical operators AND (&&)
        else if (skillLevel.equalsIgnoreCase("Intermediate")
                && windSpeed >= 20 && windSpeed <= 30) {
            return new SafetyResult("CAUTION",
                    "Challenging conditions for Intermediate");
        }
        // Rule 4: Hint for low wind
        else if (windSpeed < 5) {
            return new SafetyResult("HINT",
                    "Low wind - recommend waiting");
        } else {
            // Default: All other conditions are approved
            return new SafetyResult("APPROVED",
                    "Conditions acceptable");
        }
    }
}
