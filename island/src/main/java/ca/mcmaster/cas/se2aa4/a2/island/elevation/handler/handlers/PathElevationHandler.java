package ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.AbstractElevationHandler;

public class PathElevationHandler extends AbstractElevationHandler {
    @Override
    protected double calculateElevation(IElevation e, double elevation) {
        double oldElevation = e.getElevation();
        return Math.min(elevation, oldElevation);
    }
}
