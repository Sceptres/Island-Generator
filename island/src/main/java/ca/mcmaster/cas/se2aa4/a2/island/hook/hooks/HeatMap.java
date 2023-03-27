package ca.mcmaster.cas.se2aa4.a2.island.hook.hooks;

import ca.mcmaster.cas.se2aa4.a2.island.hook.Hook;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;

import java.awt.*;
import java.util.List;

public class HeatMap implements Hook {
    @Override
    public void apply(List<Tile> tiles) {
        tiles.forEach(tile -> {
            if(tile.getType().getGroup() == TileGroup.WATER) {
                tile.getPolygon().setColor(Color.BLACK);
            } else {
                float humidityRatio = tile.getHumidity()/500;
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

    private Color findColor(float humidityRatio) {
        int red =  (int) (255 - (humidityRatio * 255));
        int green = (int) (255 - (humidityRatio * 255));
        int blue = 255;
        return new Color(red, green, blue);
    }
}