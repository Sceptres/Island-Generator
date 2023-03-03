package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import java.lang.reflect.Array;

public interface Positionable<T extends Number> {

    /**
     *
     * @return The X axis coordinate
     */
    T getX();

    /**
     *
     * @return The Y axis coordinate
     */
    T getY();

    /**
     *
     * @return The 2 element array with the position. [X, Y]
     */
    T[] getPosition();
}
