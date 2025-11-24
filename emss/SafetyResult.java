package emss;

/**
 * Simple value object for safety decision output.
 * Lecture 6: Using classes to encapsulate related data.
 */
public class SafetyResult {

    private String status;
    private String message;

    public SafetyResult(String status, String message) {
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
