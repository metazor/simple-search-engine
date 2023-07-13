package search.engine.strategies;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Context for the strategies.
 */
public class EntryFinder {

    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the numbers of lines which satisfy the search criteria in the
     * specified data.
     *
     * @param entryToFind   the string array of the entry to find
     * @param invertedIndex the map of the inverted index
     * @return the set of line numbers of the found entries
     */
    public Set<Integer> getFoundEntries(String[] entryToFind,
                                        Map<String, ArrayList<Integer>>
                                                invertedIndex) {
        return strategy.getFoundEntries(entryToFind, invertedIndex);
    }
}
