package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.awt.geom.Point2D;

public class AbstractShape implements Shape {

    protected final java.awt.Shape shape;

    protected AbstractShape(java.awt.Shape shape) {
        this.shape = shape;
    }

    @Override
    public <T extends Positionable<Double>> boolean contains(T t) {
        Point2D point = new Point2D.Double(t.getX(), t.getY());
        return this.shape.contains(point);
    }
}
