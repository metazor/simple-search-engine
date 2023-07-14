package search;

import search.engine.Engine;

import java.io.FileNotFoundException;

public class Main {

    private static final Engine engine = new Engine();

    public static void main(String[] args) throws FileNotFoundException {
        UI ui = new UI(engine, args[1]);
        ui.startMenu();
    }
}
