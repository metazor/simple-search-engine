package search.strategies;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface Strategy {

    /**
     * Gets the numbers of lines which satisfy the search criteria in the
     * specified data.
     *
     * @param entryToFind   the string of the entry to find
     * @param invertedIndex the map of the inverted index
     * @return the set of line numbers of the found entries
     */
    Set<Integer> getFoundEntries(String entryToFind,
                                 Map<String, ArrayList<Integer>> invertedIndex);
}
