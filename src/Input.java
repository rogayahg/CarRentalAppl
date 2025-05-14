package src;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Input {
    //Password pattern
    //Condition: <=8 characters, one special character, and one number
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*.,?\":{}|<>])(?=\\S+$).{8,}$");

    public Scanner scanner;
    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

    //taking user input for # of passengers
    public int getInputPassengers() {
        int passengers = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter number of passengers (1-7): ");
                String input = scanner.nextLine().trim();
                passengers = Integer.parseInt(input);

                if (validatePassengers(passengers)) {
                    valid = true;
                } else { //if user enters invalid input
                    System.out.println("Invalid input. Number of passengers must be between 1 and 7.");
                }
            } catch (NumberFormatException e) { //exception handling
                System.out.println("Please enter a valid number.");
            }
        }

        return passengers;
    }

    //taking user input for # of days
    public int getInputDays() {
        int days = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter number of rental days (1-31): ");
                String input = scanner.nextLine().trim();
                days = Integer.parseInt(input);

                if (validateDays(days)) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Number of days must be between 1 and 31.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        return days;
    }

    //taking user input for # for mileage
    public int getInputMileage() {
        int mileage = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter approximate mileage for the trip (1-5000): ");
                String input = scanner.nextLine().trim();
                mileage = Integer.parseInt(input);

                if (validateMileage(mileage)) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Mileage must be between 1 and 5000.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        return mileage;
    }

    public String getValidUsername() {
        String username = "";
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter username (4-20 characters): ");
            username = scanner.nextLine().trim();

            if (validateUsername(username)) {
                valid = true;
            } else {
                System.out.println("Invalid username. Must be between 4-20 characters.");
            }
        }

        return username;
    }

    public String getValidPassword() {
        String password = "";
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter password (min 8 characters with at least one number and special character): ");
            password = scanner.nextLine();

            if (validatePassword(password)) {
                valid = true;
            } else {
                System.out.println("Invalid password. Must be at least 8 characters with at least one number and one special character.");
            }
        }

        return password;
    }

    //Validating Inputs - Data Integrity - DiD

    //validate input for passengers
    public boolean validatePassengers(int passengers) {

        return passengers >= 1 && passengers <= 7;
    }
    //validate input for days
    public boolean validateDays(int days) {
        return days >= 1 && days <= 31;
    }
    //validate input for mileage
    public boolean validateMileage(int mileage) {
        return mileage >= 1 && mileage <= 5000;
    }
    //validate username
    //condition: between 4-20 charcters
    public boolean validateUsername(String username) {
        return username != null && username.length() >= 4 && username.length() <= 20;
    }
    //validate password
    //Condition: >=8, special character, number
    public boolean validatePassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}

