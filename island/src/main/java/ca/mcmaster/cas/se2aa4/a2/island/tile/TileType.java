package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.island.tile.color.TileColorGenerator;

public enum TileType {
    ;
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
