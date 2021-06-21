package movie;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    void testCreateMovie(){
        Movie dieHard = new Movie("Die Hard", 134, LocalDate.of(1992, 10,11));

        assertEquals("Die Hard", dieHard.getName());
        assertEquals(134, dieHard.getLength());
        assertEquals(LocalDate.of(1992,10,11), dieHard.getReleaseDate());
    }
}
