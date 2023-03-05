package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.BeachColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.LagoonColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.LandColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.OceanColorGenerator;

public enum TileType {
    BEACH_TILE(new BeachColorGenerator()),
    LAND_TILE(new LandColorGenerator()),
    LAGOON_TILE(new LagoonColorGenerator()),
    OCEAN_TILE(new OceanColorGenerator());


    private final TileColorGenerator generator;

    TileType(TileColorGenerator generator) {
        this.generator = generator;
    }

    /**
     *
     * @return The {@link TileColorGenerator} of this type
     */
    public TileColorGenerator getColorGenerator() {
        return this.generator;
    }
}
