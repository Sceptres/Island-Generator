package ca.mcmaster.cas.se2aa4.a2.island.tile.type;

import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.Configurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators.*;

public enum TileType {
    TROPICAL_SEASONAL_FOREST(TileGroup.LAND, new TropicalSeasonalForestConfigurator()),
    TROPICAL_RAIN_FOREST(TileGroup.LAND, new TropicalRainForestConfigurator()),
    SUBTROPICAL_DESERT(TileGroup.LAND, new SubtropicalDesertConfigurator()),
    BEACH(TileGroup.LAND, new BeachConfigurator()),
    ICE(TileGroup.LAND, new IceConfigurator()),
    SNOW(TileGroup.LAND, new SnowConfigurator()),
    LAND(TileGroup.LAND, new LandConfigurator()),
    LAND_WATER(TileGroup.WATER, new LandWaterConfigurator()),
    OCEAN(TileGroup.WATER, new OceanConfigurator());


    private final TileGroup group;
    private final Configurator configurator;

    TileType(TileGroup group, Configurator configurator) {
        this.group = group;
        this.configurator = configurator;
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
     * @return The {@link ColorGenerator} of this type
     */
    public Configurator getConfigurator() {
        return this.configurator;
    }
}