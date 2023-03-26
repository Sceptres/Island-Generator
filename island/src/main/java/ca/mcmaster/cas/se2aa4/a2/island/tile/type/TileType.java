package ca.mcmaster.cas.se2aa4.a2.island.tile.type;

import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.Configurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators.BeachConfigurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators.LandConfigurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators.LandWaterConfigurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.configurators.OceanConfigurator;

public enum TileType {
    BEACH_TILE(TileGroup.LAND, new BeachConfigurator()),
    LAND_TILE(TileGroup.LAND, new LandConfigurator()),
    LAND_WATER_TILE(TileGroup.WATER, new LandWaterConfigurator()),
    OCEAN_TILE(TileGroup.WATER, new OceanConfigurator());


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