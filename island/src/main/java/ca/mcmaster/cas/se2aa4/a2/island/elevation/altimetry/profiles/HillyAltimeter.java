package ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.util.Random;

public class HillyAltimeter implements AltimeterProfile {

    @Override
    public double generateElevation(Random random, Shape shape, Positionable<Double> pos) {
        double x = pos.getX();
        double y = pos.getY();

        return (0.5*Math.sin(x)) + (0.5*Math.sin(y));
    }
}
