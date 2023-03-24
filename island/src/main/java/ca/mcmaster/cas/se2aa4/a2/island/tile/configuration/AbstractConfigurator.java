package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public class AbstractConfigurator implements Configurator {

    private final ElevationHandler elevationHandler;
    private final TileColorGenerator colorGenerator;

    protected AbstractConfigurator(
            TileColorGenerator colorGenerator,
            ElevationHandler elevationHandler
    ) {
        this.colorGenerator = colorGenerator;
        this.elevationHandler = elevationHandler;
    }

    @Override
    public TileColorGenerator getColorGenerator() {
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
