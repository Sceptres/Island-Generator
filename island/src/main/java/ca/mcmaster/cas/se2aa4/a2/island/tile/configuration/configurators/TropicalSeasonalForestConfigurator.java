package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles.TropicalSeasonalForestColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NormalElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.NormalHumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class TropicalSeasonalForestConfigurator extends AbstractConfigurator {
    public TropicalSeasonalForestConfigurator() {
        super(
                new TropicalSeasonalForestColorGenerator(),
                new NormalElevationHandler(),
                new NormalHumidityHandler()
        );
    }
}
