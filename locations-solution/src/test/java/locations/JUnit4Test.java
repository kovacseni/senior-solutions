package locations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class JUnit4Test {

    @DisplayName("JUnit 4 test case.")
    @Test
    public void testCreate() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        Assert.assertEquals("Budapest", location.getName());
        Assert.assertEquals(47.497912, location.getLat(), 0.001);
        Assert.assertEquals(19.040235, location.getLon(), 0.001);
    }
}
