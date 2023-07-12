package search;

import search.strategies.All;
import search.strategies.Any;
import search.strategies.None;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Creates and runs the search engine.
 */
class Engine {

    private final UI ui;
    private final String fileName;
    private final Map<String, ArrayList<Integer>> invertedIndex =
            new HashMap<>();
    private final Map<Integer, String> lines = new HashMap<>();

    /**
     * Class constructor, which initializes the specified fields.
     *
     * @param ui       the UI used by this
     * @param fileName the string of the name of the file with the data we want
     *                 to search in
     */
    Engine(UI ui, String fileName) {
        this.ui = ui;
        this.fileName = fileName;
    }

    /**
     * Adds the contents of the file to the inverted index and to the map of
     * lines.
     */
    void readFile() {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                lines.put(lineNumber, currentLine);
                addLine(currentLine.toLowerCase().split(" "), lineNumber);
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            ui.printFileNotFoundExceptionMessage(e);
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
     * Prints the menu, reads the user's command and executes it.
     */
    void startMenu() {
        Command input = null;

        while (input != Command.EXIT) {
            input = ui.readCommand();

            if (input == Command.SEARCH) {
                search();
            } else if (input == Command.PRINT_EVERYONE) {
                ui.printEveryone(lines.values());
            }
        }

        ui.printBye();
    }

    /**
     * Asks the user to choose a search strategy and the entry to find, then
     * executes the search and prints the results.
     */
    private void search() {
        ChosenStrategy chosenStrategy = ui.readStrategy();
        String entryToFind = ui.readEntry();
        System.out.println();
        EntryFinder entryFinder = new EntryFinder();

        switch (chosenStrategy) {
            case ALL -> entryFinder.setStrategy(new All());
            case ANY -> entryFinder.setStrategy(new Any());
            case NONE -> entryFinder.setStrategy(new None());
        }

        Set<Integer> foundEntries = entryFinder.getFoundEntries(entryToFind,
                invertedIndex);
        ui.printFoundEntries(foundEntries, lines);
    }
}
