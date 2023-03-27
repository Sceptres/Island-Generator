package ca.mcmaster.cas.se2aa4.a2.island.hook.hooks;

import ca.mcmaster.cas.se2aa4.a2.island.hook.Hook;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;

public class EmptyHook implements Hook {
    @Override
    public void apply(List<Tile> tiles) {}
}