package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles.TemperateRainForestColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NormalElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.NormalHumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class TemperateRainForestConfigurator extends AbstractConfigurator {
    public TemperateRainForestConfigurator() {
        super(
                new TemperateRainForestColorGenerator(),
                new NormalElevationHandler(),
                new NormalHumidityHandler()
        );
    }
}
