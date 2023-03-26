package ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles;

import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.TileColors;

import java.awt.*;

public class TaigaColorGenerator implements ColorGenerator {
    @Override
    public Color generateColor() {
        return TileColors.TAIGA;
    }
}
