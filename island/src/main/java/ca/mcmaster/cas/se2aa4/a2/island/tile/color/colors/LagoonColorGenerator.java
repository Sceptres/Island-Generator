package ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors;

import ca.mcmaster.cas.se2aa4.a2.island.tile.TileColors;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public class LagoonColorGenerator implements TileColorGenerator {
    @Override
    public Color generateColor() {
        return TileColors.LAGOON;
    }
}
