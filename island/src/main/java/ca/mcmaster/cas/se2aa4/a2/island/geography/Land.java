package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.ArrayList;
import java.util.List;

public class Land extends TiledGeography {
    private final List<Lake> lakes;

    public Land() {
        super(TileType.LAND_TILE);
        this.lakes = new ArrayList<>();
    }

    @Override
    public void addTile(Tile tile) {
        this.addTile(tile, tile.getType());
    }

    /**
     *
     * @param tile The {@link Tile} to add to the land
     * @param type The type of the tile to add
     */
    public void addTile(Tile tile, TileType type) {
        tile.setType(type);
        super.addTile(tile);
    }

    /**
     *
     * @param lake The {@link Lake} to add to the land
     */
    public void addLake(Lake lake) {
        this.lakes.add(lake);
    }

    @Override
    public List<Tile> getNeighbors() {
        return super.tiles.stream()
                .flatMap(t -> t.getNeighbors().stream())
                .filter(t -> t.getType() == TileType.OCEAN_TILE)
                .toList();
    }
}
