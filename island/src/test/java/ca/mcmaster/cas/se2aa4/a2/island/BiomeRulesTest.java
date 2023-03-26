package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.biome.Rule;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
public class BiomeRulesTest {
    private Rule rule;

    @BeforeEach
    public void beforeTest() {
        this.rule = new Rule(250, 500, 25, 50, TileType.LAND);
    }

    @Test
    public void matchTest() {
        boolean matches;
        float humidity;
        double temperature;

        humidity = 325;
        temperature = 35;
        matches = this.rule.matches(humidity, temperature);
        assertTrue(matches);

        humidity = 250;
        temperature = 25;
        matches = this.rule.matches(humidity, temperature);
        assertTrue(matches);

        humidity = 240;
        temperature = 15;
        matches = this.rule.matches(humidity, temperature);
        assertFalse(matches);

        humidity = 0;
        temperature = 0;
        matches = this.rule.matches(humidity, temperature);
        assertFalse(matches);
    }

    @Test
    public void typeTest() {
        float humidity;
        double temperature;
        boolean matches;
        TileType type = null;

        humidity = 50;
        temperature = 20;
        matches = this.rule.matches(humidity, temperature);
        if(matches)
            type = this.rule.getType();
        assertNull(type);

        humidity = 275;
        temperature = 45;
        matches = this.rule.matches(humidity, temperature);
        if(matches)
            type = this.rule.getType();

        assertEquals(type, TileType.LAND);
    }

    public void equalityTest() {
        Rule rule1 = new Rule(250, 500, 25, 50, TileType.LAND);
        assertEquals(this.rule, rule1);

        Rule rule2 = new Rule(250, 500, 0, 25, TileType.LAND);
        assertNotEquals(this.rule, rule2);
    }
}