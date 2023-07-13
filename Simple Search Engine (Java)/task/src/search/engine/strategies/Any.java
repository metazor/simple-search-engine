package search.engine.strategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Searches for lines which have at least one word of the search query.
 */
public class Any implements Strategy {

    /**
     * Searches for lines which have at least one word of the search query.
     *
     * @param entryToFind   the string array of the entry to find
     * @param invertedIndex the map of the inverted index
     * @return the set of line numbers of the found entries
     */
    @Override
    public Set<Integer> getFoundEntries(String[] entryToFind,
                                        Map<String, ArrayList<Integer>>
                                                invertedIndex) {
        Set<Integer> foundEntries = new HashSet<>();

        for (String word : entryToFind) {
            if (invertedIndex.containsKey(word)) {
                foundEntries.addAll(invertedIndex.get(word));
            }
        }

        return foundEntries;
    }
}
