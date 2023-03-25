package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NoElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.humidityHandlers.NoHumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.OceanColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class OceanConfigurator extends AbstractConfigurator {
    public OceanConfigurator() {
        super(
                new OceanColorGenerator(),
                new NoElevationHandler(),
                new NoHumidityHandler()
        );
    }
}
