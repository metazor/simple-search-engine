package search.engine;

import search.engine.strategies.All;
import search.engine.strategies.Any;
import search.engine.strategies.ChosenStrategy;
import search.engine.strategies.EntryFinder;
import search.engine.strategies.None;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Creates and runs the search engine.
 */
public class Engine {

    private final Map<String, ArrayList<Integer>> invertedIndex =
            new HashMap<>();
    private final Map<Integer, String> lines = new HashMap<>();

    /**
     * Adds the contents of the specified file to the inverted index and to the
     * map of lines.
     *
     * @param fileName the string of the name of the file to read
     * @return true, if the file is found
     */
    public boolean readFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                lines.put(lines.size(), currentLine);
                addLine(currentLine.toLowerCase().split(" "), lines.size() - 1);
            }

            return true;
        } catch (FileNotFoundException e) {
            Logger.getAnonymousLogger().warning("File not found! Exception "
                    + e.getClass() + " happened.");

            return false;
        }
    }

    /**
     * Adds the line number of the words from the specified array to the
     * inverted index if it contains them, or creates a new entry of them if it
     * doesn't.
     *
     * @param currentLine the string array of the words
     * @param line        the line number of the words
     */
    private void addLine(String[] currentLine, int line) {
        for (String word : currentLine) {
            if (invertedIndex.containsKey(word)) {
                invertedIndex.get(word).add(line);
            } else {
                invertedIndex.put(word, new ArrayList<>(List.of(line)));
            }
        }
    }

    /**
     * Searches the inverted index with the specified search strategy and entry
     * to find.
     *
     * @param chosenStrategy the chosen strategy
     * @param entryToFind    the string array of the entry to find
     * @return the set of line numbers of the found entries
     */
    public Set<Integer> search(ChosenStrategy chosenStrategy,
                               String[] entryToFind) {
        EntryFinder entryFinder = new EntryFinder();

        switch (chosenStrategy) {
            case ALL -> entryFinder.setStrategy(new All());
            case ANY -> entryFinder.setStrategy(new Any());
            case NONE -> entryFinder.setStrategy(new None());
        }

        return entryFinder.getFoundEntries(entryToFind,
                Collections.unmodifiableMap(invertedIndex));
    }

    public Map<Integer, String> getLines() {
        return Collections.unmodifiableMap(lines);
    }
}
