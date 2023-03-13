package ca.mcmaster.cas.se2aa4.a2.island.geometry;

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
     * @param x The x position of the circle
     * @param y The y position of the circle
     * @param r The radius of the circle
     * @return A circle geometry
     */
    private static Geometry createCircle(double x, double y, final double r) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(500);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(r * 2);
        return shapeFactory.createCircle();
    }

    /**
     *
     * @param radius radius of the circle
     * @param vertex the center of the circle
     */
    public Circle(double radius, Vertex vertex){
        super(createCircle(vertex.getX(), vertex.getY(), radius));
    }


}
