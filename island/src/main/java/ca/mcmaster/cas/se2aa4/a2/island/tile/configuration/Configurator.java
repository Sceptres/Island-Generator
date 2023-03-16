package ca.mcmaster.cas.se2aa4.a2.island.tile.configuration;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

import java.awt.*;

public interface Configurator {
    /**
     *
     * @return The {@link TileColorGenerator}
     */
    TileColorGenerator getColorGenerator();

    Color apply();
}
