package src;

public class Car {
    private String make;
    private String model;
    private String category;
    private int mpg;
    private int maxPassengers;
    private String comfortLevel;
    private double rentalCostPerDay;

    public Car(String make, String model, String category, int mpg,
               int maxPassengers, String comfortLevel, double rentalCostPerDay) {
        this.make = make;
        this.model = model;
        this.category = category;
        this.mpg = mpg;
        this.maxPassengers = maxPassengers;
        this.comfortLevel = comfortLevel;
        this.rentalCostPerDay = rentalCostPerDay;
    }

    //Method to calculate Trip cost
    public double calculateTripCost(int days, int mileage) {
        double rentalCost = rentalCostPerDay * days;
        double gasCost = (mileage / (double) mpg) * 2.25;
        return rentalCost + gasCost;
    }

    //Method to check if car can fit the # of passengers provided
    public boolean canFitPassengers(int passengers) {
        return maxPassengers >= passengers;
    }

    //Getters
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getCategory() {
        return category;
    }
    public int getMpg() {
        return mpg;
    }
    public int getMaxPassengers() {
        return maxPassengers;
    }
    public String getComfortLevel() {
        return comfortLevel;
    }
    public double getRentalCostPerDay() {
        return rentalCostPerDay;
    }

    @Override
    public String toString() {
        return make + " " + model + " (" + category + ")";
    }
}
