package ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.AbstractShape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.util.GeometricShapeFactory;

public class Oval extends AbstractShape {
    /**
     *
     * @param hRadius The horizontal radius of the oval
     * @param vRadius The vertical radius of the oval
     * @param v The center vertex of this oval
     * @return The oval geometry
     */
    private static Geometry createOval(double hRadius, double vRadius, Vertex v) {
        GeometryFactory factory = new GeometryFactory();
        Coordinate center = new Coordinate(v.getX(), v.getY());

        GeometricShapeFactory shapeFactory = new GeometricShapeFactory(factory);
        shapeFactory.setCentre(center);
        shapeFactory.setWidth(hRadius * 2);
        shapeFactory.setHeight(vRadius * 2);

        // create an ellipse geometry using the shape factory
        return shapeFactory.createEllipse();
    }

    public Oval(double hRadius, double vRadius, Vertex center) {
        super(createOval(hRadius, vRadius, center));
    }
}
