package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public class AbstractConfigurator implements Configurator {

    private final ElevationHandler elevationHandler;
    private final TileColorGenerator colorGenerator;
    private final IHumidityTransmitter humidityTransmitter;

    protected AbstractConfigurator(
            TileColorGenerator colorGenerator,
            ElevationHandler elevationHandler,
            IHumidityTransmitter humidityTransmitter
    ) {
        this.colorGenerator = colorGenerator;
        this.elevationHandler = elevationHandler;
        this.humidityTransmitter = humidityTransmitter;
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
    public IHumidityTransmitter getHumidityTransmitter() {
        return this.humidityTransmitter;
    }

    @Override
    public Color apply() {
        return this.colorGenerator.generateColor();
    }
}
