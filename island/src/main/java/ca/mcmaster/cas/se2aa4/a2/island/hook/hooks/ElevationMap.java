package ca.mcmaster.cas.se2aa4.a2.island.hook.hooks;

import ca.mcmaster.cas.se2aa4.a2.island.hook.Hook;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.awt.*;
import java.util.List;

public class ElevationMap implements Hook {
    @Override
    public void apply(List<Tile> tiles) {
        tiles.forEach(tile -> {
            if(tile.getType() == TileType.OCEAN) {
                tile.getPolygon().setColor(Color.BLACK);
            } else {
                double humidityRatio = tile.getElevation();
                Color color = this.findColor(humidityRatio);
                tile.getPolygon().setColor(color);

                List<Path> tilePaths = tile.getPaths();
                tilePaths.forEach(p -> {
                    p.setWidth(1f);
                    p.getSegment().setColor(Color.BLACK);
                });
            }
        });
    }

    private Color findColor(double elevationRatio) {
        int red =  (int) (255 - (elevationRatio * 255));
        int green = (int) (255 - (elevationRatio * 255));
        int blue = 255;
        return new Color(red, green, blue);
    }
}