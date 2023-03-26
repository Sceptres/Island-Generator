package ca.mcmaster.cas.se2aa4.a2.island.biome.biomes;

import ca.mcmaster.cas.se2aa4.a2.island.biome.AbstractBiome;
import ca.mcmaster.cas.se2aa4.a2.island.biome.Rule;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

public class TemperateBiome extends AbstractBiome {
    public TemperateBiome() {
        super();
        super.addRule(new Rule(0, 1, 30, 50, TileType.SUBTROPICAL_DESERT));
        super.addRule(new Rule(1, 50, 30, 50, TileType.TEMPERATE_GRASSLAND));
        super.addRule(new Rule(50, 100, 30, 50, TileType.TEMPERATE_DECIDUOUS_FOREST));
        super.addRule(new Rule(100, 500, 30, 50, TileType.TAIGA));
        super.addRule(new Rule(0, 100, 0, 30, TileType.TEMPERATE_RAIN_FOREST));
        super.addRule(new Rule(100, 500, 0, 30, TileType.TAIGA));
    }
}
