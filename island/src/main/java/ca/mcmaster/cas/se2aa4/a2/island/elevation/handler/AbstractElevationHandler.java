package ca.mcmaster.cas.se2aa4.a2.island.elevation.handler;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;

import java.text.DecimalFormat;

public abstract class AbstractElevationHandler implements ElevationHandler {
    private static final DecimalFormat f = new DecimalFormat("#.#####");

    @Override
    public final void takeElevation(IElevation e, double elevation) {
        double calculatedElevation = this.calculateElevation(e, elevation);

        String elevationStr = f.format(calculatedElevation);
        double roundedElevation = Double.parseDouble(elevationStr);

        e.setElevation(roundedElevation);
    }

    /**
     *
     * @param elevation The elevation that should be set
     * @return The elevation is calculated
     */
    protected abstract double calculateElevation(IElevation e, double elevation);
}
