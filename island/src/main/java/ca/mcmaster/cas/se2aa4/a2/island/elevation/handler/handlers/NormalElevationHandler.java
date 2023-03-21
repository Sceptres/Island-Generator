package ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;

public class NormalElevationHandler implements ElevationHandler {
    @Override
    public void takeElevation(IElevation e, double elevation) {
        e.setElevation(elevation);
    }
}
