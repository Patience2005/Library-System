package lims.service;

import java.util.HashMap;
import java.util.Map;

/**
 * EDUCATIONAL DEMONSTRATION: Lecture 2 - Using Map collection for data storage
 */
public class AuthService {

    private Map<String, String> users = new HashMap<>();

    public AuthService() {
        // Demo library staff users
        users.put("librarian", "admin123");
        users.put("staff", "library");
        users.put("admin", "password");
    }

    public boolean validateUser(String username, String password) {
        if (!users.containsKey(username)) {
            return false;
        }
        String stored = users.get(username);
        return stored.equals(password);
    }
}
