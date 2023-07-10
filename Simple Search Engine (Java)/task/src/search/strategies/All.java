package search.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Searches for lines which have every word of the search query.
 */
public class All implements Strategy {

    /**
     * Searches for lines which have every word of the search query.
     *
     * @param entryToFind   the string of the entry to find
     * @param invertedIndex the map of the inverted index
     * @return the set of line numbers of the found entries
     */
    @Override
    public Set<Integer> getFoundEntries(String entryToFind,
                                        Map<String, ArrayList<Integer>>
                                                invertedIndex) {
        List<String> splitEntryToFind = Arrays.asList(entryToFind.split(" "));
        Set<Integer> foundEntries = new HashSet<>();

        for (int i = 0; i < splitEntryToFind.size(); i++) {
            String word = splitEntryToFind.get(i);
            verifyWord(invertedIndex, foundEntries, i, word);
        }

        return foundEntries;
    }

    /**
     * If the inverted index has the word, it adds its line numbers to the found
     * entries if it's the first word of the entry to find then changes the
     * found entries to the intersection of the found line numbers and the line
     * numbers of the word.
     *
     * @param invertedIndex the map of the inverted index
     * @param foundEntries  the set of the found entries
     * @param i             the index of the word
     * @param word          the string of the word
     */
    private static void verifyWord(Map<String, ArrayList<Integer>>
                                           invertedIndex,
                                   Collection<Integer> foundEntries,
                                   int i,
                                   String word) {
        if (invertedIndex.containsKey(word)) {
            if (i == 0) {
                foundEntries.addAll(invertedIndex.get(word));
            }

            foundEntries.retainAll(invertedIndex.get(word));
        }
    }
}
