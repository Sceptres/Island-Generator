package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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

    @Override
    public double getWidth() {
        Rectangle2D rect = this.shape.getBounds2D();
        return rect.getWidth();
    }

    @Override
    public double getHeight() {
        Rectangle2D rect = this.shape.getBounds2D();
        return rect.getHeight();
    }

    @Override
    public Double getX() {
        Rectangle2D rect = this.shape.getBounds2D();
        return rect.getX() + rect.getWidth()/2;
    }

    @Override
    public Double getY() {
        Rectangle2D rect = this.shape.getBounds2D();
        return rect.getY() + rect.getHeight()/2;
    }

    @Override
    public Double[] getPosition() {
        return new Double[]{this.getX(), this.getY()};
    }
}
