package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;

import java.awt.*;

public class PolygonTransparentColorGenerator implements ColorGenerator<Polygon> {

    private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    @Override
    public void generateColor(Polygon polygon) {
        polygon.setColor(TRANSPARENT_COLOR);
    }
}
