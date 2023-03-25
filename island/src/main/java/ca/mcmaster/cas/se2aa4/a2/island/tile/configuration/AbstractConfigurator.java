package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.HumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public class AbstractConfigurator implements Configurator {

    private final ElevationHandler elevationHandler;
    private final TileColorGenerator colorGenerator;
    private final HumidityHandler humidityHandler;

    protected AbstractConfigurator(
            TileColorGenerator colorGenerator,
            ElevationHandler elevationHandler,
            HumidityHandler humidityHandler
    ) {
        this.colorGenerator = colorGenerator;
        this.elevationHandler = elevationHandler;
        this.humidityHandler = humidityHandler;
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
    public HumidityHandler getHumidityHandler(){ return this.humidityHandler; }

    @Override
    public Color apply() {
        return this.colorGenerator.generateColor();
    }
}
