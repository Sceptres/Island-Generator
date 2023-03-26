package ca.mcmaster.cas.se2aa4.a2.island.biome;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

public interface Biome {
    /**
     *
     * @param tile Takes a {@link Tile} into the biome and gives it the appropriate tile type
     */
    void takeTile(Tile tile);
}
