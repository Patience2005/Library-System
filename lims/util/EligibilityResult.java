package lims.util;

/**
 * EDUCATIONAL DEMONSTRATION: Lecture 6 - Using classes to encapsulate related data
 */
public class EligibilityResult {

    private String status;
    private String message;

    public EligibilityResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
