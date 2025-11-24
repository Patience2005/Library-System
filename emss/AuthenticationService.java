package emss;

import java.util.HashMap;
import java.util.Map;

/**
 * Very simple authentication using in-memory username/password pairs.
 * Lecture 2: Using Map collection for data storage.
 */
public class AuthenticationService {

    private Map<String, String> users = new HashMap<>();

    public AuthenticationService() {
        // Demo users
        users.put("staff1", "pass123");
        users.put("manager", "admin");
    }

    public boolean isValidUser(String username, String password) {
        if (!users.containsKey(username)) {
            return false;
        }
        String stored = users.get(username);
        return stored.equals(password);
    }
}
