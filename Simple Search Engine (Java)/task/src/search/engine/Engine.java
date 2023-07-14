package search.engine;

import search.engine.strategies.All;
import search.engine.strategies.Any;
import search.engine.strategies.None;
import search.engine.strategies.Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Creates and runs the search engine.
 */
public class Engine {

    private final Map<String, ArrayList<Integer>> invertedIndex =
            new HashMap<>();
    private final Map<Integer, String> lines = new HashMap<>();
    private Strategy strategy;

    /**
     * Adds the contents of the specified file to the inverted index and to the
     * map of lines.
     *
     * @param fileName the string of the name of the file to read
     */
    public void readFile(String fileName) throws FileNotFoundException {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                lines.put(lines.size(), currentLine);
                addLine(currentLine.toLowerCase().split(" "), lines.size() - 1);
            }
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
    public Set<Integer> search(String chosenStrategy, String[] entryToFind) {
        switch (chosenStrategy) {
            case "ALL" -> strategy = new All();
            case "ANY" -> strategy = new Any();
            case "NONE" -> strategy = new None();
        }

        return strategy.getFoundEntries(entryToFind,
                Collections.unmodifiableMap(invertedIndex));
    }

    public Map<Integer, String> getLines() {
        return Collections.unmodifiableMap(lines);
    }
}
