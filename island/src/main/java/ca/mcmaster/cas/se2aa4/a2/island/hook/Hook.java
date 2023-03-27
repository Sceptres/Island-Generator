package ca.mcmaster.cas.se2aa4.a2.island.hook;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;

public interface Hook {
    /**
     *
     * @param tiles The list of {@link Tile} to apply the hook to
     */
    void apply(List<Tile> tiles);
}