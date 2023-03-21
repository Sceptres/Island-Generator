package ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

public class VolcanoAltimeter implements AltimeterProfile {
    @Override
    public double generateElevation(Shape shape, Positionable<Double> pos) {
        double midX = shape.getX();
        double midY = shape.getY();
        double maxDist = Math.hypot(shape.getWidth()/2, shape.getHeight()/2);

        double dist = Math.hypot(midX-pos.getX(), midY-pos.getY())/maxDist;
        return 1-dist;
    }
}
