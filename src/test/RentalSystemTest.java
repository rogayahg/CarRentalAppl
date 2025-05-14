package src.test;
import src.Car;
import src.RentalSystem;
import src.User;
//Junit imports
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.AfterEach;


import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentalSystemTest {

    private RentalSystem rentalSystem;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        // Set the current directory to the temp directory for file operations
        System.setProperty("user.dir", tempDir.toString());
        rentalSystem = new RentalSystem();
    }

    @AfterEach
    public void tearDown() {
        // Ensure we clean up any test files
        new File(tempDir.toString() + "/users.dat").delete();
    }

    //Test Case 1
    @Test
    @DisplayName("Default Admin Registration Test")
    public void testDefaultAdminCreation() {
        // Login with default admin credentials
        assertTrue(rentalSystem.login("admin", "Admin.123"));
        assertTrue(rentalSystem.isAdmin());
        assertNotNull(rentalSystem.getCurrentUser());
        assertEquals("admin", rentalSystem.getCurrentUser().getUsername());
    }

    //Test Case 2
    @Test
    @DisplayName("User Registration & Login Test")
    public void testUserRegistrationAndLogin() {
        // Register a new user
        assertTrue(rentalSystem.register("testuser1", "P@ssword123"));

        // Login with new user
        assertTrue(rentalSystem.login("testuser1", "P@ssword123"));
        assertFalse(rentalSystem.isAdmin());
        assertEquals("testuser1", rentalSystem.getCurrentUser().getUsername());

        // Test duplicate registration
        assertFalse(rentalSystem.register("testuser1", "AnotherP@ss123"));
    }

    //Test Case 3
    @Test
    @DisplayName("User Logout Test")
    public void testLogout() {
        // Login first
        assertTrue(rentalSystem.login("admin", "Admin.123"));
        assertNotNull(rentalSystem.getCurrentUser());

        // Logout
        rentalSystem.logout();
        assertNull(rentalSystem.getCurrentUser());
    }

    //Test Case 4
    @Test
    @DisplayName("Reccomend Best Car Functionality Test")
    public void testFindBestCars() {
        // Login as admin
        rentalSystem.login("admin", "Admin.123");

        // Test with 4 passengers, 5 days, 300 miles
        List<Car> cars = rentalSystem.findBestCars(4, 5, 300);

        // Should return at least one car
        assertFalse(cars.isEmpty());

        // Cars should be sorted by trip cost (lowest first)
        for (int i = 0; i < cars.size() - 1; i++) {
            double cost1 = cars.get(i).calculateTripCost(5, 300);
            double cost2 = cars.get(i + 1).calculateTripCost(5, 300);
            assertTrue(cost1 <= cost2);
        }

        // All cars should fit the number of passengers
        for (Car car : cars) {
            assertTrue(car.canFitPassengers(4));
        }
    }

    //Test Case 5
    @Test
    @DisplayName("Password Verification Test (Multiple Attempts)")
    public void testPasswordVerification() {
        // Register user with known password
        rentalSystem.register("passtest", "Test@123");

        // Correct password should work
        assertTrue(rentalSystem.login("passtest", "Test@123"));

        // Logout for next tests
        rentalSystem.logout();

        // Incorrect password should fail
        assertFalse(rentalSystem.login("passtest", "WrongPassword"));
        assertFalse(rentalSystem.login("passtest", "Test@1234"));
        assertFalse(rentalSystem.login("passtest", "test@123")); // Case sensitive
    }

    //Test Case 6
    @Test
    @DisplayName("Admin Access to Users List Test")
    public void testAdminAccessControl() {
        // Register regular users
        rentalSystem.register("user1", "User@123");
        rentalSystem.register("user2", "User@456");

        // Login as regular user
        rentalSystem.login("user1", "User@123");

        // Regular user should get empty list when trying to access user list
        assertTrue(rentalSystem.getUserList().isEmpty());

        // Logout and login as admin
        rentalSystem.logout();
        rentalSystem.login("admin", "Admin.123");

        // Admin should see all users
        List<User> users = rentalSystem.getUserList();
        assertFalse(users.isEmpty());

        // Should contain at least admin, user1, and user2
        boolean foundAdmin = false;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        for (User user : users) {
            if ("admin".equals(user.getUsername())) foundAdmin = true;
            if ("user1".equals(user.getUsername())) foundUser1 = true;
            if ("user2".equals(user.getUsername())) foundUser2 = true;
        }

        assertTrue(foundAdmin);
        assertTrue(foundUser1);
        assertTrue(foundUser2);
    }

    //Test Case 7
    @Test
    @DisplayName("PAssword HAshing And Salt Test")
    public void testPasswordHashingAndSalt() {
        // Register two users with the same password
        rentalSystem.register("hashtest1", "Same@123");
        rentalSystem.register("hashtest2", "Same@123");

        // Login to get the users
        rentalSystem.login("admin", "Admin.123");
        List<User> users = rentalSystem.getUserList();

        User user1 = null;
        User user2 = null;

        for (User user : users) {
            if ("hashtest1".equals(user.getUsername())) user1 = user;
            if ("hashtest2".equals(user.getUsername())) user2 = user;
        }

        assertNotNull(user1);
        assertNotNull(user2);

        // Same password should generate different hashes due to different salts
        assertNotEquals(user1.getPassword(), user2.getPassword());
        assertNotEquals(user1.getSalt(), user2.getSalt());

        // But login should work for both
        rentalSystem.logout();
        assertTrue(rentalSystem.login("hashtest1", "Same@123"));
        rentalSystem.logout();
        assertTrue(rentalSystem.login("hashtest2", "Same@123"));
    }
}