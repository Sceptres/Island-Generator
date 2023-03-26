package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.color.colors.tiles.IceColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NormalElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.NormalHumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class IceConfigurator extends AbstractConfigurator {
    public IceConfigurator() {
        super(
                new IceColorGenerator(),
                new NormalElevationHandler(),
                new NormalHumidityHandler()
        );
    }
}
