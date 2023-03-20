package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.List;

public class Ocean extends TiledGeography {
    public Ocean() {
        super(TileType.OCEAN_TILE);
    }

    @Override
    public List<Tile> getNeighbors() {
        return List.of();
    }
}
