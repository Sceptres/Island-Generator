package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;

import java.awt.*;

public class AbstractConfigurator implements Configurator {
    private final ElevationHandler elevationHandler;
    private final ColorGenerator colorGenerator;

    protected AbstractConfigurator(ColorGenerator colorGenerator, ElevationHandler elevationHandler) {
        this.colorGenerator = colorGenerator;
        this.elevationHandler = elevationHandler;
    }

    @Override
    public ColorGenerator getColorGenerator() {
        return this.colorGenerator;
    }

    @Override
    public ElevationHandler getElevationHandler() {
        return this.elevationHandler;
    }

    @Override
    public Color apply() {
        return this.colorGenerator.generateColor();
    }
}
