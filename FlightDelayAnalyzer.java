package flightanalyzer;

import java.util.*;
import java.io.*;

/**
   Reads a CSV file of flight records and computes delay
   statistics: average delay per carrier, average delay per
   origin airport, the single most-delayed route, and each
   carrier's on-time percentage.

   Expected CSV format (with header row):
      date,carrier,origin,dest,delayMinutes
*/
public class FlightDelayAnalyzer
{
   private List<Flight> flights;

   /**
      Construct an analyzer and load all flight records from
      the given CSV file.
      @param filename the file containing the flight data.
   */
   public FlightDelayAnalyzer(String filename)
   {
      flights = new ArrayList<>();

      try {
         Scanner file = new Scanner(new File(filename));

         boolean firstLine = true;

         while (file.hasNextLine()) {
            String line = file.nextLine();

            if (firstLine) {
               firstLine = false; // skip header row
               continue;
            }

            if (line.isBlank()) continue;

            String[] parts = line.split(",");

            String date = parts[0];
            String carrier = parts[1];
            String origin = parts[2];
            String dest = parts[3];
            int delayMinutes = Integer.parseInt(parts[4]);

            flights.add(new Flight(date, carrier, origin, dest, delayMinutes));
         }

         file.close();

      } catch (FileNotFoundException e) {
         System.out.println("File not found: " + filename);
      }
   }

   /**
      Return the total number of flight records loaded.
   */
   public int getFlightCount()
   {
      return flights.size();
   }

   /**
      Return the average delay, in minutes, for each carrier.
      @return a map from carrier code to average delay.
   */
   public Map<String, Double> averageDelayByCarrier()
   {
      Map<String, Integer> totalDelay = new TreeMap<>();
      Map<String, Integer> count = new TreeMap<>();

      for (Flight f : flights) {
         totalDelay.merge(f.getCarrier(), f.getDelayMinutes(), Integer::sum);
         count.merge(f.getCarrier(), 1, Integer::sum);
      }

      Map<String, Double> result = new TreeMap<>();
      for (String carrier : totalDelay.keySet()) {
         result.put(carrier, totalDelay.get(carrier) / (double) count.get(carrier));
      }

      return result;
   }

   /**
      Return the average delay, in minutes, for each origin
      airport.
      @return a map from airport code to average delay.
   */
   public Map<String, Double> averageDelayByOrigin()
   {
      Map<String, Integer> totalDelay = new TreeMap<>();
      Map<String, Integer> count = new TreeMap<>();

      for (Flight f : flights) {
         totalDelay.merge(f.getOrigin(), f.getDelayMinutes(), Integer::sum);
         count.merge(f.getOrigin(), 1, Integer::sum);
      }

      Map<String, Double> result = new TreeMap<>();
      for (String airport : totalDelay.keySet()) {
         result.put(airport, totalDelay.get(airport) / (double) count.get(airport));
      }

      return result;
   }

   /**
      Return the on-time percentage (0-100) for each carrier.
      A flight counts as on-time if its delay is 15 minutes
      or less.
      @return a map from carrier code to on-time percentage.
   */
   public Map<String, Double> onTimePercentageByCarrier()
   {
      Map<String, Integer> onTimeCount = new TreeMap<>();
      Map<String, Integer> totalCount = new TreeMap<>();

      for (Flight f : flights) {
         totalCount.merge(f.getCarrier(), 1, Integer::sum);
         if (f.isOnTime()) {
            onTimeCount.merge(f.getCarrier(), 1, Integer::sum);
         }
      }

      Map<String, Double> result = new TreeMap<>();
      for (String carrier : totalCount.keySet()) {
         int onTime = onTimeCount.getOrDefault(carrier, 0);
         result.put(carrier, 100.0 * onTime / totalCount.get(carrier));
      }

      return result;
   }

   /**
      Return the single flight record with the longest delay.
      @return the most-delayed flight, or null if no flights
              were loaded.
   */
   public Flight mostDelayedFlight()
   {
      Flight worst = null;

      for (Flight f : flights) {
         if (worst == null || f.getDelayMinutes() > worst.getDelayMinutes()) {
            worst = f;
         }
      }

      return worst;
   }

   /**
      Return the average delay, in minutes, for each route
      (origin-destination pair).
      @return a map from route string ("ORD-LAX") to average delay.
   */
   public Map<String, Double> averageDelayByRoute()
   {
      Map<String, Integer> totalDelay = new TreeMap<>();
      Map<String, Integer> count = new TreeMap<>();

      for (Flight f : flights) {
         totalDelay.merge(f.getRoute(), f.getDelayMinutes(), Integer::sum);
         count.merge(f.getRoute(), 1, Integer::sum);
      }

      Map<String, Double> result = new TreeMap<>();
      for (String route : totalDelay.keySet()) {
         result.put(route, totalDelay.get(route) / (double) count.get(route));
      }

      return result;
   }
}
