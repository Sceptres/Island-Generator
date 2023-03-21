package ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

public interface AltimeterProfile {
    /**
     *
     * @param shape The {@link Shape} of the island
     * @param pos The {@link Positionable} to get position of
     * @return The elevation of this element
     */
    double generateElevation(Shape shape, Positionable<Double> pos);
}
