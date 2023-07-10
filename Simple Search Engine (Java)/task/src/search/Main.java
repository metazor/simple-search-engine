package search;

public class Main {

    private static final UI ui = new UI();

    public static void main(String[] args) {
        Engine engine = new Engine(ui, args[1]);
        engine.readFile();
        engine.startMenu();
    }
}
