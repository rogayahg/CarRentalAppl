package src;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String passwordHash;
    private String salt;

    private boolean isAdmin;

    public User(String username, String passwordHash, String salt,boolean isAdmin) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return passwordHash;
    }
    public String getSalt() {
        return salt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
