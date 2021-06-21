package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public List<Game> getGames() {
        return new ArrayList<Game>(games);
    }

    public void readFromFile(String fileName) {
        Path file = Path.of(fileName);
        try (BufferedReader bf = Files.newBufferedReader(file)) {
            String line;
            String[] result;
            while ((line = bf.readLine()) != null) {
                result = line.split(";");
                this.addGame(new Game(result[0], result[1], result[2], result[3]));
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("File not found", ioe);
        }
    }
}
