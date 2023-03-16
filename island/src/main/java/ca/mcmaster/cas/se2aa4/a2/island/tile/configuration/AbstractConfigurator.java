package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public class AbstractConfigurator implements Configurator {
    private final TileColorGenerator colorGenerator;

    protected AbstractConfigurator(TileColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;
    }

    @Override
    public TileColorGenerator getColorGenerator() {
        return this.colorGenerator;
    }

    @Override
    public Color apply() {
        return this.colorGenerator.generateColor();
    }
}
