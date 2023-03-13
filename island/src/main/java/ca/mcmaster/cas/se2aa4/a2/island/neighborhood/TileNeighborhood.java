package ca.mcmaster.cas.se2aa4.a2.island.neighborhood;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;

public class TileNeighborhood implements NeighborhoodRelation {
    @Override
    public void calculateNeighbors(List<Tile> tiles) {
        for(Tile tile : tiles) {
            List<Tile> neighbors = tiles.stream().filter(tile::isNeighbor).toList();
            tile.addNeighbors(neighbors);
        }
    }
}
