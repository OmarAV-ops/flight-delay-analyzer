package flightanalyzer;

/**
   Represents a single flight record: the route it flew and
   how many minutes late it arrived.
*/
public class Flight
{
   private String date;
   private String carrier;
   private String origin;
   private String dest;
   private int delayMinutes;

   public Flight(String date, String carrier, String origin, String dest, int delayMinutes)
   {
      this.date = date;
      this.carrier = carrier;
      this.origin = origin;
      this.dest = dest;
      this.delayMinutes = delayMinutes;
   }

   public String getDate() { return date; }
   public String getCarrier() { return carrier; }
   public String getOrigin() { return origin; }
   public String getDest() { return dest; }
   public int getDelayMinutes() { return delayMinutes; }

   /**
      A flight is considered on-time if it arrived within 15
      minutes of schedule, matching the standard industry
      definition used by the U.S. Bureau of Transportation
      Statistics.
   */
   public boolean isOnTime()
   {
      return delayMinutes <= 15;
   }

   public String getRoute()
   {
      return origin + "-" + dest;
   }

   public String toString()
   {
      return date + " " + carrier + " " + getRoute() + ": " + delayMinutes + " min delay";
   }
}
