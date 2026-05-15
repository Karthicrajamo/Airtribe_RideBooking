# RideWise 🚗

A console-based Ride-Sharing system (Uber/Ola style) built to demonstrate **clean Low-Level Design** using SOLID principles, Strategy Pattern, and Composition over Inheritance in Java.

---

## Table of contents

- [Project overview](#project-overview)
- [Features](#features)
- [Project structure](#project-structure)
- [Architecture](#architecture)
- [Design patterns used](#design-patterns-used)
- [SOLID principles](#solid-principles)
- [Fare calculation](#fare-calculation)
- [Getting started](#getting-started)
- [Usage walkthrough](#usage-walkthrough)
- [Extending the system](#extending-the-system)
- [Class relationships](#class-relationships)

---

## Project overview

RideWise is an **MVP console application** that simulates the core booking flow of a ride-sharing platform. The goal is not to build a production app — it is to demonstrate high-quality LLD and design thinking through:

- Correct application of the **Strategy Pattern** using interfaces
- **Composition over inheritance** for pluggable behaviour
- **SOLID principles** applied at every layer
- **Low coupling / high cohesion** between services
- **Law of Demeter** — no deep method chains across service boundaries

---

## Features

- Register riders and drivers (with vehicle type)
- View available drivers
- Request a ride — matched to a driver using a pluggable strategy
- Calculate fare using a pluggable pricing strategy
- Track ride status: `REQUESTED → ASSIGNED → COMPLETED / CANCELLED`
- Cancel a ride (frees the assigned driver)
- Switch matching and pricing strategies at runtime from the menu

---

## Project structure

```
ridewise/
├── src/
│   └── com/airtribe/ridewise/
│       ├── Main.java                         # Console I/O, composition root
│       ├── model/
│       │   ├── Rider.java
│       │   ├── Driver.java
│       │   ├── Ride.java
│       │   ├── FareReceipt.java
│       │   ├── RideStatus.java               # enum: REQUESTED, ASSIGNED, COMPLETED, CANCELLED
│       │   └── VehicleType.java              # enum: BIKE, AUTO, CAR
│       ├── strategy/
│       │   ├── RideMatchingStrategy.java     # interface
│       │   ├── NearestDriverStrategy.java
│       │   ├── LeastActiveDriverStrategy.java
│       │   ├── FareStrategy.java             # interface
│       │   ├── DefaultFareStrategy.java
│       │   └── PeakHourFareStrategy.java
│       ├── service/
│       │   ├── RiderService.java
│       │   ├── DriverService.java
│       │   └── RideService.java
│       ├── exception/
│       │   └── NoDriverAvailableException.java
│       └── util/
│           └── IdGenerator.java
└── docs/
    ├── Requirements.md
    ├── Class_Model.md
    ├── SOLID_Reflection.md
    └── Object_Relationships.md
```

---

## Architecture

```
┌──────────────────────────────────────────────────────┐
│                     Main.java                        │
│           (console I/O + composition root)           │
└────────────────────┬─────────────────────────────────┘
                     │ creates & wires
        ┌────────────┼────────────┐
        ▼            ▼            ▼
  RiderService  DriverService  RideService
                                  │
                     ┌────────────┴────────────┐
                     ▼                         ▼
          RideMatchingStrategy            FareStrategy
          ┌──────────────────┐        ┌──────────────────┐
          │ NearestDriver    │        │ DefaultFare       │
          │ LeastActiveDriver│        │ PeakHourFare      │
          └──────────────────┘        └──────────────────┘
```

`RideService` depends **only on interfaces** — it never imports a concrete strategy class. All wiring happens in `Main.java`.

---

## Design patterns used

### Strategy Pattern

Two strategy interfaces allow behaviour to be swapped without touching `RideService`:

**Ride matching:**
```java
public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> availableDrivers);
}
```

**Fare calculation:**
```java
public interface FareStrategy {
    double calculateFare(Ride ride);
}
```

Strategies are injected into `RideService` via constructor:
```java
RideService rideService = new RideService(
    riderService,
    driverService,
    new NearestDriverStrategy(),   // swap anytime
    new DefaultFareStrategy()      // swap anytime
);
```

### Composition over inheritance

- `Ride` **composes** `FareReceipt` — a receipt has no meaning outside a ride and is created/destroyed with it.
- `RideService` **composes** strategy interfaces — behaviour is injected, not inherited.

### Factory-style ID generation

`IdGenerator` produces prefixed sequential IDs (`R001`, `D001`, `RIDE001`) using `AtomicInteger` — thread-safe by habit.

---

## SOLID principles

| Principle | Applied where |
|-----------|---------------|
| **S** — Single Responsibility | Every class has one job: `RiderService` registers riders, `DriverService` manages drivers, `FareReceipt` holds fare data |
| **O** — Open/Closed | Add a new fare tier or matching algorithm by creating one new class — zero changes to existing code |
| **L** — Liskov Substitution | `NearestDriverStrategy` and `LeastActiveDriverStrategy` are fully interchangeable — same contract, same return type |
| **I** — Interface Segregation | Both strategy interfaces are single-method contracts — implementors never need unused methods |
| **D** — Dependency Inversion | `RideService` depends on `RideMatchingStrategy` and `FareStrategy` interfaces, never on concrete classes |

**Law of Demeter:** `RideService` only calls methods on its direct collaborators. No chains like `ride.getDriver().getService().update()`.

---

## Fare calculation

### DefaultFareStrategy

| Vehicle | Base fare | Per km | Example (10 km) |
|---------|-----------|--------|-----------------|
| BIKE    | ₹20       | ₹8     | ₹100            |
| AUTO    | ₹20       | ₹12    | ₹140            |
| CAR     | ₹20       | ₹18    | ₹200            |

### PeakHourFareStrategy

Applies a **1.5× surge multiplier** on top of `DefaultFareStrategy` rates during:
- Morning peak: **8:00 AM – 10:00 AM**
- Evening peak: **5:00 PM – 8:00 PM**

---

## Getting started

### Prerequisites

- Java 17 or higher
- No external dependencies — pure Java standard library

### Compile

```bash
# From the project root
javac -d out $(find src -name "*.java")
```

### Run

```bash
java -cp out com.airtribe.ridewise.Main
```

---

## Usage walkthrough

```
╔══════════════════════════════════╗
║       Welcome to RideWise 🚗      ║
╚══════════════════════════════════╝

  Strategy: [Nearest Driver | Default Fare]
─────────────────────────────────────
  1. Add Rider
  2. Add Driver
  3. View Available Drivers
  4. Request Ride
  5. Complete Ride
  6. Cancel Ride
  7. View All Rides
  8. Change Strategies
  9. Exit
```

**Typical session:**

1. Add a rider: name `Arjun`, location `7`
2. Add two drivers: `Ravi` (CAR, loc `5`) and `Priya` (AUTO, loc `12`)
3. Request a ride for `R001`, distance `8 km`
    - With **NearestDriverStrategy**, Ravi is matched (distance 2 vs Priya's 5)
4. Complete `RIDE001` → receipt generated
5. Switch to **LeastActiveDriverStrategy** via menu option 8
6. Request another ride — now Ravi is matched again (0 completed rides vs Priya's 1)

---

## Extending the system

### Add a new matching strategy

```java
// 1. Create the class — no other file changes needed
public class HighestRatedDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        return drivers.stream()
            .max(Comparator.comparingDouble(Driver::getRating))
            .orElse(null);
    }
}

// 2. Wire it in Main.java (or via menu option 8)
rideService = rideService.withMatchingStrategy(new HighestRatedDriverStrategy());
```

### Add a new fare strategy

```java
// 1. Create the class
public class RainSurgeFareStrategy extends DefaultFareStrategy {
    @Override
    public double calculateFare(Ride ride) {
        return super.calculateFare(ride) * 2.0; // 2× in rain
    }
}

// 2. Wire it in Main.java
rideService = rideService.withFareStrategy(new RainSurgeFareStrategy());
```

No changes to `RideService`, `Main`, or any existing strategy are required.

---

## Class relationships

| Relationship | Type | Reason |
|---|---|---|
| `Rider` → `Ride` | Association | Rider exists independently of any ride |
| `Driver` → `Ride` | Association | Driver exists independently of any ride |
| `Ride` → `FareReceipt` | **Composition** | Receipt has no meaning without its ride |
| `RideService` → `RideMatchingStrategy` | **Composition** | Strategy injected at construction, owned by service |
| `RideService` → `FareStrategy` | **Composition** | Same as above |
| `RideService` → `RiderService` | Dependency | Injected collaborator |
| `RideService` → `DriverService` | Dependency | Injected collaborator |
| `PeakHourFareStrategy` → `DefaultFareStrategy` | Inheritance | Extends base rates with surge multiplier |

---

*Built as a Low-Level Design exercise for the Airtribe Java LLD course.*