package ca.mcmaster.cas.se2aa4.a2.island.tile.tiles;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.TileColors;
import ca.mcmaster.cas.se2aa4.a2.island.tile.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;


public class BeachTile extends Tile {
    public BeachTile(Polygon polygon){
        super(polygon);
        super.type = TileType.LAND_TILE;
        super.polygon.setColor(TileColors.BEACH);
    }


}
