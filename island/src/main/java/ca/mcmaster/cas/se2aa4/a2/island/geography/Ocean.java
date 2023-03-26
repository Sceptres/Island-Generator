package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.List;

public class Ocean extends TiledGeography implements IHumidity {
    public Ocean() {
        super(TileType.OCEAN);
    }

    @Override
    public List<Tile> getNeighbors() {
        return super.tiles.stream()
                .flatMap(t -> t.getNeighbors().stream())
                .filter(t -> t.getType().getGroup() == TileGroup.LAND)
                .toList();
    }

    @Override
    public float getHumidity() {
        return -100;
    }

    @Override
    public void setHumidity(float humidity) {}

    @Override
    public void giveHumidity(IHumidity h) {
        float oceanHumidity = this.getHumidity();
        float oldHumidity = h.getHumidity();
        h.setHumidity(oldHumidity + oceanHumidity);
    }
}
