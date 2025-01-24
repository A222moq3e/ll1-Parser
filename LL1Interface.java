import java.util.Map;
import java.util.Set;

public interface LL1Interface {
    void readGrammarFromString(String grammarInput); // Read the grammar from a Input String
    void computeFirstSets(); 
    void computeFollowSets();
    void constructParsingTable(); 
    void printParsingTable(); 
    void printAllResults();
    Map<String, Set<String>> getFirstSets();
    Map<String, Set<String>> getFollowSets();
    Map<String, Map<String, String>> getParsingTable();
    boolean isLL1Grammar();
    String getParsingTableAsString();
}
