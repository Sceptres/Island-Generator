package ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.util.Random;

public interface AltimeterProfile {
    /**
     *
     * @param random The {@link Random} to use in generating the elevation
     * @param shape The {@link Shape} of the island
     * @param pos The {@link Positionable} to get position of
     * @return The elevation of this element
     */
    double generateElevation(Random random, Shape shape, Positionable<Double> pos);
}
