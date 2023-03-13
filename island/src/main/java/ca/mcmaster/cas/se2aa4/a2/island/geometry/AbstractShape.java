package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class AbstractShape implements Shape {

    protected final Geometry geometry;

    protected AbstractShape(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public <T extends Positionable<Double>> boolean contains(T t) {
        Coordinate coordinate = new Coordinate(t.getX(), t.getY());
        CoordinateSequence sequence = new CoordinateArraySequence(new Coordinate[]{coordinate});
        Point point = new Point(sequence, new GeometryFactory());
        return this.geometry.contains(point);
    }
}
