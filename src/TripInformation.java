package src;

public class TripInformation {
    private int passengers;
    private int days;
    private int mileage;

    //Constructor
    public TripInformation(int passengers, int days, int mileage) {
        this.passengers = passengers;
        this.days = days;
        this.mileage = mileage;
    }

    //Getters
    public int getPassengers() {
        return passengers;
    }

    public int getDays() {
        return days;
    }

    public int getMileage() {
        return mileage;
    }
}
