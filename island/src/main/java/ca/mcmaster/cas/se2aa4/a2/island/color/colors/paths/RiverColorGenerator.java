package ca.mcmaster.cas.se2aa4.a2.island.color.colors.paths;

import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.path.PathColor;

import java.awt.*;

public class RiverColorGenerator implements ColorGenerator {
    @Override
    public Color generateColor() {
        return PathColor.RIVER_COLOR;
    }
}
