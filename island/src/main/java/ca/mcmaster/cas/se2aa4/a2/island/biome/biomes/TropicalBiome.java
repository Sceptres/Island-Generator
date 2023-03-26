package ca.mcmaster.cas.se2aa4.a2.island.biome.biomes;

import ca.mcmaster.cas.se2aa4.a2.island.biome.AbstractBiome;
import ca.mcmaster.cas.se2aa4.a2.island.biome.Rule;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

public class TropicalBiome extends AbstractBiome {
    public TropicalBiome() {
        super();
        super.addRule(new Rule(0, 1, 30, 50, TileType.SUBTROPICAL_DESERT));
        super.addRule(new Rule(1, 50, 30, 50, TileType.BEACH));
        super.addRule(new Rule(50, 200, 30, 50, TileType.TROPICAL_SEASONAL_FOREST));
        super.addRule(new Rule(200, 500, 30, 50, TileType.TROPICAL_RAIN_FOREST));
        super.addRule(new Rule(0, 200, 0, 30, TileType.TROPICAL_SEASONAL_FOREST));
        super.addRule(new Rule(200, 500, 0, 30, TileType.TROPICAL_RAIN_FOREST));
    }
}
