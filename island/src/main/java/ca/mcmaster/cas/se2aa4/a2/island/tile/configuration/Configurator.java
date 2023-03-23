package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public interface Configurator {
    /**
     *
     * @return The {@link TileColorGenerator}
     */
    TileColorGenerator getColorGenerator();

    /**
     *
     * @return The {@link ElevationHandler}
     */
    ElevationHandler getElevationHandler();

    /**
     *
     * @return The {@link IHumidityTransmitter}
     */
    IHumidityTransmitter getHumidityTransmitter();

    /**
     *
     * @return The {@link Color} after applying configurations
     */
    Color apply();
}
