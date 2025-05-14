package src;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class RentalSystem {
    private static final int SSALT_LENGTH= 16;
    private List<Car> cars;
    private Map<String, User> users;
    private User currentUser;
    private static final String USER_FILE = "src/users.dat";

    //Constructor
    //Initializes system with predefined cars
    //Loads users files
    public RentalSystem() {
        initializeCars();
        users = new HashMap<>();
        loadUsers(); //Load users File

        //Default admin user
        if (users.isEmpty()) {
            //Hashed password for admin
            String salt = generateSalt();
            String hashedPassword = hashPassword("Admin.123",salt);

            users.put("admin", new User("admin", hashedPassword, salt, true));
            saveUsers();
            //System.out.println("admin user created");
        }
    }

    //method genrates random salt
    private String generateSalt() {
        SecureRandom randomSalt = new SecureRandom();
        byte[] salt = new byte[SSALT_LENGTH];
        randomSalt.nextBytes(salt);
        //Encoding salt by BAse64
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            //Initializing with SHA-256 algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(Base64.getDecoder().decode(salt));

            //computing hash value
            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            //returning hashed password
            return Base64.getEncoder().encodeToString(hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private boolean verifyPass(String password, String storedHash, String salt) {
        boolean verified = false;

        String hashedPassword = hashPassword(password, salt);
        verified = storedHash.equals(hashedPassword);

        return verified;
    }

    //Initialse cars
    private void initializeCars() {
        cars = new ArrayList<>();
        cars.add(new Car("Honda", "Pilot", "SUV", 22, 5, "Good", 55));
        cars.add(new Car("Mazda", "CX-5", "Crossover", 28, 5, "Good", 55));
        cars.add(new Car("Hyundai", "Sonata", "Sedan", 32, 4, "Medium", 50));
        cars.add(new Car("Ram", "1500", "Truck", 20, 5, "Good", 55));
        cars.add(new Car("Chevrolet", "Camaro", "Coupe", 25, 4, "Poor", 45));
        cars.add(new Car("Hyundai", "Ioniq Hybrid", "Hybrid", 55, 4, "Medium", 50));
        cars.add(new Car("Honda", "Odyssey", "Van/Minivan", 22, 7, "Medium", 70));
    }

    //Method to load users from users.dat
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            Object readObject = ois.readObject();
            if (readObject instanceof HashMap) {
                HashMap<String, User> loadedUsers = (HashMap<String, User>) readObject;
                users.putAll(loadedUsers);
            }
        } catch (FileNotFoundException e) { //File doesnt exist - FIRST RUN
            System.out.println("No existing user database found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Save file safely
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //User registration
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(password,salt);

        //Registering new user
        //Fail-safe default && Least privilege
        users.put(username, new User(username, hashedPassword,salt,  false));// NON ADMIN privileges by default
        saveUsers();
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        //Validate Input - Hash verfication
        if (user != null && verifyPass(password, user.getPassword(), user.getSalt())) {
            currentUser = user;
            return true;
        }
        return false;//Default deny authentication
    }

    //Log out
    public void logout() {
        currentUser = null;
    }

    //Role-based Access Control
    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }


    public List<User> getUserList() {
        if (!isAdmin()) return new ArrayList<>();// This returns an empty list for non admins
        return new ArrayList<>(users.values());
    }

    public List<Car> findBestCars(int passengers, int days, int mileage) {
        List<Car> suitableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.canFitPassengers(passengers)) {
                suitableCars.add(car);
            }
        }

        suitableCars.sort(Comparator.comparingDouble(car -> car.calculateTripCost(days, mileage)));
        return suitableCars;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
