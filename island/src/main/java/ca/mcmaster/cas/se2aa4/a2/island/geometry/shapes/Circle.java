package ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.AbstractShape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;

/**
 * This class is to make a perimeter for the lagoon and the beach
 */
public class Circle extends AbstractShape {
    /**
     *
     * @param v The centroid of the circle
     * @param r The radius of the circle
     * @return A circle geometry
     */
    private static Geometry createCircle(double r, Vertex v) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(500);
        shapeFactory.setCentre(new Coordinate(v.getX(), v.getY()));
        shapeFactory.setSize(r * 2);
        return shapeFactory.createCircle();
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
