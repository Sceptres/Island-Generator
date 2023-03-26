package ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.AbstractShape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * This class is to make a perimeter for the lagoon and the beach
 */
public class Circle extends AbstractShape {
    /**
     *
     * @param v The centroid of the circle
     * @param r The radius of the circle
     * @return A circle shape
     */
    private static Shape createCircle(double r, Vertex v) {
        double diameter = r*2f;
        return new Ellipse2D.Double(v.getX()-r, v.getY()-r, diameter, diameter);
    }

    /**
     *
     * @param radius radius of the circle
     * @param vertex the center of the circle
     */
    public Circle(double radius, Vertex vertex){
        super(createCircle(radius, vertex));
    }
}
