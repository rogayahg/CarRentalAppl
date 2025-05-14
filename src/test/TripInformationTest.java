package src.test;
import src.TripInformation;
//Junit imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class TripInformationTest {
    //Test Case1
    @Test
    @DisplayName("Trip Information with Valid Values")
    public void testTripInformationCreation() {
        TripInformation trip = new TripInformation(4, 7, 500);
        assertEquals(4, trip.getPassengers());
        assertEquals(7, trip.getDays());
        assertEquals(500, trip.getMileage());
    }

    //Test Case 2
    @Test
    @DisplayName("Trip Information with ZERO Values Test")
    public void testZeroValues() {
        TripInformation trip = new TripInformation(0, 0, 0);
        assertEquals(0, trip.getPassengers());
        assertEquals(0, trip.getDays());
        assertEquals(0, trip.getMileage());
    }

    //Test Case 3
    @Test
    @DisplayName("Trip Information with Negative Values")
    public void testNegativeValues() {
        TripInformation trip = new TripInformation(-3, -5, -200);
        assertEquals(-3, trip.getPassengers());
        assertEquals(-5, trip.getDays());
        assertEquals(-200, trip.getMileage());
    }

    //Test Case 4
    @Test
    @DisplayName("Trip Information with BOUNDARY Values")
    public void testBoundaryValues() {
        TripInformation trip = new TripInformation(1, 1, 1);
        assertEquals(1, trip.getPassengers());
        assertEquals(1, trip.getDays());
        assertEquals(1, trip.getMileage());

        TripInformation tripMax = new TripInformation(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, tripMax.getPassengers());
        assertEquals(Integer.MAX_VALUE, tripMax.getDays());
        assertEquals(Integer.MAX_VALUE, tripMax.getMileage());
    }

    //Test Case 5
    @ParameterizedTest
    @CsvSource({
            "1, 2, 100",
            "4, 7, 500",
            "7, 30, 3000"
    })
    @DisplayName("Trip information with Various Valid Inputs")
    public void testValidInputScenarios(int passengers, int days, int mileage) {
        TripInformation trip = new TripInformation(passengers, days, mileage);
        assertEquals(passengers, trip.getPassengers());
        assertEquals(days, trip.getDays());
        assertEquals(mileage, trip.getMileage());
    }

    //Test Case 6
    @Test
    @DisplayName("Trip Information with SAME Qualities Test")
    public void testObjectEquality() {
        TripInformation trip1 = new TripInformation(4, 7, 500);
        TripInformation trip2 = new TripInformation(4, 7, 500);
        TripInformation trip3 = new TripInformation(5, 7, 500);

        // Object equality (uses memory reference)
        assertNotEquals(trip1, trip2);

        // Value equality (manually comparing values)
        assertEquals(trip1.getPassengers(), trip2.getPassengers());
        assertEquals(trip1.getDays(), trip2.getDays());
        assertEquals(trip1.getMileage(), trip2.getMileage());

        assertNotEquals(trip1.getPassengers(), trip3.getPassengers());
    }
}
