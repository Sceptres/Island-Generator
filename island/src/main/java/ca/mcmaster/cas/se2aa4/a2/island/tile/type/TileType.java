package ca.mcmaster.cas.se2aa4.a2.island.tile.type;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.BeachColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.LandWaterColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.LandColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.color.colors.OceanColorGenerator;

public enum TileType {
    BEACH_TILE(TileGroup.LAND, new BeachColorGenerator()),
    LAND_TILE(TileGroup.LAND, new LandColorGenerator()),
    LAND_WATER_TILE(TileGroup.WATER, new LandWaterColorGenerator()),
    OCEAN_TILE(TileGroup.WATER, new OceanColorGenerator());


    private final TileGroup group;
    private final TileColorGenerator generator;

    TileType(TileGroup group, TileColorGenerator generator) {
        this.group = group;
        this.generator = generator;
    }

    /**
     *
     * @return The {@link TileGroup} that this tile type belongs to
     */
    public TileGroup getGroup() {
        return this.group;
    }

    /**
     *
     * @return The {@link TileColorGenerator} of this type
     */
    public TileColorGenerator getColorGenerator() {
        return this.generator;
    }
}
