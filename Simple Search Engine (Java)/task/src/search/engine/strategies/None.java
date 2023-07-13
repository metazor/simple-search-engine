package search.engine.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Searches for lines which don't have any word of the search query.
 */
public class None implements Strategy {

    /**
     * Searches for lines which don't have any word of the search query.
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

        for (Collection<Integer> integers : invertedIndex.values()) {
            foundEntries.addAll(integers);
        }

        for (String word : entryToFind) {
            if (invertedIndex.containsKey(word)) {
                invertedIndex.get(word).forEach(foundEntries::remove);
            }
        }

        return foundEntries;
    }
}
