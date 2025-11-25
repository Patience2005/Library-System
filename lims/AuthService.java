import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private Map<String, String> users = new HashMap<>();

    public AuthService() {
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
