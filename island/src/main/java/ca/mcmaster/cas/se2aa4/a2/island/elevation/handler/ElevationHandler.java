package ca.mcmaster.cas.se2aa4.a2.island.elevation.handler;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;

public interface ElevationHandler {
    /**
     *
     * @param e The element to give the elevation to
     * @param elevation The elevation to give to the element
     */
    void takeElevation(IElevation e, double elevation);
}
