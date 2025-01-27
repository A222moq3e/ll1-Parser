import java.io.*;
import java.util.*;

class LL1P implements LL1Interface {
    private final Map<String, List<String>> grammarRules = new HashMap<>();
    private final Map<String, Set<String>> firstSets = new HashMap<>();
    private final Map<String, Set<String>> followSets = new HashMap<>();
    private final Map<String, Map<String, String>> parsingTable = new HashMap<>();
    private final List<String> terminalSymbols = new ArrayList<>();
    private final List<String> nonTerminalSymbols = new ArrayList<>();
    private boolean isLL1Grammar = true;
    private final String absolineSymbole = "ε";

    @Override
    public void readGrammarFromString(String fileContent) {
        // clear all Maps
        grammarRules.clear();
        terminalSymbols.clear();
        nonTerminalSymbols.clear();
        // split lines of the grammar
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] sides = line.split("->");
            if (sides.length != 2) {
                // if it is not 2 sides then it is not a valid grammar so throw error
                throw new IllegalArgumentException("Invalid grammar format: " + line);
            }
            // left hand side of the production (nonTerminals)
            String nonTerminal = sides[0].trim();
            nonTerminalSymbols.add(nonTerminal);
            grammarRules.put(nonTerminal, new ArrayList<>());
        }

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] sides = line.split("->");
            String nonTerminal = sides[0].trim();
            String[] productions = sides[1].trim().split("\\|");

            for (String production : productions) {
                grammarRules.get(nonTerminal).add(production.trim());

                for (String symbol : production.trim().split("\\s+")) {
                    symbol = symbol.trim();
                    if (!nonTerminalSymbols.contains(symbol) && 
                        !symbol.equals(absolineSymbole) && 
                        !symbol.isEmpty()) {
                        terminalSymbols.add(symbol);
                    }
                }
            }
        }

        terminalSymbols.add("$");
    }

    public void processGrammar() {
        System.out.println("Processing grammar...");
        computeFirstSets();
        // System.out.println("Computed 'first' sets: " + firstSets);
        computeFollowSets();
        // System.out.println("Computed 'follow' sets: " + followSets);
        constructParsingTable();
        // System.out.println("Constructed Parsing Table: " + parsingTable);
    }

    @Override
    public void computeFirstSets() {
        for (String nonTerminal : nonTerminalSymbols) {
            firstSets.put(nonTerminal, new HashSet<>());
        }

        boolean updated;
        do {
            updated = false;
            for (String nonTerminal : grammarRules.keySet()) {
                for (String production : grammarRules.get(nonTerminal)) {
                    int sizeBefore = firstSets.get(nonTerminal).size();
                    Set<String> firstOfProduction = computeFirstOfProduction(production);
                    firstSets.get(nonTerminal).addAll(firstOfProduction);
                    if (firstSets.get(nonTerminal).size() > sizeBefore) {
                        updated = true;
                    }
                }
            }
        } while (updated);
        System.out.println("FIRST sets: " + firstSets);
    }

    private Set<String> computeFirstOfProduction(String production) {
        Set<String> result = new HashSet<>();
        for (String symbol : production.split("\\s+")) {
            if (!grammarRules.containsKey(symbol)) {
                result.add(symbol);
                break;
            } else {
                result.addAll(firstSets.get(symbol));
                if (!firstSets.get(symbol).contains(absolineSymbole)) {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void computeFollowSets() {
        for (String nonTerminal : nonTerminalSymbols) {
            followSets.put(nonTerminal, new HashSet<>());
        }
        followSets.get(nonTerminalSymbols.get(0)).add("$");

        boolean updated;
        do {
            updated = false;
            for (String nonTerminal : grammarRules.keySet()) {
                for (String production : grammarRules.get(nonTerminal)) {
                    String[] symbols = production.split("\\s+");
                    for (int i = 0; i < symbols.length; i++) {
                        if (grammarRules.containsKey(symbols[i])) {
                            Set<String> follow = followSets.get(symbols[i]);
                            int sizeBefore = follow.size();

                            if (i + 1 < symbols.length) {
                                Set<String> firstOfNext = computeFirstOfProduction(String.join(" ", Arrays.copyOfRange(symbols, i + 1, symbols.length)));
                                follow.addAll(firstOfNext);
                                follow.remove(absolineSymbole);
                            }

                            if (i + 1 == symbols.length || computeFirstOfProduction(String.join(" ", Arrays.copyOfRange(symbols, i + 1, symbols.length))).contains(absolineSymbole)) {
                                follow.addAll(followSets.get(nonTerminal));
                            }

                            if (follow.size() > sizeBefore) {
                                updated = true;
                            }
                        }
                    }
                }
            }
        } while (updated);
        System.out.println("FOLLOW sets: " + followSets);
    }

    @Override
    public void constructParsingTable() {
        parsingTable.clear();
        for (String nonTerminal : nonTerminalSymbols) {
            parsingTable.put(nonTerminal, new HashMap<>());
        }

        for (String nonTerminal : grammarRules.keySet()) {
            for (String production : grammarRules.get(nonTerminal)) {
                Set<String> first = computeFirstOfProduction(production);
                for (String terminal : first) {
            
                    if (!terminal.equals(absolineSymbole)) {
                        // if deplecation is found in the parsing table, then the grammar is not LL(1)
                        if (parsingTable.get(nonTerminal).containsKey(terminal)) {
                            System.out.println("Error: The grammar is ambiguous or it is not a LL(1) grammar :(");
                            isLL1Grammar = false;
                            return;
                        }
                        parsingTable.get(nonTerminal).put(terminal, production);
                    }
                }
                if (first.contains(absolineSymbole)) {
                    for (String follow : followSets.get(nonTerminal)) {
                        // if deplecation is found in the parsing table, then the grammar is not LL(1)
                        if (parsingTable.get(nonTerminal).containsKey(follow)) {
                            System.out.println("Error: The grammar is ambiguous or it is not a LL(1) grammar :(");
                            isLL1Grammar = false;
                            return;
                        }
                        parsingTable.get(nonTerminal).put(follow, production);
                    }
                }
            }
        }
        System.out.println("Parsing table: " + parsingTable);
    }

    @Override
    public void printParsingTable() {
        int columnWidth = 20;

        System.out.printf("%-" + columnWidth + "s", "");
        for (String terminal : terminalSymbols) {
            System.out.printf("%-" + columnWidth + "s",  "|  "+terminal);
        }
        System.out.println();
        System.out.printf("%-" + columnWidth + "s", "");
        for (String terminal : terminalSymbols) {
            // System.out.printf("%-" + columnWidth + "s",  "_");
            for (int i = 0; i < columnWidth - 1; i++) {
                System.out.print("_");
            }
            System.out.print(" ");
        }
        System.out.println();

        for (String nonTerminal : nonTerminalSymbols) {
            System.out.printf("%-" + columnWidth + "s", nonTerminal);

            for (String terminal : terminalSymbols) {
                String rule = parsingTable.get(nonTerminal).get(terminal);
                if (rule != null) {
                    System.out.printf("%-" + columnWidth + "s",  "|  "+nonTerminal + " -> " + rule);
                } else {
                    System.out.printf("%-" + columnWidth + "s", "|  "+"");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void printAllResults() {
        System.out.println("first Sets:");
        for (String nonTerminal : firstSets.keySet()) {
            System.out.println(nonTerminal + " : " + firstSets.get(nonTerminal));
        }
        System.out.println();

        System.out.println("follow Sets:");
        for (String nonTerminal : followSets.keySet()) {
            System.out.println(nonTerminal + " : " + followSets.get(nonTerminal));
        }
        System.out.println();

        System.out.println("Parsing Table:");
        for (String nonTerminal : parsingTable.keySet()) {
            System.out.println(nonTerminal + " : ");
            Map<String, String> row = parsingTable.get(nonTerminal);
            for (String terminal : row.keySet()) {
                System.out.println("     " + terminal + " -> " + row.get(terminal));
            }
        }
        System.out.println();
        
        System.out.println("Parsing Table:");
        printParsingTable();
    }

    @Override
    public Map<String, Set<String>> getFirstSets() {
        return firstSets;
    }
    
    @Override
    public Map<String, Set<String>> getFollowSets() {
        return followSets;
    }
    
    @Override
    public Map<String, Map<String, String>> getParsingTable() {
        return parsingTable;
    }
    
    @Override
    public boolean isLL1Grammar() {
        return isLL1Grammar;
    }

    @Override
    public String getParsingTableAsString() {
        StringBuilder sb = new StringBuilder();
        int columnWidth = 20;

        sb.append(String.format("%-" + columnWidth + "s", ""));
        for (String terminal : terminalSymbols) {
            sb.append(String.format("%-" + columnWidth + "s", "|  " + terminal));
        }
        sb.append("\n");
        sb.append(String.format("%-" + columnWidth + "s", ""));
        for (String terminal : terminalSymbols) {
            for (int i = 0; i < columnWidth - 1; i++) {
                sb.append("_");
            }
            sb.append(" ");
        }
        sb.append("\n");

        for (String nonTerminal : nonTerminalSymbols) {
            sb.append(String.format("%-" + columnWidth + "s", nonTerminal));

            for (String terminal : terminalSymbols) {
                String rule = parsingTable.get(nonTerminal).get(terminal);
                if (rule != null) {
                    sb.append(String.format("%-" + columnWidth + "s", "|  " + nonTerminal + " -> " + rule));
                } else {
                    sb.append(String.format("%-" + columnWidth + "s", "|  "));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

