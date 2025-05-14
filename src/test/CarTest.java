package src.test;
import src.Car;
//Junit imports
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car sedanCar;
    private Car suvCar;

    @BeforeEach
    public void setUp() {
        sedanCar = new Car("Toyota", "Camry", "Sedan", 32, 5, "Good", 45.0);
        suvCar = new Car("Ford", "Explorer", "SUV", 22, 7, "Premium", 65.0);
    }

    //Test Case 1
    @Test
    @DisplayName("Car Initialization Test")
    public void testCarInitialization() {
        assertEquals("Toyota", sedanCar.getMake());
        assertEquals("Camry", sedanCar.getModel());
        assertEquals("Sedan", sedanCar.getCategory());
        assertEquals(32, sedanCar.getMpg());
        assertEquals(5, sedanCar.getMaxPassengers());
        assertEquals("Good", sedanCar.getComfortLevel());
        assertEquals(45.0, sedanCar.getRentalCostPerDay());
    }

    //Test Case 2
    @Test
    @DisplayName("Passengers CApacity Test")
    public void testCanFitPassengers() {
        // Sedan with 5 passenger capacity
        assertTrue(sedanCar.canFitPassengers(5));
        assertTrue(sedanCar.canFitPassengers(4));
        assertTrue(sedanCar.canFitPassengers(1));
        assertFalse(sedanCar.canFitPassengers(6));
        assertFalse(sedanCar.canFitPassengers(10));

        // SUV with 7 passenger capacity
        assertTrue(suvCar.canFitPassengers(7));
        assertTrue(suvCar.canFitPassengers(6));
        assertFalse(suvCar.canFitPassengers(8));
    }

    //Test Case 3
    @Test
    @DisplayName("Correct Trip Cost Calculation Test")
    public void testCalculateTripCost() {
        // Sedan: 3 days, 200 miles
        // Rental cost: 45 * 3 = 135
        // Gas cost: (200 / 32) * 2.25 = 14.0625
        // Total: 135 + 14.0625 = 149.0625
        assertEquals(149.0625, sedanCar.calculateTripCost(3, 200), 0.001);

        // SUV: 5 days, 400 miles
        // Rental cost: 65 * 5 = 325
        // Gas cost: (400 / 22) * 2.25 = 40.909...
        // Total: 325 + 40.909... = 365.909...
        assertEquals(365.9090909090909, suvCar.calculateTripCost(5, 400), 0.001);
    }

    //Test Case 4
    @Test
    @DisplayName("Trip Cost Calculation with ZEROS Test")
    public void testZeroValuesInTripCost() {
        // Some days, zero miles
        assertEquals(45.0 * 3, sedanCar.calculateTripCost(3, 0), 0.001);
    }

    //Test Case 5
    @Test
    @DisplayName("Trip Cost Calculation with Negative Values Test")
    public void testNegativeValuesInTripCost() {
        // Negative days
        assertEquals(-45.0, sedanCar.calculateTripCost(-1, 0), 0.001);

        // Negative miles
        assertEquals(45.0 - ((100.0 / 32.0) * 2.25), sedanCar.calculateTripCost(1, -100), 0.001);
    }

    //Test Case 6
    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        assertEquals("Toyota Camry (Sedan)", sedanCar.toString());
        assertEquals("Ford Explorer (SUV)", suvCar.toString());
    }

}
