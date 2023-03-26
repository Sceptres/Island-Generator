package ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.Random;

public class LagoonAltimeter implements AltimeterProfile {
    @Override
    public double generateElevation(Random random, Shape shape, Positionable<Double> pos) {
        Random rand = new Random();

        Vertex meshCenter = new Vertex(shape.getX(), shape.getY());
        double diagonalLength = Math.hypot(shape.getX(), shape.getY());
        Shape circle = new Circle(diagonalLength/4f, meshCenter);

        double elevation = rand.nextDouble(0, 0.2);

        if(!circle.contains(pos))
            elevation = rand.nextDouble(0.2, 1);

        return elevation;
    }
}
