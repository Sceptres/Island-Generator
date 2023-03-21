package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NoElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NormalElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles.ElevationProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class ElevationTest {
    private static ElevationProfile profile;

    @BeforeAll
    public static void beforeAll() {
        profile = new ElevationProfile();
    }

    @Test
    public void testSetElevation() {
        profile.setElevation(0.2);
        assertEquals(profile.getElevation(), 0.2);

        profile.setElevation(0);
        assertEquals(profile.getElevation(), 0);

        profile.setElevation(1);
        assertEquals(profile.getElevation(), 1);

        profile.setElevation(2);
        assertEquals(profile.getElevation(), 1);

        profile.setElevation(-1);
        assertEquals(profile.getElevation(), 0);
    }

    @Test
    public void testElevationHandler() {
        ElevationHandler handler = new NormalElevationHandler();
        handler.takeElevation(profile, 0.5);
        assertEquals(profile.getElevation(), 0.5);

        handler = new NoElevationHandler();
        handler.takeElevation(profile, 0);
        assertEquals(profile.getElevation(), 0);
    }
}
