package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameRepositoryTest {

    GameRepository gameRepository;
    List<Game> games;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository();
    }

    @Test
    void testReadFromFile() {
        gameRepository.readFromFile("c:/training/java-halado-shared/game/src/main/resources/results.csv");
        games = gameRepository.getGames();

        Assertions.assertEquals("Wales", games.get(1).getFirstCountry());
        Assertions.assertEquals("Russia", games.get(3).getSecondCountry());
        Assertions.assertEquals("0", games.get(7).getFirstCountryScore());
        Assertions.assertEquals("1", games.get(12).getSecondCountryScore());
    }

    @Test
    void testAddGame() {
        Assertions.assertEquals(0, gameRepository.getGames().size());

        gameRepository.addGame(new Game("Magyarország", "Portugália", "3", "1"));

        Assertions.assertEquals(1, gameRepository.getGames().size());
        Assertions.assertEquals("Magyarország", gameRepository.getGames().get(0).getFirstCountry());
        Assertions.assertEquals("3", gameRepository.getGames().get(0).getFirstCountryScore());
    }
}