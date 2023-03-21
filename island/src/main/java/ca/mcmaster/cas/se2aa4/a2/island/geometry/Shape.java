package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

public interface Shape extends Positionable<Double> {
    /**
     *
     * @param t An object that has a position
     * @return True if this shape contains t
     * @param <T> The {@link Positionable} object
     */
    <T extends Positionable<Double>> boolean contains(T t);

    /**
     *
     * @return The width of this shape
     */
    double getWidth();

    /**
     *
     * @return The height of this shape
     */
    double getHeight();
}
