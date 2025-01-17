import java.util.*;

public class LL1P2 {

    private final Map<String, List<String>> grammarRules = new LinkedHashMap<>();
    private final Map<String, Set<String>> firstSets = new HashMap<>();
    private final Map<String, Set<String>> followSets = new HashMap<>();
    private final Map<String, Map<String, String>> parsingTable = new HashMap<>();
    private final Set<String> nonTerminalSymbols = new HashSet<>();
    private final Set<String> terminalSymbols = new HashSet<>();
    private String startSymbol;

    public void readGrammarFromString(String grammarInput) {
        String[] lines = grammarInput.split("\\n");
        for (String line : lines) {
            String[] parts = line.split("->");
            String lhs = parts[0].trim();
            String[] rhsParts = parts[1].split("\\|");

            nonTerminalSymbols.add(lhs);
            List<String> productions = new ArrayList<>();

            for (String rhs : rhsParts) {
                String production = rhs.trim();
                productions.add(production);
                for (String symbol : production.split(" ")) {
                    if (!nonTerminalSymbols.contains(symbol) && !symbol.equals("ε")) {
                        terminalSymbols.add(symbol);
                    }
                }
            }

            grammarRules.put(lhs, productions);
        }
        terminalSymbols.removeAll(nonTerminalSymbols);
        terminalSymbols.add("$"); // Add end marker
        startSymbol = lines[0].split("->")[0].trim();
    }

    public void computeFirstSets() {
        for (String nonTerminal : nonTerminalSymbols) {
            firstSets.put(nonTerminal, new HashSet<>());
        }

        boolean changed;
        do {
            changed = false;
            for (Map.Entry<String, List<String>> entry : grammarRules.entrySet()) {
                String lhs = entry.getKey();
                for (String rhs : entry.getValue()) {
                    String[] symbols = rhs.split(" ");
                    for (String symbol : symbols) {
                        Set<String> firstSet = symbol.equals("ε") || terminalSymbols.contains(symbol)
                                ? Set.of(symbol)
                                : firstSets.get(symbol);

                        if (firstSet != null && firstSets.get(lhs).addAll(firstSet)) {
                            changed = true;
                        }

                        if (!firstSet.contains("ε")) {
                            break;
                        }
                    }
                }
            }
        } while (changed);
    }

    public void computeFollowSets() {
        for (String nonTerminal : nonTerminalSymbols) {
            followSets.put(nonTerminal, new HashSet<>());
        }
        followSets.get(startSymbol).add("$");

        boolean changed;
        do {
            changed = false;
            for (Map.Entry<String, List<String>> entry : grammarRules.entrySet()) {
                String lhs = entry.getKey();
                for (String rhs : entry.getValue()) {
                    String[] symbols = rhs.split(" ");
                    Set<String> trailer = new HashSet<>(followSets.get(lhs));

                    for (int i = symbols.length - 1; i >= 0; i--) {
                        String symbol = symbols[i];
                        if (nonTerminalSymbols.contains(symbol)) {
                            if (followSets.get(symbol).addAll(trailer)) {
                                changed = true;
                            }
                            if (firstSets.get(symbol).contains("ε")) {
                                trailer.addAll(firstSets.get(symbol));
                                trailer.remove("ε");
                            } else {
                                trailer = new HashSet<>(firstSets.get(symbol));
                            }
                        } else {
                            trailer = Set.of(symbol);
                        }
                    }
                }
            }
        } while (changed);
    }

    public void constructParsingTable() {
        for (String nonTerminal : nonTerminalSymbols) {
            parsingTable.put(nonTerminal, new HashMap<>());
        }

        for (Map.Entry<String, List<String>> entry : grammarRules.entrySet()) {
            String lhs = entry.getKey();
            for (String rhs : entry.getValue()) {
                Set<String> firstSet = new HashSet<>();
                for (String symbol : rhs.split(" ")) {
                    if (terminalSymbols.contains(symbol) || symbol.equals("ε")) {
                        firstSet.add(symbol);
                        break;
                    }
                    firstSet.addAll(firstSets.get(symbol));
                    if (!firstSets.get(symbol).contains("ε")) {
                        break;
                    }
                }

                for (String terminal : firstSet) {
                    if (!terminal.equals("ε")) {
                        if (parsingTable.get(lhs).containsKey(terminal)) {
                            System.out.println("The grammar is ambiguous or it is inherently not a LL(1) grammar.");
                            return;
                        }
                        parsingTable.get(lhs).put(terminal, lhs + " -> " + rhs);
                    }
                }

                if (firstSet.contains("ε")) {
                    for (String terminal : followSets.get(lhs)) {
                        if (parsingTable.get(lhs).containsKey(terminal)) {
                            System.out.println("The grammar is ambiguous or it is inherently not a LL(1) grammar.");
                            return;
                        }
                        parsingTable.get(lhs).put(terminal, lhs + " -> " + rhs);
                    }
                }
            }
        }
    }

    public void printParsingTable() {
        System.out.print("\t");
        for (String terminal : terminalSymbols) {
            System.out.print(terminal + "\t");
        }
        System.out.println();

        for (String nonTerminal : nonTerminalSymbols) {
            System.out.print(nonTerminal + "\t");
            for (String terminal : terminalSymbols) {
                String rule = parsingTable.get(nonTerminal).get(terminal);
                System.out.print((rule != null ? rule : "") + "\t");
            }
            System.out.println();
        }
    }

    public void printAllResults() {
        System.out.println("first Sets:");
        for (Map.Entry<String, Set<String>> entry : firstSets.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();

        System.out.println("follow Sets:");
        for (Map.Entry<String, Set<String>> entry : followSets.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();

        System.out.println("Parsing Table:");
        printParsingTable();
    }

    public static void main(String[] args) {
        LL1P2 parser = new LL1P2();
        
        String grammarEx = """
        S -> ( L ) | a
        L -> S L'
        L' -> ) S L' |  ε
        """;
        parser.readGrammarFromString(grammarEx);
        parser.computeFirstSets();
        parser.computeFollowSets();
        parser.constructParsingTable();
        parser.printAllResults();
    }
}
