# flight-delay-analyzer
Java program that analyzes flight delay data by carrier, airport, and route

# Flight Delay Analyzer

A Java program that reads flight records from a CSV file and computes delay statistics: average delay per carrier, average delay per origin airport, average delay per route, each carrier's on time percentage, and the single most delayed flight in the dataset.

## How It Works

Flight data is read in from a CSV file in the format:

```
date,carrier,origin,dest,delayMinutes
2026-01-05,UA,ORD,LAX,12
```

Each row is parsed into a `Flight` object. `FlightDelayAnalyzer` then aggregates these records using `HashMap`/`TreeMap` structures to compute:

- **Average delay by carrier** — which airline has the worst average delays
- **On-time percentage by carrier** — percentage of each carrier's flights arriving within 15 minutes of schedule (the standard on-time definition used by the U.S. Bureau of Transportation Statistics)
- **Average delay by origin airport** — which airports tend to produce the most delays
- **Average delay by route** — which specific origin-destination pairs are least reliable
- **Most delayed flight** — the single worst individual flight in the dataset

## Project Structure

```
src/flightanalyzer/
├── Flight.java                     # Represents a single flight record
├── FlightDelayAnalyzer.java        # Core logic: parses CSV, computes statistics
├── FlightDelayAnalyzerDemo.java    # Command-line demo that prints a full delay report
└── FlightDelayAnalyzerTest.java    # JUnit test suite covering the analyzer's methods

data/
├── flights1.csv
└── flights2.csv
```

## Key Concepts Used

- **File I/O and CSV parsing** to read structured flight data
- **HashMap / TreeMap aggregation** to group and average values by carrier, airport, and route
- **Custom object modeling** (`Flight` class) to represent each data record
- **JUnit testing** to verify statistics are computed correctly across multiple datasets

## Running It

1. Open the project in Eclipse (or any Java IDE).
2. Run `FlightDelayAnalyzerDemo.java`.
3. When prompted, enter the path to a data file (e.g. `data/flights1.csv`).
4. The program prints a full delay report: average delays by carrier, airport, and route, on-time percentages, and the most delayed flight.

## Sample Output

```
Loaded 42 flight records.

Average delay by carrier (minutes):
  AA     18.6
  DL     23.8
  UA     31.4

On-time percentage by carrier:
  AA     58.3%
  DL     41.7%
  UA     38.9%

Most delayed flight: 2026-01-06 UA ORD-SFO: 90 min delay
```

## Testing

`FlightDelayAnalyzerTest.java` contains JUnit tests covering:
- That flight records load correctly from the CSV file
- That per-carrier averages are computed
- That on-time percentages fall within a valid 0-100 range
- That the most-delayed flight is correctly identified
- That different input files produce different results
- That the on-time/late classification logic is correct

Run the tests via Eclipse's built-in JUnit runner, or any JUnit-compatible test runner.
