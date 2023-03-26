package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NormalElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.NoHumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles.LandWaterColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class LandWaterConfigurator extends AbstractConfigurator {
    public LandWaterConfigurator() {
        super(
                new LandWaterColorGenerator(),
                new NormalElevationHandler(),
                new NoHumidityHandler()
        );
    }
}
