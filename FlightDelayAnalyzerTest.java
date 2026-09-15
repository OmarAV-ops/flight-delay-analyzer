package flightanalyzer;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Map;

public class FlightDelayAnalyzerTest {

    @Test
    public void testFlightCountNotZero() {
        FlightDelayAnalyzer a = new FlightDelayAnalyzer("data/flights1.csv");
        assertTrue(a.getFlightCount() > 0);
    }

    @Test
    public void testAverageDelayByCarrierNotEmpty() {
        FlightDelayAnalyzer a = new FlightDelayAnalyzer("data/flights1.csv");
        Map<String, Double> result = a.averageDelayByCarrier();

        assertTrue(result.size() > 0);
    }

    @Test
    public void testOnTimePercentageInValidRange() {
        FlightDelayAnalyzer a = new FlightDelayAnalyzer("data/flights1.csv");
        Map<String, Double> result = a.onTimePercentageByCarrier();

        for (double percentage : result.values()) {
            assertTrue(percentage >= 0.0 && percentage <= 100.0);
        }
    }

    @Test
    public void testMostDelayedFlightIsActuallyWorst() {
        FlightDelayAnalyzer a = new FlightDelayAnalyzer("data/flights1.csv");
        Flight worst = a.mostDelayedFlight();

        assertNotNull(worst);
        assertEquals(90, worst.getDelayMinutes());
    }

    @Test
    public void testDifferentFilesProduceDifferentResults() {
        FlightDelayAnalyzer a1 = new FlightDelayAnalyzer("data/flights1.csv");
        FlightDelayAnalyzer a2 = new FlightDelayAnalyzer("data/flights2.csv");

        assertNotEquals(a1.getFlightCount(), a2.getFlightCount());
    }

    @Test
    public void testFlightOnTimeLogic() {
        Flight onTime = new Flight("2026-01-01", "UA", "ORD", "LAX", 10);
        Flight late = new Flight("2026-01-01", "UA", "ORD", "LAX", 20);

        assertTrue(onTime.isOnTime());
        assertFalse(late.isOnTime());
    }
}
