package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void testCreate() {
        Game game = new Game("Magyarország", "Portugália", "3", "1");

        Assertions.assertEquals("Magyarország", game.getFirstCountry());
        Assertions.assertEquals("Portugália", game.getSecondCountry());
        Assertions.assertEquals("3", game.getFirstCountryScore());
        Assertions.assertEquals("1", game.getSecondCountryScore());
    }
}
