# LL1 Parser

## Overview
This project implements an LL(1) parser that reads a simple grammar, computes the First and Follow sets, constructs the LL(1) parsing table, and checks if the grammar is LL(1). The parser also provides a graphical user interface (GUI) to input grammars and view the results.

## Features
- Compute First and Follow sets for all variables.
- Print LL(1) parsing table.
- Detect if the grammar is ambiguous or inherently not LL(1).
- GUI for easy interaction and visualization.

## Project Structure
The project consists of the following main files:
- `Main.java`: The main class that sets up the GUI and handles user interactions.
- `LL1P.java`: The class that implements the LL(1) parser logic.
- `LL1Interface.java`: An interface that defines the methods for the LL(1) parser.
- `README.md`: This file, providing an overview and instructions.

## How to Run
1. **Clone the repository**:
    ```sh
    git clone https://github.com/A222moq3e/ll1-Parser
    cd ll1-Parser
    ```

2. **Compile the Java files**:
    ```sh
    javac Main.java
    ```

3. **Run the application**:
    ```sh
    java Main
    ```

## Usage
1. **Input Grammar**: Enter the grammar in the provided text area. The grammar should be free from left recursion and left factoring.
2. **Run Input**: Click the "Run Input" button to process the entered grammar.
3. **Example Grammars**: Use the "Run Ex1" and "Run Ex2" buttons to test with predefined example grammars.
4. **View Results**: The First sets, Follow sets, and Parsing table will be displayed in the respective text areas.

## Code Explanation
### Main.java
- **GUI Components**: Sets up the GUI components such as buttons, text areas, and scroll panes.
- **Event Handlers**: Handles button click events to process the grammar and display results.
- **Customization**: Customizes the appearance of the GUI components.

### LL1P.java
- **Grammar Processing**: Reads the grammar, computes First and Follow sets, constructs the parsing table, and checks if the grammar is LL(1).
- **Helper Methods**: Contains methods to compute First sets, Follow sets, and construct the parsing table.
- **Output Methods**: Provides methods to print the results and get the parsing table as a string.

### LL1Interface.java
- **Interface Definition**: Defines the methods that must be implemented by the LL(1) parser classes.

## Example
### Input Grammar
```
E -> T E'
E' -> + T E' | ε
T -> F T'
T' -> * F T' | ε
F -> ( E ) | id
```

### Output
- **First Sets**:
    ```
    E : [id, (]
    E' : [+, ε]
    T : [id, (]
    T' : [*, ε]
    F : [id, (]
    ```

- **Follow Sets**:
    ```
    E : [$, )]
    E' : [$, )]
    T : [+, $, )]
    T' : [+, $, )]
    F : [*, +, $, )]
    ```

- **Parsing Table**:
    ```
                |  id                 |  (                  |  +                  |  *                  |  )                  |  $                  
    ________________________________________________________________________________________________________________________
    E           |  E -> T E'          |  E -> T E'          |                     |                     |                     |                     
    E'          |                     |                     |  E' -> + T E'       |                     |  E' -> ε            |  E' -> ε            
    T           |  T -> F T'          |  T -> F T'          |                     |                     |                     |                     
    T'          |                     |                     |  T' -> ε            |  T' -> * F T'       |  T' -> ε            |  T' -> ε            
    F           |  F -> id            |  F -> ( E )         |                     |                     |                     |                     
    ```

## Authors
- Abdullah Khalid Bin Ammar
- Abdullah Altamimi
- Abdulrahim Alfaifi
- Moath Alshehri


## Requirements
- Implement LL(1) parser and print LL(1) parsing table.
- Compute the First, Follow for all variables.
- Print LL(1) parsing table.
- If the resulting parsing table contains multiply defined entries in any cell then print “The grammar is ambiguous or it is inherently not a LL(1) grammar”.
- Include the full code in the report.
- Test your program for at least two different examples and print the results in the report.
