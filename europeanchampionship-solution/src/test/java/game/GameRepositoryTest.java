package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRepositoryTest {

    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository();
    }

    @Test
    void testReadFromFile() {
        gameRepository.readFromFile("src/main/resources/results.csv");

        Assertions.assertEquals("Wales", gameRepository.getGames().get(1).getFirstCountry());
        Assertions.assertEquals("Russia", gameRepository.getGames(). get(3).getSecondCountry());
        Assertions.assertEquals(0, gameRepository.getGames().get(7).getFirstCountryScore());
        Assertions.assertEquals(1, gameRepository.getGames().get(12).getSecondCountryScore());
    }

    @Test
    void testAddGame() {
        Assertions.assertEquals(0, gameRepository.getGames().size());

        gameRepository.addGame(new Game("Magyarország", "Portugália", 3, 1));

        Assertions.assertEquals(1, gameRepository.getGames().size());
        Assertions.assertEquals("Magyarország", gameRepository.getGames().get(0).getFirstCountry());
        Assertions.assertEquals(3, gameRepository.getGames().get(0).getFirstCountryScore());
    }
}