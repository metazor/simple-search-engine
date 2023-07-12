package search;

public class Main {

    private static final Engine engine = new Engine();

    public static void main(String[] args) {
        if (engine.readFile(args[1])) {
            UI ui = new UI(engine);
            ui.startMenu();
        }
    }
}
