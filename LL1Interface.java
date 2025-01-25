import java.util.Map;
import java.util.Set;

public interface LL1Interface {
    void readGrammarFromString(String grammarInput); // Read the grammar from a Input String
    void computeFirstSets();  // Compute the First Sets
    void computeFollowSets(); // Compute the Follow Sets
    void constructParsingTable();  // Construct the Parsing Table
    void printParsingTable();   // Print the Parsing Table to Text Area
    void printAllResults(); // Print all the results to Terminal
    Map<String, Set<String>> getFirstSets();
    Map<String, Set<String>> getFollowSets();
    Map<String, Map<String, String>> getParsingTable();
    boolean isLL1Grammar();
    String getParsingTableAsString();
}
