package src;

import java.util.Scanner;
import java.util.List;

public class CarRentalApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //User input
        RentalSystem rentalSystem = new RentalSystem(); //Rental System class object
        Input inputValidator = new Input(scanner); //Input class object

        boolean exit = false;
        while (!exit) {
            if (rentalSystem.getCurrentUser() == null) { //If user not logged in
                showAuthenticationMenu(scanner, inputValidator, rentalSystem); // shows authentication menu (login and register)
            } else {
                if (rentalSystem.isAdmin()) { // if user is admin
                    showAdminMenu(scanner, inputValidator, rentalSystem); // shows admin menu
                } else { //if normal user
                    showUserMenu(scanner, inputValidator, rentalSystem); // shows normal user menu
                }
            }

            if (rentalSystem.getCurrentUser() == null) {
                System.out.print("\nDo you want to exit the application? (y/n): ");
                String input = scanner.nextLine();
                exit = input.equalsIgnoreCase("y");
            }
        }
        scanner.close();
    }

    //Authentication menu
    private static void showAuthenticationMenu(Scanner scanner, Input inputValidator, RentalSystem rentalSystem) {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option (1-2): ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                login(scanner, inputValidator, rentalSystem);
                break;
            case "2":
                register(inputValidator, rentalSystem);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    //Login
    private static void login(Scanner scanner, Input inputValidator, RentalSystem rentalSystem) {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean successfulLogin = rentalSystem.login(username, password);
        if (successfulLogin) {
            System.out.println("\nWelcome, " + username + "!");
        } else {
            System.out.println("\nInvalid username or password. Try again.");
        }
    }

    //Register
    private static void register(Input inputValidator, RentalSystem rentalSystem) {
        System.out.println("\n--- Register ---");
        String username = inputValidator.getValidUsername(); //validates username
        String password = inputValidator.getValidPassword(); //validates password

        //confirming password
        boolean passwordsMatch = false;
        while (!passwordsMatch) {
            System.out.print("Confirm password: ");
            String confirmPassword = inputValidator.scanner.nextLine();
            if (password.equals(confirmPassword)) {
                passwordsMatch = true;
            } else {
                System.out.println("Passwords don't match. Try again.");
            }
        }

        //Completing register
        boolean successfulRegister= rentalSystem.register(username, password);
        if (successfulRegister) {
            System.out.println("\nRegistration successful! Login Now");
        } else {
            System.out.println("\nUsername already exists. Try Logging in");
        }
    }

    //displays admin operations
    private static void showAdminMenu(Scanner scanner, Input inputValidator, RentalSystem rentalSystem) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("Welcome, " + rentalSystem.getCurrentUser().getUsername() + " (Admin)");
            System.out.println("1. View All Users");
            System.out.println("2. Find Best Car Rental");
            System.out.println("3. Logout");
            System.out.print("Choose an option (1-3): ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    viewAllUsers(rentalSystem);
                    break;
                case "2":
                    findUserCar(inputValidator, rentalSystem);
                    break;
                case "3":
                    logout = true;
                    rentalSystem.logout();
                    System.out.println("\nYou have been logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //displays user menu
    private static void showUserMenu(Scanner scanner, Input inputValidator, RentalSystem rentalSystem) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- User Menu ---");
            System.out.println("Welcome, " + rentalSystem.getCurrentUser().getUsername());
            System.out.println("1. Find Best Car Rental");
            System.out.println("2. Logout");
            System.out.print("Choose an option (1-2): ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    findUserCar(inputValidator, rentalSystem);
                    break;
                case "2":
                    logout = true;
                    rentalSystem.logout();
                    System.out.println("\nYou have been logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Admin-only-access method
    private static void viewAllUsers(RentalSystem rentalSystem) {
        //if user not admin
        if (!rentalSystem.isAdmin()) {
            System.out.println("Access denied. This function is for administrators only.");
            return; //access denied
        }

        List<User> users = rentalSystem.getUserList();
        System.out.println("\n--- Registered Users ---");
        if (users.isEmpty()) {
            System.out.println("No users registered yet.");
        } else {
            System.out.println("Username\t\tRole");
            for (User user : users) {
                System.out.println(user.getUsername() + "\t\t" + (user.isAdmin() ? "Admin" : "User"));
            }
        }
    }

    //Finding best car based on user input
    private static void findUserCar(Input inputValidator, RentalSystem rentalSystem) {
        //Taking user input
        System.out.println("\n--- Find Best Car Rental ---");
        int passengers = inputValidator.getInputPassengers();
        int days = inputValidator.getInputDays();
        int mileage = inputValidator.getInputMileage();

        //passing data to findBestCars method
        List<Car> suitableCars = rentalSystem.findBestCars(passengers, days, mileage);

        //displaying recommended cars
        System.out.println("\n-----------------");
        System.out.println("Recommended Cars");
        System.out.println("-----------------");
        if (suitableCars.isEmpty()) {
            System.out.println("No suitable cars found for your requirements.");
        } else {
            System.out.printf("%-20s %-18s %-15s %-18s $%-18s $%-18s %n", "Make&Model", "Category", "Passengers", "Comfort Level", "Rent Cost", "Trip Cost");
            for (Car car : suitableCars) {
                // Calculate costs
                double rentalCost = car.getRentalCostPerDay() * days;
                double tripCost = car.calculateTripCost(days, mileage); //with gas

                System.out.printf("%-20s %-18s %-15d %-15s $%-15f $%-15f%n",
                        car.getMake() + " " + car.getModel(),
                        car.getCategory(),
                        car.getMaxPassengers(),
                        car.getComfortLevel(),
                        rentalCost,
                        tripCost);
            }

            //displaying best option
            if (!suitableCars.isEmpty()) {
                Car bestCar = suitableCars.get(0);

                System.out.println("\n--------------------------------------");
                System.out.println("Detailed Information for Best Option:");
                System.out.println("--------------------------------------");
                System.out.println("Car: " + bestCar.getMake() + " " + bestCar.getModel() + " (" + bestCar.getCategory() + ")");
                System.out.println("Maximum Passengers: " + bestCar.getMaxPassengers());
                System.out.println("Comfort Level: " + bestCar.getComfortLevel());
                System.out.println("Miles Per Gallon: " + bestCar.getMpg() + " MPG");
            }
        }
    }
}
