package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.List;
import java.util.Random;

public class Lake extends TiledGeography {
    public Lake(Tile start) {
        super(TileType.LAND_WATER_TILE);
        super.addTile(start);
    }

    /**
     *
     * @param random The random instance to use in finding next tile
     * @return The next {@link Tile} of the lake
     */
    public Tile getNextTile(Random random) {
        Tile tile = super.tiles.get(super.tiles.size()-1);
        List<Tile> neighbors = tile.getNeighbors();
        return neighbors.get(random.nextInt(neighbors.size()));
    }

    @Override
    public List<Tile> getNeighbors() {
        return super.tiles.stream()
                .flatMap(t -> t.getNeighbors().stream())
                .filter(t -> t.getType().getGroup() == TileGroup.LAND)
                .toList();
    }
}
