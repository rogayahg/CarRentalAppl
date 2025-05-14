package src.test;
import src.User;
import java.io.*;
//Junit imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    //Test Case 1
    @Test
    @DisplayName("Succsessful Registration Test")
    public void testRegistration() {
        User user = new User("testUser", "hashedPassword", "salt123", false);
        assertEquals("testUser", user.getUsername());
        assertEquals("hashedPassword", user.getPassword());
        assertEquals("salt123", user.getSalt());
        assertFalse(user.isAdmin());
    }

    //Test Case 2
    @Test
    @DisplayName("Admin Privileges Test")
    public void testAdminPrivileges() {
        User adminUser = new User("admin", "hashedPassword", "salt123", true);
        User regularUser = new User("user", "hashedPassword", "salt123", false);

        assertTrue(adminUser.isAdmin());
        assertFalse(regularUser.isAdmin());
    }

    //Test Case 3
    @Test
    @DisplayName("User with NULL Values Test")
    public void testEmptyValues() {
        User user = new User("", "", "", false);
        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
        assertEquals("", user.getSalt());
        assertFalse(user.isAdmin());
    }

    //Test Case 4
    @Test
    @DisplayName("User with ZEROS Values Test")
    public void testNullValues() {
        User user = new User(null, null, null, false);
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getSalt());
    }

    //Test Case 5
    @Test
    @DisplayName("Serialization and Deserialization Test")
    public void testSerialization() throws IOException, ClassNotFoundException {
        User originalUser = new User("testUser", "hashedPassword", "salt123", true);

        // Serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(originalUser);
        out.close();

        // Deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        User deserializedUser = (User) in.readObject();
        in.close();

        // Verify
        assertEquals(originalUser.getUsername(), deserializedUser.getUsername());
        assertEquals(originalUser.getPassword(), deserializedUser.getPassword());
        assertEquals(originalUser.getSalt(), deserializedUser.getSalt());
        assertEquals(originalUser.isAdmin(), deserializedUser.isAdmin());
    }

    //Test Case 6
    @Test
    @DisplayName("Special Characters in Username and Password Test")
    public void testSpecialCharacters() {
        User user = new User("user@123!#", "hash$%^&*", "salt!@#", true);
        assertEquals("user@123!#", user.getUsername());
        assertEquals("hash$%^&*", user.getPassword());
        assertEquals("salt!@#", user.getSalt());
    }
}
