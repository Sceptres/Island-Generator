package ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.AbstractShape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Oval extends AbstractShape {
    /**
     *
     * @param hRadius The horizontal radius of the oval
     * @param vRadius The vertical radius of the oval
     * @param v The center vertex of this oval
     * @return The oval geometry
     */
    private static Shape createOval(double hRadius, double vRadius, Vertex v) {
        return new Ellipse2D.Double(v.getX()-hRadius, v.getY()-vRadius, hRadius*2, vRadius*2);
    }

    public Oval(double hRadius, double vRadius, Vertex center) {
        super(createOval(hRadius, vRadius, center));
    }
}
