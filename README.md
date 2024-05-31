# Table of Contents
- [Overview](#overview)
- [Requirements](#requirements)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Format Description](#format-description)

# COMPANY ORGANIZATIONAL STRUCTURE ANALYZER

<a name="overview"></a>
## Overview

This project analyzes the organizational structure of "BIG COMPANY". The purposes of the project are to ensure that:

- Every manager earns at least 20% more than the average salary of their direct subordinates, but no more than 50% more.
- No employee should have more than 4 managers between them and the CEO.

<a name="requirements"></a>
## Requirements

- Java 21 (installed)
- Maven (installed)

<a name="building-the-project"></a>
## Building the Project

To package the application into a JAR file, use the following command:

```bash
mvn package
```

<a name="running-the-application"></a>
## Running the Application

To run the application after it has been built, use the following command:

```bash
java -jar target/big-company-app-1.0-SNAPSHOT.jar path/to/file.csv
```
Where path/to/file.csv is full or relative path to file with employees

To run the app with debugging enabled logs, use the following command:
```bash
java -Djava.util.logging.config.file=path/to/logging.properties -jar target/big-company-app-1.0-SNAPSHOT.jar
```
Where path/to/logging.properties is full or relative path to file with logging properties
#### Example of logging properties
```properties
.level= CONFIG
handlers= java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.level = ALL
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
```

<a name="format-description"></a>
## Format Description

The application reads data from a CSV file. Each line of the CSV file serves as an employee record with the following structure:

```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```

### The application reports:

Which managers earn less than they should, and by how much.
Which managers earn more than they should, and by how much.
Which employees have a reporting line which is too long, and by how much.
