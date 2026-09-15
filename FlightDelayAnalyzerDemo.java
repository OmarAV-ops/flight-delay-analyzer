package flightanalyzer;

import java.util.Map;
import java.util.Scanner;

/**
   Command-line demo that runs FlightDelayAnalyzer on a chosen
   CSV file and prints a readable delay report.
*/
public class FlightDelayAnalyzerDemo
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.print("CSV file with flight data: ");
      String filename = in.nextLine();

      FlightDelayAnalyzer analyzer = new FlightDelayAnalyzer(filename);

      System.out.println();
      System.out.println("Loaded " + analyzer.getFlightCount() + " flight records.");

      System.out.println();
      System.out.println("Average delay by carrier (minutes):");
      for (Map.Entry<String, Double> entry : analyzer.averageDelayByCarrier().entrySet()) {
         System.out.printf("  %-6s %.1f%n", entry.getKey(), entry.getValue());
      }

      System.out.println();
      System.out.println("On-time percentage by carrier:");
      for (Map.Entry<String, Double> entry : analyzer.onTimePercentageByCarrier().entrySet()) {
         System.out.printf("  %-6s %.1f%%%n", entry.getKey(), entry.getValue());
      }

      System.out.println();
      System.out.println("Average delay by origin airport (minutes):");
      for (Map.Entry<String, Double> entry : analyzer.averageDelayByOrigin().entrySet()) {
         System.out.printf("  %-6s %.1f%n", entry.getKey(), entry.getValue());
      }

      System.out.println();
      System.out.println("Average delay by route (minutes):");
      for (Map.Entry<String, Double> entry : analyzer.averageDelayByRoute().entrySet()) {
         System.out.printf("  %-8s %.1f%n", entry.getKey(), entry.getValue());
      }

      System.out.println();
      Flight worst = analyzer.mostDelayedFlight();
      System.out.println("Most delayed flight: " + worst);
   }
}
