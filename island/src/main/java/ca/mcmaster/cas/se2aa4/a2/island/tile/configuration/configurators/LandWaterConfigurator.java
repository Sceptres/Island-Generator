package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.LandWaterColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.AbstractConfigurator;

public class LandWaterConfigurator extends AbstractConfigurator {
    public LandWaterConfigurator() {
        super(new LandWaterColorGenerator());
    }
}
