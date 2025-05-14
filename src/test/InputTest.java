package src.test;
import src.Input;
//Junit imports
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {

    /**
     * Helper method to create an Input object with simulated user input
     */
    private Input createInputWithSimulatedUserInput(String simulatedInput) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        return new Input(scanner);
    }

    //Test CAse 1
    @ParameterizedTest
    @CsvSource({
            "1, true",
            "3, true",
            "7, true",
            "0, false",
            "8, false",
            "-1, false"
    })
    @DisplayName("Passenger Input Test")
    public void testValidatePassengers(int passengers, boolean expected) {
        // Arrange
        Input input = createInputWithSimulatedUserInput("");

        // Act
        boolean result = input.validatePassengers(passengers);

        // Assert
        assertEquals(expected, result, "validatePassengers should return " + expected + " for input: " + passengers);
    }

    //Test CAse 2
    @ParameterizedTest
    @CsvSource({
            "1, true",
            "15, true",
            "31, true",
            "0, false",
            "32, false",
            "-5, false"
    })
    @DisplayName("Days Input Validation Test")
    public void testValidateDays(int days, boolean expected) {
        // Arrange
        Input input = createInputWithSimulatedUserInput("");

        // Act
        boolean result = input.validateDays(days);

        // Assert
        assertEquals(expected, result, "validateDays should return " + expected + " for input: " + days);
    }

    //Test Case 3
    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2500, true",
            "5000, true",
            "0, false",
            "5001, false",
            "-100, false"
    })
    @DisplayName("Mileage Input Test")
    public void testValidateMileage(int mileage, boolean expected) {
        // Arrange
        Input input = createInputWithSimulatedUserInput("");

        // Act
        boolean result = input.validateMileage(mileage);

        // Assert
        assertEquals(expected, result, "validateMileage should return " + expected + " for input: " + mileage);
    }

    //Test Case 4
    @Test
    @DisplayName("getInputPassengers Method Test")
    public void testGetInputPassengers() {
        // Arrange - first invalid input, then valid input
        String simulatedInput = "0\nabc\n5\n";
        Input input = createInputWithSimulatedUserInput(simulatedInput);

        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act
            int result = input.getInputPassengers();

            // Assert
            assertEquals(5, result, "getInputPassengers should return 5 after trying invalid inputs");
            String output = outputStream.toString();
            assertTrue(output.contains("Invalid input"), "Should display error for invalid input");
            assertTrue(output.contains("Please enter a valid number"), "Should display error for non-numeric input");
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }

    //Test Case 5
    @ParameterizedTest
    @CsvSource({
            "user1, true",
            "abcd, true",
            "user_name_20chars_ok, true",
            "abc, false",
            "'', false",
            "'toolongusernameexceeding20chars', false"
    })
    @DisplayName("Username Validation Test")
    public void testValidateUsername(String username, boolean expected) {
        // Arrange
        Input input = createInputWithSimulatedUserInput("");

        // Act
        boolean result = input.validateUsername(username);

        // Assert
        assertEquals(expected, result, "validateUsername should return " + expected + " for input: " + username);
    }

    // Test Case 6
    @ParameterizedTest
    @CsvSource({
            "Password1!, true",
            "12345678!, true",
            "Abcdefg1#, true",
            "short1!, false",
            "nospeci4lchar, false",
            "NoNumber!, false",
            "No$paces Allowed1, false"
    })
    @DisplayName("Passsword Validation Test")
    public void testValidatePassword(String password, boolean expected) {
        // Arrange
        Input input = createInputWithSimulatedUserInput("");

        // Act
        boolean result = input.validatePassword(password);

        // Assert
        assertEquals(expected, result, "validatePassword should return " + expected + " for input: " + password);
    }
}