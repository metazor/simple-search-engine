package search;

import search.engine.Engine;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class UI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String VALID_COMMANDS = "[0-2]";
    private static final String UNEXPECTED_VALUE = "Unexpected value: ";
    private final Engine engine;
    private final String fileName;

    /**
     * Class constructor, which initializes the specified field.
     *
     * @param engine   the engine used by this
     * @param fileName the string of the name of the file with the data
     */
    UI(Engine engine, String fileName) {
        this.engine = engine;
        this.fileName = fileName;
    }

    /**
     * Reads the user's command and executes it.
     */
    void startMenu() throws FileNotFoundException {
        engine.readFile(fileName);
        Command input = null;

        while (input != Command.EXIT) {
            input = readCommand();

            if (input == Command.SEARCH) {
                String chosenStrategy = readStrategy();
                String[] entryToFind = readEntry().split(" ");
                System.out.println();
                Set<Integer> foundEntries = engine.search(chosenStrategy,
                        entryToFind);
                printFoundEntries(foundEntries, engine.getLines());
            } else if (input == Command.PRINT_EVERYONE) {
                printEveryone(engine.getLines().values());
            }
        }

        System.out.println("Bye!");
    }

    /**
     * Prints every line of the specified data.
     *
     * @param lines the iterable of the lines to print
     */
    private void printEveryone(Iterable<String> lines) {
        System.out.println("=== List of people ===");

        for (String value : lines) {
            System.out.println(value);
        }

        System.out.println();
    }

    /**
     * Prints the menu and reads the user's command.
     *
     * @return the user's command
     */
    private Command readCommand() {
        while (true) {
            System.out.println("""
                    === Menu ===
                    1. Find a person
                    2. Print all people
                    0. Exit""");
            String command = scanner.nextLine();

            if (!command.matches(VALID_COMMANDS)) {
                System.out.printf("%nIncorrect option! Try again.%n");
                continue;
            }

            System.out.println();

            return switch (command) {
                case "0" -> Command.EXIT;
                case "1" -> Command.SEARCH;
                case "2" -> Command.PRINT_EVERYONE;
                default -> throw new IllegalStateException(UNEXPECTED_VALUE
                        + command);
            };
        }
    }

    /**
     * Asks the user for a search query and reads it.
     *
     * @return the string of the search query
     */
    private String readEntry() {
        System.out.println("Enter a name or email to search all suitable "
                + "people.");
        return scanner.nextLine().toLowerCase();
    }

    /**
     * Asks the user for a search strategy and reads it.
     *
     * @return the chosen strategy
     */
    private String readStrategy() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String chosenStrategy = scanner.nextLine();
        System.out.println();

        return chosenStrategy;
    }

    /**
     * Prints the found entries of the search query.
     *
     * @param entries the set of line numbers of the found entries of the search
     *                query
     * @param lines   the map of the lines
     */
    private void printFoundEntries(Set<Integer> entries, Map<Integer,
            String> lines) {
        if (entries.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.printf("%d persons found:%n", entries.size());

            for (Integer entry : entries) {
                System.out.println(lines.get(entry));
            }
        }

        System.out.println();
    }
}
