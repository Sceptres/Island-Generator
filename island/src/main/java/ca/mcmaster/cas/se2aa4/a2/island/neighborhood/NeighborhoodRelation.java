package ca.mcmaster.cas.se2aa4.a2.island.neighborhood;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;

public interface NeighborhoodRelation {
    void calculateNeighbors(List<Tile> tiles);
}
