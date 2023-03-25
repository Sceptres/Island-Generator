package ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles;

import ca.mcmaster.cas.se2aa4.a2.island.tile.TileColors;
import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;

import java.awt.*;

public class LandColorGenerator implements ColorGenerator {
    @Override
    public Color generateColor() {
        return TileColors.REGULAR;
    }
}
